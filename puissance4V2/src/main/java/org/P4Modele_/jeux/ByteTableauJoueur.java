package org.P4Modele_.jeux;

import org.P4Modele_.GestDonnee;

/**
 * tableau sous forme de byte pour 1 joueur de ces pions <br>
 * !!!attention du faite de sa structure en byte on ne peut pas modifier la
 * taille du jeux au dessu de 7*7!!!
 *
 * @author Xavier Gouraud
 *
 */
public class ByteTableauJoueur {
	// calcul du nb de colonne gagnable en diagonale
	/**
	 * nb de colonne diagonale possible
	 */
	// taille =HAUTEUR - 3 (nb de cas impossible pour avoir 4 pion successif en
	// hauteur)
	// + LARGEUR - 3 (nb de cas impossible pour avoir 4 pion successif en largeur)
	// -1 pour enlever le cas du coin qui est en double.
	private final static int NB_COLONNE_DIAGONALE = ((((GestDonnee.HAUTEUR - 3) + GestDonnee.LARGEUR) - 3) - 1);
	/**
	 * decalage du pion en 0,0 a effectuer pour une diagonal vers le haut
	 */
	// calcul du decalage diagonal vers le haut
	// decalage par raport au pion 0,0
	// le pion (0,0) peut faire partie d'une combinaison
	// gagnante et les pion au dessu de lui aussi jusqu'a hauteur_max-4
	// donc on doit le decaler de 0+hauteur_max-4
	private final static int DECALAGE_HAUT = (GestDonnee.HAUTEUR - 4);
	/**
	 * decalage du pion en 0,0 a effectuer pour une diagonal vers le bas
	 */
	// calcul du decalage diagonal vers le bas
	// decalage par raport au pion 0,0
	// le pion (0,0) ne fait pas partie d'une combinaison
	// gagnante et il faut le decale de 3 sur la droite pour qu'il puisse gangee
	// donc on doit le decaler de 0-3
	private final static int DECALAGE_BAS = -3;

	/**
	 * tableau de byte des colonne
	 */
	private byte[] hauteur = new byte[GestDonnee.LARGEUR];
	/**
	 * tableau de byte des diagonal vers le bas
	 */
	private byte[] diagonal1 = new byte[NB_COLONNE_DIAGONALE];
	/**
	 * tableau de byte des diagonal vers le haut
	 */
	private byte[] diagonal2 = new byte[NB_COLONNE_DIAGONALE];
	/**
	 * tableau de byte des largeur
	 */
	private byte[] horizontal = new byte[GestDonnee.HAUTEUR];

	/**
	 * retourne le Byte hauteur pour le pion en hauteur,colonne
	 *
	 * @param hauteur
	 *            hauteur du pion
	 * @param colonne
	 *            colonne du pion
	 * @return le byte hauteur
	 */
	public byte getHauteur(int hauteur, int colonne) {
		return this.hauteur[colonne];
	}

	/**
	 * retourne le Byte Diagonal1 pour le pion en hauteur,colonne
	 *
	 * @param hauteur
	 *            hauteur du pion
	 * @param colonne
	 *            colonne du pion
	 * @return le byte Diagonal1
	 */
	public byte getDiagonal1(int hauteur, int colonne) {
		// calcul du decalage vers la droite pour le pion hauteur,colonne
		// on est en digonal vers le bas donc plus on monte plus il y a de decalage
		// decalage=decalage de colonne + decalage hauteur+ decalage initiale
		int decalage = colonne + hauteur + DECALAGE_BAS;
		// si le pion decalee est se trouve dans diagonal1
		if ((decalage > -1) && (decalage < NB_COLONNE_DIAGONALE)) {
			// on retourne le byte qui concerne le pion
			return diagonal1[decalage];
		} else {
			return 0x00;
		}
	}

	/**
	 * retourne le Byte Diagonal2 pour le pion en hauteur,colonne
	 *
	 * @param hauteur
	 *            hauteur du pion
	 * @param colonne
	 *            colonne du pion
	 * @return le byte Diagonal2
	 */
	public byte getDiagonal2(int hauteur, int colonne) {
		// calcul du decalage vers la droite pour le pion hauteur,colonne
		// on est en digonal vers le haut donc plus on monte moin il y a de decalage
		// decalage=decalage de colonne - decalage hauteur+ decalage initiale
		int i = (colonne - hauteur) + DECALAGE_HAUT;
		// si le pion decalee est se trouve dans diagonal2
		if ((i > -1) && (i < NB_COLONNE_DIAGONALE)) {
			// on retourne le byte qui concerne le pion
			return diagonal2[i];
		} else {
			return 0x00;
		}
	}

	/**
	 * retourne le Byte Horizontal pour le pion en hauteur,colonne
	 *
	 * @param hauteur
	 *            hauteur du pion
	 * @param colonne
	 *            colonne du pion
	 * @return le byte Horizontal
	 */
	public byte getHorizontal(int hauteur, int colonne) {
		return this.horizontal[hauteur];
	}

	/**
	 * retourne le tableau de tous les Byte pour le pion en hauteur,colonne
	 *
	 * @param hauteur
	 *            hauteur du pion
	 * @param colonne
	 *            colonne du pion
	 * @return le tableau de tous les Byte Byte[4];
	 */
	public byte[] getTabJoueur(int hauteur, int colonne) {
		byte[] resultat = new byte[4];
		resultat[0] = getHauteur(hauteur, colonne);
		resultat[1] = getDiagonal1(hauteur, colonne);
		resultat[2] = getDiagonal2(hauteur, colonne);
		resultat[3] = getHorizontal(hauteur, colonne);
		return resultat;
	}

	/**
	 * affecte un pion en hauteur,colonne pour le tableau byte Hauteur
	 *
	 * @param hauteur
	 *            hauteur du pion
	 * @param colonne
	 *            colonne du pion
	 *
	 */
	protected void setHauteur(int hauteur, int colonne) {
		this.hauteur[colonne] = (byte) (this.hauteur[colonne] | ((byte) 1 << (byte) hauteur));
	}

	/**
	 * affecte un pion en hauteur,colonne pour le tableau byte Diagonal1
	 *
	 * @param hauteur
	 *            hauteur du pion
	 * @param colonne
	 *            colonne du pion
	 *
	 */
	protected void setDiagonal1(int hauteur, int colonne) {
		int i = (hauteur + colonne) + DECALAGE_BAS;
		if ((i > -1) && (i < NB_COLONNE_DIAGONALE)) {
			this.diagonal1[i] = (byte) (this.diagonal1[i] | ((byte) 1 << (byte) hauteur));
		}
	}

	/**
	 * affecte un pion en hauteur,colonne pour le tableau byte Diagonal2
	 *
	 * @param hauteur
	 *            hauteur du pion
	 * @param colonne
	 *            colonne du pion
	 *
	 */
	protected void setDiagonal2(int hauteur, int colonne) {
		int i = (+colonne - hauteur) + DECALAGE_HAUT;
		if ((i > -1) && (i < NB_COLONNE_DIAGONALE)) {
			this.diagonal2[i] = (byte) (this.diagonal2[i] | ((byte) 1 << (byte) hauteur));
		}
	}

	/**
	 * affecte un pion en hauteur,colonne pour le tableau byte hauteur
	 *
	 * @param hauteur
	 *            hauteur du pion
	 * @param colonne
	 *            colonne du pion
	 *
	 */
	protected void setHorizontal(int hauteur, int colonne) {
		this.horizontal[hauteur] = (byte) (this.horizontal[hauteur] | ((byte) 1 << (byte) colonne));
	}

	/**
	 * affecte un pion en hauteur,colonne pour le tableau
	 *
	 * @param hauteur
	 *            hauteur du pion
	 * @param colonne
	 *            colonne du pion
	 */
	public void ajouterPion(int hauteur, int colonne) {
		setHauteur(hauteur, colonne);
		setDiagonal1(hauteur, colonne);
		setDiagonal2(hauteur, colonne);
		setHorizontal(hauteur, colonne);
	}

	/**
	 * on enleve un pion en hauteur,colonne pour le tableau byte hauteur
	 *
	 * @param hauteur
	 *            hauteur du pion
	 * @param colonne
	 *            colonne du pion
	 *
	 */
	protected void remHauteur(int hauteur, int colonne) {
		this.hauteur[colonne] = (byte) (this.hauteur[colonne] & ~((byte) 1 << (byte) hauteur));
	}

	/**
	 * on enleve un pion en hauteur,colonne pour le tableau byte Diagonal1
	 *
	 * @param hauteur
	 *            hauteur du pion
	 * @param colonne
	 *            colonne du pion
	 *
	 */
	protected void remDiagonal1(int hauteur, int colonne) {
		// calcul du decalage vers la droite pour le pion hauteur,colonne
		// on est en digonal vers le bas donc plus on monte plus il y a de decalage
		// decalage=decalage de colonne + decalage hauteur+ decalage initiale
		int i = (hauteur + colonne) + DECALAGE_BAS;
		// si le pion decalee est se trouve dans diagonal1
		if ((i > -1) && (i < NB_COLONNE_DIAGONALE)) {
			// on enleve le bit
			this.diagonal1[i] = (byte) (this.diagonal1[i] & ~((byte) 1 << (byte) hauteur));
		}
	}

	/**
	 * on enleve un pion en hauteur,colonne pour le tableau byte Diagonal2
	 *
	 * @param hauteur
	 *            hauteur du pion
	 * @param colonne
	 *            colonne du pion
	 *
	 */
	protected void remDiagonal2(int hauteur, int colonne) {
		// calcul du decalage vers la droite pour le pion hauteur,colonne
		// on est en digonal vers le bas donc plus on monte plus il y a de decalage
		// decalage=decalage de colonne + decalage hauteur+ decalage initiale
		int i = (+colonne - hauteur) + DECALAGE_HAUT;
		// si le pion decalee est se trouve dans diagonal2
		if ((i >= 0) && (i < NB_COLONNE_DIAGONALE)) {
			// on enleve le bit
			this.diagonal2[i] = (byte) (this.diagonal2[i] & ~((byte) 1 << (byte) hauteur));
		}
	}

	/**
	 * on enleve un pion en hauteur,colonne pour le tableau byte horizontal
	 *
	 * @param hauteur
	 *            hauteur du pion
	 * @param colonne
	 *            colonne du pion
	 *
	 */
	protected void remHorizontal(int hauteur, int colonne) {
		this.horizontal[hauteur] = (byte) (this.horizontal[hauteur] & ~((byte) 1 << (byte) colonne));
	}

	/**
	 * on enleve un pion en hauteur,colonne pour tous les tableau byte
	 *
	 * @param hauteur
	 *            hauteur du pion
	 * @param colonne
	 *            colonne du pion
	 *
	 */
	public void enleverPion(int hauteur, int colonne) {
		remHauteur(hauteur, colonne);
		remDiagonal1(hauteur, colonne);
		remDiagonal2(hauteur, colonne);
		remHorizontal(hauteur, colonne);
	}

	/**
	 * retourne 1 si il y a un pion en hauteur,colonne
	 *
	 * @param hauteur
	 *            hauteur du pion
	 * @param colonne
	 *            colonne du pion
	 *
	 * @return 1 si il y a un pion en hauteur,colonne
	 *
	 */

	public int getPion(int hauteur, int colonne) {
		int resultat = 0;
		if ((byte) (this.hauteur[colonne] & ((byte) 1 << (byte) hauteur)) != 0) {
			resultat = 1;
		}
		return resultat;
	}
}
