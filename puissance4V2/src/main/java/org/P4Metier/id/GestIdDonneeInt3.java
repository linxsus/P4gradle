package org.P4Metier.id;

import java.util.Arrays;

import org.P4Metier.GestIdDonnee;
import org.P4Metier.Factory.Factory;
import org.P4Modele_.GestDonnee;

/**
 * class pour le calcul de l'id de type int[3]<br>
 *
 * construction de l'id<br>
 * on travaille en base 3<br>
 * 1er chifre du 1er int correspond au pion en [0][0]<br>
 * 2eme chifre du 1er int correspond au pion en [0][1]<br>
 * 3eme chifre du 1er int correspond au pion en [0][2]<br>
 * 4eme chifre du 1er int correspond au pion en [0][3]<br>
 * ...<br>
 * 8eme chifre du 1er int correspond au pion en [1][0]<br>
 * 8eme chifre du 1er int correspond au pion en [1][1]<br>
 * ...<br> 
 * 1er chifre du 2eme int correspond au pion en [2][0]<br>
 * 2eme chifre du 2eme int correspond au pion en [2][1]<br>
 *<br>
 * ce qui donne pour ajouter un pion du joueur 2 en [0][0] on a donc<br>
 * int[0]=(base3) 0000000|0000002<br>
 * (en base 10) 2 <br>
 * int[1]=(base3) 0000000|0000000<br>
 * (en base 10) 0 <br>
 * int[2]=(base3) 0000000|0000000<br>
 * (en base 10) 0
 *
 * pour ajouter un pion du joueur 2 en [5][1] on a donc<br>
 * int[0]=(base3) 0000000|0000000<br>
 * (en base 10) 0 <br>
 * int[1]=(base3) 0000000|0000000<br>
 * (en base 10) 0 <br>
 * int[2]=(base3) 0000020|0000000<br>
 * (en base 10) 13122<br>
 * <br>
 *
 * @see GestIdDonnee
 *
 * @author  <a href="mailto:xavier.gouraud@wanadoo.fr">xavier</a> 
 *
 */
public class GestIdDonneeInt3 implements GestIdDonnee<int[]> {

	/**
	 * id jeux normal et id jeux miroire a la suite<br>
	 *
	 */
	private int idBaseDonnee[] = new int[6];

	/**
	 * object GestBaseDonnee pour faire le pseudo heritage
	 */
	private GestDonnee baseDonnee;

	/**
	 *  le factory 
	 */
	protected Factory factory;
	
	
	/**
	 *  tableau contenant Mat.pow(3,x) evite de refaire sans arret des calcul.
	 */
	private final int[] matPow3 = { 1, 3, 9, 27, 81, 243, 729, 2187, 6561, 19683, 59049, 177147, 531441, 1594323 };

	/**
	 * constructeur
	 * 
	 * @param factory le factory pour cree des object
	 */
	public GestIdDonneeInt3(Factory factory) {
		super();
		this.factory = factory;
		baseDonnee = factory.getGestBaseDonnee();
	}

	
	/**
	 * constructeur avec comme parametre un gestDonnee deja d'initialiser
	 * 
	 * @param donneeId un gestDonnee deja d'initialiser
	 * @param factory le factory pour cree des object
	 */
	public GestIdDonneeInt3(GestDonnee donneeId,Factory factory) {
		super();
		baseDonnee = donneeId;
		idBaseDonnee = CalculInitIdBaseDonnee();
		this.factory = factory;
	}

	@Override
	public boolean ajoutPion(int colonne) {
		// si le pion est dans le tableau
		// nessesaire car colonne peut venir de saisie utilisateur.
		if ((colonne > 0) && (colonne <= GestDonnee.LARGEUR)) {
			// on enleve 1 car recuperation de la saisie utilisateur.
			--colonne;
			// on calcul sur quelle indice du tableau on est
			// ligne 1 a 2 indice 0
			// ligne 3 a 4 indice 1
			// ligne 5 a 6 indice 2
			int nbPionColonne = getNbPionColonne(colonne);
			int indice = nbPionColonne / 2;
			// on calcul les nouveau id (id 'normal' et 'inverse')
			int puissance1 = ((7 * (nbPionColonne % 2)) + colonne);
			int puissance2 = ((7 * (nbPionColonne % 2)) - colonne) + 6;
			idBaseDonnee[indice] += (getJoueur() * matPow3[puissance1]);
			idBaseDonnee[indice + 3] += (getJoueur() * matPow3[puissance2]);
			// on fait apelle a la classe mere
			return getBaseDonnee().ajoutPion(colonne + 1);
		}
		return false;
	}

	@Override
	public boolean enleverPion() {
		// on recupere sur quelle colonne le dernier pion a ete jouer
		int colonneDerJouer = getDernierJouerPion()[getNbPionJouer() - 1];
		// on calcul sur quelle indice du tableau on est
		int nbPionColonne = getNbPionColonne(colonneDerJouer) - 1;
		int indice = nbPionColonne / 2;
		// on calcul les nouveau id (id 'normal' et 'inverse')
		int puissance1 = ((GestDonnee.LARGEUR * (nbPionColonne % 2)) + colonneDerJouer);
		int puissance2 = (((GestDonnee.LARGEUR * (nbPionColonne % 2)) - colonneDerJouer) + GestDonnee.LARGEUR) - 1;
		idBaseDonnee[indice] -= (getPrecedent() * matPow3[puissance1]);
		idBaseDonnee[indice + 3] -= (getPrecedent() * matPow3[puissance2]);
		// on fait apelle a la classe mere
		return getBaseDonnee().enleverPion();
	}

	@Override
	public int[] getIdBaseDonnee() {
		return getIdBaseDonnee(idBaseDonnee);
	}

	/**
	 * retourne l'id le plus petit dans le tableau de 2 id fournis en parametre
	 *
	 * @param ids
	 *            les 2 id 'normal' et inverser' dans le meme tableau a annaliser.
	 * @return l'id le plus petit
	 */
	private int[] getIdBaseDonnee(int[] ids) {
		int[] resultat;
		// calcul pour savoir si c'est l'id 'normal' ou 'inverse' qui est le plus petit
		boolean premiereTranche = false;
		if (ids[2] < ids[5]) {
			premiereTranche = true;
		} else if ((ids[2] == ids[5]) && (ids[1] < ids[4])) {
			premiereTranche = true;
		} else if ((ids[1] == ids[4]) && (ids[0] < ids[3])) {
			premiereTranche = true;
		}

		// on affecte le param en fonction de ce qui a ete calculer
		if (premiereTranche) {
			resultat = Arrays.copyOfRange(ids, 0, 3);
		} else {
			resultat = Arrays.copyOfRange(ids, 3, 6);
		}

		return resultat;
	}

	@Override
	public int[] getIdBaseDonnee(int[][] tableau) {
		int[] resultat = new int[6];
		// on divise la hauteur en 3
		for (int k = 0; k < 3; k++) {
			// pour tous les pions qui vont dans cette id[]
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < GestDonnee.LARGEUR; j++) {
					// gestion de l'id 'normal'
					{
						// decalage pour une base3
						resultat[2 - k] *= 3;
						// je met le pion
						resultat[2 - k] += tableau[5 - (2 * k) - i][j];
					}
					// gestion de l'id 'inverser'
					{
						// decalage pour une base3
						resultat[5 - k] *= 3;
						// je met le pion
						resultat[5 - k] += tableau[5 - (2 * k) - i][6 - j];
					}
				}
			}
		}
		return getIdBaseDonnee(resultat);
	}

	@Override
	public GestDonnee getBaseDonnee() {
		return baseDonnee;
	}

	@Override
	public GestDonnee getDonneeId(int[] id) {
		GestDonnee temp = factory.getGestBaseDonnee();
		// pour tous les id
		for (int k = 0; k < 3; k++) {
			// optimise un peut et surtout evite de modifier l'original
			int idCopie = id[k];
			// pour toutes les 2 ligne comprise dans l'id
			for (int i = 0; i < 2; i++) {
				// pour toute les colonnes comprise dans l'id
				for (int j = 0; j < GestDonnee.LARGEUR; j++) {
					// ajoute le pion dans la nouvelle base si c'est un joueur
					int joueur = idCopie % 3;
					temp.ajoutPion((k * 2) + i, j, joueur);
					// decalage pour la base 3
					idCopie /= 3;
				}
			}
		}
		return temp;
	}

	@Override
	public void setIdBaseDonnee(int[] id) {
		baseDonnee = getDonneeId(id);
		idBaseDonnee = CalculInitIdBaseDonnee();
	}

	@Override
	public GestIdDonnee<int[]> newBaseDonneeId(int[] id) {
		return new GestIdDonneeInt3(getDonneeId(id),factory);
	}

	@Override
	public String toString() {
		return getBaseDonnee().toString();
	};

	/**
	 * recalcul complet de l'id. utile en cas d'import
	 *
	 * @return les 2 id normal et inverser (6 int)
	 */
	private int[] CalculInitIdBaseDonnee() {
		int[] resultat = new int[6];
		for (int k = 0; k < 3; k++) {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < GestDonnee.LARGEUR; j++) {
					resultat[2 - k] *= 3;
					resultat[2 - k] += getTableau((5 - (2 * k) - i), j);
					resultat[5 - k] *= 3;
					resultat[5 - k] += getTableau((5 - (2 * k) - i), 6 - j);
				}
			}
		}
		return resultat;
	}

	@Override
	public boolean isMiroire() {
		// calcul pour savoir si c'est l'id 'normal' ou 'inverse' qui est le plus petit
		boolean premiereTranche = false;
		if (idBaseDonnee[2] < idBaseDonnee[5]) {
			premiereTranche = true;
		} else if ((idBaseDonnee[2] == idBaseDonnee[5]) && (idBaseDonnee[1] < idBaseDonnee[4])) {
			premiereTranche = true;
		} else if ((idBaseDonnee[1] == idBaseDonnee[4]) && (idBaseDonnee[0] < idBaseDonnee[3])) {
			premiereTranche = true;
		}

		return premiereTranche;
	}

	@Override
	public GestDonnee getNewBase() {
		return baseDonnee.getNewBase();
	}

	@Override
	public void setDonnee(int[][] tableau) {
		GestIdDonnee.super.setDonnee(tableau);
		idBaseDonnee = CalculInitIdBaseDonnee();
	}
}
