package com.github.cbuschka.gcp_examples.pure_http_gcp.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class IOUtils
{
	public static String readUTF8String(InputStream in) throws IOException
	{
		return new String(readBytes(in), StandardCharsets.UTF_8);
	}

	public static byte[] readBytes(InputStream in) throws IOException
	{
		int count = -1;
		byte[] bbuf = new byte[1024];
		ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
		while ((count = in.read(bbuf)) != -1)
		{
			bytesOut.write(bbuf, 0, count);
		}
		return bytesOut.toByteArray();
	}
}
