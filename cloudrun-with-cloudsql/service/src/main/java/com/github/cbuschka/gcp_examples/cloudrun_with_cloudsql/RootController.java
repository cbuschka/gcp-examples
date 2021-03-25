package com.github.cbuschka.gcp_examples.cloudrun_with_cloudsql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController
{
	@Autowired
	private MessageRepository messageRepository;

	@GetMapping(path = {"/", "/*"})
	public ResponseEntity<Response> getIndex()
	{
		return ResponseEntity.of(this.messageRepository.findAny().map((e) -> new Response(e.getBody())));
	}


}
