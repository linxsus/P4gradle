/**
 *
 */
package org.P4Donnee_;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.P4Modele_.jeux.PionJouer;
import org.junit.jupiter.api.Test;

/**
 * test du javabean
 *
 * @author Xavier Gouraud
 *
 */
class PionJouerTest {

	/**
	 * Test method for {@link puissance4.modele.PionJouer#toString()}.
	 */
	@Test
	void testToString() {
		PionJouer test = new PionJouer();
		test.setColonne(5);
		test.setHauteur(4);
		String message = "" + test;
		assertTrue(message.equals("PionJouer [colonne=5, hauteur=4] \n"),
				"test ToString() \n" + message + " au lieux de/n PionJouer [colonne=5, hauteur=4]");
	}

	/**
	 * Test method for {@link puissance4.modele.PionJouer#getColonne()}.
	 */
	@Test
	void testGetColonne() {
		PionJouer test = new PionJouer();
		test.setColonne(5);
		assertTrue(test.getColonne() == 5, "test GetColonne() " + test.getColonne() + " au lieux de 5");
	}

	/**
	 * Test method for {@link puissance4.modele.PionJouer#setColonne(int)}.
	 */
	@Test
	void testSetColonne() {
		PionJouer test = new PionJouer();
		test.setColonne(1);
		test.setColonne(5);
		assertTrue(test.getColonne() == 5, "test setColonne() " + test.getColonne() + " au lieux de 5");
	}

	/**
	 * Test method for {@link puissance4.modele.PionJouer#getHauteur()}.
	 */
	@Test
	void testGetHauteur() {
		PionJouer test = new PionJouer();
		test.setHauteur(5);
		assertTrue(test.getHauteur() == 5, "test GetHauteur() " + test.getHauteur() + " au lieux de 5");
	}

	/**
	 * Test method for {@link puissance4.modele.PionJouer#setHauteur(int)}.
	 */
	@Test
	void testSetHauteur() {
		PionJouer test = new PionJouer();
		test.setHauteur(3);
		test.setHauteur(5);
		assertTrue(test.getHauteur() == 5, "test setHauteur() " + test.getHauteur() + " au lieux de 5");
	}

	/**
	 * Test method for {@link puissance4.modele.PionJouer#PionJouer()}.
	 */
	@Test
	void testPionJouer() {
		PionJouer test = new PionJouer();
		assertTrue(test.getHauteur() == 0, "test PionJouer() hauteur= " + test.getHauteur() + " au lieux de 5");
		assertTrue(test.getColonne() == 0, "test PionJouer() colonne= " + test.getColonne() + " au lieux de 5");
	}

}
