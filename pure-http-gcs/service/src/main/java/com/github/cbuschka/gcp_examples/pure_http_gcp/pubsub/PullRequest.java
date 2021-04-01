package com.github.cbuschka.gcp_examples.pure_http_gcp.pubsub;

public class PullRequest
{
	// public boolean returnImmediately = true;
	public int maxMessages = 1;

	public PullRequest(int maxMessages)
	{
		this.maxMessages = maxMessages;
	}
}
