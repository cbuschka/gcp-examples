package com.github.cbuschka.gcp_examples.pure_http_gcp.oauth2;

public class Authorization
{
	private final String accessToken;

	private final String jwt;

	public Authorization(String accessToken, String jwt)
	{
		this.accessToken = accessToken;
		this.jwt = jwt;
	}

	public String getAccessToken()
	{
		return accessToken;
	}

	public String getJwt() {
		return jwt;
	}
}
