package brere.nat.uploader.upload.bl;

import java.util.List;

import brere.nat.mydb.model.ProcessedFile;
import brere.nat.uploader.AbstractUploaderBL;

public class FilesBL extends AbstractUploaderBL {

	public List<ProcessedFile> getFiles() {
		getEM();
		return ProcessedFile.Queries.getAll();
	}
	
}
