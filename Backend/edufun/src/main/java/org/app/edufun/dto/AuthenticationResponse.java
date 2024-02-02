package org.app.edufun.dto;

public class AuthenticationResponse {
	
	private String token;
	private Boolean status;
	private String message;
	private Object body = null;
	
	public AuthenticationResponse(String token, Boolean status, String message, Object body) {
		this.token = token;
		this.status = status;
		this.message = message;
		this.body = body;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}
	
	
}
