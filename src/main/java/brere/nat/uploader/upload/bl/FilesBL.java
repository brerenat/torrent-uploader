package brere.nat.uploader.upload.bl;

import java.util.List;

import javax.persistence.TypedQuery;

import brere.nat.mydb.model.ProcessedFile;
import brere.nat.uploader.AbstractUploaderBL;

public class FilesBL extends AbstractUploaderBL {

	public List<ProcessedFile> getFiles() {
		getEM();
		final TypedQuery<ProcessedFile> getAll = getEM().createNamedQuery("ProcessedFile.getAll", ProcessedFile.class);
		
		return getAll.getResultList();
	}
	
}
