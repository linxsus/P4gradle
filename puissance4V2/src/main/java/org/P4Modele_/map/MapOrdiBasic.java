/**
 *
 */
package org.P4Modele_.map;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.P4Metier.Factory.Factory;
import org.Persistant_.requette.SqlOrdinateurBasic;

/**
 * un pseudo map pour l'object ordinateurBasic la cle est l'id la valeur est le
 * param calculer pour l'id
 *
 * tous les maxTour on lance la sauvgarde des donnee dans la base (sauvegarde
 * faite par un thread)
 *
 * on gere la memeoire avec un tampon tournant
 *
 * @author Xavier Gouraud
 *
 */
public class MapOrdiBasic {

	/**
	 * on enregistre le temps de debut utiliser pour le calcul du nb op/s
	 */
	protected long debut;

	/**
	 * nb de tour de sauvegarde utiliser pour le calcul du nb op/s
	 */
	protected static int nbtour = 1;

	/**
	 * nb maximum d'enregistrement avant une sauvegarde
	 */
	protected final static int memGranule = 16384;
	protected final static int memMax = memGranule * 32;

	/**
	 * les differents map contenant tout les id,param cree.
	 */
	protected Map<Long, Integer> tampon;

	protected Map<Long, Integer> memsauvegarde;

	/**
	 * object mysqlOrdinateur (toute les requettes sql)
	 */
	protected SqlOrdinateurBasic sqlOrdinateurBasic;

	/**
	 * thread pour la sauvegarde
	 */
	protected Thread thread;

	/**
	 * le factory pour la creation de nouveau object
	 */
	protected Factory factory;

	/**
	 * initialisation des variables doit etre lancer dans chaque constructeur
	 *
	 * @param factory
	 */
	@SuppressWarnings("serial")
	protected void init(Factory factory) {
		this.factory = factory;
		debut = System.currentTimeMillis();
		tampon = new LinkedHashMap<Long, Integer>(memMax) {
			@Override
			protected boolean removeEldestEntry(Map.Entry<Long, Integer> eldest) {
				return size() > (memMax);
			}
		};
		memsauvegarde = new HashMap<>(memGranule);
		// TODO verifier si c'est indispensable
		// on cree un Tread vide pour eviter tout pb
		thread = new Thread() {
			@Override
			public void run() {

			}
		};
		sqlOrdinateurBasic = new SqlOrdinateurBasic(factory);
	}

	/**
	 * constructeur basic
	 *
	 * @param factory
	 */
	public MapOrdiBasic(Factory factory) {
		super();
		init(factory);
	}

	/**
	 * retourne le l'integer(param calculer) pour key (Id)
	 *
	 * @param key
	 *            id du param que l'on veut recuperer
	 * @return le param calculer de l'id.
	 */
	public Integer get(Long key) {
		Integer result = tampon.get(key);
		if (result == null) {
			// je le recupere dans la base
			result = sqlOrdinateurBasic.getDonnee(key);
			if (result == null) {
				tampon.put(key, result);
			}
		}
		return result;
	}

	/**
	 * ajout un nouveaux lien <-> id param dans la base
	 *
	 * @param key
	 * @param value
	 */
	public void put(Long key, Integer value) {
		// si le map en cours du map tournant a plus de maxTampon object
		if (memsauvegarde.size() > memGranule) {
			// on verifie (attend) que le dernier thread est bien terminer
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// on fait une copie du map en cours

			Map<Long, Integer> maptemp = memsauvegarde;
			memsauvegarde = new HashMap<>(memGranule);
			// on cree le thread d'enregistrement
			thread = new Thread() {
				@Override
				public void run() {
					sqlOrdinateurBasic.setDonnee(maptemp);
				}
			};
			// on lance le thread
			thread.setPriority(4);
			thread.start();
			// gestion de l'affichage
			if ((nbtour % 1) == 0) {
				long resultat = 0;
				long fin = System.currentTimeMillis();
				if ((fin - debut) != 0) {
					resultat = (memGranule * 1000 * 10) / (fin - debut);
				}
				System.out.println(" op/s " + resultat);
				// donnee.setIdBaseDonnee(key);
				// System.out.println(donnee);
				debut = fin;
			}
			nbtour++;
		}

		// on ajoute le lien id <-> param dans le tampon en cours
		tampon.put(key, value);
		memsauvegarde.put(key, value);
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public boolean containsKey(long id) {
		boolean resultat = false;
		// on verifie si ils est dans les tampon
		resultat = tampon.containsKey(id);
		// si il ne l'est pas on verifie dans la base
		if (resultat == false) {
			Integer value = sqlOrdinateurBasic.getDonnee(id);
			if (value != null) {
				tampon.put(id, value);
				resultat = true;
			}
		}
		return resultat;
	}
}
