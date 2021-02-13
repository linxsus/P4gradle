package org.P4Metier.ordi;

import org.P4Metier.GestIdDonnee;
import org.P4Metier.Factory.Factory;

/**
 * class pour le calcul du toujour gagner si on commance au milieux.
 * modification des fonctions calculPoids et calculResultat pour ne prendre en
 * compt que le chemin ou on gagne systematiquement.
 *
 * @author Xavier Gouraud
 *
 */
public class OrdinateurMilieux extends OrdinateurBasic {

	protected Factory factory;

	/**
	 * creation de la class avec comme DonneeBase donnee
	 *
	 * @param donnee
	 *            DonneeBase
	 */
	public OrdinateurMilieux(Factory factory, GestIdDonnee<Long> donnee) {
		super(factory, donnee);
	}

	/**
	 * calcul le param d'un tableau si peut importe ou je jour je perd alors il
	 * gagne sinon il perd.
	 *
	 * @param tabResultat
	 *            tableau de DonneeOrdinateurOld[] a calculer
	 * @return le param du tableau
	 */
	@Override
	protected int calculResultat(int[] tabResultat) {
		int resultat = -1;
		int total = 0;
		boolean gagnee = false;
		for (int i = 0; i < tabResultat.length; i++) // pour toutes les valeur du tableau
		{
			if (tabResultat[i] == 1000) {
				gagnee = true;
			}
			total += tabResultat[i]; // je les aditionnes
		}
		total /= tabResultat.length; // je le divise par le nb d'element du tableau.
		if (gagnee) {
			resultat = 1000;
		} else if (total == -1000) {
			resultat = -1000;
		} else {
			resultat = 0;
		}
		return resultat;
	}
}
