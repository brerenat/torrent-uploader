package brere.nat.uploader.upload.bl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import brere.nat.mydb.model.AutoPollSeries;
import brere.nat.uploader.AbstractUploaderBL;

public class TrackerBL extends AbstractUploaderBL {
	
	private static final Logger LOG = LoggerFactory.getLogger(TrackerBL.class);
	private static final String ILLEGAL = "(<)|(>)|(:)|(\\\")|(\\/)|(\\|)|(\\?)|(\\*)";
	private static final String AMP = "&";
	
	public void insertNewTrackingSeries(final AutoPollSeries autoPoll) throws IOException {
		final EntityManager em = getEM();
		final EntityTransaction transaction = em.getTransaction();
		
		final String seriesDir = getReferenceData("Series Dir");
		
		final String folderName = autoPoll.getTitle().replaceAll(ILLEGAL, "").replaceAll(AMP, "and");
		
		
		LOG.info("Series Dir :" + seriesDir);
		
		final StringBuilder builder = new StringBuilder(seriesDir);
		if (!seriesDir.endsWith(File.separator)) {
			builder.append(File.separator);
		}
		builder.append(folderName);
		
		autoPoll.setFolderName(builder.toString());
		
		transaction.begin();
		em.flush();
		em.persist(autoPoll);
		transaction.commit();
		em.close();
		
		LOG.info(new StringBuilder("Started Auto Polling for series :").append(autoPoll.getTitle())
				.append("(").append(autoPoll.getYear()).append(")").toString());
	}
	
	public List<AutoPollSeries> getAllActivePolls(final int index, final int pageSize, final String search, final String sorting) {
		final EntityManager em = getEM();
		final EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.flush();
		final TypedQuery<AutoPollSeries> query;
		if (search != null) {
			if (sorting != null) {
				query = em.createNamedQuery("AutoPollSeries_getAllSearchSorted", AutoPollSeries.class);
			} else {
				query = em.createNamedQuery("AutoPollSeries_getAllSearch", AutoPollSeries.class);
			}
			query.setParameter("title", "%" + search + "%");
		} else {
			if (sorting != null) {
				query = em.createNamedQuery("AutoPollSeries_getAllSorted", AutoPollSeries.class);
			} else {
				query = em.createNamedQuery("AutoPollSeries_getAll", AutoPollSeries.class);
			}
		}
		
		query.setFirstResult(index);
		query.setMaxResults(pageSize);
		
		List<AutoPollSeries> resultList = query.getResultList();
		transaction.commit();
		em.close();
		return resultList;
	}

	public long countAllActivePolls(final String search) {
		final EntityManager em = getEM();
		final EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.flush();
		final TypedQuery<Long> query;
		if (search != null) {
			query = em.createNamedQuery("AutoPollSeries_countSearch", Long.class);
			query.setParameter("title", "%" + search + "%");
		} else {
			query = em.createNamedQuery("AutoPollSeries_countAll", Long.class);
		}
		
		Long singleResult = query.getSingleResult();
		transaction.commit();
		em.close();
		return singleResult;
	}
	
	public AutoPollSeries updateAutoPollSeries(final AutoPollSeries aps) {
		final EntityManager em = getEM();
		final EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.flush();
		final TypedQuery<AutoPollSeries> query = em.createNamedQuery("AutoPollSeries_getByID", AutoPollSeries.class);
		query.setParameter("id", aps.getId());
		
		final AutoPollSeries item = query.getSingleResult();
		item.setActive(aps.isActive());
		
		transaction.commit();
		em.close();
		return item;
	}
}
