package org.quantum;

import org.P4Metier.Factory.Factory;
import org.P4Modele_.GestDonnee;
import org.P4Modele_.jeux.GestDonneeTabByte;

public class GestDonneeTabByteQuantum extends GestDonneeTabByte {
	
	
	
	public GestDonneeTabByteQuantum(Factory factory) {
		super(factory);
	}

	protected void init() {
		for (int i = 0; i < byteTableauJoueur.length; i++) {
			byteTableauJoueur[i] = new ByteTableauJoueurQuantum();
		}
	}
	protected int quantum;
	

	public int calcQuantum() {
		int result = 0;
		byte test;
		ByteTableauJoueurQuantum byteTableau;
		for (int colonne=0; colonne < GestDonnee.LARGEUR; colonne++) {
			for (int hauteur=0; hauteur < nbPionColonne[colonne]; hauteur++) {
				int joueur = byteTableauJoueur[0].getPion(hauteur, colonne);
				byteTableau=(ByteTableauJoueurQuantum) byteTableauJoueur[joueur];
				test = (byte) (byteTableau.getHauteurInv(hauteur, colonne));
				result += test(test);
				test = (byte) (byteTableau.getHorizontalInv(hauteur, colonne));
				result += test(test);
				test = (byte) (byteTableau.getDiagonal1Inv(hauteur, colonne));
				result += test(test);
				test = (byte) (byteTableau.getDiagonal2Inv(hauteur, colonne));
				result += test(test);
			}
		}
		return result;
	}

	/**
	 * retourne 1 si il y a 4 pion a la suite dans le byte test fournit en parametre
	 * sinon retourne 0
	 *
	 * @param test
	 *            byte a tester
	 * @return 1 si il y a 4 pion sinon 0
	 */
	private int test(Byte test) {
		int resultat = 0;
		if ((0b1111 & test) == 0b1111) {
			resultat += 1;
		}
		if ((0b11110 & test) == 0b11110) {
			resultat += 1;
		}
		if ((0b111100 & test) == 0b111100) {
			resultat += 1;
		}
		if ((0b1111000 & test) == 0b1111000) {
			resultat += 1;
		}
		return resultat;
	}	
}
