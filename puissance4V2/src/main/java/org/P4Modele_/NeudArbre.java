package org.P4Modele_;

import java.util.Set;

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
	void setCalculer(Calculer calculer);

	/**
	 * @param niveau
	 *            the niveau to set
	 */
	void setNiveau(int niveau);

	/**
	 * @param explorable
	 *            the explorable to set
	 */
	void setExplorable(boolean explorable);

	/**
	 * ajoute un parent au neud
	 *
	 * @param newParent
	 *            parent a ajouter
	 */
	void addParent(long newParent);

	/**
	 * ajoute un enfant au neud
	 *
	 * @param newEnfant
	 *            enfant a ajouter
	 */
	void addEnfant(long newEnfant);

	/**
	 * enleve un parent a enlever
	 *
	 * @param oldParent
	 *            parent a enlever
	 */
	void removeParent(Long oldParent);

	/**
	 * enleve un enfant oldEnfant
	 *
	 * @param oldEnfant
	 *            enfant a enlever
	 */
	void removeEnfant(Long oldEnfant);

	/**
	 * affecte la valeur feuille
	 *
	 * @param boolean1
	 */
	void setFeuille(boolean boolean1);

	/**
	 * modifie la liste des parents au neud
	 *
	 * @param Parent
	 *            list des parents (complette)
	 */
	void addParent(Set<Long> parent);

	/**
	 * modifie la liste des enfants au neud
	 *
	 * @param enfant
	 *            list des enfant (complette)
	 */
	void addEnfant(Set<Long> enfant);

}