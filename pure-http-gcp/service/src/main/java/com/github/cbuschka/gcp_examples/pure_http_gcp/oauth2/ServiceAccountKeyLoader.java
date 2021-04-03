package com.github.cbuschka.gcp_examples.pure_http_gcp.oauth2;

import com.github.cbuschka.gcp_examples.pure_http_gcp.util.ObjectMapperHolder;

import java.io.File;
import java.io.IOException;

public class ServiceAccountKeyLoader
{
	public static ServiceAccountKey loadServiceAccountKey(File serviceAccountKeyFile) throws IOException
	{
		return ObjectMapperHolder.objectMapper.readerFor(ServiceAccountKey.class).readValue(serviceAccountKeyFile);
	}
}
