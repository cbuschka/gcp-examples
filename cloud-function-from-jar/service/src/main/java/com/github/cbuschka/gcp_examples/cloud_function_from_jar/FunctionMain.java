package com.github.cbuschka.gcp_examples.cloud_function_from_jar;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;

import java.io.BufferedWriter;
import java.io.IOException;

public class FunctionMain implements HttpFunction
{
	public void service(HttpRequest request, HttpResponse response)
		throws IOException
	{
		response.setContentType("text/plain");
		BufferedWriter writer = response.getWriter();
		writer.write("Hello World!");
	}

}
