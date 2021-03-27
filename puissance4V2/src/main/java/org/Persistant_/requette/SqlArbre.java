package org.Persistant_.requette;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import org.P4Metier.Factory.Factory;
import org.P4Modele_.Calculer;
import org.P4Modele_.Neud;
import org.P4Modele_.NeudArbre;
import org.P4Modele_.arbre.Lien;
import org.P4Modele_.arbre.TamponBD;
import org.Persistant_.ExecuteSql.ExecuteSqlDelete;
import org.Persistant_.ExecuteSql.ExecuteSqlDeleteMultiple;
import org.Persistant_.ExecuteSql.ExecuteSqlInsert;
import org.Persistant_.ExecuteSql.ExecuteSqlInsertMultiple;
import org.Persistant_.ExecuteSql.ExecuteSqlPrim;
import org.Persistant_.ExecuteSql.ExecuteSqlSelect;

/**
 * classe pour la gestion des requette sql de (ordinateurArbreDB,NeudDB)
 *
 * @author Xavier Gouraud
 *
 */
public class SqlArbre {

	/**
	 * connexion a la base
	 */
	protected Connection cn;
	/**
	 * object factory
	 */
	protected Factory factory;
	/**
	 * object tampon
	 */
	protected TamponBD tampon;

	/**
	 * creation d'un object SqlArbre
	 */
	public SqlArbre(Factory factory) {
		super();
		this.factory = factory;
		cn = factory.getBaseConnection();
		tampon = factory.getTampon();
	}

	/**
	 * savegarde dans la base du Neud fournis en parametre
	 *
	 * @param neudBD
	 *            neuds a sauvegarder
	 */
	public void saveNeud(Neud neudBD) {
		String sql = "INSERT INTO `neud` (`idneud`, `explorable`,`calculer`,`niveau`,`feuille`) VALUES (?, ?,?,?,?);";
		new ExecuteSqlInsert(cn, sql) {
			@Override
			public void run() throws SQLException {
				st.setLong(1, neudBD.getId());
				st.setBoolean(2, neudBD.isExplorable());
				st.setInt(3, neudBD.getCalculer().getValue());
				st.setInt(4, neudBD.getNiveau());
				st.setBoolean(5, neudBD.isFeuille());
			}
		};
	}

	/**
	 * savegarde dans la base de la Liste des Neud fournis en parametre
	 *
	 * @param neudBD
	 *            neuds a sauvegarder
	 */
	public void saveNeud(Collection<Neud> neuds) {
		String sql = "INSERT INTO `neud` (`idneud`, `explorable`,`calculer`,`niveau`,`feuille`) VALUES (?, ?,?,?,?); ";
		new ExecuteSqlInsertMultiple<Neud>(cn, sql, neuds) {
			@Override
			public void forEach(Neud neud) throws SQLException {
				st.setLong(1, neud.getId());
				st.setBoolean(2, neud.isExplorable());
				st.setInt(3, neud.getCalculer().getValue());
				st.setInt(4, neud.getNiveau());
				st.setBoolean(5, neud.isFeuille());
			}
		};
	}

	/**
	 * modification en lot de neud
	 *
	 * @param neuds
	 *            collection de neud
	 */
	public void editNeud(Collection<Neud> neuds) {
		String sql = "update `neud` SET  `explorable`=?, `calculer`=?,`niveau`=?,`feuille`=? where `idneud`=?; ";
		new ExecuteSqlInsertMultiple<Neud>(cn, sql, neuds) {
			@Override
			public void forEach(Neud neud) throws SQLException {
				st.setBoolean(1, neud.isExplorable());
				st.setInt(2, neud.getCalculer().getValue());
				st.setInt(3, neud.getNiveau());
				st.setBoolean(4, neud.isFeuille());
				st.setLong(5, neud.getId());
			}
		};
	}

	/**
	 * suppresion en lot de neud
	 *
	 * @param neuds
	 *            neud a supprimer
	 */
	public void removeNeud(Collection<Long> neuds) {
		String sql = "delete from `neud` where `idneud`=?;";
		new ExecuteSqlDeleteMultiple<Long>(cn, sql, neuds) {
			@Override
			public void forEach(Long neud) throws SQLException {
				st.setLong(1, neud);
			}
		};
	}

	/**
	 * sauvegarde par lot de lien
	 *
	 * @param lienBD
	 *            collection de lien
	 */
	public void saveLien(Collection<Lien> lienBD) {
		String sql = "INSERT INTO `lien` (`parent`, `enfant`) VALUES (?, ?); ";
		new ExecuteSqlInsertMultiple<Lien>(cn, sql, lienBD) {
			@Override
			public void forEach(Lien entity) throws SQLException {
				st.setLong(1, entity.getParent());
				st.setLong(2, entity.getEnfant());
			}
		};
	}

	/**
	 * supression en lot de lien
	 *
	 * @param liens
	 *            collection de lien a suprimer
	 */
	public void removeLien(Collection<Lien> liens) {
		String sql = "delete from  `lien` where `parent`=? and `enfant`=?; ";
		new ExecuteSqlDeleteMultiple<Lien>(cn, sql, liens) {
			@Override
			public void forEach(Lien lien) throws SQLException {
				st.setLong(1, lien.getParent());
				st.setLong(2, lien.getEnfant());
			}
		};
	}

	/**
	 * recuperation du neud dans la base dont l'id est fournit
	 *
	 * @param id
	 *            id du neud a recuperer
	 * @return neud recuperer de la base
	 */
	public NeudArbre getNeud(Long id) {
		ExecuteSqlPrim<NeudArbre> neud = new ExecuteSqlPrim<>(null);
		String sql = "SELECT `explorable`,`calculer`,`niveau`,`feuille` FROM `neud` WHERE `idneud`=" + id + ";";
		new ExecuteSqlSelect(cn, sql) {
			@Override
			public void forEach(ResultSet rs) throws SQLException {
				boolean explorable = rs.getBoolean(1);
				Calculer calculer = Calculer.GetValue(rs.getInt(2));
				int niveau = rs.getInt(3);
				boolean feuille = rs.getBoolean(4);
				neud.set(factory.getNeudArbreBD(null, null, feuille, explorable, id, calculer, niveau));
				
			}
		};
		//TODO ici pour faire des tests rechargement complet du neud
		NeudArbre result=neud.get();
		if (result!=null) {
		result.getParent();
		result.getEnfant();
		}
		return result;
	}

	/**
	 * recuperation de la liste des parents
	 *
	 * @param id
	 *            id du neud dont on veut les parent
	 * @return parent du neud
	 */
	public Set<Long> getParent(long id) {
		Set<Long> parents = new HashSet<>();
		String sql = "SELECT `parent` FROM `lien` WHERE `enfant`=" + id + ";";
		new ExecuteSqlSelect(cn, sql) {
			@Override
			public void forEach(ResultSet rs) throws SQLException {
				long newParent = rs.getLong(1);
				// if (!tampon.isRemoveLien(newParent, id)) {
				parents.add(newParent);
				// }
			}
		};
		return parents;
	}

	/**
	 * recuperation de la liste des enfants
	 *
	 * @param id
	 *            id du neud dont on veut les enfants
	 * @return enfant du neud
	 */
	public Set<Long> getEnfant(long id) {
		Set<Long> enfants = new HashSet<>();
		String sql = "SELECT `enfant` FROM `lien` WHERE `parent`=" + id + ";";
		new ExecuteSqlSelect(cn, sql) {
			@Override
			public void forEach(ResultSet rs) throws SQLException {
				long newParent = rs.getLong(1);
				// if (!tampon.isRemoveLien(newParent, id)) {
				enfants.add(newParent);
				// }
			}
		};
		return enfants;
	}

	/**
	 * recuperation d'un nombre (nb) de la liste des neud Explorable du niveau
	 * (niveau)
	 *
	 * @param niveau
	 *            niveau a analyser
	 * @param nb
	 *            nombre de neud explorable a recuperer
	 * @param debut
	 *            a partir de quelle enregistrement je lie
	 * @return neud explorable
	 */
	public HashMap<Long, NeudArbre> getExplorable(int niveau, int nb, int debut) {
		HashMap<Long, NeudArbre> result = new LinkedHashMap<>();
		String sql = "SELECT `idneud`, `explorable`,`calculer`,`feuille` FROM `neud` WHERE `explorable`=1 and `niveau`="
				+ niveau + " LIMIT " + debut + "," + nb + ";";
		new ExecuteSqlSelect(cn, sql) {

			@Override
			public void forEach(ResultSet rs) throws SQLException {
				Long idneud = rs.getLong(1);
				boolean explorable = rs.getBoolean(2);
				Calculer calculer = Calculer.GetValue(rs.getInt(3));
				boolean feuille = rs.getBoolean(4);
				NeudArbre neud = factory.getNeudArbreBD(null, null, feuille, explorable, idneud, calculer, niveau);
				result.put(idneud, neud);
			}
		};
		return result;
	}

	/**
	 * recuperation d'un nombre (nb) de la liste des neud Explorable du niveau
	 * (niveau)
	 *
	 * @param niveau
	 *            niveau a analyser
	 * @param nb
	 *            nombre de neud explorable a recuperer
	 * @return neud explorable
	 */

	public HashMap<Long, NeudArbre> getExplorable(int niveau, int nb) {
		return getExplorable(niveau, nb, 0);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "tampon lier " + tampon;
	}

	/**
	 * savegarde dans la table reprise les id fournis en parametre
	 *
	 * @param ids
	 *            ids a sauvegarder
	 */
	public void saveReprise(Collection<Neud> ids) {
		String sql = "INSERT INTO `reprise` (`idreprise`) VALUES (?); ";
		new ExecuteSqlInsertMultiple<Neud>(cn, sql, ids) {
			@Override
			public void forEach(Neud id) throws SQLException {
				st.setLong(1, id.getId());
			}
		};
	}

	/**
	 * recuperation des ids dans la table reprise
	 *
	 * @return ids dans la table
	 */
	public HashSet<Long> getReprise() {
		HashSet<Long> result = new HashSet<>();
		String sql = "SELECT `idreprise` FROM `reprise`;";
		new ExecuteSqlSelect(cn, sql) {

			@Override
			public void forEach(ResultSet rs) throws SQLException {
				result.add(rs.getLong(1));
			}
		};
		return result;
	}

	/**
	 * rend le neuds dont l'id est fournit en parametre non_calculer et explorable.
	 * et suprime ses enfants
	 *
	 * @param id
	 *            id du neud a reinitialiser
	 */
	public void reinitNeud(Long id) {
		String sql = "update `neud` SET  `explorable`=1, `calculer`=4 where `idneud`=?;";
		new ExecuteSqlInsert(cn, sql) {
			@Override
			public void run() throws SQLException {
				st.setLong(1, id);
				removeEnfant(id);
			}
		};
	}

	/**
	 * supression des lien enfant du neud dont on fournis l'id
	 *
	 * @param id
	 *            id du parents dont on veut supprimer les enfants
	 */
	public void removeEnfant(Long id) {
		String sql = "delete from  `lien` where `parent`=" + id + ";";
		new ExecuteSqlDelete(cn, sql);
	}

	/**
	 * vidage de la table reprise
	 */
	public void removeReprise() {
		String sql = "delete from  `reprise`";
		new ExecuteSqlDelete(cn, sql);
	}
}
