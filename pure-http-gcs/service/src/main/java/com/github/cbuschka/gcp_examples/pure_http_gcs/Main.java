package com.github.cbuschka.gcp_examples.pure_http_gcs;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Main
{
	public static void main(String[] args) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException
	{
		String keyfile = args[0];
		String bucket = args[1];
		String object = args[2];

		new Main().dumpObject(keyfile, bucket, object);
	}

	private void dumpObject(String keyfile, String bucket, String object) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException
	{
		ServiceAccountKey serviceAccountKey = ServiceAccountKeyLoader.loadServiceAccountKey(new File(keyfile));

		String accessToken = loginViaOauth(serviceAccountKey);

		byte[] data = Gcs.getObject(bucket, object, accessToken);
		System.err.println(new String(data, StandardCharsets.UTF_8));
	}

	private String loginViaOauth(ServiceAccountKey serviceAccountKey) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException
	{
		String jwt = JwtBuilder.newInstance()
			.withEmail(serviceAccountKey.email)
			.withValidityInSeconds(60)
			.withAudience("https://www.googleapis.com/oauth2/v4/token")
			.withScope("https://www.googleapis.com/auth/cloud-platform")
			.withSigningKey(serviceAccountKey.getPrivateKey())
			.build();
		return OauthLogin.getAccessToken(jwt);
	}
}
