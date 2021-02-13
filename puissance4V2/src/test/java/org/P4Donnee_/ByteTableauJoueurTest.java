package org.P4Donnee_;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.P4Modele_.jeux.ByteTableauJoueur;
import org.junit.jupiter.api.Test;

class ByteTableauJoueurTest {

	@Test
	void testSetHauteur() {
		ByteTableauJoueur test = new ByteTableauJoueur();
		byte temp = test.getHauteur(1, 0);
		assertTrue(temp == 0, "test " + temp + " au lieux de 0");
		test.ajouterPion(0, 0);
		test.ajouterPion(1, 0);
		temp = test.getHauteur(1, 0);
		assertTrue(temp == 3, "test " + temp + " au lieux de 3");
	}

	@Test
	void testSetDiagonal1() {
		ByteTableauJoueur test = new ByteTableauJoueur();
		test.ajouterPion(1, 1);
		byte temp = test.getDiagonal1(1, 1);
		assertTrue(temp == 0, "test " + temp + " au lieux de 0");
		test.ajouterPion(1, 2);
		test.ajouterPion(0, 3);
		temp = test.getDiagonal1(1, 2);
		assertTrue(temp == 3, "test " + temp + " au lieux de 3");
	}

	@Test
	void testSetDiagonal2() {
		ByteTableauJoueur test = new ByteTableauJoueur();
		test.ajouterPion(3, 0);
		byte temp = test.getDiagonal2(3, 0);
		assertTrue(temp == 0, "test " + temp + " au lieux de 00");
		test.ajouterPion(2, 0);
		test.ajouterPion(3, 1);
		temp = test.getDiagonal2(3, 1);
		assertTrue(temp == 12, "test " + temp + " au lieux de 12");
	}

	@Test
	void testSethorizontal() {
		ByteTableauJoueur test = new ByteTableauJoueur();
		test.ajouterPion(3, 0);
		byte temp = test.getHorizontal(3, 0);
		assertTrue(temp == 1, "test " + temp + " au lieux de 1");
		test.ajouterPion(2, 0);
		test.ajouterPion(2, 1);
		temp = test.getHorizontal(2, 1);
		assertTrue(temp == 3, "test " + temp + " au lieux de 3");
	}

	@Test
	void enleverTest() {
		ByteTableauJoueur test = new ByteTableauJoueur();
		test.ajouterPion(3, 0);
		byte temp = test.getHorizontal(3, 0);
		assertTrue(temp == 1, "test " + temp + " au lieux de 1");
		test.enleverPion(3, 0);
		temp = test.getHorizontal(3, 0);
		assertTrue(temp == 0, "test " + temp + " au lieux de 0");
		test.ajouterPion(2, 0);
		test.ajouterPion(2, 1);
		test.enleverPion(2, 0);
		temp = test.getHorizontal(2, 1);
		assertTrue(temp == 2, "test " + temp + " au lieux de 2");
	}

}
