package org.P4Donnee_;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.P4Metier.GestIdDonnee;
import org.P4Metier.Factory.Factory;
import org.P4Metier.id.GestIdDonneeLong;
import org.P4Modele_.GestDonnee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GestIdDonneeLongTest implements GestIdDonneeTest<Long> {

	GestIdDonneeLong donnee;

	Factory factory;

	@BeforeEach
	public void init() {
		factory = new Factory(false, true, false, false);
		donnee = (GestIdDonneeLong) factory.getGestIDDonnee();
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
	public GestIdDonnee<Long> createInstance() {
		return (GestIdDonneeLong) factory.getGestIDDonnee(); // TODO utiliser (modifier le factory)
	}

	@Test
	void testAjoutPion() {
		donnee.ajoutPion(1);
		long temp = 262604863L;
		assertTrue(temp == donnee.getIdBaseDonnee(),
				"test de getIdBaseDonnee devrais renvoyer " + temp + " mais renvoie " + donnee.getIdBaseDonnee());
	}

	@Test
	void testEnleverPion() {
		donnee.enleverPion();
		long temp = 65651209L;
		long temp1 = donnee.getIdBaseDonnee();
		assertTrue(temp == temp1,
				"test de getIdBaseDonnee devrais renvoyer " + temp + " mais renvoie " + donnee.getIdBaseDonnee());
	}

	@Override
	@Test
	public void testGetIdBaseDonnee() {
		long temp = 131302418L;// 18068942
		Long temp1 = donnee.getIdBaseDonnee();
		assertTrue(temp == temp1,
				"test de getIdBaseDonnee devrais renvoyer " + temp + " mais renvoie " + donnee.getIdBaseDonnee());
	}

	@Override
	@Test
	public void testGetIdBaseDonneeIntArrayArray() {
		int temp[][] = { { 1, 1, 2, 1, 2, 0, 1 }, { 1, 2, 1, 2, 0, 0, 0 }, { 0, 0, 0, 2, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 } };
		long temp1 = 131302418L;
		long temp10 = donnee.getIdBaseDonnee(temp);
		assertTrue(temp1 == temp10, "test de GetIdBaseDonneeIntArrayArray devrais renvoyer " + temp1 + " mais renvoie "
				+ donnee.getIdBaseDonnee(temp));
		int temp2[][] = { { 1, 1, 2, 1, 2, 0, 1 }, { 1, 2, 1, 2, 0, 0, 0 }, { 0, 0, 0, 2, 0, 0, 0 },
				{ 0, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 2, 0, 0, 0 }, { 0, 0, 0, 1, 0, 0, 0 } };
		long temp3 = 1045976402L;
		long temp30 = donnee.getIdBaseDonnee(temp2);
		assertTrue(temp3 == temp30,
				"test de GetIdBaseDonneeIntArrayArray devrais renvoyer " + temp3 + " mais renvoie " + temp30);
	}

	@Override
	@Test
	public void testGetDonneeId() {
		long temp1 = 1046596350L;
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
		long temp1 = 1046596350L;
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
		long temp1 = 1046596350L;
		GestIdDonnee<Long> temp2 = donnee.newBaseDonneeId(temp1);
		int[] temp3 = { 2, 4, 1, 5, 0, 6 };
		int[] temp30 = temp2.getColoneJouable();
		assertTrue(Arrays.equals(temp30, temp3),
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
		Long temp1 = donnee.getIdBaseDonnee();
		GestIdDonneeLong donnee2 = new GestIdDonneeLong(donnee.getDonneeId(temp1));
		Long temp2 = donnee2.getIdBaseDonnee();
		assertTrue(temp2.equals(temp1),
				"test de coherance d'export (getIdBaseDonnee) (getDonneeId) import par constructeur(GestBaseDonnee) si cela ne marche pas regarder d'abort si il n'y a pas d'erreur autre part");
	}

	@Test
	void testMaxInLong() {
		int temp2[][] = { { 2, 2, 2, 2, 2, 2, 2 }, { 2, 2, 2, 2, 2, 2, 2 }, { 2, 2, 2, 2, 2, 2, 2 },
				{ 2, 2, 2, 2, 2, 2, 2 }, { 2, 2, 2, 2, 2, 2, 2 }, { 2, 2, 2, 2, 2, 2, 2 } };
		long temp3 = 3621980417894121471L;
		long temp30 = donnee.getIdBaseDonnee(temp2);
		assertTrue(temp3 == temp30,
				"test de GetIdBaseDonneeIntArrayArray devrais renvoyer " + temp3 + " mais renvoie " + temp30);
	}

	@Override
	@Test
	public void testIsMiroire() {
		int temp[][] = { { 1, 1, 2, 1, 2, 0, 1 }, { 1, 2, 1, 2, 0, 0, 0 }, { 0, 0, 0, 2, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 } };
		donnee.setDonnee(temp);
		long id1 = donnee.getIdBaseDonnee();
		boolean isMiroire1 = donnee.isMiroire();
		int temp1[][] = { { 1, 0, 2, 1, 2, 1, 1 }, { 0, 0, 0, 2, 1, 2, 1 }, { 0, 0, 0, 2, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 } };
		donnee.setDonnee(temp1);
		long id2 = donnee.getIdBaseDonnee();
		boolean isMiroire2 = donnee.isMiroire();
		assertTrue(id1 == id2, "test pour verifier si 2 tableau miroire renvoie bien le meme id");
		assertTrue(((isMiroire1 || isMiroire2) && !(isMiroire1 && isMiroire2)),
				"test pour verifier si il n'y a bien 1 et 1 seul id qui renvoie isMiroire");
	}
}
