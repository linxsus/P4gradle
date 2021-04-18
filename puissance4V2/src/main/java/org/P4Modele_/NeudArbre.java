package org.P4Modele_;


public interface NeudArbre extends Neud {

	/**
	 * @param id
	 *            the id to set
	 */
	void setId(long id);

	/**
	 * @param calculer
	 *            the calculer to set
	 */
	boolean setCalculer(Calculer calculer);

	/**
	 * @param niveau
	 *            the niveau to set
	 */
	void setNiveau(int niveau);

	/**
	 * @param explorable
	 *            the explorable to set
	 */
	boolean setExplorable(boolean explorable);

	/**
	 * ajoute un parent au neud
	 *
	 * @param newParent
	 *            parent a ajouter
	 */
	boolean addParent(long newParent);

	/**
	 * ajoute un enfant au neud
	 *
	 * @param newEnfant
	 *            enfant a ajouter
	 */
	boolean addEnfant(long newEnfant);

	/**
	 * enleve un parent a enlever
	 *
	 * @param oldParent
	 *            parent a enlever
	 */
	boolean removeParent(Long oldParent);

	/**
	 * enleve un enfant oldEnfant
	 *
	 * @param oldEnfant
	 *            enfant a enlever
	 */
	boolean removeEnfant(Long oldEnfant);

	/**
	 * affecte la valeur feuille
	 *
	 * @param boolean1
	 */
	boolean setFeuille(boolean boolean1);
	

}