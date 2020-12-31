package com.home.uploader.model;

public class FileUpload {

	private String name = null;
	private byte[] file = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

}
