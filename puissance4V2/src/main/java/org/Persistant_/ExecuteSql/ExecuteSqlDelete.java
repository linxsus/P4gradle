package org.Persistant_.ExecuteSql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExecuteSqlDelete {
	protected PreparedStatement st;
	protected ResultSet rs;
	protected Connection cn;
	protected String sql;

	public ExecuteSqlDelete(Connection cn, String sql) {
		super();
		this.cn = cn;
		this.st = null;
		this.rs = null;
		this.sql = sql;
		runPrivate();
	}

	private void runPrivate() {
		st = null;
		try {
			st = cn.prepareStatement(sql);
			st.executeUpdate();

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
}
