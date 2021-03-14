package com.github.cbuschka.gcp_examples.pubsub_to_cloudrun;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@RestController
public class MessageHandler
{
	@Autowired
	private ObjectMapper objectMapper;

	@PostMapping(value = "/messages", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> handleMessage(@RequestBody JsonNode json) throws JsonProcessingException
	{
		log.info("Received request {}.", objectMapper.writer().writeValueAsString(json));

		if (json.isObject() && json.has("message") && json.get("message").isObject() && json.get("message").has("data") && json.get("message").get("data").isTextual())
		{
			String dataBase64 = json.get("message").get("data").asText();
			byte[] dataBytes = Base64.getDecoder().decode(dataBase64);
			String dataUtf8String = new String(dataBytes, StandardCharsets.UTF_8);
			log.info("Payload data {}.", dataUtf8String);
		}

		return ResponseEntity.noContent().build();
	}
}
