package com.home.uploader.upload.bl;

public class UploadException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3641869764509564046L;
	
	public UploadException(String message) {
		super(message);
	}
	
	public UploadException(String message, Throwable cause) {
		super(message, cause);
	}

}
