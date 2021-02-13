package org.P4Donnee_;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.P4Modele_.GestDonnee;
import org.junit.jupiter.api.Test;

public interface GestBaseDonneeTest {

	public abstract GestDonnee createInstance();

	@Test
	default void testGetJoueur() {
		GestDonnee donnee = createInstance();
		assertTrue(donnee.getJoueur() == 1, "test de getJoueur init");
		donnee.ajoutPion(7);
		assertTrue(donnee.getJoueur() == 2, "test de getJoueur apres un ajout");
		donnee.ajoutPion(7);
		assertTrue(donnee.getJoueur() == 1, "test de getJoueur apres 2 ajout");

	}

	@Test
	default void testGetPrecedent() {
		GestDonnee donnee = createInstance();
		assertTrue(donnee.getPrecedent() == 0, "test de getPrecedent init");
		donnee.ajoutPion(7);
		assertTrue(donnee.getPrecedent() == 1, "test de getPrecedent apres un ajout " + donnee.getPrecedent());
		donnee.ajoutPion(7);
		assertTrue(donnee.getPrecedent() == 2, "test de getPrecedent apres 2 ajout");
		donnee.ajoutPion(7);
		assertTrue(donnee.getPrecedent() == 1, "test de getPrecedent apres 3 ajout");
	}

	@Test
	default void testAjoutPion() {
		GestDonnee donnee = createInstance();
		assertTrue(donnee.ajoutPion(7), "test de ajout pion a 7  doit revoyer true");
		assertTrue(!donnee.ajoutPion(8), "test de ajout pion a 8  doit revoyer false");
		assertTrue(!donnee.ajoutPion(0), "test de ajout pion a 0  doit revoyer false");
		assertTrue(donnee.getPrecedent() == 1, "verifie que precedent a bien changer apres un ajout");
		assertTrue(donnee.getJoueur() == 2, "verifie que Joueur a bien changer apres un ajout");
		assertTrue(donnee.getTableau(0, 6) == 1, "apres un ajout verifier qu'il est bien ajouter");
		donnee.ajoutPion(7);
		donnee.ajoutPion(7);
		donnee.ajoutPion(7);
		donnee.ajoutPion(7);
		donnee.ajoutPion(7);
		assertTrue(donnee.getTableau(5, 6) == 2,
				"test de ajoutPion en 6,7 devrait etre a 2 et est a " + donnee.getTableau(5, 6));
		assertTrue(!donnee.ajoutPion(7), "test de ajout pion a 7 alors que coloneest pleine doit revoyer false");
	}

	@Test
	default void testGetTableau() {
		GestDonnee donnee = createInstance();
		donnee.ajoutPion(7);
		donnee.ajoutPion(7);
		donnee.ajoutPion(7);
		donnee.ajoutPion(7);
		donnee.ajoutPion(7);
		assertTrue(donnee.getTableau(4, 6) == 1,
				"test de getTableau en 5,7 devrait etre a 1 et est a " + donnee.getTableau(4, 6));
		donnee.ajoutPion(1);
		donnee.ajoutPion(1);
		donnee.ajoutPion(1);
		donnee.ajoutPion(1);
		donnee.ajoutPion(1);
		assertTrue(donnee.getTableau(4, 0) == 2,
				"test de getTableau en 5,1 devrait etre a 2 et est a " + donnee.getTableau(4, 0));
	}

	// @Test
	// default void testGetTableauExceptionSurLargeurMax() {
	//
	// GestBaseDonnee donnee = createInstance();
	// assertThrows(ArrayIndexOutOfBoundsException.class, new Executable() {
	// @Override
	// public void execute() throws Throwable {
	// donnee.getTableau(0, GestBaseDonnee.LARGEUR);
	// }
	// });
	// }

	@Test
	default void testgetNbPionJouer() {
		GestDonnee donnee = createInstance();
		donnee.ajoutPion(7);
		donnee.ajoutPion(7);
		donnee.ajoutPion(7);
		donnee.ajoutPion(7);
		donnee.ajoutPion(7);
		assertTrue(donnee.getNbPionJouer() == 5,
				"test de getNbPionJouer devrait etre a 5 et est a " + donnee.getNbPionJouer());
		donnee.ajoutPion(1);
		donnee.ajoutPion(1);
		donnee.ajoutPion(1);
		donnee.ajoutPion(1);
		donnee.ajoutPion(1);
		assertTrue(donnee.getNbPionJouer() == 10,
				"test de getNbPionJouer devrait etre a 10 et est a " + donnee.getNbPionJouer());
	}

	@Test
	default void testgetDernierJouerPion() {
		GestDonnee donnee = createInstance();
		donnee.ajoutPion(7);
		donnee.ajoutPion(6);
		donnee.ajoutPion(3);
		donnee.ajoutPion(1);
		donnee.ajoutPion(0); // on teste aussi le ajouter pion
		int[] temp = { 6, 5, 2, 0 };
		assertTrue(Arrays.equals(temp, donnee.getDernierJouerPion()), "test de getDernierJouerPion() devrait etre a "
				+ Arrays.toString(temp) + " et est a " + Arrays.toString(donnee.getDernierJouerPion()));
		donnee.ajoutPion(1);
		donnee.ajoutPion(3);
		donnee.ajoutPion(7);
		donnee.ajoutPion(7);
		donnee.ajoutPion(5);
		temp = new int[] { 6, 5, 2, 0, 0, 2, 6, 6, 4 };
		assertTrue(Arrays.equals(temp, donnee.getDernierJouerPion()),
				"test de getDernierJouerPion() devrait etre a " + Arrays.toString(donnee.getColoneJouable())
						+ " et est a " + Arrays.toString(donnee.getDernierJouerPion()));
	}

	@Test
	default void testgetColoneJouable() {
		GestDonnee donnee = createInstance();
		donnee.ajoutPion(7);
		donnee.ajoutPion(7);
		donnee.ajoutPion(7);
		donnee.ajoutPion(7);
		donnee.ajoutPion(7);
		int[] temp = { 3, 2, 4, 1, 5, 0, 6 };
		assertTrue(Arrays.equals(temp, donnee.getColoneJouable()),
				"test de getColoneJouable() devrait etre a " + Arrays.toString(donnee.getColoneJouable()) + " et est a "
						+ Arrays.toString(donnee.getColoneJouable()));
		donnee.ajoutPion(7);
		temp = new int[] { 3, 2, 4, 1, 5, 0 };
		assertTrue(Arrays.equals(temp, donnee.getColoneJouable()),
				"test de getColoneJouable() devrait etre a " + Arrays.toString(donnee.getColoneJouable()) + " et est a "
						+ Arrays.toString(donnee.getColoneJouable()));

		donnee.ajoutPion(1);
		donnee.ajoutPion(1);
		donnee.ajoutPion(1);
		donnee.ajoutPion(1);
		donnee.ajoutPion(1);
		donnee.ajoutPion(1);
		temp = new int[] { 3, 2, 4, 1, 5 };
		assertTrue(Arrays.equals(temp, donnee.getColoneJouable()),
				"test de getColoneJouable() devrait etre a " + Arrays.toString(donnee.getColoneJouable()) + " et est a "
						+ Arrays.toString(donnee.getColoneJouable()));

	}

	@Test
	default void testgetNbPionColonne() {
		GestDonnee donnee = createInstance();
		donnee.ajoutPion(7);
		donnee.ajoutPion(7);
		donnee.ajoutPion(7);
		donnee.ajoutPion(7);
		donnee.ajoutPion(7);
		assertTrue(donnee.getNbPionColonne(6) == 5,
				"test de getNbPionColonne(6) devrait etre a 5 et est a " + donnee.getNbPionColonne(6));
		donnee.ajoutPion(7);
		donnee.ajoutPion(1);
		donnee.ajoutPion(1);
		donnee.ajoutPion(1);
		donnee.ajoutPion(1);
		donnee.ajoutPion(1);
		donnee.ajoutPion(1);
		assertTrue(donnee.getNbPionColonne(0) == 6,
				"test de getNbPionColonne(0) devrait etre a 6 et est a " + donnee.getNbPionColonne(0));
	}

	@Test
	default void testAjoutPion_H_L_J() {
		GestDonnee donnee = createInstance();
		donnee.ajoutPion(4, 6, 1);
		donnee.ajoutPion(5, 5, 2);
		assertTrue(donnee.getTableau(4, 6) == 1,
				"test de testAjoutPion_H_L_J 5,7 devrait etre a 1 et est a " + donnee.getTableau(4, 6));
		assertTrue(donnee.getTableau(5, 5) == 2,
				"test de testAjoutPion_H_L_Jen 5,5 devrait etre a 2 et est a " + donnee.getTableau(5, 5));
		assertTrue(donnee.getNbPionJouer() == 2,
				"test de testAjoutPion_H_L_J le nb de pion jouer devrait etre 2 et est a " + donnee.getNbPionJouer());
		donnee.ajoutPion(0, 0, 2);
		donnee.ajoutPion(1, 0, 2);
		donnee.ajoutPion(2, 0, 2);
		donnee.ajoutPion(3, 0, 2);
		donnee.ajoutPion(4, 0, 2);
		donnee.ajoutPion(5, 0, 2);
		int[] temp2 = { 3, 2, 4, 1, 5, 6 };
		assertTrue(Arrays.equals(donnee.getColoneJouable(), temp2),
				"test de testAjoutPion_H_L_J le tableau des colonneJouable devrais renvoye " + Arrays.toString(temp2)
						+ " mais renvoie " + Arrays.toString(donnee.getColoneJouable()));
	}

	@Test
	default void testenleverPion() {
		GestDonnee donnee = createInstance();
		assertTrue(!donnee.enleverPion(), "test de enlever pion devrait renvoyer false car aucun pion et renvoie true");

		donnee.ajoutPion(7);
		donnee.ajoutPion(7);
		donnee.ajoutPion(7);
		donnee.ajoutPion(7);
		donnee.ajoutPion(1);
		donnee.ajoutPion(7);
		assertTrue(donnee.getTableau(4, 6) == 2,
				"test de getTableau en 5,7 devrait etre a 2 et est a " + donnee.getTableau(4, 6));
		assertTrue(donnee.enleverPion(), "test de enlever pion devrait renvoyer true et renvoie false");
		assertTrue(donnee.getTableau(4, 6) == 0,
				"test apres enleverPion getTableau en 5,7 devrait etre a 0 et est a " + donnee.getTableau(4, 6));
		assertTrue(donnee.getTableau(0, 0) == 1,
				"test de getTableau en 0,0 devrait etre a 1 et est a " + donnee.getTableau(0, 0));
		assertTrue(donnee.enleverPion(), "test de enlever pion devrait renvoyer true et renvoie false");
		assertTrue(donnee.getTableau(0, 0) == 0,
				"test apres enleverPion getTableau en 0,0 devrait etre a 0 et est a " + donnee.getTableau(0, 0));
	}

	@Test
	default void testSetDonneeTabInt() {
		int temp[][] = { { 1, 1, 2, 1, 2, 0, 1 }, { 1, 2, 1, 2, 0, 0, 0 }, { 0, 0, 0, 2, 0, 0, 0 },
				{ 0, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 2, 0, 0, 0 }, { 0, 0, 0, 1, 0, 0, 0 } };
		GestDonnee test = createInstance();
		test.setDonnee(temp);
		int[] temp2 = { 2, 4, 1, 5, 0, 6 };

		assertTrue(Arrays.equals(test.getColoneJouable(), temp2),
				"test de GetDonneeId le tableau des colonneJouable du GestBaseDonnee cree devrais renvoye "
						+ Arrays.toString(temp2) + " mais renvoie " + Arrays.toString(test.getColoneJouable()));
		assertTrue(test.getNbPionJouer() == 14,
				"test de GetDonneeId le tableau des colonneJouable du GestNbPionJouer cree devrais renvoye 14 mais renvoie "
						+ test.getNbPionJouer());
		assertTrue(test.getTableau(5, 3) == 1,
				"test de GetDonneeId en 5,3 devrait etre a 1 et est a " + test.getTableau(5, 3));
		assertTrue(test.getJoueur() == 1, "test de GetDernierjouer devrait etre a 1 et est a " + test.getJoueur());
	}

	@Test
	default void testSetDonneeTabIntInt() {
		int temp[][] = { { 1, 1, 2, 1, 2, 0, 1 }, { 1, 2, 1, 2, 0, 0, 0 }, { 0, 0, 0, 2, 0, 0, 0 },
				{ 0, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 2, 0, 0, 0 }, { 0, 0, 0, 1, 0, 0, 0 } };
		GestDonnee test = createInstance();
		test.setDonnee(temp, 5);
		int[] temp2 = { 2, 4, 1, 5, 0, 6 };

		assertTrue(Arrays.equals(test.getColoneJouable(), temp2),
				"test de GetDonneeId le tableau des colonneJouable du GestBaseDonnee cree devrais renvoye "
						+ Arrays.toString(temp2) + " mais renvoie " + Arrays.toString(test.getColoneJouable()));
		assertTrue(test.getNbPionJouer() == 14,
				"test de GetDonneeId le tableau des colonneJouable du GestNbPionJouer cree devrais renvoye 14 mais renvoie "
						+ test.getNbPionJouer());
		assertTrue(test.getTableau(5, 3) == 1,
				"test de GetDonneeId en 5,3 devrait etre a 1 et est a " + test.getTableau(5, 3));
		assertTrue(test.getJoueur() == 1, "test de GetJoueur devrait etre a 1 et est a " + test.getJoueur());
		assertTrue(test.getDernierJouer() == 5,
				"test de GetDernierJouer devrait etre a 5 et est a " + test.getDernierJouer());
	}

	@Test
	default void testGetDernierJouer() {
		GestDonnee donnee = createInstance();
		donnee.ajoutPion(7);
		assertTrue(donnee.getDernierJouer() == 6,
				"test de getDernierJouer apres un ajout sur 7 devrait etre de 6 et est a  " + donnee.getDernierJouer());
	}

}
