package org.P4Metier.ordi;

import org.P4Metier.Gagnee;
import org.P4Metier.GestIdDonnee;
import org.P4Metier.Factory.Factory;

/**
 * @author Xavier Gouraud
 *
 *         classe pour calculer le pion a jouer pour gagner
 *
 */

public class OrdinateurAlphaBeta {

	private GestIdDonnee<Long> donnee;
	protected Gagnee gagnee;
	protected Factory factory;

	/**
	 * creation de la class avec comme donnee donnee
	 *
	 * @param donnee
	 *            Donnee
	 */
	public OrdinateurAlphaBeta(Factory factory, GestIdDonnee<Long> donnee) {
		super();
		this.donnee = donnee;
		this.factory = factory;
		init();

	}

	private void init() {
		gagnee = factory.getGagnee();
	}

	/**
	 * retourn la colone a jouer pour les donnee donnee !!! reinitialise les donnee
	 * lors de la creation !!!
	 *
	 * @param donnee
	 *            Donnee
	 * @return colone a jouer pour les donnee donnee
	 */
	public int jouer(GestIdDonnee<Long> donnee) {
		this.donnee = donnee; // reinitialise donnee
		return jouer();
	};

	/**
	 * retourne la colone a jouer
	 *
	 * @return colone a jouer
	 */

	public int jouer() {
		int resultat = -1;
		int max = -1;
		int valeurColonne;
		for (int i : donnee.getColoneJouable()) {
			donnee.ajoutPion(i + 1);
			valeurColonne = alphaBeta(-1000, +1000);
			donnee.enleverPion();
			if (valeurColonne > max) {
				max = valeurColonne;
				resultat = i + 1;
			}
		}
		return resultat;
	}

	/**
	 * calcul le tableau des poids
	 *
	 * @param donnee
	 *            donnee a annalyser
	 * @param nbTour
	 *            nb de tour restant a effectuer
	 * @return le tableau des poids calculer
	 */
	protected int alphaBeta(int A, int B) {

		// if ((donnee.getNbPionJouer() < 19)) {
		// System.out.println(donnee);
		// int[] pionJouer = donnee.getDernierJouerPion();
		// int nbPionJouer = donnee.getNbPionJouer();
		// for (int i = 5; i < nbPionJouer; i++) {
		// System.out.print((pionJouer[i] + 1) + " ");
		// }
		// System.out.println(" ");
		//
		// }
		int v;
		int meilleur;
		if (gagnee.isGagnee(donnee)) {
			// TODO a verifier et voir comment gerer le cangement de joueur
			return (donnee.getJoueur() == 1) ? 1000 : -1000;
		} else {
			meilleur = -1000;
			for (int i : donnee.getColoneJouable()) {
				donnee.ajoutPion(i + 1);
				v = -alphaBeta(-B, -A);
				donnee.enleverPion();
				if (v > meilleur) {
					meilleur = v;
					if (meilleur > A) {
						A = meilleur;
						if (A >= B) {
							return meilleur;
						}
					}
				}
			}
			return meilleur;
		}
	}

	@Override
	public String toString() {
		return "OrdinateurBasic [donneeS=" + donnee + "]";
	}

};
