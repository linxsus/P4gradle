package org.P4Metier;

import org.P4Modele_.GestDonnee;
import org.P4Modele_.jeux.ByteTableauJoueur;

/**
 *
 * ajout des fonctionnaliter pour de gestion d'un id<br>
 * et d'import et export.<br>
 *<br>
 * n'est pas reelement un extends GestDonneeBase mais se comporte comme si. voir.<br>
 * {@link #getBaseDonnee}<br>
 * <br>
 * class implemantant cette interface<br>
 * {@link GestIdDonneeInt3}<br>
 * {@link GestIdDonneeLong}<br>
 * {@link GestIdDonneeLongTabByte}<br>
 * 
 * @see GestDonnee
 * 
 * @param <T> format de l'id
 *
 * @author  <a href="mailto:xavier.gouraud@wanadoo.fr">xavier</a> 
 *
 */

public interface GestIdDonnee<T> extends GestDonnee {

	/**
	 * affect l'id a GestIdDonnee. !!! atention cela modifie tout GestIdDonnee on
	 * recharge les valeurs de GestIdDonnee qui a generer cette id
	 *
	 * @param id
	 *            id a recharger.
	 */
	abstract public void setIdBaseDonnee(T id);

	/**
	 * genere un nouvelle object GestIdDonnee avec l'id fournit en parametre et on
	 * le renvoie
	 *
	 * @param id
	 *            id du GestIDonnee a renvoyer
	 * @return GestIDonnee dont on fournit l'id
	 */
	abstract public GestIdDonnee<T> newBaseDonneeId(T id);

	@Override
	default public boolean enleverPion() {
		return getBaseDonnee().enleverPion();
	}

	@Override
	default public boolean ajoutPion(int colonne) {
		return getBaseDonnee().ajoutPion(colonne);
	};

	@Override
	default public boolean ajoutPion(int hauteur, int colonne, int joueur) {
		return getBaseDonnee().ajoutPion(hauteur, colonne, joueur);
	}

	/**
	 * renvoie si l'id est le miroire du tableau en cours
	 *
	 * @return true si l'id est le miroire, false sinon
	 */
	abstract public boolean isMiroire();

	/**
	 * retourne la GestBaseDonnee utiliser. 
	 * mise en public pour pouvoir l'utiliser dans l'interface 
	 * et simplifier la programation et retomber sur un pseudo
	 * heritage
	 *
	 * @return retourne la GestBaseDonnee utiliser
	 */
	abstract public GestDonnee getBaseDonnee();

	/**
	 * retourne l'id actuelle de GestIdDonnee celui ci change a chaque fois que l'on
	 * ajoute ou enleve un pion. L'id est une image du jeux compresse
	 *
	 * * pour simplifier la gestion des id et le stoquage on va considerer que le
	 * miroire du tableau en horizontale doit donnee le meme id.<br>
	 * un petit peux comme si je tournais le jeux <br>
	 * !!! cela ne pose pas de souci pour l'exploratin et le calcul. !!! mais pour
	 * analyser le jeux actuel il faut savoir si l'id actuel est inverser ou non.
	 * <br>
	 * expl:<br>
	 * {@code
	 * | | | | | | | | ____ | | | | | | | |}<br>
	 * {@code
	 * | | | | | | | | ____ | | | | | | | |}<br>
	 * {@code
	 * | | | | | | | | ____ | | | | | | | |}<br>
	 * {@code
	 * | | | | | | | | _et_ | | | | | | | |}<br>
	 * {@code
	 * | |O| | | | | | ____ | | | | | |O| |}<br>
	 * {@code
	 * |X|O|X| | | | | ____ | | | | |X|O|X| }<br>
	 * doivent renvoyer le meme id<br>
	 * <br>
	 * cela veux dire aussi que si j'exporte l'id et que je le reimporte je peux
	 * recuperer le jeux miroire.<br>
	 * cela ne pose pas de souci pour le calcul de gagner<br>
	 * pour tester les diferentes possibiliter <br>
	 *
	 * @return l'id actuelle de GestIdDonnee
	 */
	abstract public T getIdBaseDonnee();

	/**
	 * retourne l'id du tableau passer en parametre
	 * @see#getIdBaseDonnee()
	 *
	 * @param tableau
	 *            tableau sous forme [hauteur][largeur] du jeux
	 * @return l'id du jeux fournis en parametre
	 */
	abstract public T getIdBaseDonnee(int[][] tableau);

	/**
	 * retourne un GestBaseDonnee initialiser avec l'id fournit. ne modifie pas le
	 * GestIdDonnee en cours.
	 *
	 * @param id
	 *            id de la GestBaseDonnee a recuperer
	 * @return GestBaseDonnee generer
	 * 
	 * @see GestDonnee
	 */
	abstract public GestDonnee getDonneeId(T id);

	@Override
	default public int getNbPionJouer() {
		return getBaseDonnee().getNbPionJouer();
	}

	@Override
	default public int getNbPionColonne(int colonne) {
		return getBaseDonnee().getNbPionColonne(colonne);
	}

	@Override
	default int getTableau(int i, int j) {
		return getBaseDonnee().getTableau(i, j);
	}

	@Override
	default public int[] getDernierJouerPion() {
		return getBaseDonnee().getDernierJouerPion();
	}

	@Override
	default ByteTableauJoueur getByteTableauJoueur() {
		return getBaseDonnee().getByteTableauJoueur();
	}

	@Override
	default int[] getColoneJouable() {
		return getBaseDonnee().getColoneJouable();
	}

	@Override
	default int getJoueur() {
		return getBaseDonnee().getJoueur();
	}

	@Override
	default int getPrecedent() {
		return getBaseDonnee().getPrecedent();
	}

	@Override
	default void setDonnee(int[][] tableau, int colonne) {
		getBaseDonnee().setDonnee(tableau, colonne);
	}
	// impossible de declarer toString en default domage il faut donc pensser a le
	// faire dans la classe 'pseudo derivé'
	// default public String toString() {
	// return getBaseDonnee().toString();
	// };

	@Override
	default void setDonnee(int[][] tableau) {
		getBaseDonnee().setDonnee(tableau);

	}
}
