package com.github.cbuschka.gcp_examples.pubsub_to_cloudrun;

import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MessageHandler
{
	@PostMapping("/messages")
	public ResponseEntity<?> handleMessage(@RequestBody byte[] data)
	{
		log.info("Received request {}.", new String(data, StandardCharsets.UTF_8));

		return ResponseEntity.noContent().build();
	}
}
