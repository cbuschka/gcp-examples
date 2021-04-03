package com.github.cbuschka.gcp_examples.pure_http_gcp.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class ServiceAccountKey
{
	@JsonProperty("client_email")
	public String email;
	@JsonProperty("private_key")
	public String privateKey;
	@JsonProperty("project_id")
	public String projectId;

	public RSAPrivateKey getPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		String privateKeyPEM = this.privateKey
			.replace("-----BEGIN PRIVATE KEY-----", "")
			.replaceAll(System.lineSeparator(), "")
			.replace("-----END PRIVATE KEY-----", "");

		byte[] decoded = Base64.getDecoder().decode(privateKeyPEM);

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
		return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
	}
}
