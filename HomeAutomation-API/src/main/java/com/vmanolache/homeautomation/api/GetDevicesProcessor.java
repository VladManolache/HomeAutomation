package com.vmanolache.homeautomation.api;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.vmanolache.homeautomation.model.Device;
import com.vmanolache.httpserver.api.constants.HttpMethod;
import com.vmanolache.httpserver.api.Request;
import com.vmanolache.httpserver.api.RequestProcessor;
import com.vmanolache.httpserver.api.Response;
import com.vmanolache.httpserver.api.constants.MediaType;
import com.vmanolache.httpserver.api.constants.StatusCode;
import lombok.extern.log4j.Log4j2;

import com.fasterxml.jackson.databind.*;

/**
 * Created by Vlad Manolache on 2018-09-28.
 */
@Log4j2
public class GetDevicesProcessor extends RequestProcessor {

	public GetDevicesProcessor(Path path, HttpMethod method) {
		super(path, method);
	}

	public boolean process(Request request, Response response) {
		final Path file = request.getRequestLine().getPath();
		if (!Files.isRegularFile(file)) {
			return false;
		}

		log.debug("Will process GET request " + request.getRequestLine().getPath());

		try {
			if (!response.isDiscardBody()) {
				byte[] content = Files.readAllBytes(file);
                response.getHeaders().setStatusCode(StatusCode.OK);
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

				if (request.getParams().size() == 0) {
                    response.setBody(content);
                }
                else {
                    List<Device> devices = retrieveDevices(request, content);
                    ObjectMapper mapper = new ObjectMapper();
                    response.setBody(mapper.writeValueAsBytes(devices));
                }
			}
			response.send();

		} catch (IOException e) {
			return false;
		}

		return true;
	}

	private List<Device> retrieveDevices(Request request, byte[] content) throws IOException {
        String input = new String(content, StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        TypeFactory typeFactory = mapper.getTypeFactory();

        List<Device> devices = mapper.readValue(
                input, typeFactory.constructCollectionType(LinkedList.class, Device.class));

        return devices.stream()
                .filter(p -> {
                    String nameFilter = request.getParams().get("name");
                    String typeFilter = request.getParams().get("type");
                    return p.getName().equals(nameFilter) || p.getType().equals(typeFilter);
                }).collect(Collectors.toList());
    }

}
