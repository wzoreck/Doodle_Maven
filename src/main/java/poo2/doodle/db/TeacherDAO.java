package poo2.doodle.db;

import java.util.List;

import javax.persistence.EntityManager;

import poo2.doodle.entities.Teacher;

public class TeacherDAO implements InterfaceDAO<Teacher> {

	@Override
	public void persist(Teacher teacher) {
		EntityManager em = UtilDB.getEntityManager();
		em.getTransaction().begin();
		System.out.println("Entrou");
		em.persist(teacher);
		System.out.println("Persistiu");
		
		em.getTransaction().commit();

	}

	@Override
	public void remove(Teacher teacher) {
		EntityManager em = UtilDB.getEntityManager();
		em.getTransaction().begin();
		em.remove(teacher);
		em.getTransaction().commit();
	}

	public Teacher get(String username) {
		EntityManager em = UtilDB.getEntityManager();
		Teacher teacher = em.find(Teacher.class, username);
		return teacher;
	}

	@Override
	public List<Teacher> getAll() {
		EntityManager em = UtilDB.getEntityManager();
//		List<Teacher> teacher = em.createNativeQuery("SELECT * FROM Teacher").getResultList();
		return null;
	}

}
