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
	 * @return explorable
	 */
	boolean isExplorable(int niveau);

	/**
	 * @return explorable
	 */
	boolean isExplorable();

	/**
	 * @return supprimable
	 */
	boolean isSupprimable();
	
	/**
	 * retourne si on est bien sur le niveau et si le neud est supprimable
	 *
	 * @return supprimable
	 */
	boolean isSupprimable(int niveau);
	
	/**
	 * affect le neud en tant que tron
	 */
	void setTron(boolean tron);
	
	/**
	 * demande si le neud est un tron
	 * 
	 * @return tron
	 */
	boolean isTron();
	
	/**
	 * retourne l'etat du neud <br>
	 * a lire bit a bit <br>
	 * <code>
	 * |tron|supprimer|explorable|feuille|
	 * |__x_|____x____|____x_____|___x___|
	 * </code><br>
	 * exple: en decimal 3 donne 0011 en binaire<br>
	 * donc c'est un  explorable et une feuille
	 * @return etat
	 */
	int etat();
	
	
	
	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	String toString();

}