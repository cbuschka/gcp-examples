package com.github.cbuschka.gcp_examples.pure_http_gcs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.util.Base64;

public class JwtBuilder
{
	private static final SecureRandom secureRandom;

	static
	{
		try
		{
			secureRandom = SecureRandom.getInstanceStrong();
		}
		catch (NoSuchAlgorithmException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	private static final ObjectMapper objectMapper = new ObjectMapper();

	private final Jwt jwt;
	private int validityInSeconds = 60;
	private RSAPrivateKey privateKey;

	public static JwtBuilder newInstance()
	{
		return new JwtBuilder(new Jwt());
	}

	private JwtBuilder(Jwt jwt)
	{
		this.jwt = jwt;
	}

	private static class Jwt
	{
		public String iss;
		public String scope;
		public String aud;
		public Integer exp;
		public Integer nbf;
		public Integer iat;
		public String jti;
	}

	public JwtBuilder withSigningKey(RSAPrivateKey privateKey)
	{
		this.privateKey = privateKey;
		return this;
	}

	public JwtBuilder withEmail(String email)
	{
		this.jwt.iss = email;
		return this;
	}

	public JwtBuilder withScope(String scope)
	{
		this.jwt.scope = scope;
		return this;
	}

	public JwtBuilder withValidityInSeconds(int validityInSeconds)
	{
		this.validityInSeconds = validityInSeconds;
		return this;
	}

	public JwtBuilder withAudience(String aud)
	{
		this.jwt.aud = aud;
		return this;
	}

	public String build()
	{
		try
		{
			String headerJsonBase64Encoded = buildHeader();
			String claimsJsonBase64Encoded = buildClaimsJson();
			String headerWitClaimsBase64encoded = headerJsonBase64Encoded + "." + claimsJsonBase64Encoded;
			String signatureBase64Encoded = signatureFor(headerWitClaimsBase64encoded);
			return headerWitClaimsBase64encoded + "." + signatureBase64Encoded;
		}
		catch (IOException | NoSuchAlgorithmException | InvalidKeyException | SignatureException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	private String buildHeader()
	{
		String prefixJson = "{\"alg\":\"RS256\",\"typ\":\"JWT\"}";
		return toBase64WithoutPadding(prefixJson.getBytes(StandardCharsets.UTF_8));
	}

	private String buildClaimsJson() throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeyException, SignatureException
	{
		jwt.jti = generateNonce();
		int nowInSecondsEpochTime = (int) (System.currentTimeMillis() / 1000);
		jwt.iat = nowInSecondsEpochTime;
		jwt.nbf = nowInSecondsEpochTime;
		jwt.exp = nowInSecondsEpochTime + this.validityInSeconds;
		String claimsJson = objectMapper.writeValueAsString(jwt);
		return toBase64WithoutPadding(claimsJson.getBytes(StandardCharsets.UTF_8));
	}

	private String generateNonce()
	{
		byte[] buf = new byte[16];
		secureRandom.nextBytes(buf);
		BigInteger nonceAsBigInteger = new BigInteger(buf);
		return nonceAsBigInteger.toString(16);
	}

	private String signatureFor(String headerWithClaims) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException
	{
		Signature sig = Signature.getInstance("SHA256WithRSA");
		sig.initSign(this.privateKey);
		sig.update(headerWithClaims.getBytes(StandardCharsets.UTF_8));
		byte[] signatureBytes = sig.sign();
		return toBase64WithoutPadding(signatureBytes);
	}

	private String toBase64WithoutPadding(byte[] bytes)
	{
		String encoded = Base64.getEncoder().encodeToString(bytes);
		while (encoded.endsWith("="))
		{
			encoded = encoded.substring(0, encoded.length() - 1);
		}
		return encoded;
	}
}
