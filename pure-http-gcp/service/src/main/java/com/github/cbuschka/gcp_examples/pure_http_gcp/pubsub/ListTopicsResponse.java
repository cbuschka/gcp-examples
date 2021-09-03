package com.github.cbuschka.gcp_examples.pure_http_gcp.pubsub;

import java.util.List;

public class ListTopicsResponse {

	public static class Topic {

		public String name;

		@Override
		public String toString() {
			return "Topic{name=" + name + '}';
		}
	}

	public List<Topic> topics;
	public String nextPageToken;
}
