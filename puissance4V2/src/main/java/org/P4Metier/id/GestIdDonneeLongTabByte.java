package org.P4Metier.id;

import org.P4Metier.Factory.Factory;

public class GestIdDonneeLongTabByte extends GestIdDonneeLong {

	public GestIdDonneeLongTabByte(Factory factory) {
		super(factory);
	}

	@Override
	protected long[] getIdBaseDonneeTab() {
		long[] resultat = new long[2];
		resultat[0] = 0L;
		resultat[1] = 0L;

		int hauteur;
		for (int i = 0; i < LARGEUR; i++) {
			// pour tous les pion dans la colonne (et seulement les pion)
			hauteur = getNbPionColonne(i);
			resultat[0] = resultat[0] << hauteur;
			resultat[0] += baseDonnee.getByteTableauJoueur(2).getHauteur(0, i);

			// je decale pour pouvoir inserrer le nb de pion que j'ai mis
			resultat[0] *= 7;
			resultat[0] += hauteur;
		}

		for (int i = LARGEUR - 1; i >= 0; i--) {
			// pour tous les pion dans la colonne (et seulement les pion)
			hauteur = getNbPionColonne(i);
			// je decale pour avoir la place d'inserrer un pion
			resultat[1] = resultat[1] << hauteur;
			resultat[1] += baseDonnee.getByteTableauJoueur(2).getHauteur(0, i);

			// je decale pour pouvoir inserrer le nb de pion que j'ai mis
			resultat[1] *= 7;
			resultat[1] += hauteur;
		}
		return resultat;
	}
}
