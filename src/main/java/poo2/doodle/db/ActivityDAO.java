package poo2.doodle.db;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

import poo2.doodle.entities.Activity;

public class ActivityDAO implements InterfaceDAO<Activity> {

	@Override
	public void persist(Activity activity) {
		EntityManager em = UtilDB.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(activity);
			em.getTransaction().commit();
		} catch (EntityExistsException e) {
			System.out.println("Entrou na exception, mas não há o que alterar");
		}
	}

	@Override
	public void remove(Activity activity) {
		EntityManager em = UtilDB.getEntityManager();
		em.getTransaction().begin();
		em.remove(activity);
		em.getTransaction().commit();
	}

	@Override
	public Activity get(Object pk) {
		EntityManager em = UtilDB.getEntityManager();
		Activity activity = em.find(Activity.class, pk);
		return activity;
	}

	@Override
	public List<Activity> getAll() {
		EntityManager em = UtilDB.getEntityManager();
		List<Activity> activity = em.createQuery("SELECT u FROM Activity u", Activity.class).getResultList();
		return activity;
	}

}
