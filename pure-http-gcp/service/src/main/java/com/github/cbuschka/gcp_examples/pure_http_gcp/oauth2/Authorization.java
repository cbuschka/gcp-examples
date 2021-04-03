package com.github.cbuschka.gcp_examples.pure_http_gcp.oauth2;

public class Authorization
{
	private final String accessToken;

	public Authorization(String accessToken)
	{
		this.accessToken = accessToken;
	}

	public String getAccessToken()
	{
		return accessToken;
	}
}
