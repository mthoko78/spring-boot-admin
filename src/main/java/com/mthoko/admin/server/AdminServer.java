package com.mthoko.admin.server;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableAdminServer
@EnableConfigurationProperties
public class AdminServer {

	public static void main(String[] args) {
		SpringApplication.run(AdminServer.class, args);
	}

}
