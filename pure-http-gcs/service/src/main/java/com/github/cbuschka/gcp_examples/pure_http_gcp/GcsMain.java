package com.github.cbuschka.gcp_examples.pure_http_gcp;

import com.github.cbuschka.gcp_examples.pure_http_gcp.common.Authorization;
import com.github.cbuschka.gcp_examples.pure_http_gcp.common.JwtBuilder;
import com.github.cbuschka.gcp_examples.pure_http_gcp.common.OauthLogin;
import com.github.cbuschka.gcp_examples.pure_http_gcp.common.ServiceAccountKey;
import com.github.cbuschka.gcp_examples.pure_http_gcp.common.ServiceAccountKeyLoader;
import com.github.cbuschka.gcp_examples.pure_http_gcp.gcs.GcsClient;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class GcsMain
{
	public static void main(String[] args) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException
	{
		String keyfile = args[0];
		String bucket = args[1];
		String object = args[2];

		new GcsMain().dumpObject(keyfile, bucket, object);
	}

	private void dumpObject(String keyfile, String bucket, String object) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException
	{
		ServiceAccountKey serviceAccountKey = ServiceAccountKeyLoader.loadServiceAccountKey(new File(keyfile));

		Authorization accessToken = OauthLogin.getAccessToken(serviceAccountKey);

		byte[] data = GcsClient.getObject(bucket, object, accessToken);
		System.err.println(new String(data, StandardCharsets.UTF_8));
	}
}
