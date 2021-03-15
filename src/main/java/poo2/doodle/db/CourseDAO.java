package poo2.doodle.db;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

import poo2.doodle.entities.Course;

public class CourseDAO implements InterfaceDAO<Course> {

	@Override
	public void persist(Course course) {
		EntityManager em = UtilDB.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(course);
			em.getTransaction().commit();
		} catch (EntityExistsException e) {
			em.getTransaction().rollback();
			Course original = get(course.getName());
			em.getTransaction().begin();
			original.setDescription(course.getDescription());
			em.getTransaction().commit();
			System.out.println("Commitou");
		}
	}

	@Override
	public void remove(Course course) {
		EntityManager em = UtilDB.getEntityManager();
		em.getTransaction().begin();
		em.remove(course);
		em.getTransaction().commit();
	}

	@Override
	public Course get(Object pk) {
		EntityManager em = UtilDB.getEntityManager();
		Course course = em.find(Course.class, pk);
		return course;
	}

	@Override
	public List<Course> getAll() {
		EntityManager em = UtilDB.getEntityManager();
		List<Course> courses = em.createQuery("SELECT u FROM Course u", Course.class).getResultList();
		return courses;
	}
}
