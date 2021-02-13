package org.P4Metier;

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
	
	//TODO
	public void setTourMax(int nbtour);

}
