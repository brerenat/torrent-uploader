package com.home.uploader.upload.bl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

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
		
		try {
			Path path = Paths.get(torrentDir + uploadedFile.getName() + ".tmp");
	        
			if (!Files.exists(path)) {
	        	Files.createFile(path);
				LOG.info("File Doesn't Exist");
				
				final Set<PosixFilePermission> perms = Files.readAttributes(path, PosixFileAttributes.class).permissions();
				
		        perms.add(PosixFilePermission.OWNER_WRITE);
		        perms.add(PosixFilePermission.OWNER_READ);
		        perms.add(PosixFilePermission.OWNER_EXECUTE);
		        perms.add(PosixFilePermission.GROUP_WRITE);
		        perms.add(PosixFilePermission.GROUP_READ);
		        perms.add(PosixFilePermission.GROUP_EXECUTE);
		        perms.add(PosixFilePermission.OTHERS_WRITE);
		        perms.add(PosixFilePermission.OTHERS_READ);
		        perms.add(PosixFilePermission.OTHERS_EXECUTE);
		        Files.setPosixFilePermissions(path, perms);
		        
		        Files.write(path, uploadedFile.getFile());
				
		        LOG.info("Finished Writting file :" + path.toAbsolutePath().normalize().toString());
				LOG.info("Finished Saving File");
			} else {
				LOG.error("File " + path.toAbsolutePath().normalize().toString() + " Already exists");
				throw new UploadException("File " + path.toAbsolutePath().normalize().toString() + " Already exists");
			}
		} catch (IOException e) {
			LOG.error("IOException when copying uploaded file to " + torrentDir + uploadedFile.getName() + ".tmp", e);
			throw new UploadException("IOException when copying uploaded file to " + torrentDir + uploadedFile.getName() + ".tmp", e);
		}
	}
	
}
