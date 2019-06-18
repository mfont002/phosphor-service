package com.eaads.phosphor_service.http;

import java.io.Serializable;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Response<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public Response() {	
	}
	
	private int statusCode;
	private Map<String, ?> headers;
	private T body;
	private Boolean isBase64Encoded;


	public Boolean getIsBase64Encoded() {
		return isBase64Encoded;
	}

	public void setIsBase64Encoded(Boolean isBase64Encoded) {
		this.isBase64Encoded = isBase64Encoded;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	public Map<String, ?> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, ?> headers) {
		this.headers = headers;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	@SuppressWarnings("unchecked")
	public void bodyToString() {
		  Gson gson = new GsonBuilder().setPrettyPrinting().create();
		  body = (T) gson.toJson(getBody());	  
		}

}
