package org.app.edufun.dto;

public class ResponseDtoLogin {
	
	private Boolean status = false;
	private String message = null;
	private Object data = null;
	private String accessibility = null; //none/viewable/editable/deletable
	
	public void reset() {
		this.status = false;
		this.message = null;
		this.data = null;
		this.accessibility = null;
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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getAccessibility() {
		return accessibility;
	}

	public void setAccessibility(String accessibility) {
		this.accessibility = accessibility;
	}
	
	
}
