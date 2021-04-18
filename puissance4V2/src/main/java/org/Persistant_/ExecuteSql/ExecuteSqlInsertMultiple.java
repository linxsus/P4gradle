package org.Persistant_.ExecuteSql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class ExecuteSqlInsertMultiple<T> {

	protected PreparedStatement st;
	protected ResultSet rs;
	protected Connection cn;
	protected String sql;
	protected Collection<T> m;

	public ExecuteSqlInsertMultiple(Connection cn, String sql, Collection<T> m) {
		super();
		this.cn = cn;
		this.st = null;
		this.rs = null;
		this.sql = sql;
		this.m = m;
		runPrivate();
	}

	private void runPrivate() {
		st = null;
		try {
			st = cn.prepareStatement(sql);
			int i = 0;
			for (T t : m) {
				forEach(t);
				st.addBatch();
				if ((i % 1000000) == 0) {
					System.out.println("");
				}
				if ((i % 10000) == 0) {
					st.executeBatch(); // Execute every 1000 items.
					System.out.print("-");
					
				}
				i++;
			}
			
			st.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void forEach(T t) throws SQLException {
	}

}
