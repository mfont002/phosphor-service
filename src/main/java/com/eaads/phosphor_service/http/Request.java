package com.eaads.phosphor_service.http;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Request implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Request() {	
	}
	
	private String resource;
	private String path;
	private String httpMethod;
	private Map<String, ?> headers;
	private Map<String, List<?>> multiValueHeaders;
	private Map<String, ?> queryStringParameters;
	private Map<String, List<?>>  multiValueQueryStringParameters;
	private Map<String, ?> pathParameters;
	private String body;
	
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getHttpMethod() {
		return httpMethod;
	}
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}
	public Map<String, ?> getHeaders() {
		return headers;
	}
	public void setHeaders(Map<String, ?> headers) {
		this.headers = headers;
	}
	public Map<String, List<?>> getMultiValueHeaders() {
		return multiValueHeaders;
	}
	public void setMultiValueHeaders(Map<String, List<?>> multiValueHeaders) {
		this.multiValueHeaders = multiValueHeaders;
	}
	public Map<String, ?> getQueryStringParameters() {
		return queryStringParameters;
	}
	public void setQueryStringParameters(Map<String, ?> queryStringParameters) {
		this.queryStringParameters = queryStringParameters;
	}
	public Map<String, List<?>> getMultiValueQueryStringParameters() {
		return multiValueQueryStringParameters;
	}
	public void setMultiValueQueryStringParameters(Map<String, List<?>> multiValueQueryStringParameters) {
		this.multiValueQueryStringParameters = multiValueQueryStringParameters;
	}

	public Map<String, ?> getPathParameters() {	
		return pathParameters;
	}
	
	public void setPathParameters(Map<String, ?> pathParameters) {
		this.pathParameters = pathParameters;
	}
		
	
}
