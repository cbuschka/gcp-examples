package com.github.cbuschka.gcp_examples.pure_http_gcp.pubsub;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.Base64;

public class Message
{
	public String messageId;
	public OffsetDateTime publishTime;
	public String data;

	public String getBase64DecodedData()
	{
		return new String(Base64.getDecoder().decode(this.data), StandardCharsets.UTF_8);
	}
}
