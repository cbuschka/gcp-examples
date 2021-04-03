package com.github.cbuschka.gcp_examples.pure_http_gcp.gcs;

import com.github.cbuschka.gcp_examples.pure_http_gcp.common.Authorization;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GcsClient
{
	public static byte[] getObject(String bucket, String object, Authorization authorization) throws IOException
	{
		HttpsURLConnection conn = sendGetForObject(bucket, object, authorization);

		failIfResponseNotOk(conn);

		return readAllBytes(conn);
	}

	private static HttpsURLConnection sendGetForObject(String bucket, String object, Authorization authorization) throws IOException
	{
		URL url = new URL(String.format("https://storage.googleapis.com/%s/%s", URLEncoder.encode(bucket, StandardCharsets.UTF_8),
			URLEncoder.encode(object, StandardCharsets.UTF_8)));
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestProperty("Authorization", String.format("Bearer %s", authorization.getAccessToken()));
		return conn;
	}

	private static void failIfResponseNotOk(HttpsURLConnection conn) throws IOException
	{
		int statusCode = conn.getResponseCode();
		String message = conn.getResponseMessage();
		if (statusCode != 200)
		{
			throw new IOException("Request failed with: " + statusCode + ", " + message);
		}
	}

	private static byte[] readAllBytes(HttpsURLConnection conn) throws IOException
	{
		ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
		try (InputStream in = conn.getInputStream())
		{
			byte[] buf = new byte[1024];
			int count = -1;
			while ((count = in.read(buf)) != -1)
			{
				bytesOut.write(buf, 0, count);
			}
		}
		return bytesOut.toByteArray();
	}
}
