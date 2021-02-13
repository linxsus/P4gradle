package org.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.P4Metier.Factory.Factory;

/**
 * class pour la gestion d'une base test
 *
 * @author Xavier Gouraud
 *
 */
public class TestSql {
	private Connection cn;
	private Statement st;
	private static ResultSet rs = null;
	protected Factory factory;

	/**
	 * passe Mysqlconnexion sur a base de test et la reinitialise
	 */
	public TestSql(Factory factory) {

		this.factory = factory;
		cn = factory.getBaseConnection();
		initBaseTest();

	}

	/**
	 * test si la requette 'sql' renvoie 1 ligne
	 *
	 * @param sql
	 *            requette a analiser
	 *
	 * @return retourn true si la requette a generer 1 ligne faulse sinon
	 *
	 */
	public boolean isOneLigne(String sql) {
		int i = 0;
		try {
			st = cn.prepareStatement(sql);
			rs = st.executeQuery(sql);
			while (rs.next()) {
				i++;
			}
		} catch (SQLException e) {
			if (e.getErrorCode() != 1062) {
				e.printStackTrace();
			}
		}
		if (i == 1) {
			return true;
		}
		return false;
	}

	public void initBaseTest() {
		PreparedStatement statement;
		try {
			statement = cn.prepareStatement("delete from lien");
			statement.execute();
			statement = cn.prepareStatement("delete from neud");
			statement.execute();
		} catch (SQLException e) {
			if (e.getErrorCode() != 1062) {
				e.printStackTrace();
			}
		}
	}
}
