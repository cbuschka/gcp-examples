package com.github.cbuschka.gcp_examples.pubsub_to_cloudrun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class Main
{
	public static void main(String[] args)
	{
		SpringApplication.run(Main.class, args);
	}
}
