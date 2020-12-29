package com.home.uploader;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.home.mydb.utils.ProcessUtils;

public abstract class AbstractUploader {
	
	public EntityManager getEM() {
		if (ProcessUtils.getEm() == null) {
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("torrentmover");
			ProcessUtils.setEm(factory.createEntityManager());
		}
		return ProcessUtils.getEm();
	}

}
