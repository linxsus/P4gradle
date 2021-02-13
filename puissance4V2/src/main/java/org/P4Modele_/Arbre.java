package org.P4Modele_;

public interface Arbre {

	/**
	 * retourn le tron si il n'existe pas le cree
	 *
	 * @return the tron
	 */
	long getTron();

	/**
	 * initilaise le tron avec l'id. avant de l'affecter on verifie que le neud
	 * existe sinon on le cree.
	 *
	 * @param tron
	 *            the tron to set
	 */
	Neud setTron(Long tron, int niveau);

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	String toString();

	/**
	 * affecte la valeur caculer pour l'id idCalculer
	 *
	 * @param idCalculer
	 *            the id for set calculer
	 * @param calculer
	 *            the calculer to set
	 */
	void setCalculer(Long idCalculer, Calculer calculer);

	/**
	 * ajout un lien enfant parent fournit en parametre
	 *
	 * @param parent
	 *            le parent du lien
	 * @param enfant
	 *            l'enfant du lien
	 */
	Neud addEnfant(Long parent, Long enfant);

	/**
	 * mise a false de explorable du neud l
	 *
	 * @param l
	 *            neud a metre explorable a false
	 */
	void removeExplorable(long l);

	/**
	 * retourne un neud explorable du 'niveau'
	 *
	 * @param niveau
	 *            niveau sur le quelle on veut un explorable
	 * @return un neud
	 */
	Neud nextExplorable(int niveau);

	/**
	 * retourn le neud don l'id est id
	 *
	 * @param id
	 *            id du neud voulue
	 * @return neud dont l'id est id
	 */
	Neud getNeud(Long id);

	void setExplorableFalse(long id);

}