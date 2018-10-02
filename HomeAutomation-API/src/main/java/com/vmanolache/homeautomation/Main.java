package com.vmanolache.homeautomation;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.vmanolache.homeautomation.api.GetDevicesProcessor;
import com.vmanolache.homeautomation.api.PostDeviceProcessor;
import com.vmanolache.httpserver.api.*;
import com.vmanolache.httpserver.api.constants.HttpMethod;

import lombok.extern.log4j.Log4j2;

/**
 * Created by Vlad Manolache on 2018-09-19.
 */
@Log4j2
public class Main {

	public static void main(String[] args) {
		if (args.length < 2 || args[0].equals("-h") || args[0].equals("-help")) {
			System.out.println("Usage: \njava -jar webserver <documentRoot> <port>\n" +
					"To enable SSL: specify VM options -Djavax.net.ssl.keyStore and -Djavax.net.ssl.keyStorePassword");
			return;
		}

		String documentRoot = args[0];
		int port = Integer.parseInt(args[1]);

		ServerConfig serverConfig = new ServerConfig(documentRoot, port);
        List<RequestProcessor> processorList = new ArrayList<>();
        PathResolver pathResolver = new PathResolver(documentRoot);
        Path path = pathResolver.resolve("/api/storage/devices.json");
        processorList.add(new GetDevicesProcessor(path, HttpMethod.GET));
        processorList.add(new PostDeviceProcessor(path, HttpMethod.POST));
        serverConfig.setProcessorList(processorList);

        HttpServerBuilder httpServerBuilder = new HttpServerBuilder();
		HttpServer server = httpServerBuilder.build(serverConfig);

		try {
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
