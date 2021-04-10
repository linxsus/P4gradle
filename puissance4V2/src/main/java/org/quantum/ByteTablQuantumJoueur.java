package org.quantum;

import org.P4Modele_.GestDonnee;
import org.P4Modele_.jeux.ByteTableauJoueur;

public class ByteTablQuantumJoueur extends ByteTableauJoueur{

	/**
	 *  valeur quantum du tableau
	 */
	protected long quantum;
	/**
	 * tableau de byte des colonne inverse
	 */
	protected byte[] hauteurInv = new byte[GestDonnee.LARGEUR];
	/**
	 * tableau de byte des diagonal vers le bas inverse
	 */
	protected byte[] diagonal1Inv = new byte[NB_COLONNE_DIAGONALE];
	/**
	 * tableau de byte des diagonal vers le haut inverse
	 */
	protected byte[] diagonal2Inv = new byte[NB_COLONNE_DIAGONALE];
	/**
	 * tableau de byte des largeur inverse
	 */
	protected byte[] horizontalInv = new byte[GestDonnee.HAUTEUR];
	

	/**
	 * retourne le quantum du tableau
	 * 
	 * @return the quantum
	 */
	public long getQuantum() {
		return quantum;
	}

	/**
	 * creation de l'object 
	 *  
	 */
	public ByteTablQuantumJoueur() {
		super();
		for (int hauteur=0;hauteur<GestDonnee.HAUTEUR;hauteur++) {
			for(int colonne=0;colonne<GestDonnee.LARGEUR;colonne++) {
				this.hauteurInv[colonne] = (byte) (this.hauteurInv[colonne] | ((byte) 1 << (byte) hauteur));
				this.horizontalInv[hauteur] = (byte) (this.horizontalInv[hauteur] | ((byte) 1 << (byte) colonne));
				int i = (hauteur + colonne) + DECALAGE_BAS;
				if ((i > -1) && (i < NB_COLONNE_DIAGONALE)) {
					this.diagonal1Inv[i] = (byte) (this.diagonal1Inv[i] | ((byte) 1 << (byte) hauteur));
				}
				i = (+colonne - hauteur) + DECALAGE_HAUT;
				if ((i > -1) && (i < NB_COLONNE_DIAGONALE)) {
					this.diagonal2Inv[i] = (byte) (this.diagonal2Inv[i] | ((byte) 1 << (byte) hauteur));
				}
			}
		}
		
	}
	
	
	/**
	 * affecte un pion en hauteur,colonne pour le tableau byte Hauteur
	 * et enleve un pion en hauteur,colonne pour le tableau byte HauteurInv
	 * 
	 */
	@Override
	protected void setHauteur(int hauteur, int colonne) {
		super.setHauteur(hauteur, colonne);
		this.hauteurInv[colonne] = (byte) (this.hauteurInv[colonne] & ~((byte) 1 << (byte) hauteur));
	}
	
	/**
	 * on enleve un pion en hauteur,colonne pour le tableau byte hauteur 
	 * affecte un pion en hauteur,colonne pour le tableau byte HauteurInv
	 * 
	 */
	@Override
	protected void remHauteur(int hauteur, int colonne) {
		super.remHauteur(hauteur, colonne);
		this.hauteurInv[colonne] = (byte) (this.hauteurInv[colonne] | ((byte) 1 << (byte) hauteur));
	}
	
	/**
	*affecte un pion en hauteur,colonne pour le tableau byte Diagonal1
	*on enleve un pion en hauteur,colonne pour le tableau byte Diagonal1Inv 
	**/
	@Override
	protected void setDiagonal1(int hauteur, int colonne) {
		super.setDiagonal1(hauteur,colonne);
		// calcul du decalage vers la droite pour le pion hauteur,colonne
		// on est en digonal vers le bas donc plus on monte plus il y a de decalage
		// decalage=decalage de colonne + decalage hauteur+ decalage initiale
		int i = (hauteur + colonne) + DECALAGE_BAS;
		// si le pion decalee se trouve dans diagonal1
		if ((i > -1) && (i < NB_COLONNE_DIAGONALE)) {
			// on enleve le bit
			this.diagonal1Inv[i] = (byte) (this.diagonal1Inv[i] & ~((byte) 1 << (byte) hauteur));
		}
	}

	/**
	 * on enleve un pion en hauteur,colonne pour le tableau byte Diagonal1
	 * affecte un pion en hauteur,colonne pour le tableau byte Diagonal1Inv
	 *
	 */
	@Override
	protected void remDiagonal1(int hauteur, int colonne) {
		super.remDiagonal1(hauteur,colonne);
		int i = (hauteur + colonne) + DECALAGE_BAS;
		if ((i > -1) && (i < NB_COLONNE_DIAGONALE)) {
			this.diagonal1Inv[i] = (byte) (this.diagonal1Inv[i] | ((byte) 1 << (byte) hauteur));
		}
		
		
	}

	/**
	 * affecte un pion en hauteur,colonne pour le tableau byte Diagonal2
	 * on enleve un pion en hauteur,colonne pour le tableau byte Diagonal2Inv
	 * 
	 */
	@Override
	protected void setDiagonal2(int hauteur, int colonne) {
		super.setDiagonal2(hauteur,colonne);
		// calcul du decalage vers la droite pour le pion hauteur,colonne
		// on est en digonal vers le bas donc plus on monte plus il y a de decalage
		// decalage=decalage de colonne + decalage hauteur+ decalage initiale
		int i = (+colonne - hauteur) + DECALAGE_HAUT;
		// si le pion decalee est se trouve dans diagonal2
		if ((i >= 0) && (i < NB_COLONNE_DIAGONALE)) {
			// on enleve le bit
			this.diagonal2Inv[i] = (byte) (this.diagonal2Inv[i] & ~((byte) 1 << (byte) hauteur));
		}
	}

	/**
	 * affecte un pion en hauteur,colonne pour le tableau byte hauteur
	 * on enleve un pion en hauteur,colonne pour le tableau byte hauteurInv
	 *
	 */
	@Override
	protected void setHorizontal(int hauteur, int colonne) {
		super.setHorizontal(hauteur, colonne);
		this.horizontalInv[hauteur] = (byte) (this.horizontalInv[hauteur] & ~((byte) 1 << (byte) colonne));

	}
	

	/**
	 * on enleve un pion en hauteur,colonne pour le tableau byte Diagonal2
	 * affecte un pion en hauteur,colonne pour le tableau byte Diagonal2Inv
	 *
	 */
	@Override
	protected void remDiagonal2(int hauteur, int colonne) {
		super.remDiagonal2(hauteur, colonne);
		int i = (+colonne - hauteur) + DECALAGE_HAUT;
		if ((i > -1) && (i < NB_COLONNE_DIAGONALE)) {
			this.diagonal2Inv[i] = (byte) (this.diagonal2Inv[i] | ((byte) 1 << (byte) hauteur));
		}
		
		
	}

	/**
	 * on enleve un pion en hauteur,colonne pour le tableau byte horizontal
	 * affecte un pion en hauteur,colonne pour le tableau byte horizontalInv
	 *
	 */
	@Override
	protected void remHorizontal(int hauteur, int colonne) {
		super.remHorizontal(hauteur, colonne);
		this.horizontalInv[hauteur] = (byte) (this.horizontalInv[hauteur] | ((byte) 1 << (byte) colonne));

	}

	/**
	 * on enleve un pion en hauteur,colonne pour tous les tableau byte
	 * et on met a jour le quantum
	 *
	 */
	@Override
	public void enleverPion(int hauteur, int colonne) {
		long quantumDebut=calcQuantum(hauteur,colonne);
		super.enleverPion(hauteur, colonne);
		quantum+=calcQuantum(hauteur,colonne)-quantumDebut;
		
	}
	//TODO
	/**
	 * ajoute un pion en hauteur,colonne pour tous les tableau byte
	 * et on met a jour le quantum
	 *
	 */
	public void ajouterPion(int hauteur, int colonne) {
		long quantumDebut=calcQuantum(hauteur,colonne);
		super.ajouterPion(hauteur, colonne);
		quantum+=calcQuantum(hauteur,colonne)-quantumDebut;
	}

	private long calcQuantum(int hauteur, int colonne) {
		byte test = 0;
		long gagnee=0;
		 il faut faire un masque pour ne prendre que la partie qui concerne le piont jouer
		test = hauteurInv[colonne];
		gagnee += test(test);
		test = horizontalInv[hauteur];
		gagnee += test(test);
		int decalage = colonne + hauteur + DECALAGE_BAS;
		manque le if
		test = diagonal1Inv[decalage];
		
		gagnee += test(test);
		
		caluler le decalage
		test = getDiagonal2(hauteur - 1, colonne);
		gagnee += test(test);
		if (getPion(hauteur, colonne) == 1) {
			test *= test;
		}
		return gagnee;
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
