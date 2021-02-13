package org.P4Metier.id;

import org.P4Metier.GestIdDonnee;
import org.P4Metier.Factory.Factory;
import org.P4Modele_.GestDonnee;

/**
 * id=|nbPionColonne[0]|couleur pion[0][0]|couleur pion[0][1]|...|nbPionColonne[6]|couleur pion[6][0]|couleur pion[6][1]
 *
 * ou autre façon de le voire
 * 
 * id=nbPionColonne[0]*7*<br>
 * tableau[nbPionColonne[0]-1][0]*2*<br>
 * ...<br>
 * tableau[0][0]*2*<br>
 * nbPionColonne[1]*7*<br>
 * tableau[nbPionColonne[1]-1][1]*2*<br>
 * ...<br>
 * tableau[0][1]*2*<br>
 * ...<br>
 * nbPionColonne[6]*7*<br>
 * tableau[nbPionColonne[6]-1][6]*2*<br>
 * ...<br>
 *
 * tableau[0][6]*2*<br>
 *
 * on utilise aussi l'effet miroire
 *
 * @author Xavier Gouraud
 *
 */
public class GestIdDonneeLong implements GestIdDonnee<Long> {
	/**
	 * id jeux normal et id jeux miroire a la suite<br>
	 *
	 */
	// private Long[] idBaseDonnee=new Long[2];

	/**
	 * object GestBaseDonnee pour faire le pseudo heritage
	 */
	protected GestDonnee baseDonnee;

	protected Factory factory;

	/**
	 * constructeur
	 */
	public GestIdDonneeLong(Factory factory) {
		super();
		this.factory = factory;
		baseDonnee = factory.getGestBaseDonnee();
	}

	public GestIdDonneeLong(GestDonnee donneeId) {
		super();
		baseDonnee = donneeId;
	}

	@Override
	public Long getIdBaseDonnee() {
		long[] resultat = getIdBaseDonneeTab();

		if (resultat[0] < resultat[1]) {
			return resultat[0];
		}
		return resultat[1];
	}

	protected long[] getIdBaseDonneeTab() {
		long[] resultat = new long[2];
		resultat[0] = 0L;
		resultat[1] = 0L;

		int hauteur;
		for (int i = 0; i < LARGEUR; i++) {
			// pour tous les pion dans la colonne (et seulement les pion)
			hauteur = getNbPionColonne(i);
			for (int j = hauteur - 1; j >= 0; j--) {
				// je decale pour avoir la place d'inserrer un pion
				resultat[0] *= 2;
				// j'insere le pion
				// -1 pour avoir 0 pour joueur 1 et 1 pour joueur 2 vue que l'on ne prend que
				// les case du tableau avec un pion cela ne pose pas de souci
				resultat[0] += getTableau(j, i) - 1;
			}
			// je decale pour pouvoir inserrer le nb de pion que j'ai mis
			resultat[0] *= 7;
			resultat[0] += hauteur;
		}

		for (int i = LARGEUR - 1; i >= 0; i--) {
			// pour tous les pion dans la colonne (et seulement les pion)
			hauteur = getNbPionColonne(i);
			for (int j = hauteur - 1; j >= 0; j--) {
				// je decale pour avoir la place d'inserrer un pion
				resultat[1] *= 2;
				// j'insere le pion
				// -1 pour avoir 0 pour joueur 1 et 1 pour joueur 2 vue que l'on ne prend que
				// les case du tableau avec un pion cela ne pose pas de souci
				resultat[1] += getTableau(j, i) - 1;
			}
			// je decale pour pouvoir inserrer le nb de pion que j'ai mis
			resultat[1] *= 7;
			resultat[1] += hauteur;
		}

		return resultat;
	}

	@Override
	public Long getIdBaseDonnee(int[][] tableau) {
		Long resultat1 = 0L;
		Long resultat2 = 0L;
		int hauteur = 0;
		// pour toute les colonne
		for (int i = 0; i < LARGEUR; i++) {
			// pour tous les pion dans la colonne (et seulement les pion)
			for (int j = HAUTEUR - 1; j >= 0; j--) {
				// je decale pour avoir la place d'inserrer un pion
				if (tableau[j][i] > 0) {
					resultat1 *= 2;
					// j'insere le pion
					// -1 pour avoir 0 pour joueur 1 et 1 pour joueur 2 vue que l'on ne prend que
					// les case du tableau avec un pion cela ne pose pas de souci
					resultat1 += tableau[j][i] - 1;
					hauteur++;
				}
			}
			// je decale pour pouvoir inserrer le nb de pion que j'ai mis
			resultat1 *= 7;
			resultat1 += hauteur;
			hauteur = 0;
		}

		for (int i = LARGEUR - 1; i >= 0; i--) {
			// pour tous les pion dans la colonne (et seulement les pion)

			for (int j = HAUTEUR - 1; j >= 0; j--) {
				// je decale pour avoir la place d'inserrer un pion
				if (tableau[j][i] > 0) {
					resultat2 *= 2;
					// j'insere le pion
					// -1 pour avoir 0 pour joueur 1 et 1 pour joueur 2 vue que l'on ne prend que
					// les case du tableau avec un pion cela ne pose pas de souci
					resultat2 += tableau[j][i] - 1;
					hauteur++;
				}
			}
			// je decale pour pouvoir inserrer le nb de pion que j'ai mis
			resultat2 *= 7;
			resultat2 += hauteur;
			hauteur = 0;
		}

		if (resultat1 < resultat2) {
			return resultat1;
		}
		return resultat2;
	}

	@Override
	public GestDonnee getBaseDonnee() {
		return baseDonnee;
	}

	@Override
	public GestDonnee getDonneeId(Long idOriginal) {
		long id = idOriginal;// copie pour eviter de modifier le long original.
		GestDonnee resultat = factory.getGestBaseDonnee();
		// pour toute les colonne en partant de la derniere vue que c'est la derniere
		// mise
		for (int i = LARGEUR - 1; i >= 0; i--) {
			// je recupere le nb de pion mis
			int hauteur = (int) (id % 7);
			// je decale pour pouvoir lire l'info suivante
			id /= 7;
			// pour tous les pions de la colonne
			for (int j = 0; j < hauteur; j++) {
				// j'insere le pion dans le param
				resultat.ajoutPion(j, i, (int) (id % 2) + 1);
				// je decale pour pouvoir lire l'info suivante
				id /= 2;
			}
		}
		// si il reste encod des info a lire c'est qu'il y a une erreur.
		if (id != 0) {
			System.out.println("erreur decriptage");
			return null;
		}
		return resultat;
	}

	@Override
	public void setIdBaseDonnee(Long id) {
		baseDonnee = getDonneeId(id);
	}

	@Override
	public GestIdDonnee<Long> newBaseDonneeId(Long id) {
		return new GestIdDonneeLong(getDonneeId(id));
	}

	@Override
	public String toString() {
		return getBaseDonnee().toString();
	}

	@Override
	public boolean isMiroire() {
		long[] resultat = getIdBaseDonneeTab();

		if (resultat[0] < resultat[1]) {
			return false;
		}
		return true;
	}

	@Override
	public GestDonnee getNewBase() {
		return baseDonnee.getNewBase();
	};
}
