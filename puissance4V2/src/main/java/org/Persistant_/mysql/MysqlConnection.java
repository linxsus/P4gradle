package org.Persistant_.mysql;

import java.sql.Connection;

import java.sql.SQLException;

import java.util.concurrent.TimeUnit;

public class MysqlConnection {
	private static String url = "jdbc:mariadb://localhost/puissance4?autoReconnect=true&useSSL=false";
	private static String login = "Puissance4";
	private static String passwd = "p4C@llans";
	private static Connection cn = null;

	/**
	 * creation de la connexion a la base de donnee
	 */
	private void init() {
		if (cn == null) {
			try {
				cn = new XGConnection(url,login,passwd);  // Etape 2 : r�cup�ration de la connexion
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					TimeUnit.MINUTES.sleep(1);
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					cn.close();// lib�rer ressources de la m�moire.
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.Persistant_.BaseConnection#cn()
	 */
	public Connection cn() {
		if (cn == null) {
			init();
		}
		return cn;
	}

	/**
	 * !!!!ATENTION DOIT ETRE UTILISER AVANT TOUTE LES AUTRES METHODE DE LA CLASS!!!
	 * initialise la valeur url pour la connexion.
	 */
	public void setUrl(String newUrl) {
		url = newUrl;
		
		try {
			if (cn != null) {
				cn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cn = null;
		init();
	}

	public void refresh() {
		try {
			cn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cn = null;
		init();
	}

}
