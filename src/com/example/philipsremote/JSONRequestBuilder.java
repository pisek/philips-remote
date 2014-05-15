package com.example.philipsremote;

import java.io.UnsupportedEncodingException;
import java.net.URI;

import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;


public class JSONRequestBuilder {
	
	private static final String CONTENT_TYPE = "Content-Type";
	
	public enum HttpMethod {
		DELETE, GET, HEAD, OPTIONS, PATCH, POST, PUT, TRACE
	}
	   
	private HttpMethod method;
	private String uri;
	private String contentType = "application/json";
	private JSONObject jsonObject;
	
	
	public JSONRequestBuilder method(HttpMethod method) {
		this.method = method;
		return this;
	}
	
	public JSONRequestBuilder uri(String uri) {
		this.uri = uri;
		return this;
	}
	
	public JSONRequestBuilder contentType(String contentType) {
		this.contentType = contentType;
		return this;
	}
	
	public JSONRequestBuilder jsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
		return this;
	}

	public HttpRequest build() {
		
		HttpRequestBase request;
		
		switch (method) {
		case GET:
			request = new HttpGet();
			break;
		case POST:
			HttpPost postRequest = new HttpPost();
			
			try {
				postRequest.setEntity(new StringEntity(jsonObject.toString()));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			request = postRequest;
			
			break;

		default:
			throw new NotImplementedException();
		}
		
		request.setURI(URI.create(uri));
		
		request.setHeader(CONTENT_TYPE, contentType);
		
		
		return request;
	}

}
