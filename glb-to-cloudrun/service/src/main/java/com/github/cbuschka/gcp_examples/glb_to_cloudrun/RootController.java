package com.github.cbuschka.gcp_examples.glb_to_cloudrun;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class RootController
{
	@GetMapping(path = {"/", "/service", "/service/*", "/*"}, produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> getIndex(HttpServletRequest request)
	{
		String html = "<!doctype html>\n" +
			"<html>\n" +
			"<head>\n" +
			"<title>Service root</title>\n" +
			"</head>\n" +
			"<body>\n" +
			"<h1>Service root, path=" + request.getRequestURI() + "</h1>\n" +
			"</body>\n" +
			"</html>\n";
		return ResponseEntity.ok(html);
	}
}
