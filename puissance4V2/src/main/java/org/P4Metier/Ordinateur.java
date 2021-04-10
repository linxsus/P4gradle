package org.P4Metier;

/**
 * Ordinateur qui permet de savoir sur quelle colonne il est le plus interessant de jouer<br>
 * {@link #jouer(GestIdDonnee)} retourne la colonne a jouer pour le jeux fourni en parametre<br>
 * {@link #jouer()} retourne la colonne a jouer pour le jeux en cours<br>
 * {@link #setTourMax} definit la profondeur de recherche de l'ordinateur.<br>
 * 
 * @author  <a href="mailto:xavier.gouraud@wanadoo.fr">xavier</a> 
 *
 * @param <T> format de l'Id
 */
public interface Ordinateur<T> {

	/**
	 * retourn la colone a jouer pour les donnee donnee !!! reinitialise les donnee
	 * lors de la creation !!!
	 *
	 * @param donnee
	 *            Donnee
	 * @return colone a jouer pour les donnee donnee
	 */
	public int jouer(GestIdDonnee<T> donnee);

	/**
	 * retourne la colone a jouer
	 *
	 * @return colone a jouer
	 */
	public int jouer();
	
	
	/**
	 * definit la profondeur de recherche de l'ordinateur
	 * 
	 * @param nbtour
	 */
	public void setTourMax(int nbtour);

}
