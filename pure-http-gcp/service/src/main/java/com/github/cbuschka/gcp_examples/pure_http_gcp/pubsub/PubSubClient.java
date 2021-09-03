package com.github.cbuschka.gcp_examples.pure_http_gcp.pubsub;

import com.github.cbuschka.gcp_examples.pure_http_gcp.oauth2.Authorization;
import com.github.cbuschka.gcp_examples.pure_http_gcp.util.IOUtils;
import com.github.cbuschka.gcp_examples.pure_http_gcp.util.ObjectMapperHolder;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class PubSubClient
{
	private static final String SUBSCRIPTIONS_ENDPOINT = "https://pubsub.googleapis.com/v1/projects/%s/subscriptions/%s:%s";
	private static final String TOPICS_ENDPOINT = "https://pubsub.googleapis.com/v1/projects/%s/topics?pageSize=%d&pageToken=%s";

	public List<AcknowledgableMessage> pull(String projectId, String subscriptionName, int maxMessages, Authorization authorization) throws IOException
	{
		PullRequest request = new PullRequest(maxMessages);

		String urlStr = String.format(SUBSCRIPTIONS_ENDPOINT, URLEncoder.encode(projectId, StandardCharsets.UTF_8), URLEncoder.encode(subscriptionName, StandardCharsets.UTF_8), "pull");
		return post(authorization, request, PullResponse.class, urlStr).receivedMessages;
	}

	public void acknowledge(String projectId, String subscriptionName, Collection<String> messageIds, Authorization authorization) throws IOException
	{
		AcknowledgeRequest acknowledgeRequest = new AcknowledgeRequest(new HashSet<>(messageIds));

		String url = String.format(SUBSCRIPTIONS_ENDPOINT, URLEncoder.encode(projectId, StandardCharsets.UTF_8), URLEncoder.encode(subscriptionName, StandardCharsets.UTF_8), "acknowledge");
		post(authorization, acknowledgeRequest, String.class, url);
	}

	public ListTopicsResponse listTopics(String projectId, int pageSize, String pageToken, Authorization authorization)
		throws IOException {

		String url = String.format(TOPICS_ENDPOINT, URLEncoder.encode(projectId, StandardCharsets.UTF_8), pageSize, pageToken != null ? pageToken : "");
		return get(authorization, ListTopicsResponse.class, url);
	}

	private <T> T get(Authorization authorization, Class<T> responseType, String urlStr) throws IOException
	{
		URL url = new URL(urlStr);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setAllowUserInteraction(false);
		conn.setDoOutput(false);
		conn.setDoInput(true);
		conn.setInstanceFollowRedirects(false);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Authorization", String.format("Bearer %s", authorization.getAccessToken()));
		conn.setRequestProperty("Accept", "application/json");
		conn.setUseCaches(false);
		conn.connect();
		failIfResponseNotOk(conn);
		try (InputStream in = conn.getInputStream();)
		{
			if (String.class.equals(responseType))
			{
				return (T) IOUtils.readUTF8String(in);
			}
			else if (null == responseType || byte[].class.equals(responseType))
			{
				return (T) IOUtils.readBytes(in);
			}
			else
			{
				return ObjectMapperHolder.objectMapper.readerFor(responseType).readValue(in);
			}
		}
	}


	private <T> T post(Authorization authorization, Object request, Class<T> responseType, String urlStr) throws IOException
	{
		URL url = new URL(urlStr);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setAllowUserInteraction(false);
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setInstanceFollowRedirects(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Charset", "utf-8");
		byte[] postData = ObjectMapperHolder.objectMapper.writeValueAsBytes(request);
		conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
		conn.setRequestProperty("Authorization", String.format("Bearer %s", authorization.getAccessToken()));
		conn.setRequestProperty("Accept", "application/json");
		conn.setUseCaches(false);
		conn.connect();
		try (OutputStream wr = conn.getOutputStream();)
		{
			wr.write(postData);
		}
		failIfResponseNotOk(conn);
		try (InputStream in = conn.getInputStream();)
		{
			if (String.class.equals(responseType))
			{
				return (T) IOUtils.readUTF8String(in);
			}
			else if (null == responseType || byte[].class.equals(responseType))
			{
				return (T) IOUtils.readBytes(in);
			}
			else
			{
				return ObjectMapperHolder.objectMapper.readerFor(responseType).readValue(in);
			}
		}
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

}
