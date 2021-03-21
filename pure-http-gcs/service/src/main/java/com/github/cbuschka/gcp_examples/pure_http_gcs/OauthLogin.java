package com.github.cbuschka.gcp_examples.pure_http_gcs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class OauthLogin
{
	private static final ObjectMapper objectMapper = new ObjectMapper();

	static
	{
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}


	public static String getAccessToken(String jwt) throws IOException
	{
		HttpURLConnection conn = sendPostForToken(jwt);

		failIfResponseNotOk(conn);

		return extractAccessToken(conn);
	}

	private static String extractAccessToken(HttpURLConnection conn) throws IOException
	{
		GetAccessTokenResponse response = objectMapper.readerFor(GetAccessTokenResponse.class).readValue(conn.getInputStream());
		return response.accessToken;
	}

	private static void failIfResponseNotOk(HttpURLConnection conn) throws IOException
	{
		int statusCode = conn.getResponseCode();
		String message = conn.getResponseMessage();
		if (statusCode != 200)
		{
			throw new IOException("Request failed with: " + statusCode + ", " + message);
		}
	}

	private static HttpURLConnection sendPostForToken(String jwt) throws IOException
	{
		URL url = new URL("https://www.googleapis.com/oauth2/v4/token");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setAllowUserInteraction(false);
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setInstanceFollowRedirects(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Charset", "utf-8");
		byte[] postData = String.format("grant_type=%s&assertion=%s", URLEncoder.encode("urn:ietf:params:oauth:grant-type:jwt-bearer", StandardCharsets.UTF_8),
			URLEncoder.encode(jwt, StandardCharsets.UTF_8)).getBytes(StandardCharsets.UTF_8);
		conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
		conn.setRequestProperty("Accept", "application/json");
		conn.setUseCaches(false);
		conn.connect();
		try (OutputStream wr = conn.getOutputStream();)
		{
			wr.write(postData);
		}
		return conn;
	}

	private static class GetAccessTokenResponse
	{
		@JsonProperty("access_token")
		public String accessToken;
	}
}
