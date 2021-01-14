package brere.nat.uploader.upload;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import brere.nat.mydb.model.AutoPollSeries;
import brere.nat.uploader.AbstractUploader;
import brere.nat.uploader.upload.bl.TrackerBL;

@Path("/Tracker")
public class Tracker extends AbstractUploader {
	
	private static final TrackerBL BL = new TrackerBL();

	@POST
	@Path("/insertNewTrackingSeries")
	@Consumes({MediaType.APPLICATION_JSON})
	public void insertNewTrackingSeries(final AutoPollSeries aps) {
		BL.insertNewTrackingSeries(aps);
	}
}
