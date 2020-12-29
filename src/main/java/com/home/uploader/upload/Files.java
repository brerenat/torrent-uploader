package com.home.uploader.upload;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.home.mydb.model.ProcessedFile;
import com.home.uploader.AbstractUploader;

@Path("/Files")
public class Files extends AbstractUploader {

	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProcessedFile> getFiles() {

		try {
			Context initCtx = new InitialContext();
			DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/mydb");
		} catch (NamingException ex) {
			System.out.println("!!!! Got NamingException:");
			ex.printStackTrace(System.out);
		}

		return ProcessedFile.getAll(getEM());
	}

}
