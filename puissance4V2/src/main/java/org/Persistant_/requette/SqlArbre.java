package org.Persistant_.requette;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.P4Metier.Factory.Factory;
import org.P4Modele_.Calculer;
import org.P4Modele_.Neud;
import org.P4Modele_.NeudArbre;
import org.P4Modele_.arbre.NeudArbreBD;
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
	public void saveNeud(Neud neud) {
		String sql = "INSERT INTO `neud` (`idneud`, `etat`,`calculer`,`niveau`,`enfant1`,`enfant2`,`enfant3`,`enfant4`,`enfant5`,`enfant6`,`enfant7`,`parent1`,`parent2`,`parent3`,`parent4`,`parent5`,`parent6`,`parent7`,`quantum`) VALUES (?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		new ExecuteSqlInsert(cn, sql) {
			@Override
			public void run() throws SQLException {
				st.setLong(1, neud.getId());
				st.setInt(2, neud.etat());
				st.setInt(3, neud.getCalculer().getValue());
				st.setInt(4, neud.getNiveau());
				int i=5;
				for (Long enfant :neud.getEnfant()) {
					st.setLong(i++, enfant);
				}
				for (;i<12;) {
					
					st.setLong(i, 9L);
					i++;
				}
				//`parent1`=?,`parent2`=?,`parent3`=?,`parent4`=?,`parent5`=?,`parent6`=?,`parent7`=?
				for (Long parent :neud.getParent()) {
					st.setLong(i++, parent);
				}
				for (;i<19;) {
					
					st.setLong(i, 9L);
					i++;
				}
				st.setInt(19, neud.getQuantum());	
				((NeudArbreBD) neud).setEnBase();
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
		String sql ="INSERT INTO `neud` (`idneud`, `etat`,`calculer`,`niveau`,`enfant1`,`enfant2`,`enfant3`,`enfant4`,`enfant5`,`enfant6`,`enfant7`,`parent1`,`parent2`,`parent3`,`parent4`,`parent5`,`parent6`,`parent7`,`quantum`) VALUES (?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		new ExecuteSqlInsertMultiple<Neud>(cn, sql, neuds) {
			@Override
			public void forEach(Neud neud) throws SQLException {
				st.setLong(1, neud.getId());
				st.setInt(2, neud.etat());
				st.setInt(3, neud.getCalculer().getValue());
				st.setInt(4, neud.getNiveau());
				int i=5;
				for (Long enfant :neud.getEnfant()) {
					st.setLong(i++, enfant);
				}
				for (;i<12;i++) {
					st.setLong(i, 9L);
				}
				//`parent1`=?,`parent2`=?,`parent3`=?,`parent4`=?,`parent5`=?,`parent6`=?,`parent7`=?
				for (Long parent :neud.getParent()) {
					st.setLong(i++, parent);
				}
				for (;i<19;i++) {			
					st.setLong(i, 9L);
				}
				st.setInt(19, neud.getQuantum());
				((NeudArbreBD) neud).setEnBase();
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
		String sql = "update `neud` SET  `etat`=?, `calculer`=?,`niveau`=?,`enfant1`=?,`enfant2`=?,`enfant3`=?,`enfant4`=?,`enfant5`=?,`enfant6`=?,`enfant7`=?,`parent1`=?,`parent2`=?,`parent3`=?,`parent4`=?,`parent5`=?,`parent6`=?,`parent7`=?,`quantum`=? where `idneud`=?; ";
		new ExecuteSqlInsertMultiple<Neud>(cn, sql, neuds) {
			@Override
			public void forEach(Neud neud) throws SQLException {
				//update `neud` SET  `etat`=?, `calculer`=?,`niveau`=?,
				st.setInt(1, neud.etat());
				st.setInt(2, neud.getCalculer().getValue());
				st.setInt(3, neud.getNiveau());
				//`enfant1`=?,`enfant2`=?,`enfant3`=?,`enfant4`=?,`enfant5`=?,`enfant6`=?,`enfant7`=?
				int i=4;
				for (Long enfant :neud.getEnfant()) {
					st.setLong(i++, enfant);
				}
				for (;i<11;i++) {
					st.setLong(i, 9L);
				}
				//`parent1`=?,`parent2`=?,`parent3`=?,`parent4`=?,`parent5`=?,`parent6`=?,`parent7`=?
				for (Long parent :neud.getParent()) {
					st.setLong(i++, parent);
				}
				for (;i<18;i++) {
					st.setLong(i, 9L);
				}
				st.setInt(18, neud.getQuantum());
				//where `idneud`=?;
				st.setLong(19, neud.getId());
				((NeudArbreBD) neud).setModifier(false);
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

//	/**
//	 * sauvegarde par lot de lien
//	 *
//	 * @param lienBD
//	 *            collection de lien
//	 */
//	public void saveLien(Collection<Lien> lienBD) {
//		String sql = "INSERT INTO `lien` (`parent`, `enfant`) VALUES (?, ?); ";
//		new ExecuteSqlInsertMultiple<Lien>(cn, sql, lienBD) {
//			@Override
//			public void forEach(Lien entity) throws SQLException {
//				st.setLong(1, entity.getParent());
//				st.setLong(2, entity.getEnfant());
//			}
//		};
//	}

//	/**
//	 * supression en lot de lien
//	 *
//	 * @param liens
//	 *            collection de lien a suprimer
//	 */
//	public void removeLien(Collection<Lien> liens) {
//		String sql = "delete from  `lien` where `parent`=? and `enfant`=?; ";
//		new ExecuteSqlDeleteMultiple<Lien>(cn, sql, liens) {
//			@Override
//			public void forEach(Lien lien) throws SQLException {
//				st.setLong(1, lien.getParent());
//				st.setLong(2, lien.getEnfant());
//			}
//		};
//	}

	/**
	 * recuperation du neud dans la base dont l'id est fournit
	 *
	 * @param id
	 *            id du neud a recuperer
	 * @return neud recuperer de la base
	 */
	public NeudArbre getNeud(Long id) {
		ExecuteSqlPrim<NeudArbreBD> neud = new ExecuteSqlPrim<>(null);
		String sql = "SELECT `etat`,`calculer`,`niveau`,`enfant1`,`enfant2`,`enfant3`,`enfant4`,`enfant5`,`enfant6`,`enfant7`,`parent1`,`parent2`,`parent3`,`parent4`,`parent5`,`parent6`,`parent7` FROM `neud` WHERE `idneud`=" + id + ";";
		new ExecuteSqlSelect(cn, sql) {
			@Override
			public void forEach(ResultSet rs) throws SQLException {
				int etat = rs.getInt(1);
				Calculer calculer = Calculer.GetValue(rs.getInt(2));
				int niveau = rs.getInt(3);
				Set<Long> parents = new HashSet<>();
				Set<Long> enfants = new HashSet<>();
				Long idEnfant;
				Long idParent;
				for (int i=0;i<7;i++) {
					idEnfant=rs.getLong(i+4);
					idParent=rs.getLong(i+11);
					if (idEnfant!=9L) {
						enfants.add(idEnfant);
					}
					if (idParent!=9L) {
						parents.add(idParent);
					}
				}
				NeudArbreBD result=(NeudArbreBD) factory.getNeudArbreBD(parents, enfants, etat, id, calculer, niveau);
				result.setEnBase();
				neud.set(result);
				
			}
		};
		//TODO ici pour faire des tests rechargement complet du neud
		return neud.get();
	}

//	/**
//	 * recuperation de la liste des parents
//	 *
//	 * @param id
//	 *            id du neud dont on veut les parent
//	 * @return parent du neud
//	 */
//	public Set<Long> getParent(long id) {
//		Set<Long> parents = new HashSet<>();
//		String sql = "SELECT `parent` FROM `lien` WHERE `enfant`=" + id + ";";
//		new ExecuteSqlSelect(cn, sql) {
//			@Override
//			public void forEach(ResultSet rs) throws SQLException {
//				long newParent = rs.getLong(1);
//				// if (!tampon.isRemoveLien(newParent, id)) {
//				parents.add(newParent);
//				// }
//			}
//		};
//		return parents;
//	}

//	/**
//	 * recuperation de la liste des enfants
//	 *
//	 * @param id
//	 *            id du neud dont on veut les enfants
//	 * @return enfant du neud
//	 */
//	public Set<Long> getEnfant(long id) {
//		Set<Long> enfants = new HashSet<>();
//		String sql = "SELECT `enfant` FROM `lien` WHERE `parent`=" + id + ";";
//		new ExecuteSqlSelect(cn, sql) {
//			@Override
//			public void forEach(ResultSet rs) throws SQLException {
//				long newParent = rs.getLong(1);
//				// if (!tampon.isRemoveLien(newParent, id)) {
//				enfants.add(newParent);
//				// }
//			}
//		};
//		return enfants;
//	}

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
	public Map<Long,NeudArbre> getExplorable(int niveau, int nb, int debut) {
		Map<Long,NeudArbre> result = new HashMap<>();
		String sql = "SELECT `etat`,`calculer`,`niveau`,`enfant1`,`enfant2`,`enfant3`,`enfant4`,`enfant5`,`enfant6`,`enfant7`,`parent1`,`parent2`,`parent3`,`parent4`,`parent5`,`parent6`,`parent7`,`idneud` FROM `neud` WHERE `etat`=3 order by `niveau` DESC LIMIT " + 0 + "," + 2940 + ";";
		
		new ExecuteSqlSelect(cn, sql) {
			@Override
			public void forEach(ResultSet rs) throws SQLException {
				int etat = rs.getInt(1);
				Calculer calculer = Calculer.GetValue(rs.getInt(2));
				int niveau = rs.getInt(3);
				Set<Long> parents = new HashSet<>();
				Set<Long> enfants = new HashSet<>();
				Long idEnfant;
				Long idParent;
				for (int i = 0; i < 7; i++) {
					idEnfant = rs.getLong(i + 4);
					idParent = rs.getLong(i + 11);
					if (idEnfant != 9L) {
						enfants.add(idEnfant);
					}
					if (idParent != 9L) {
						parents.add(idParent);
					}
				}
				long id = rs.getLong(18);
				NeudArbreBD neudTemp = (NeudArbreBD) factory.getNeudArbreBD(parents, enfants, etat, id, calculer,
						niveau);
				neudTemp.setEnBase();
				result.put(id, neudTemp);
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

	public Map<Long,NeudArbre> getExplorable(int niveau, int nb) {
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
	//TODO a refaire completement
	public void reinitNeud(Long id) {
		String sql = "update `neud` SET  `etat`=3, `calculer`=4 where `idneud`=?;";
		new ExecuteSqlInsert(cn, sql) {
			@Override
			public void run() throws SQLException {
				st.setLong(1, id);
				//removeEnfant(id);
			}
		};
	}

//	/**
//	 * supression des lien enfant du neud dont on fournis l'id
//	 *
//	 * @param id
//	 *            id du parents dont on veut supprimer les enfants
//	 */
//	public void removeEnfant(Long id) {
//		String sql = "delete from  `lien` where `parent`=" + id + ";";
//		new ExecuteSqlDelete(cn, sql);
//	}

	/**
	 * vidage de la table reprise
	 */
	public void removeReprise() {
		String sql = "delete from  `reprise`";
		new ExecuteSqlDelete(cn, sql);
	}

	public Collection<Long> getSupprimable(int nb) {
		Collection<Long> result = new HashSet<>();
		String sql = "SELECT `idneud` FROM `neud` WHERE `etat`="+4+" LIMIT 0," + nb + ";";
		new ExecuteSqlSelect(cn, sql) {

			@Override
			public void forEach(ResultSet rs) throws SQLException {
				Long idneud = rs.getLong(1);
				result.add(idneud);
			}
		};
		return result;
	}
	
	public Map<Long,NeudArbre> getExplorable1(int niveau, int nb) {
		Map<Long,NeudArbre> result = new HashMap<>();
		String sql = "SELECT `etat`,`calculer`,`niveau`,`enfant1`,`enfant2`,`enfant3`,`enfant4`,`enfant5`,`enfant6`,`enfant7`,`parent1`,`parent2`,`parent3`,`parent4`,`parent5`,`parent6`,`parent7`,`idneud` FROM `neud` WHERE `etat`="+3+" and `niveau`="
				+ niveau + " LIMIT "  + nb + ";";
		
		new ExecuteSqlSelect(cn, sql) {
			@Override
			public void forEach(ResultSet rs) throws SQLException {
				int etat = rs.getInt(1);
				Calculer calculer = Calculer.GetValue(rs.getInt(2));
				int niveau = rs.getInt(3);
				Set<Long> parents = new HashSet<>();
				Set<Long> enfants = new HashSet<>();
				Long idEnfant;
				Long idParent;
				for (int i = 0; i < 7; i++) {
					idEnfant = rs.getLong(i + 4);
					idParent = rs.getLong(i + 11);
					if (idEnfant != 9L) {
						enfants.add(idEnfant);
					}
					if (idParent != 9L) {
						parents.add(idParent);
					}
				}
				long id = rs.getLong(19);
				NeudArbreBD neudTemp = (NeudArbreBD) factory.getNeudArbreBD(parents, enfants, etat, id, calculer,
						niveau);
				neudTemp.setEnBase();
				result.put(id, neudTemp);
			}
		};

		return result;
	}
}
