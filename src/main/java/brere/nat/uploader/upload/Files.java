package brere.nat.uploader.upload;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import brere.nat.mydb.model.ProcessedFile;
import brere.nat.uploader.AbstractUploader;
import brere.nat.uploader.upload.bl.FilesBL;

@Path("/Files")
public class Files extends AbstractUploader {

	private static final FilesBL BL = new FilesBL();
	
	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProcessedFile> getFiles() {
		return BL.getFiles();
	}

}
