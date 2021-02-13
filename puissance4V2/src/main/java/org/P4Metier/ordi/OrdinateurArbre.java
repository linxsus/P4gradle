package org.P4Metier.ordi;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.P4Metier.Gagnee;
import org.P4Metier.GestIdDonnee;
import org.P4Metier.Ordinateur;
import org.P4Metier.Factory.Factory;
import org.P4Modele_.Arbre;
import org.P4Modele_.Calculer;
import org.P4Modele_.GestDonnee;
import org.P4Modele_.Neud;

/**
 * @author Xavier Gouraud
 *
 *         classe pour calculer le pion a jouer pour gagner
 *
 */
public class OrdinateurArbre implements Ordinateur<Long> {
	protected GestIdDonnee<Long> donnee;
	// private boolean mysql = false;
	protected int tourMax = 5;
	protected Gagnee gagnee;
	protected Factory factory;
	protected Arbre arbre;

	/**
	 * creation de la class avec comme donnee donnee
	 *
	 * @param donnee
	 *            Donnee
	 */
	public OrdinateurArbre(Factory factory, GestIdDonnee<Long> donnee) {
		super();
		this.factory = factory;
		this.donnee = donnee;
		long tron = donnee.getIdBaseDonnee();
		arbre = factory.getArbre();
		gagnee = factory.getGagnee();
		arbre.setTron(tron, donnee.getNbPionJouer());
	}

	// /**
	// * creation de la class avec comme donnee donnee et mettre le boolean a true
	// * pour utiliser mysql.
	// *
	// * @param donnee
	// * Donnee
	// * @param mysql
	// * boolean
	// * @param tourMax
	// * int
	// */
	// public OrdinateurArbre(GestBaseDonnee donnee, boolean mysql, int tourMax) {
	// super();
	// this.donnee = donnee;
	// this.mysql = mysql;
	// this.tourMax = tourMax;
	// }

	/**
	 * retourn la colone a jouer pour les donnee donnee !!! reinitialise les donnee
	 * lors de la creation !!!
	 *
	 * @param donnee
	 *            Donnee
	 * @return colone a jouer pour les donnee donnee
	 */
	@Override
	public int jouer(GestIdDonnee<Long> donnee) {
		this.donnee = donnee; // reinitialise donnee
		return jouer();
	};

	/**
	 * retourne la colone a jouer avec un reglage sur la profondeur de l'arbre
	 *
	 * @param profondeur
	 * @return
	 */
	public int jouer(int profondeur) {
		tourMax = profondeur;
		return jouer();
	}

	/**
	 * retourne la colone a jouer
	 *
	 * @return colone a jouer
	 */
	@Override
	public int jouer() {
		Gagnee gagnee = factory.getGagnee();
		boolean miroire = donnee.isMiroire();
		HashMap<Integer, Long> resultatColonne = new HashMap<>();
		int niveauInitial = donnee.getNbPionJouer();
		int niveau = niveauInitial;
		long idParent = 0;
		long idEnfant = 0;
		Neud next;
		GestIdDonnee<Long> donneeTravaille;
		int niveauMax = niveauInitial + tourMax;
		// je recupere l'id actuelle
		long tron = donnee.getIdBaseDonnee();
		// je lui affecte le tron
		arbre.setTron(tron, niveauInitial);
		while (niveau < niveauMax) {
			// je recupere le neud suivant explorable
			next = arbre.nextExplorable(niveau);
			if (next == null) {
				niveau++;
			} else {
				//TODO next.getId() puis donneeTravaille.getIdBaseDonnee() pourquoi recalculer l'id??
				// je recupere le neud de travaille
				donneeTravaille = donnee.newBaseDonneeId(next.getId());
				idParent = donneeTravaille.getIdBaseDonnee();
				// pour toute les possibilter du neud
				int[] colonnes = donneeTravaille.getColoneJouable();
				boolean nonGagner = true;
				//TODO il faut enlever le && nonGagner pour devalider les autre possibiliter
				for (int i = 0; (i < colonnes.length) && nonGagner; i++) {
					// j'ajoute le pion
					donneeTravaille.ajoutPion(colonnes[i] + 1);
					idEnfant = donneeTravaille.getIdBaseDonnee();
					// je sauvegade le lien colonne,enfant que pour le niveau initial pour faire calculer
					if (niveau == niveauInitial) {
						resultatColonne.put(colonnes[i], idEnfant);
					}
					Neud enfant = arbre.addEnfant(idParent, idEnfant);
					// si j'ai gagnee je renseigne l'arbre
					if ((enfant.getCalculer() == Calculer.GAGNER) || gagnee.isGagnee(donneeTravaille)) {
						arbre.setCalculer(idEnfant, Calculer.GAGNER);
						// j'ai gagner inutil de calculer les autre possibilité.
						nonGagner = false;
					}
					donneeTravaille.enleverPion();
				}
				arbre.setExplorableFalse(idParent);
			}
		}
		if (miroire) {
			return (GestDonnee.LARGEUR - (calculer(resultatColonne, arbre) - 1));
		} else {
			return calculer(resultatColonne, arbre);
		}

	}

	@Override
	public String toString() {
		return "OrdinateurBasic [donneeS=" + donnee + "]";
	}

	/**
	 * calcul le param pour le neud de l'arbre
	 *
	 * @param neud
	 *            neud a calculer.
	 * @param arbre
	 *            arbre
	 * @return la colonne a jouer
	 */
	private int calculer(HashMap<Integer, Long> resultatColonne, Arbre arbre) {
		int resultat = 0;
		int nbPerdu = 0;
		int nbGagner = 0;
		int nbNonCalculer = 0; // a voir si utile
		int nbEgaliter = 0;
		int[] perduTab = new int[GestDonnee.LARGEUR];
		int[] gagnerTab = new int[GestDonnee.LARGEUR];
		int[] nonCalculerTab = new int[GestDonnee.LARGEUR];
		int[] egaliterTab = new int[GestDonnee.LARGEUR];
		Calculer enfantCalculer;
		int nbEnfant = resultatColonne.size();
		Set<Entry<Integer, Long>> enfants = resultatColonne.entrySet();
		// si il y a des enfant
		if (nbEnfant > 0) {
			// pour tous les enfants
			for (Entry<Integer, Long> aCalculer : enfants) {
				Neud neudEnfant = arbre.getNeud(aCalculer.getValue());
				// au cas ou la gestion de l'arbre aurrais 'supprimer' l'enfant
				if (neudEnfant != null) {
					enfantCalculer = arbre.getNeud(aCalculer.getValue()).getCalculer();
					// je calul si gagner, si j'ai bien tous les enfant de calculer le nb de perdu,
					// le nb d'egaliter
					switch (enfantCalculer) {
					case GAGNER:
						gagnerTab[nbGagner] = aCalculer.getKey(); // la faut trouver autre chose
						nbGagner++;
						break;
					case INDEFINI:
					case NONCALCULER:
						nonCalculerTab[nbNonCalculer] = aCalculer.getKey(); // la faut trouver autre chose
						nbNonCalculer++;
						break;
					case EGALITER:
						egaliterTab[nbEgaliter] = aCalculer.getKey(); // la faut trouver autre chose
						nbEgaliter++;
						break;
					case PERDU:
					default:
						perduTab[nbPerdu] = aCalculer.getKey(); // la faut trouver autre chose
						nbPerdu++;
						break;
					}
				}
			}

			// si sur un de mes enfant j'ai un gagner
			if (nbGagner > 0) {
				// je le choisie
				resultat = gagnerTab[0];
				// sinon si je perd sur tout saufe sur 1
			} else if ((nbPerdu + 1) == nbEnfant) {
				// je choisie celui ci
				if (nbNonCalculer > 0) {
					resultat = nonCalculerTab[0];
				} else {
					resultat = egaliterTab[0];
				}
				// sinon si j'ai une ou plusieur possibiliter sur un non calculer
			} else if (nbNonCalculer > 0) {
				// j'en choisie une au hazard
				resultat = auHazard(nonCalculerTab, nbNonCalculer);
				// sinon j'ai perdu donc j'en choisi une au hazard
			} else {
				resultat = auHazard(perduTab, nbPerdu);
			}
		}
		return resultat + 1; // un +1???
	}

	/**
	 * choisi un nb au hazard dans le tableau (tab) je ne choisi que dans les (nb)
	 * premier choix
	 *
	 * @param tab
	 *            tableaux des choix
	 * @param nb
	 *            nb premier choix
	 * @return le nb choisi
	 */
	private int auHazard(int[] tab, int nb) {
		Random rnd = new Random();
		int i = rnd.nextInt(nb);
		return tab[i];
	}

	public void setTourMax(int i) {
		tourMax = i;
	}

}
