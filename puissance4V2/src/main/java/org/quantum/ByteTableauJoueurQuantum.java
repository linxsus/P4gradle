package org.quantum;

import org.P4Modele_.GestDonnee;
import org.P4Modele_.jeux.ByteTableauJoueur;


public class ByteTableauJoueurQuantum extends ByteTableauJoueur {
//
//	/**
//	 *  valeur quantum du tableau
//	 */
//	protected long quantum;
//	/**
//	 * tableau de byte des colonne inverse
//	 */
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
	
//	protected int joueur=0;
//
//	/**
//	 * retourne le quantum du tableau
//	 * 
//	 * @return the quantum
//	 */
//	public long getQuantum() {
//		return quantum;
//	}
//
//	/**
//	 * creation de l'object 
//	 *  
//	 */
	public ByteTableauJoueurQuantum() {
		super();
		
		for (int hauteur=0;hauteur<GestDonnee.HAUTEUR;hauteur++) {
			for(int colonne=0;colonne<GestDonnee.LARGEUR;colonne++) {
				this.hauteurInv[colonne] = (byte) (this.hauteurInv[colonne] | ((byte) 1 << (byte) hauteur));
				this.horizontalInv[hauteur] = (byte) (this.horizontalInv[hauteur] | ((byte) 1 << (byte) colonne));
				int j = (hauteur + colonne) + DECALAGE_BAS;
				if ((j > -1) && (j < NB_COLONNE_DIAGONALE)) {
					this.diagonal1Inv[j] = (byte) (this.diagonal1Inv[j] | ((byte) 1 << (byte) hauteur));
				}
				j = (+colonne - hauteur) + DECALAGE_HAUT;
				if ((j > -1) && (j < NB_COLONNE_DIAGONALE)) {
					this.diagonal2Inv[j] = (byte) (this.diagonal2Inv[j] | ((byte) 1 << (byte) hauteur));
				}
			}
		}
		
		
	}
//	
//	
//	/**
//	 * affecte un pion en hauteur,colonne pour le tableau byte Hauteur
//	 * et enleve un pion en hauteur,colonne pour le tableau byte HauteurInv
//	 * 
//	 */
	protected void setHauteur(int hauteur, int colonne) {
		super.setHauteur(hauteur, colonne);
		this.hauteurInv[colonne] = (byte) (this.hauteurInv[colonne] & ~((byte) 1 << (byte) hauteur));
	}
//	
//	/**
//	 * on enleve un pion en hauteur,colonne pour le tableau byte hauteur 
//	 * affecte un pion en hauteur,colonne pour le tableau byte HauteurInv
//	 * 
//	 */
	protected void remHauteur(int hauteur, int colonne) {
		super.remHauteur(hauteur, colonne);
		this.hauteurInv[colonne] = (byte) (this.hauteurInv[colonne] | ((byte) 1 << (byte) hauteur));
	}
//	
//	/**
//	*affecte un pion en hauteur,colonne pour le tableau byte Diagonal1
//	*on enleve un pion en hauteur,colonne pour le tableau byte Diagonal1Inv 
//	**/
//
	protected void setDiagonal1(int hauteur, int colonne) {
		super.setDiagonal1(hauteur, colonne);
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
//
//	/**
//	 * on enleve un pion en hauteur,colonne pour le tableau byte Diagonal1
//	 * affecte un pion en hauteur,colonne pour le tableau byte Diagonal1Inv
//	 *
//	 */
	protected void remDiagonal1(int hauteur, int colonne) {
		super.remDiagonal1(hauteur, colonne);
		int i = (hauteur + colonne) + DECALAGE_BAS;
		if ((i > -1) && (i < NB_COLONNE_DIAGONALE)) {
			this.diagonal1Inv[i] = (byte) (this.diagonal1Inv[i] | ((byte) 1 << (byte) hauteur));
		}
		
		
	}
//
//	/**
//	 * affecte un pion en hauteur,colonne pour le tableau byte Diagonal2
//	 * on enleve un pion en hauteur,colonne pour le tableau byte Diagonal2Inv
//	 * 
//	 */
	protected void setDiagonal2(int hauteur, int colonne) {
		super.setDiagonal2(hauteur, colonne);
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

//	/**
//	 * affecte un pion en hauteur,colonne pour le tableau byte hauteur
//	 * on enleve un pion en hauteur,colonne pour le tableau byte hauteurInv
//	 *
//	 */
	protected void setHorizontal(int hauteur, int colonne) {
		super.setHorizontal(hauteur, colonne);
		this.horizontalInv[hauteur] = (byte) (this.horizontalInv[hauteur] & ~((byte) 1 << (byte) colonne));

	}
//	
//
//	/**
//	 * on enleve un pion en hauteur,colonne pour le tableau byte Diagonal2
//	 * affecte un pion en hauteur,colonne pour le tableau byte Diagonal2Inv
//	 *
//	 */
	protected void remDiagonal2(int hauteur, int colonne) {
		super.remDiagonal2(hauteur, colonne);
		int i = (+colonne - hauteur) + DECALAGE_HAUT;
		if ((i > -1) && (i < NB_COLONNE_DIAGONALE)) {
			this.diagonal2Inv[i] = (byte) (this.diagonal2Inv[i] | ((byte) 1 << (byte) hauteur));
		}
		
		
	}
//
//	/**
//	 * on enleve un pion en hauteur,colonne pour le tableau byte horizontal
//	 * affecte un pion en hauteur,colonne pour le tableau byte horizontalInv
//	 *
//	 */
	protected void remHorizontal(int hauteur, int colonne) {
		super.remHorizontal(hauteur, colonne);
		this.horizontalInv[hauteur] = (byte) (this.horizontalInv[hauteur] | ((byte) 1 << (byte) colonne));
	}
//
//	/**
//	 * methode a ne pas utiliser utiliser enleverPionQuantum(int,int)
//	 *
//	 */
//	public void enleverPion(int hauteur, int colonne) {
//		
//		super.enleverPion(hauteur, colonne);
//		quantum-=calcQuantum(hauteur,colonne);
//		joueur=(joueur+1)%2;
//	}
//	/**
//	 * methode a ne pas utiliser utiliser ajouterPionQuantum(int,int)
//	 *
//	 */
//	public void ajouterPion(int hauteur, int colonne) {
//		quantum+=calcQuantum(hauteur,colonne);
//		super.ajouterPion(hauteur, colonne);
//		joueur=(joueur+1)%2;
//	}
//
//	private long calcQuantum(int hauteur, int colonne) {
//		int gagnee = 0;
//		byte test;
//		//calcul du masque hauteur
//		byte masqueHauteur=0b1111111;
//		if (hauteur<=3) {
//			masqueHauteur>>=3-hauteur;
//		}else
//		{
//			masqueHauteur<<=hauteur-3;
//		}
//		
//		byte masquecolonne=0b1111111;
//		if (colonne<=3) {
//			masquecolonne>>=3-colonne;
//		}else
//		{
//			masquecolonne<<=colonne-3;
//		}
//		for (int j=0;j<2;j++) {
//		test = (byte) (hauteurInv[j][colonne]& masqueHauteur);
//		gagnee += test(test);
//		test = (byte) (horizontalInv[j][hauteur]& masquecolonne);
//		gagnee += test(test);
//		test = (byte) (getDiagonal1(hauteur, colonne,diagonal1Inv[j]) & masqueHauteur);
//		gagnee += test(test);
//		test = (byte) (getDiagonal2(hauteur, colonne,diagonal2Inv[j]) & masqueHauteur);
//		gagnee += test(test);
//		}
//		return gagnee;
//	}
//
//	/**
//	 * retourne 1 si il y a 4 pion a la suite dans le byte test fournit en parametre
//	 * sinon retourne 0
//	 *
//	 * @param test
//	 *            byte a tester
//	 * @return 1 si il y a 4 pion sinon 0
//	 */
//	private int test(Byte test) {
//		int resultat = 0;
//		if ((0b1111 & test) == 0b1111) {
//			resultat += 1;
//		}
//		if ((0b11110 & test) == 0b11110) {
//			resultat += 1;
//		}
//		if ((0b111100 & test) == 0b111100) {
//			resultat += 1;
//		}
//		if ((0b1111000 & test) == 0b1111000) {
//			resultat += 1;
//		}
//		return resultat;
//	}	
	

	
	public byte getHauteurInv(int hauteur, int colonne) {
		byte masqueHauteur = 0b1111111;
		// on retourne le byte qui concerne le pion
		if (hauteur <= 3) {
			masqueHauteur >>= 3 - hauteur;
		} else {
			masqueHauteur <<= hauteur - 3;
		}
		return (byte) (this.hauteurInv[colonne]& masqueHauteur);
	}


	public byte getHorizontalInv(int hauteur, int colonne) {
		return this.horizontalInv[hauteur];
	}


	public byte getDiagonal1Inv(int hauteur, int colonne) {
		byte masqueHauteur = 0b1111111;
		// on retourne le byte qui concerne le pion
		if (hauteur <= 3) {
			masqueHauteur >>= 3 - hauteur;
		} else {
			masqueHauteur <<= hauteur - 3;
		}
		return (byte) (getDiagonal1(hauteur, colonne, diagonal1Inv)& masqueHauteur);
	}


	public byte getDiagonal2Inv(int hauteur, int colonne) {
		byte masqueHauteur = 0b1111111;
		// on retourne le byte qui concerne le pion
		if (hauteur <= 3) {
			masqueHauteur >>= 3 - hauteur;
		} else {
			masqueHauteur <<= hauteur - 3;
		}
		return  (byte) (getDiagonal2(hauteur, colonne, diagonal2Inv) & masqueHauteur);		
	}

}
