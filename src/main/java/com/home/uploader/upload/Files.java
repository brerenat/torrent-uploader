package com.home.uploader.upload;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.home.mydb.model.ProcessedFile;
import com.home.mydb.utils.ProcessUtils;

@Path("/Files")
public class Files {
	
	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProcessedFile> getFiles() {
		return ProcessedFile.getAll(ProcessUtils.getEm());
	}
	
}
