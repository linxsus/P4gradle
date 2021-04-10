package org.P4Metier.id;

import org.P4Metier.Factory.Factory;

/**
 * 
 * extend de la class {@link GestIdDonneeLong}
 * permet de calculer plus vite l'id lorsque l'on utilise  GestBaseDonneeByte comme implementation de GestBaseDonnee
 * 
 * @author  <a href="mailto:xavier.gouraud@wanadoo.fr">xavier</a> 
 *
 */
public class GestIdDonneeLongTabByte extends GestIdDonneeLong {

	public GestIdDonneeLongTabByte(Factory factory) {
		super(factory);
	}

	@Override
	protected long[] getIdBaseDonneeTab() {
		long[] resultat = new long[2];
		resultat[0] = 0L;
		resultat[1] = 0L;

		int hNormal;
		int hInverse;
		//pour toute les colonnes
		for (int i = 0, j =LARGEUR - 1; i < LARGEUR; i++ , j--) {
			// je recupere le nb de pion de la colonne
			hNormal = getNbPionColonne(i);
			hInverse = getNbPionColonne(j);
			// je decale le resultat du nb de pion de la colonne
			resultat[0] = resultat[0] << hNormal;
			resultat[1] = resultat[1] << hInverse;
			// je insere les pion dedans
			// le tableau du joueur 2 est sufisant puissque je ne prendrait que le nb de pion de la colonne 
			resultat[0] += baseDonnee.getByteTableauJoueur(2).getHauteur(0, i);
			resultat[1] += baseDonnee.getByteTableauJoueur(2).getHauteur(0, j);
			// je decale pour pouvoir inserrer le nb de pion que j'ai mis
			resultat[0] *= 7;
			resultat[1] *= 7;
			//je insere le nb pions 
			resultat[0] += hNormal;
			resultat[1] += hInverse;
		}

		return resultat;
	}
}
