package org.P4Metier.gagnee;

import org.P4Metier.Gagnee;
import org.P4Metier.Factory.Factory;
import org.P4Modele_.GestDonnee;

public class GagneeTabInt implements Gagnee {

	private GestDonnee donnee;
	private int couleur = 0;
	private int hauteur = 0;
	private int colone;
	private int tempCouleur;
	protected Factory factory;

	public GagneeTabInt(Factory factory) {
		super();
		this.factory = factory;
	}

	/**
	 * verifie si le dernier pion jouer dans le donnee a fait gagner le dernier
	 * joueur.
	 *
	 * @param donnee
	 *            donnee a analyser
	 * @return retourne true si c'et gagner false sinon
	 */
	@Override
	public boolean isGagnee(GestDonnee donnee) {
		this.colone = donnee.getDernierJouer();
		this.donnee = donnee;
		coulHaut();
		if (vertical()) {
			return true;
		}
		if (horisontal()) {
			return true;
		}
		if (diagonalBasHaut()) {
			return true;
		}
		if (diagonalHautBas()) {
			return true;
		}
		return false;
	}

	/**
	 * calculer la couleur et la hauteur du pion en haut de la colone
	 */
	private void coulHaut() {
		for (int i = donnee.getNbPionColonne(colone) - 1; i >= 0; i--) { // pour chaque ligne en partant du haut
			if (donnee.getTableau(i, colone) != 0) { // si il y a pion dans la colone
				couleur = donnee.getTableau(i, colone); // on recupere la couleur
				hauteur = i; // on recupere la hauteur
				i = 0;
			}
		}
	}

	// si hauteur plus de 4 pion empil�� caluler si gagnee en hauteur
	/**
	 * retourne true si 4 pion empiler
	 *
	 * @return true si 4 pion empiler sinon false
	 */
	private boolean vertical() {
		if (hauteur >= 3) { // si il y a pas plus de 4 pion sa sert a rien de calculer
			int nbPionEmpilee = 1;
			tempCouleur = couleur; // permet de sortir de la boucle des que l'on change de couleur
			for (int i = hauteur - 1; (i >= 0) && (tempCouleur == couleur); i--) { // pour chaque ligne en dessou et que
																					// l'on a pas changer de couleur
				if (tempCouleur == donnee.getTableau(i, colone)) { // si c'est la meme couleur en bas
					nbPionEmpilee++; // on a 1 pion de plus empilee
				} else {
					tempCouleur = 0;// on a changer de couleur
				}
			}

			if (nbPionEmpilee > 3) { // si on a plus de 3 pion d'empiler
				return true; // on retourne true
			}
		}
		return false; // on retourne false par default
	}

	/**
	 * retourne true si 4 pion en horizontal
	 *
	 * @return true si 4 pion en horizontal
	 */
	private boolean horisontal() {
		int nbPionhorizontal = 1;
		tempCouleur = couleur; // permet de sortir de la boucle des que l'on change de couleur
		for (int i = (colone - 1); (i >= 0) && (tempCouleur == couleur); i--) { // tans qu'on est pas sortie du tableau
																				// sur la gauche et qu'on a pas changer
																				// de couleur
			if (tempCouleur == donnee.getTableau(hauteur, i)) { // si c'est la meme couleur a gauche
				nbPionhorizontal++; // on a 1 pion de plus a gauche
			} else {
				tempCouleur = 0;// on a changer de couleur
			}
		}
		tempCouleur = couleur; // permet de sortir de la boucle des que l'on change de couleur
		for (int i = colone + 1; (i < GestDonnee.LARGEUR) && (tempCouleur == couleur); i++) {// tans qu'on est pas
																								// sortie du
																								// tableau sur la droite
																								// et
																								// qu'on a pas changer
																								// de
																								// couleur
			if (tempCouleur == donnee.getTableau(hauteur, i)) { // si c'est la meme couleur a droite
				nbPionhorizontal++; // on a 1 pion de plus a droite
			} else {
				tempCouleur = 0;// on a changer de couleur
			}
		}

		// System.out.println(nbPionhorizontal);

		if (nbPionhorizontal > 3) { // si on a plus de 3 pion en horizontal
			return true; // on retourne true
		}
		return false; // on retourne false par default
	}

	/**
	 * retourne true si 4 pion en diagonal de bas vers haut
	 *
	 * @return true si 4 pion en diagonal de bas vers haut
	 */
	private boolean diagonalBasHaut() {
		int nbPionDiagonal1 = 1;
		int i;
		tempCouleur = couleur;// permet de sortir de la boucle des que l'on change de couleur
		// tans qu'on est pas sortie du tableau sur la gauche et en bas et qu'on a pas
		// changer de couleur
		for (i = 1; (((hauteur - i) >= 0) && ((colone - i) >= 0)) && (tempCouleur == couleur); i++) {
			if (tempCouleur == donnee.getTableau(hauteur - i, colone - i)) { // si c'est la meme couleur en bas a gauche
				nbPionDiagonal1++; // on a 1 pion de plus en diagonal
			} else {
				tempCouleur = 0;// on a changer de couleur
			}
		}

		tempCouleur = couleur;// permet de sortir de la boucle des que l'on change de couleur
		for (i = 1; (((hauteur + i) < GestDonnee.HAUTEUR) && ((colone + i) < GestDonnee.LARGEUR))
				&& (tempCouleur == couleur); i++) {// tans qu'on est pas sortie du tableau sur la droite et en haut et
													// qu'on a pas changer de couleur
			if (tempCouleur == donnee.getTableau(hauteur + i, colone + i)) { // si c'est la meme couleur en haut a
																				// droite
				nbPionDiagonal1++; // on a 1 pion de plus en diagonal
			} else {
				tempCouleur = 0;// on a changer de couleur
			}
		}
		if (nbPionDiagonal1 > 3) { // si on a plus de 3 pion en diagonal de bas vers haut
			return true; // on retourne true
		}
		return false; // on retourne false par default
	}

	/**
	 * retourne true si 4 pion en diagonal de haut vers bas
	 *
	 * @return true si 4 pion en diagonal de haut vers bas
	 */
	private boolean diagonalHautBas() {
		int nbPionDiagonal2 = 1;
		int i;
		tempCouleur = couleur; // permet de sortir de la boucle des que l'on change de couleur
		// tans qu'on est pas sortie du tableau sur la droite et en bas et qu'on a pas
		// changer de couleur
		for (i = 1; (((hauteur - i) >= 0) && ((colone + i) < GestDonnee.LARGEUR)) && (tempCouleur == couleur); i++) {
			if (tempCouleur == donnee.getTableau(hauteur - i, colone + i)) { // si c'est la meme couleur en bas a droite
				nbPionDiagonal2++; // on a 1 pion de plus en diagonal
			} else {
				tempCouleur = 0;// on a changer de couleur
			}
		}
		tempCouleur = couleur;// permet de sortir de la boucle des que l'on change de couleur
		// tansqu'on est pas sortie du tableau sur la gauche et en haut et qu'on a pas
		// changer de couleur
		for (i = 1; (((hauteur + i) < GestDonnee.LARGEUR) && ((colone - i) >= 0)) && (tempCouleur == couleur); i++) {
			if (tempCouleur == donnee.getTableau(hauteur + i, colone - i)) { // si c'est la meme couleur en haut a
																				// gauche
				nbPionDiagonal2++; // on a 1 pion de plus en diagonal
			} else {
				tempCouleur = 0;// on a changer de couleur
			}
		}
		if (nbPionDiagonal2 > 3) { // si on a plus de 3 pion en diagonal de haut vers bas
			return true; // on retourne true
		}
		return false; // on retourne false par default
	}

	@Override
	public String toString() {
		return "Gagnee [donnee=" + donnee + ", couleur=" + couleur + ", hauteur=" + hauteur + ", colone=" + colone
				+ ", tempCouleur=" + tempCouleur + "]";
	}

}
