package org.P4Metier;

import org.P4Modele_.GestDonnee;

/**
 * interface pour les class gagnee<br>
 * <br>
 * class implementant cette interface<br>
 * {@link GagneeTabByte}<br>
 * {@link GagneeTabInt}<br>
 * 
 * @author  <a href="mailto:xavier.gouraud@wanadoo.fr">xavier</a> 
 *
 */
public interface Gagnee {

	/**
	 * verifie si le dernier pion jouer dans donnee a fait gagner le dernier joueur.
	 *
	 * @param donnee
	 *            donnee a analyser
	 * @return retourne true si c'est gagner false sinon
	 */
	abstract boolean isGagnee(GestDonnee donnee);

}
