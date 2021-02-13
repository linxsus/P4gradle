package org.Persistant_.requette;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;

import org.P4Metier.Factory.Factory;
import org.Persistant_.ExecuteSql.ExecuteSqlInsertMultiple;
import org.Persistant_.ExecuteSql.ExecuteSqlPrim;
import org.Persistant_.ExecuteSql.ExecuteSqlSelect;

/**
 * class pour la gestion des requettes sql d'un ordinateurBasic
 *
 * @author Xavier Gouraud
 *
 */
public class SqlOrdinateurBasic {

	/**
	 * connexion a la base
	 */
	private Connection cn;

	/**
	 * l'object factory pour cree de nouveau object
	 */
	protected Factory factory;

	/**
	 * creation d'un object SqlOrdinateurBasic
	 *
	 */
	public SqlOrdinateurBasic(Factory factory) {
		super();
		this.factory = factory;
		cn = factory.getBaseConnection();
	}

	/**
	 *
	 * recuperation du 'param' pour l'id fournit en parametre
	 *
	 * @param id
	 *            id du param que l'on veut recuperer
	 * @return 'param' calculer pour l'id fournit en parametre
	 */
	public Integer getDonnee(Long id) {
		// on declare ici une variable modifiable.
		ExecuteSqlPrim<Integer> resultat = new ExecuteSqlPrim<>(null);
		// requette sql
		String sql = "select `id`, `resultat` from `newdonnee` WHERE `id`='" + id + "';";
		new ExecuteSqlSelect(cn, sql) {
			// pour chaque reponse de la requette
			@Override
			public void forEach(ResultSet rs) throws SQLException {
				// j'affecte la valeur (ici resultat par rebond).
				resultat.set(rs.getInt(2));
			}
		};
		return resultat.get();
	}

	/**
	 * insersion dans la base de la map<Long,Integer> fournis en parametre
	 * l'enregistrement se fait par paquet de 1000
	 *
	 * @param map
	 *            map a inserer en base
	 */
	public void setDonnee(Map<Long, Integer> map) {

		String sql = "INSERT INTO `newdonnee` (`id`, `resultat`) VALUES (?, ?); ";

		new ExecuteSqlInsertMultiple<Entry<Long, Integer>>(cn, sql, map.entrySet()) {
			// pour chaque entrySet du map
			@Override
			public void forEach(Entry<Long, Integer> var) throws SQLException {
				// je renssigne l'id
				st.setLong(1, var.getKey());
				// je renseigne le resultat
				st.setInt(2, var.getValue());
			}
		};
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SqlArbre [cn=" + cn + "]";
	}
}
