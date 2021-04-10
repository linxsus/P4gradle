package org.P4Donnee_;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.P4Metier.GestIdDonnee;
import org.P4Metier.Factory.Factory;
import org.P4Metier.id.GestIdDonneeInt3;
import org.P4Modele_.GestDonnee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GestIdDonneeInt3Test implements GestIdDonneeTest<int[]> {

	GestIdDonneeInt3 donnee;

	Factory factory;

	@BeforeEach
	public void init() {
		factory = new Factory(false, false, true, true);
		donnee = (GestIdDonneeInt3) factory.getGestIDDonnee();
		donnee.ajoutPion(1);
		donnee.ajoutPion(3);
		donnee.ajoutPion(2);
		donnee.ajoutPion(5);
		donnee.ajoutPion(4);
		donnee.ajoutPion(4);
		donnee.ajoutPion(3);
		donnee.ajoutPion(4);
		donnee.ajoutPion(7);
		donnee.ajoutPion(2);
		donnee.ajoutPion(1);

	}

	@Override
	public GestIdDonnee<int[]> createInstance() {
		return (GestIdDonneeInt3) factory.getGestIDDonnee();
	}

	@Test
	void testAjoutPion() {
		donnee.ajoutPion(1);
		int[] temp = { 154030, 56, 0 };
		assertTrue(Arrays.equals(temp, donnee.getIdBaseDonnee()), "test de getIdBaseDonnee devrais renvoyer "
				+ Arrays.toString(temp) + " mais renvoie " + Arrays.toString(donnee.getIdBaseDonnee()));
	}

	@Test
	void testEnleverPion() {
		donnee.enleverPion();
		int[] temp = { 151843, 54, 0 };
		assertTrue(Arrays.equals(temp, donnee.getIdBaseDonnee()), "test de getIdBaseDonnee devrais renvoyer "
				+ Arrays.toString(temp) + " mais renvoie " + Arrays.toString(donnee.getIdBaseDonnee()));
	}

	@Override
	@Test
	public void testGetIdBaseDonnee() {
		int[] temp = { 154030, 54, 0 };
		assertTrue(Arrays.equals(temp, donnee.getIdBaseDonnee()), "test de getIdBaseDonnee devrais renvoyer "
				+ Arrays.toString(temp) + " mais renvoie " + Arrays.toString(donnee.getIdBaseDonnee()));
	}

	@Override
	@Test
	public void testGetIdBaseDonneeIntArrayArray() {
		int temp[][] = { { 1, 1, 2, 1, 2, 0, 1 }, { 1, 2, 1, 2, 0, 0, 0 }, { 0, 0, 0, 2, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 } };
		int[] temp1 = { 154030, 54, 0 };
		assertTrue(Arrays.equals(temp1, donnee.getIdBaseDonnee(temp)),
				"test de GetIdBaseDonneeIntArrayArray devrais renvoyer " + Arrays.toString(temp1) + " mais renvoie "
						+ Arrays.toString(donnee.getIdBaseDonnee(temp)));
		int temp2[][] = { { 1, 1, 2, 1, 2, 0, 1 }, { 1, 2, 1, 2, 0, 0, 0 }, { 0, 0, 0, 2, 0, 0, 0 },
				{ 0, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 2, 0, 0, 0 }, { 0, 0, 0, 1, 0, 0, 0 } };
		int[] temp3 = { 154030, 59103, 59103 };
		assertTrue(Arrays.equals(temp3, donnee.getIdBaseDonnee(temp2)),
				"test de GetIdBaseDonneeIntArrayArray devrais renvoyer " + Arrays.toString(temp3) + " mais renvoie "
						+ Arrays.toString(donnee.getIdBaseDonnee(temp2)));
	}

	@Override
	@Test
	public void testGetDonneeId() {
		int[] temp1 = { 154030, 59103, 59103 };
		GestDonnee test = donnee.getDonneeId(temp1);
		int[] temp2 = { 2, 4, 1, 5, 0, 6 };
		assertTrue(Arrays.equals(test.getColoneJouable(), temp2),
				"test de GetDonneeId le tableau des colonneJouable du GestBaseDonnee cree devrais renvoye "
						+ Arrays.toString(temp2) + " mais renvoie " + Arrays.toString(test.getColoneJouable()));
		assertTrue(test.getNbPionJouer() == 14,
				"test de GetDonneeId le tableau des colonneJouable du GestNbPionJouer cree devrais renvoye 14 mais renvoie "
						+ test.getNbPionJouer());
		assertTrue(test.getTableau(5, 3) == 1,
				"test de GetDonneeId en 5,3 devrait etre a 1 et est a " + test.getTableau(5, 3));
	}

	@Override
	@Test
	public void testGetBaseDonnee() {
		donnee.ajoutPion(4);
		donnee.ajoutPion(4);
		donnee.ajoutPion(4);
		GestDonnee test = donnee.getBaseDonnee();
		int[] temp2 = { 2, 4, 1, 5, 0, 6 };
		assertTrue(Arrays.equals(test.getColoneJouable(), temp2),
				"test de GetBaseDonnee le tableau des colonneJouable du GestBaseDonnee cree devrais renvoye "
						+ Arrays.toString(temp2) + " mais renvoie " + Arrays.toString(test.getColoneJouable()));
		assertTrue(test.getNbPionJouer() == 14,
				"test de GetBaseDonnee le tableau des colonneJouable du GestNbPionJouer cree devrais renvoye 14 mais renvoie "
						+ test.getNbPionJouer());
		assertTrue(test.getTableau(1, 1) == 2,
				"test de GetBaseDonnee en 1,1 devrait etre a 2 et est a " + test.getTableau(1, 1));

	}

	@Override
	@Test
	public void testSetIdBaseDonnee() {
		int[] temp1 = { 154030, 59103, 59103 };
		donnee.setIdBaseDonnee(temp1);
		int[] temp2 = { 2, 4, 1, 5, 0, 6 };
		assertTrue(Arrays.equals(donnee.getColoneJouable(), temp2),
				"test de SetIdBaseDonnee le tableau des colonneJouable du GestBaseDonnee cree devrais renvoye "
						+ Arrays.toString(temp2) + " mais renvoie " + Arrays.toString(donnee.getColoneJouable()));
		assertTrue(donnee.getNbPionJouer() == 14,
				"test de SetIdBaseDonnee le tableau des colonneJouable du GestNbPionJouer cree devrais renvoye 14 mais renvoie "
						+ donnee.getNbPionJouer());
		assertTrue(donnee.getTableau(5, 3) == 1,
				"test de SetIdBaseDonnee en 5,3 devrait etre a 1 et est a " + donnee.getTableau(5, 3));
	}

	@Override
	@Test
	public void testNewBaseDonneeId() {
		int[] temp1 = { 154030, 59103, 59103 };
		GestIdDonnee<int[]> temp2 = donnee.newBaseDonneeId(temp1);
		int[] temp3 = { 2, 4, 1, 5, 0, 6 };
		assertTrue(Arrays.equals(temp2.getColoneJouable(), temp3),
				"test de NewBaseDonneeId le tableau des colonneJouable du GestBaseDonnee cree devrais renvoye "
						+ Arrays.toString(temp3) + " mais renvoie " + Arrays.toString(temp2.getColoneJouable()));
		assertTrue(temp2.getNbPionJouer() == 14,
				"test de NewBaseDonneeId le tableau des colonneJouable du GestNbPionJouer cree devrais renvoye 14 mais renvoie "
						+ temp2.getNbPionJouer());
		assertTrue(temp2.getTableau(5, 3) == 1,
				"test de NewBaseDonneeId en 5,3 devrait etre a 1 et est a " + temp2.getTableau(5, 3));
	}

	@Override
	@Test
	public void testImportExport() {
		int[] temp1 = donnee.getIdBaseDonnee();
		GestIdDonneeInt3 donnee2 = new GestIdDonneeInt3(donnee.getDonneeId(temp1),null);
		int[] temp2 = donnee2.getIdBaseDonnee();
		assertTrue(Arrays.equals(temp2, temp1),
				"test de coherance d'export (getIdBaseDonnee) (getDonneeId) import par constructeur(GestBaseDonnee) si cela ne marche pas regarder d'abort si il n'y a pas d'erreur autre part");
	}

	@Override
	@Test
	public void testIsMiroire() {
		int temp[][] = { { 1, 1, 2, 1, 2, 0, 1 }, { 1, 2, 1, 2, 0, 0, 0 }, { 0, 0, 0, 2, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 } };
		donnee.setDonnee(temp);
		int[] id1 = donnee.getIdBaseDonnee();
		boolean isMiroire1 = donnee.isMiroire();
		int temp1[][] = { { 1, 0, 2, 1, 2, 1, 1 }, { 0, 0, 0, 2, 1, 2, 1 }, { 0, 0, 0, 2, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 } };
		donnee.setDonnee(temp1);
		int[] id2 = donnee.getIdBaseDonnee();
		boolean isMiroire2 = donnee.isMiroire();
		assertTrue(Arrays.equals(id1, id2), "test pour verifier si 2 tableau miroire renvoie bien le meme id");
		assertTrue(((isMiroire1 || isMiroire2) && !(isMiroire1 && isMiroire2)),
				"test pour verifier si il n'y a bien 1 et 1 seul id qui renvoie isMiroire" + isMiroire1 + "  "
						+ isMiroire2);
	}

}
