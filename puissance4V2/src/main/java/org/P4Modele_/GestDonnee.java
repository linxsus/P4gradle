package org.P4Modele_;

import java.util.Arrays;

import org.P4Modele_.jeux.ByteTableauJoueur;

/**
 * gestion des donnees principal du jeux
 *
 * -hauteur <br>
 * -largeur<br>
 * -ensemble des pion jouer<br>
 * -joueur en cours<br>
 * -joueur precedent<br>
 * -nb de pion jouer<br>
 *
 * @author Xavier Gouraud
 *
 */
public interface GestDonnee {
	/**
	 * hauteur du jeux
	 */
	public static final int HAUTEUR = 6;
	/**
	 * largeur du jeux
	 */
	public static final int LARGEUR = 7;

	/**
	 * enleve le dernier pion jouer
	 *
	 * @return retourne true si le pion a bien ete enlever sinon false
	 */
	abstract public boolean enleverPion();

	/**
	 * ajoute un pion en colonne. le pion sera toujour de la couleur de joueur en
	 * cours.
	 *
	 * @param colonne
	 *            colonne sur la quelle on ajoute le pion
	 * @return retourne true si le pion a ete ajouter false sinon.
	 */
	abstract public boolean ajoutPion(int colonne);

	/**
	 * ajoute un pion du joueur en hauteur,colonne.
	 *
	 * @param hauteur
	 *            hauteur sur la quelle on ajoute le pion
	 * @param colonne
	 *            colonne sur la quelle on ajoute le pion
	 * @param joueur
	 *            pion du joueur a ajouter.
	 * @return retourne true si le pion a ete ajouter false sinon.
	 */
	abstract public boolean ajoutPion(int hauteur, int colonne, int joueur);

	/**
	 * demande le nb de pion jouer sur tout le tableau
	 *
	 * @return le nb de pion jouer sur tout le tableau
	 */
	abstract public int getNbPionJouer();

	/**
	 * retourne le nb de pion dans la colonne (colonne)
	 *
	 * @param colonne
	 *            int colonne colonne sur la quelle on veux avoir le nb de pion.
	 */
	abstract public int getNbPionColonne(int colonne);

	/**
	 * retourn le pion qui est en ligne i colone j pas de pion=0, pion joueur 1=1,
	 * pion joueur 2=2
	 *
	 * @param i
	 *            ligne
	 * @param j
	 *            colone
	 * @return le pion qui est en ligne i colone j
	 */
	public int getTableau(int i, int j);

	/**
	 * retourne le tableau des dernier pion jouer
	 *
	 * @return le tableau des dernier pion jouer
	 */
	public int[] getDernierJouerPion();

	/**
	 * retourne une nouvelle instance vierge
	 *
	 * @return nouvelle instance vierge
	 */
	public GestDonnee getNewBase();

	/**
	 * retourne le ByteTableauJoueur du joueur en cours
	 */
	default public ByteTableauJoueur getByteTableauJoueur() {
		System.out.println("fonction uniquement utilisable avec GestBaseDonneeByte");
		return null;
	}

	/**
	 * retourne le ByteTableauJoueur du joueur en parametre
	 *
	 */
	default public ByteTableauJoueur getByteTableauJoueur(int i) {
		System.out.println("fonction uniquement utilisable avec GestBaseDonneeByte");
		return null;
	}

	/**
	 * retourne le joueur en cours
	 *
	 * @return joueur en cours
	 */
	default public int getJoueur() {
		return (getNbPionJouer() % 2) + 1;
	}

	/**
	 * retourne le joueur qui vien de joueur
	 *
	 * @return le joueur qui vien de joueur
	 */
	default public int getPrecedent() {
		int nbPionJouer = getNbPionJouer() - 1;
		if (nbPionJouer >= 0) {
			return (nbPionJouer % 2) + 1;
		}
		return 0;
	}

	/**
	 * retourne sous forme de tableau toute les colones jouable (qui ne sont pas
	 * arriver au max)
	 *
	 * @return le tableau de toute les colones jouable
	 */
	default public int[] getColoneJouable() {
		int[] temps = new int[LARGEUR];
		int nbJouable = 0;
		for (int i = 0; i < LARGEUR; i++) {
			int colonne = (LARGEUR / 2) + (((1 - (2 * (i % 2))) * (i + 1)) / 2);
			if (getNbPionColonne(colonne) < 6) {
				temps[nbJouable] = colonne;
				nbJouable++;
			}
		}
		return Arrays.copyOfRange(temps, 0, nbJouable);
	}

	/**
	 * retourn le dernier pion jouer
	 *
	 * @return
	 */
	default int getDernierJouer() {
		int[] jouerPion = getDernierJouerPion();
		return jouerPion[jouerPion.length - 1];
	}

	/**
	 * initialise l'object avec le tableau fournit en parametre
	 *
	 * @param tableau
	 *            tableau de int de structure [hauteur][largeur] avec 0 pas de pion,
	 *            1 joueur 1, 2 joueur 2
	 */
	abstract void setDonnee(int[][] tableau);
	// {
	// init();
	// for (int i = 0; i < HAUTEUR; i++) {
	// for (int j = 0; j < LARGEUR; j++) {
	// ajoutPion(i, j, tableau[i][j]);
	// }
	// }
	// }

	/**
	 * initialise l'object avec le tableau fournit en parametre,et la derniere
	 * colonne jouer
	 *
	 * {@code {setDonnee(tableau); ajoutDernierJouer(colonne); } }
	 *
	 * @param tableau
	 *            tableau de int de structure [hauteur][largeur] avec 0 pas de pion,
	 *            1 joueur 1, 2 joueur 2
	 */
	abstract void setDonnee(int[][] tableau, int colonne);
}
