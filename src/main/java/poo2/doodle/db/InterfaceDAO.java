package poo2.doodle.db;

import java.util.List;

public interface InterfaceDAO<T> {
	
	public void persist(T t);

	public void remove(T t);

	public List<T> getAll();

}
