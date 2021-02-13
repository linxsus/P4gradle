package org.Persistant_.ExecuteSql;

public class ExecuteSqlPrim<T> {

	private T val;

	public ExecuteSqlPrim(T val) {
		this.val = val;
	}

	public T get() {
		return val;
	}

	public void set(T val) {
		this.val = val;
	}

	@Override
	public String toString() {
		return "" + val;
	}
}
