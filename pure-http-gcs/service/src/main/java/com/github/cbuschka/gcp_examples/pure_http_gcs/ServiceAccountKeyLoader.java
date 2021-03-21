package com.github.cbuschka.gcp_examples.pure_http_gcs;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ServiceAccountKeyLoader
{
	private static final ObjectMapper objectMapper = new ObjectMapper();

	static {
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

	public static ServiceAccountKey loadServiceAccountKey(File serviceAccountKeyFile) throws IOException
	{
		return objectMapper.readerFor(ServiceAccountKey.class).readValue(serviceAccountKeyFile);
	}
}
