package org.app.edufun.dto;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
public class ResponseDto {

	private boolean status;
	private String message;
	private Object body;

	public ResponseDto(boolean status, String message, Object body) {
		super();
		this.status = status;
		this.message = message;
		this.body = body;
	}
	
	

	public ResponseDto() {
		super();
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isStatus() {
		return status;
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
