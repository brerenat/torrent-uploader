package brere.nat.uploader;

import java.io.File;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import brere.nat.mydb.model.ReferenceData;
import brere.nat.mydb.utils.ProcessUtils;

public abstract class AbstractUploaderBL {
	
	private static final Logger LOG = LoggerFactory.getLogger(AbstractUploaderBL.class);

	/**
	 * 
	 * @return
	 */
	public EntityManager getEM() {
		if (ProcessUtils.getEmf() == null) {
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("torrentmover");
			ProcessUtils.setEmf(factory);
		}
		return ProcessUtils.getEm();
	}
	
	protected String getReferenceData(final String name) {
		getEM();
		
		final TypedQuery<ReferenceData> findWithName = getEM().createNamedQuery("ReferenceData_findWithName", ReferenceData.class);
		findWithName.setParameter("name", name);
		
		String torrentDir = findWithName.getSingleResult().getValue();
		LOG.info("TorrentDir :" + torrentDir);
		if (!torrentDir.endsWith(File.separator)) {
			torrentDir += File.separator;
		}
		LOG.info("TorrentDir :" + torrentDir);
		return torrentDir;
	}
}
