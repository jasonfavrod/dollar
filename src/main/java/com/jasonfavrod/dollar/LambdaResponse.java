package com.jasonfavrod.dollar;

public class LambdaResponse {
	private boolean isBase64Encoded;
	private int statusCode;
	private String body;
	
	public LambdaResponse(String res) {
		body = res;
		isBase64Encoded = false;
		statusCode = 200;
	}
	
	public boolean getIsBase64Encoded() {
		return isBase64Encoded;
	}
	
	public void setIsBase64Encoded(boolean bool) {
		isBase64Encoded = bool;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(int code) {
		statusCode = code;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String str) {
		body = str;
	}
	
	@Override
	public String toString() {
		return body;
	}
}
