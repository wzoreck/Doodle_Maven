package poo2.doodle.db;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

import poo2.doodle.entities.Teacher;

public class TeacherDAO implements InterfaceDAO<Teacher> {

	@Override
	public void persist(Teacher teacher) {
		EntityManager em = UtilDB.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(teacher);
			em.getTransaction().commit();
		} catch (EntityExistsException e) {
			em.getTransaction().rollback();
			Teacher original = get(teacher.getUsername());
			em.getTransaction().begin();
			original.setPassword(teacher.getPassword());
			original.setName(teacher.getName());
			original.setEmail(teacher.getEmail());
			em.getTransaction().commit();
		}
	}

	@Override
	public void remove(Teacher teacher) {
		EntityManager em = UtilDB.getEntityManager();
		em.getTransaction().begin();
		em.remove(teacher);
		em.getTransaction().commit();
	}

	@Override
	public Teacher get(Object pk) {
		EntityManager em = UtilDB.getEntityManager();
		Teacher teacher = em.find(Teacher.class, pk);
		return teacher;
	}

	@Override
	public List<Teacher> getAll() {
		EntityManager em = UtilDB.getEntityManager();
		List<Teacher> teachers = em.createQuery("SELECT u FROM Teacher u", Teacher.class).getResultList();
		return teachers;
	}
}
