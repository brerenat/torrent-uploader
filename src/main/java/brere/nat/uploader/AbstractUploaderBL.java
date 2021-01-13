package brere.nat.uploader;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import brere.nat.mydb.utils.ProcessUtils;

public abstract class AbstractUploaderBL {

	
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
}
