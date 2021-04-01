package com.github.cbuschka.gcp_examples.pure_http_gcp.pubsub;

import java.util.Set;

public class AcknowledgeRequest
{
	public Set<String> ackIds;

	public AcknowledgeRequest(Set<String> ackIds)
	{
		this.ackIds = ackIds;
	}
}
