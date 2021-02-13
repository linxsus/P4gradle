package org.Persistant_.ExecuteSql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExecuteSqlSelect {

	protected PreparedStatement st;
	protected ResultSet rs;
	protected Connection cn;
	protected String sql;

	public ExecuteSqlSelect(Connection cn, String sql) {
		super();
		this.cn = cn;
		this.st = null;
		this.rs = null;
		this.sql = sql;
		run();
	}

	private void run() {
		st = null;
		rs = null;
		try {
			st = cn.prepareStatement(sql);
			before();
			rs = st.executeQuery();
			while (rs.next()) {
				forEach(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	protected void before() throws SQLException {
	}

	public void forEach(ResultSet rs) throws SQLException {
	}
}
