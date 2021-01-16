package brere.nat.uploader.upload;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import brere.nat.mydb.model.AutoPollSeries;
import brere.nat.uploader.AbstractUploader;
import brere.nat.uploader.model.PagingObject;
import brere.nat.uploader.upload.bl.TrackerBL;

@Path("/Tracker")
public class Tracker extends AbstractUploader {
	
	private static final TrackerBL BL = new TrackerBL();

	@POST
	@Path("/insertNewTrackingSeries")
	@Consumes({MediaType.APPLICATION_JSON})
	public void insertNewTrackingSeries(final AutoPollSeries aps) throws IOException {
		BL.insertNewTrackingSeries(aps);
	}
	
	@GET
	@Path("/getAllActivePolls")
	@Produces({ MediaType.APPLICATION_JSON })
	public PagingObject<AutoPollSeries> getAllActivePolls(@QueryParam("index") final int index, @QueryParam("pageSize") final int pageSize, @QueryParam("search") final String search, @QueryParam("sorting") final String sorting) {
		return new PagingObject<>(BL.countAllActivePolls(search), BL.getAllActivePolls(index, pageSize, search, sorting));
	}
	
	@POST
	@Path("/updateAutoPollSeries")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public AutoPollSeries updateAutoPollSeries(final AutoPollSeries aps) {
		return BL.updateAutoPollSeries(aps);
	}
}
