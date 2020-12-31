package com.home.uploader.upload.bl;

import java.util.List;

import com.home.mydb.model.ProcessedFile;
import com.home.uploader.AbstractUploader;

public class FilesBL extends AbstractUploader {

	public List<ProcessedFile> getFiles() {
		return ProcessedFile.getAll(getEM());
	}
	
}
