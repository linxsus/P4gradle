/**
 *
 */
package org.P4Modele_.jeux;

/**
 * javabean pour la gestion des pions 2 variable colonne et hauteur qui
 * enregistre les coordonner du pion.
 *
 * @author Xavier Gouraud
 *
 */
public class PionJouer {

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("PionJouer [colonne=%s, hauteur=%s] \n", colonne, hauteur);
	}

	private int colonne;
	private int hauteur;

	/**
	 * @return the colonne
	 */
	public int getColonne() {
		return colonne;
	}

	/**
	 * @param colonne
	 *            the colonne to set
	 */
	public void setColonne(int colonne) {
		this.colonne = colonne;
	}

	/**
	 * @return the hauteur
	 */
	public int getHauteur() {
		return hauteur;
	}

	/**
	 * @param hauteur
	 *            the hauteur to set
	 */
	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}

	/**
	 * creation de la classe hauteur
	 */
	public PionJouer() {
		super();
	}

}
