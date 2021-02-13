package org.P4Modele_;

import java.util.Set;

public interface Neud {

	/**
	 * @return the id
	 */
	long getId();

	/**
	 * @return the niveau
	 */
	int getNiveau();

	/**
	 * @return the Calculer
	 */
	Calculer getCalculer();

	/**
	 * @return the parent
	 */
	Set<Long> getParent();

	/**
	 * @return the enfant
	 */
	Set<Long> getEnfant();

	/**
	 * @return if is a feuille
	 */
	boolean isFeuille();

	/**
	 * retourne si on est bien sur le niveau et si le neud est explorable
	 *
	 * @return the explorable
	 */
	boolean isExplorable(int niveau);

	/**
	 * @return the explorable
	 */
	boolean isExplorable();

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	String toString();

}