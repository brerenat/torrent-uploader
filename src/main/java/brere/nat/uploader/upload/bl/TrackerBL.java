package brere.nat.uploader.upload.bl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import brere.nat.mydb.model.AutoPollSeries;
import brere.nat.uploader.AbstractUploaderBL;

public class TrackerBL extends AbstractUploaderBL {
	
	private static final Logger LOG = LoggerFactory.getLogger(TrackerBL.class);
	
	public void insertNewTrackingSeries(final AutoPollSeries autoPoll) {
		final EntityManager em = getEM();
		final EntityTransaction transaction = em.getTransaction();
		
		transaction.begin();
		em.persist(autoPoll);
		transaction.commit();
		
		LOG.info(new StringBuilder("Started Auto Polling for series :").append(autoPoll.getTitle())
				.append("(").append(autoPoll.getYear()).append(")").toString());
	}

}
