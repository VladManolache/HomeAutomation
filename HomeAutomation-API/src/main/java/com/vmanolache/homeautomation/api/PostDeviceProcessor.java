package com.vmanolache.homeautomation.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.vmanolache.homeautomation.model.Device;
import com.vmanolache.httpserver.api.Request;
import com.vmanolache.httpserver.api.RequestProcessor;
import com.vmanolache.httpserver.api.Response;
import com.vmanolache.httpserver.api.constants.HttpMethod;
import com.vmanolache.httpserver.api.constants.StatusCode;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Log4j2
public class PostDeviceProcessor extends RequestProcessor {

    public PostDeviceProcessor(Path path, HttpMethod method) {
    super(path, method);
  }

    @Override
    public boolean process(Request request, Response response) {
        if (request.getBody() == null) {
            return false;
        }

        log.debug("Will POST file at path " + request.getRequestLine().getPath());

      try {
          final Path path = request.getRequestLine().getPath();
          byte[] content = Files.readAllBytes(path);

          String input = new String(request.getBody(), StandardCharsets.UTF_8);
          ObjectMapper mapper = new ObjectMapper();
          TypeFactory typeFactory = mapper.getTypeFactory();
          Set<Device> devices = mapper.readValue(input, typeFactory.constructCollectionType(Set.class, Device.class));
          Set<Device> storedDevices = mapper.readValue(content, typeFactory.constructCollectionType(LinkedHashSet.class, Device.class));

          updateStoredDevicesList(storedDevices, devices);

          File file = new File(request.getRequestLine().getPath().toString());
          byte[] result = mapper.writeValueAsBytes(storedDevices);
          boolean success = writeToFileWithLock(file, result, response);
          if (success) {
              response.getHeaders().setStatusCode(StatusCode.OK);
          }
          response.send();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean writeToFileWithLock(File file, byte[] body, Response response) {
        ByteBuffer buffer;

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
             FileChannel fileChannel = randomAccessFile.getChannel();
             FileLock fileLock = fileChannel.tryLock()) {

          if (fileLock == null) {
                response.getHeaders().setStatusCode(StatusCode.CONFLICT);
            } else {
                randomAccessFile.setLength(0);
                buffer = ByteBuffer.wrap(body);
                fileChannel.write(buffer);
                response.getHeaders().setStatusCode(StatusCode.OK);
                response.getHeaders().setConnection("close");
            }

          } catch (OverlappingFileLockException e) {
              response.getHeaders().setStatusCode(StatusCode.CONFLICT);
              return false;
          } catch (IOException e) {
              response.getHeaders().setStatusCode(StatusCode.BAD_REQUEST);
              return false;
          }
          return true;
    }

    private void updateStoredDevicesList(Set<Device> storedDevices, Set<Device> devices) {
        Set<Device> newDevices = new HashSet<>();
        for (Device device : devices) {
            boolean found = false;
            for (Device storedRecord : storedDevices) {
                if (device.getId().equals(storedRecord.getId())) {
                    storedRecord.setId(device.getId());
                    storedRecord.setName(device.getName());
                    storedRecord.setType(device.getType());
                    storedRecord.setState(device.getState());
                    found = true;
                }
                if (!found) {
                    newDevices.add(device);
                }
            }
        }
        storedDevices.addAll(newDevices);
    }

}

