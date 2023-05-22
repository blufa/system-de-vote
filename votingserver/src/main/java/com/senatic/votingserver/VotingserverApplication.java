package com.senatic.votingserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@EnableDiscoveryClient
@SpringBootApplication
public class VotingserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(VotingserverApplication.class, args);
	}

}
