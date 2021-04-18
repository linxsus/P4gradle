package org.P4Modele_.jeux;

import java.util.Arrays;

import org.P4Metier.Factory.Factory;
import org.P4Modele_.GestDonnee;

public class GestDonneeTabByte  implements GestDonnee {

	protected int[] dernierJouer = new int[HAUTEUR * LARGEUR];
	protected int nbPionJouer = 0;

	protected int[] nbPionColonne = new int[LARGEUR];
//TODO ici remplacer pour la gestion Quantum
	protected ByteTableauJoueur[] byteTableauJoueur = new ByteTableauJoueur[2];
	protected Factory factory;

	public GestDonneeTabByte(Factory factory) {
		super();
		this.factory = factory;
		init();
	}

	@Override
	public boolean enleverPion() {
		boolean resultat = false;
		if (nbPionJouer >= 1) {
			nbPionJouer--;
			int derJouer = dernierJouer[nbPionJouer];
			if ((nbPionJouer % 2) == 0) {
				byteTableauJoueur[0].enleverPion(nbPionColonne[derJouer] - 1, derJouer);
			} else {
				byteTableauJoueur[1].enleverPion(nbPionColonne[derJouer] - 1, derJouer);
			}
			nbPionColonne[derJouer]--;
			resultat = true;
		}
		return resultat;
	}

	@Override
	public boolean ajoutPion(int colonne) {
		colonne -= 1;
		if ((colonne >= 0) && (colonne <= (LARGEUR - 1))) { // si la colone est dans le tableau
			int hauteur = nbPionColonne[colonne];
			if (hauteur < 6) {
				byteTableauJoueur[getJoueur() - 1].ajouterPion(hauteur, colonne);
				dernierJouer[nbPionJouer] = colonne;
				nbPionJouer++;
				nbPionColonne[colonne]++;
				return true;
			}
		}
		return false; // impossible de rajouter le pion car la colone est pleine
	}

	@Override
	public boolean ajoutPion(int hauteur, int colonne, int joueur) {
		// enlever pour plus de rapidité
		// if (hauteur >=0 && hauteur <GestBaseDonnee.HAUTEUR && colonne>=0 && colonne
		// <GestBaseDonnee.LARGEUR && joueur>=0 && joueur<2)
		// enlever pour plus de rapidité
		// if (byteTableauJoueur[joueur-1].getPion(hauteur, colonne)!=0) {
		if (joueur > 0) {
			nbPionColonne[colonne]++;
			nbPionJouer++;
			byteTableauJoueur[joueur - 1].ajouterPion(hauteur, colonne);
		} else {
			//TODO ??????????????? pourquoi on enleve un pion on ne devrait pas rien faire??
			// on est en train de faire une copie de tableau
			byteTableauJoueur[0].enleverPion(hauteur, colonne);
			byteTableauJoueur[1].enleverPion(hauteur, colonne);
		}
		return true;
	}

	@Override
	public int getNbPionJouer() {
		return nbPionJouer;
	}

	@Override
	public int getNbPionColonne(int colonne) {
		return nbPionColonne[colonne];
	}

	@Override
	public int getTableau(int i, int j) {
		return byteTableauJoueur[0].getPion(i, j) + (byteTableauJoueur[1].getPion(i, j) * 2);
	}

	@Override
	public int[] getDernierJouerPion() {
		return Arrays.copyOfRange(dernierJouer, 0, nbPionJouer);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String message = "";
		for (int i = HAUTEUR - 1; i >= 0; i--) {

			for (int j = 0; j < LARGEUR; j++) {
				int t2 = getTableau(i, j);
				message += "|";
				switch (t2) {
				case 1:
					message += "X";
					break;
				case 2:
					message += "O";
					break;
				default:
					message += " ";
				}
			}
			message += "|" + i + "\n";
		}
		for (int i = 1; i <= LARGEUR; i++) {
			message += " " + i;
		}
		message += "\n";
		if (getPrecedent() != 0) {
			message += " le joueur " + getPrecedent() + " a jouer sur " + (dernierJouer[nbPionJouer - 1] + 1) + "\n";
		}
		return message;
	}

	/**
	 * initialisation du tableau dernierJoueur
	 */
	protected void init() {
		//TODO ici a remplacer pour gestion Quantum
		for (int i = 0; i < byteTableauJoueur.length; i++) {
			byteTableauJoueur[i] = new ByteTableauJoueur();
		}
	}

	/**
	 * retourne le ByteTableauJoueur du joueur qui vien de jouer
	 */
	@Override
	public ByteTableauJoueur getByteTableauJoueur() {
		int precedent=getPrecedent();
		//if (precedent==0) {
		return byteTableauJoueur[precedent - 1];
//		}
//		return  byteTableauJoueur[0];
	}

	@Override
	public ByteTableauJoueur getByteTableauJoueur(int joueur) {
		return byteTableauJoueur[joueur - 1];
	}

	@Override
	public int getDernierJouer() {
		return dernierJouer[nbPionJouer - 1];
	}

	@Override
	public void setDonnee(int[][] tableau, int colonne) {
		setDonnee(tableau);
		dernierJouer[nbPionJouer - 1] = colonne;
	}

	@Override
	public GestDonnee getNewBase() {
		return factory.getGestDonnee();
	}

	@Override
	public void setDonnee(int[][] tableau) {
		dernierJouer = new int[HAUTEUR * LARGEUR];
		nbPionJouer = 0;
		nbPionColonne = new int[LARGEUR];

		init();
		for (int i = 0; i < HAUTEUR; i++) {
			for (int j = 0; j < LARGEUR; j++) {
				ajoutPion(i, j, tableau[i][j]);
			}
		}

	}
}
