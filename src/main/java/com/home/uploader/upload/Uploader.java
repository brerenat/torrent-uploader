package com.home.uploader.upload;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.home.uploader.AbstractUploader;
import com.home.uploader.model.FileUpload;
import com.home.uploader.upload.bl.UploadException;
import com.home.uploader.upload.bl.UploaderBL;

@Path("/Uploader")
public class Uploader extends AbstractUploader {
	
	private static final UploaderBL BL = new UploaderBL();
	
	private static final Logger LOG = LoggerFactory.getLogger(Uploader.class);
	
	@POST
	@Path("/uploadTorrentFile")
	public void uploadTorrentFile() throws UploadException {
		LOG.info("Starting Upload");
		ServletFileUpload.isMultipartContent(request);
		ServletFileUpload upload = getFileUploader();
		try {
			final List<FileItem> files = upload.parseRequest(request);
			final FileUpload uploadedFile = getObjectFromUpload(files, FileUpload.class);
			BL.saveTorrentFile(uploadedFile);
			
		} catch (FileUploadException e) {
			LOG.error("FileUploadException when Parsing request", e);
			throw new UploadException("FileUploadException when Parsing request", e);
		}
	}
	
}
