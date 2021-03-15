package poo2.doodle.db;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

import poo2.doodle.entities.Content;

public class ContentDAO implements InterfaceDAO<Content> {

	@Override
	public void persist(Content content) {
		EntityManager em = UtilDB.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(content);
			em.getTransaction().commit();
		} catch (EntityExistsException e) {
			em.getTransaction().rollback();
			Content original = get(content.getName());
			em.getTransaction().begin();
			original.setNota(content.getNota());
			em.getTransaction().commit();
		}
	}

	@Override
	public void remove(Content content) {
		EntityManager em = UtilDB.getEntityManager();
		em.getTransaction().begin();
		em.remove(content);
		em.getTransaction().commit();
	}

	@Override
	public Content get(Object pk) {
		EntityManager em = UtilDB.getEntityManager();
		Content content = em.find(Content.class, pk);
		return content;
	}

	@Override
	public List<Content> getAll() {
		EntityManager em = UtilDB.getEntityManager();
		List<Content> content = em.createQuery("SELECT u FROM Content u", Content.class).getResultList();
		return content;
	}

}
