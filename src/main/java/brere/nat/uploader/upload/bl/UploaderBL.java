package brere.nat.uploader.upload.bl;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import brere.nat.uploader.AbstractUploaderBL;
import brere.nat.uploader.model.FileUpload;

public class UploaderBL extends AbstractUploaderBL {
	
	private static final Logger LOG = LoggerFactory.getLogger(UploaderBL.class);

	/**
	 * 
	 * @param uploadedFile
	 * @throws UploadException
	 */
	public void saveTorrentFile(final FileUpload uploadedFile) throws UploadException {
		
		LOG.info("Name :" + uploadedFile.getName());
		LOG.info("File Size :" + uploadedFile.getFile().length);
		
		final String torrentDir = getReferenceData("Torrent Dir");
		
		try {
			Path path = Paths.get(torrentDir, uploadedFile.getName() + ".tmp");
	        
			if (!Files.exists(path)) {
	        	Files.createFile(path);
				LOG.info("File Doesn't Exist");
				
				if (FileSystems.getDefault().supportedFileAttributeViews().contains("posix")) {
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
				}
				
		        Files.write(path, uploadedFile.getFile());
		        
		        Path newPath = Paths.get(torrentDir + uploadedFile.getName());
		        
		        Files.move(path, newPath);
				
		        LOG.info("Finished Writting file :" + newPath.toAbsolutePath().normalize().toString());
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
