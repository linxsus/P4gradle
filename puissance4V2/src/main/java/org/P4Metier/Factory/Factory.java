package org.P4Metier.Factory;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.P4Metier.Gagnee;
import org.P4Metier.GestIdDonnee;
import org.P4Metier.Ordinateur;
import org.P4Metier.arbre.SynchronizationBD;
import org.P4Metier.gagnee.GagneeTabByte;
import org.P4Metier.gagnee.GagneeTabInt;
import org.P4Metier.id.GestIdDonneeInt3;
import org.P4Metier.id.GestIdDonneeLong;
import org.P4Metier.id.GestIdDonneeLongTabByte;
import org.P4Metier.ordi.OrdinateurArbre;
import org.P4Metier.ordi.OrdinateurArbreBD;
import org.P4Metier.ordi.OrdinateurBasic;
import org.P4Modele_.Arbre;
import org.P4Modele_.Calculer;
import org.P4Modele_.GestDonnee;
import org.P4Modele_.MapArbre;
import org.P4Modele_.Neud;
import org.P4Modele_.NeudArbre;
import org.P4Modele_.arbre.ArbreBasic;
import org.P4Modele_.arbre.CopyTampon;
import org.P4Modele_.arbre.NeudArbreBD;
import org.P4Modele_.arbre.NeudArbreBasic;
import org.P4Modele_.arbre.TamponBD;
import org.P4Modele_.jeux.GestDonneeTabByte;
import org.P4Modele_.jeux.GestDonneeTabInt;
import org.P4Modele_.map.MapArbreBD;
import org.P4Modele_.map.MapArbreBasic;
import org.Persistant_.mysql.MysqlConnection;
import org.Persistant_.requette.SqlArbre;
import org.test.TestSql;

public class Factory {

	/**
	 * utilise t'on une base de donnee (remarque ne fonctionne qu'avec un id Long)
	 */
	private boolean BD = true;
	/**
	 * on utilise un id de type Long? sinon c'est int[3]
	 */
	private boolean idLong = true;
	/**
	 * le systeme de stoquage du jeux est de type tabByte? (plus rapide pour pas mal
	 * de chose mais ne permet pas de changer les dimention du tableau) ou de type
	 * tab de int[][]
	 */
	private boolean tabByte = true;

	/**
	 * la moteur pour calculer si gagner est de type TabByte?( ne fonctionne que sur
	 * system de stoquage tabByte) sinon on utilise le moteur de type tabInt.
	 * remarque le moteur GagneeTabInt fonctionne avec un system de stoquage tabByte
	 */
	private boolean gagneeTabByte = true;

	/**
	 * type d'ordinateur voir enum TypeOrdinateur
	 */
	protected TypeOrdinateur typeOrdinateur;

	/**
	 * cree un factory parametrer
	 *
	 * @param bD
	 *            utilisation d'une base de donnee ou non
	 * @param idLong
	 *            on utilise un id long ou non
	 * @param tabByte
	 *            on utilise un tabByte pour le stockage des donnee
	 * @param gagneeTabByte
	 *            on utilise un gagnee de type tabByte on pas.
	 */
	public Factory(boolean bD, boolean idLong, boolean tabByte, boolean gagneeTabByte) {
		super();
		BD = bD;
		this.idLong = idLong;
		this.tabByte = tabByte;
		this.gagneeTabByte = gagneeTabByte;
		factory=this;
	}

	/**
	 * a lancer au debut configure les type d'object a instancier pour l'ordinateur
	 *
	 * @param typeOrdinateur
	 */
	public void setOrdinateur(TypeOrdinateur typeOrdinateur) {
		this.typeOrdinateur = typeOrdinateur;
		if (typeOrdinateur == TypeOrdinateur.Arbre) {
			idLong = true;
			BD = false;
		}
		if (typeOrdinateur == TypeOrdinateur.ArbreBD) {
			idLong = true;
			BD = true;
		}
		if (typeOrdinateur == TypeOrdinateur.Basic) {
			idLong = true;
		}
	}

	/**
	 * il ne doit y avoir que un object sqlArbre par factory
	 */
	protected SqlArbre sqlArbre = null;

	/**
	 * retourne l'object SqlArbre du factory il ne doit y avoir que un object
	 * sqlArbre par factory
	 *
	 * @return
	 */
	public SqlArbre getMysqlArbre() {
		if (sqlArbre == null) {
			sqlArbre = new SqlArbre(this);
		}
		return sqlArbre;
	}

	/**
	 * creation d'un object de type GestBaseDonnee
	 *
	 * @return
	 */
	public GestDonnee getGestBaseDonnee() {
		if (tabByte) {
			return new GestDonneeTabByte(this);
		} else {
			return new GestDonneeTabInt(this);
		}
	}

	/**
	 * creation d'un object de type GestIDDonnee
	 *
	 * @return
	 */
	public GestIdDonnee<?> getGestIDDonnee() {
		if (BD || idLong) {
			if (tabByte) {
				return new GestIdDonneeLongTabByte(this);
			} else {
				return new GestIdDonneeLong(this);
			}

		}
		return new GestIdDonneeInt3(this);
	}

	/**
	 * creation d'un object de type Gagnee
	 *
	 * @return
	 */
	public Gagnee getGagnee() {
		if (tabByte && gagneeTabByte) {
			return new GagneeTabByte(this);
		} else {
			return new GagneeTabInt(this);
		}
	}

	/**
	 * * creation d'un object de type Arbre
	 *
	 * @return
	 */
	public Arbre getArbre() {
		return new ArbreBasic(this);
	}

	/**
	 * creation d'un object de type NeudArbre(Long, Neud)
	 *
	 * @param l1
	 *            (Long)id du neud cree
	 * @param n1
	 *            (Neud) parent du neud cree
	 * @return
	 */
	public NeudArbre getNeudArbre(Long l1, Neud n1) {
		if (BD) {
			return new NeudArbreBD(this, l1, n1);
		} else {
			return new NeudArbreBasic(this, l1, n1);
		}
	}

	/**
	 * creation d'un object de type NeudArbre(Long)
	 *
	 * @param l1
	 *            (Long)id du neud cree
	 * @return
	 */
	public NeudArbre getNeudArbre(Long l1) {
		if (BD) {
			return new NeudArbreBD(this, l1);
		} else {
			return new NeudArbreBasic(this, l1);
		}
	}

	/**
	 * creation d'un object de type Arbre NeudArbre(Long,int)
	 *
	 * @param l1
	 *            (Long)id du neud tron
	 * @param i1
	 *            (int) niveau
	 * @return
	 */
	public NeudArbre getNeudArbre(Long l1, int i1) {
		if (BD) {
			return new NeudArbreBD(this, l1, i1);
		} else {
			return new NeudArbreBasic(this, l1, i1);
		}
	}

	/**
	 * creation d'un object de type Arbre NeudArbre
	 *
	 * @return
	 */
	public NeudArbre getNeudArbre() {
		if (BD) {
			return new NeudArbreBD(this);
		} else {
			return new NeudArbreBasic(this);
		}
	}

	/**
	 * creation d'un neud deja parametrer !!! utilisable que dans le cas de BD!!!
	 *
	 * @param parent
	 * @param enfant
	 * @param feuille
	 * @param explorable
	 * @param id
	 * @param calculer
	 * @param niveau
	 * @return
	 */
	public NeudArbre getNeudArbreBD(Set<Long> parent, Set<Long> enfant, boolean feuille, boolean explorable, long id,
			Calculer calculer, int niveau) {
		if (BD) {
			return new NeudArbreBD(this, parent, enfant, feuille, explorable, id, calculer, niveau);
		}
		return null;
	}

	/**
	 * creation d'un MapArbre pour ordinateurArbre
	 *
	 * @return
	 */
	public MapArbre getMapArbre() {
		if (BD) {
			return new MapArbreBD(this);
		}
		return new MapArbreBasic();
	}

	/**
	 * creation d'un joueur ordinateur.
	 *
	 * @param donnee
	 * @return
	 */
	public Ordinateur<Long> getOrdinateur(GestIdDonnee<Long> donnee) {
		if (typeOrdinateur == TypeOrdinateur.Basic) {
			return new OrdinateurBasic(this, donnee);
		}

		if (BD) {
			return new OrdinateurArbreBD(this, donnee);
		}
		return new OrdinateurArbre(this, donnee);
	}

	/**
	 * il n'y a qu'un seul object mysqlconnexion par factory
	 */
	protected MysqlConnection mysqlconnexion = null;

	/**
	 * retourn l'object MysqlConnection du factory il n'y a qu'un seul object
	 * mysqlconnexion par factory
	 *
	 * @return
	 */
	public MysqlConnection getMysqlConnection() {
		mysqlconnexion = new MysqlConnection();
		return mysqlconnexion;
	}

	/**
	 * retourne l'object Connection(java.sql.Connection) du factory.
	 *
	 * @return
	 */
	public Connection getBaseConnection() {
		if (mysqlconnexion == null) {
			mysqlconnexion = new MysqlConnection();
		}
		return mysqlconnexion.cn();
	}

	/**
	 * il n'y a qu'un seul object tampon par factory
	 */
	protected TamponBD tampon = null;

	/**
	 * retourn l'object Tampon du factory il n'y a qu'un seul object tampon par
	 * factory
	 *
	 * @return
	 */
	public TamponBD getTampon() {
		if (tampon == null) {
			tampon = new TamponBD();
		}
		return tampon;
	}

	/**
	 * retourn un object TestSql object destiner au test (init de la base sur une
	 * base test vierge)
	 *
	 * @return
	 */
	public TestSql getTestSql() {
		mysqlconnexion = new MysqlConnection();
		mysqlconnexion.setUrl("jdbc:mysql://localhost/puissancetest?autoReconnect=true&useSSL=false");
		return new TestSql(this);
	}

	/**
	 * il n'y a qu'un seul object tampon par factory
	 */
	protected SynchronizationBD synchronizationBD = null;
	
	/**
	 * creation d'u object de synchronisation entre la base et le mapArbre
	 *
	 * @return
	 */
	public SynchronizationBD getSynchronizationBD() {
		if (synchronizationBD == null) {
			synchronizationBD = new SynchronizationBD(this);
		}
		return synchronizationBD;
	}

	// TODO a ameliorer car il devrait renvoyer soit map soit
	// MapOrdiBasic
	/**
	 * creation d'un mapDonneeOrdinteur
	 *
	 * @return
	 */
	public Map<Long, Integer> getMapDonneeOrdinateur() {
		return new HashMap<>();
	}

	/**
	 * cree un object copyTampon lors de la creation. Cette object remet a 0 le
	 * tampon du factory et memorise le tampon.
	 *
	 * @return
	 */
	public CopyTampon getCopyTampon() {
		return new CopyTampon(tampon);
	}
	
	static private Factory factory;

	/**
	 * @return the factory
	 */
	static public Factory getFactory() {
		return factory;
	}
	
	static public void setFactory(Factory temp) {
	   factory=temp;
	}
	
}
