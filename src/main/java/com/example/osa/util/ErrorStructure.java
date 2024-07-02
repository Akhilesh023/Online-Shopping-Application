package com.example.osa.util;

public class ErrorStructure {
	
	private int status;
	private String message;
	private String rootcause;
	public int getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public String getRootcause() {
		return rootcause;
	}
	public ErrorStructure setStatus(int status) {
		this.status = status;
		return this;
	}
	public ErrorStructure setMessage(String message) {
		this.message = message;
		return this;
	}
	public ErrorStructure setRootcause(String rootcause) {
		this.rootcause = rootcause;
		return this;
	}

}
