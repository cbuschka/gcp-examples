package com.github.cbuschka.gcp_examples.gcp_to_cloudrun;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Controller
public class RootController
{
	@GetMapping(value = {"/", "/*"})
	public String getIndex()
	{
		return "forward:/index.html";
	}
}
