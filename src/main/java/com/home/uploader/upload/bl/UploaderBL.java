package com.home.uploader.upload.bl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.home.mydb.model.ReferenceData;
import com.home.uploader.AbstractUploader;
import com.home.uploader.model.FileUpload;

public class UploaderBL extends AbstractUploader {
	
	private static final Logger LOG = LoggerFactory.getLogger(UploaderBL.class);

	public void saveTorrentFile(final FileUpload uploadedFile) throws UploadException {
		
		LOG.info("Name :" + uploadedFile.getName());
		LOG.info("File Size :" + uploadedFile.getFile().length);
		
		String torrentDir = ReferenceData.findWithName(getEM(), "Torrent Dir").getValue();
		LOG.info("TorrentDir :" + torrentDir);
		if (!torrentDir.endsWith(File.separator)) {
			torrentDir += File.separator;
		}
		LOG.info("TorrentDir :" + torrentDir);
		final File file = new File(torrentDir + uploadedFile.getName() + ".tmp");
		if (!file.exists()) {
			LOG.info("File Doesn't Exist");
			
			final ByteArrayInputStream bis = new ByteArrayInputStream(uploadedFile.getFile());
			
			try {
				IOUtils.copy(bis, new FileOutputStream(file));
				
				file.renameTo(new File(torrentDir + uploadedFile.getName()));
				
				file.setExecutable(true, false);
				file.setWritable(true, false);
				file.setReadable(true, false);
				LOG.info(file.getAbsolutePath());
				LOG.info("Finished Saving File");
			} catch (IOException e) {
				LOG.error("IOException when copying uploaded file to " + file.getAbsolutePath(), e);
				throw new UploadException("IOException when copying uploaded file to " + file.getAbsolutePath(), e);
			}
		} else {
			LOG.error("File " + file.getAbsolutePath() + " Already exists");
			throw new UploadException("File " + file.getAbsolutePath() + " Already exists");
		}
	}
	
}
