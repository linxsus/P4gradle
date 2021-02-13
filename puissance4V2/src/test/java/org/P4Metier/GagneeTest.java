package org.P4Metier;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.P4Modele_.GestDonnee;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
interface GagneeTest {

	abstract Gagnee createInstance();

	abstract GestDonnee createDonee();

	@Test
	default void testGagnerHorizontalBas1() {
		int temp[][] = { { 1, 1, 1, 1, 2, 1, 0 }, { 0, 0, 2, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 } };
		GestDonnee donnee = createDonee();
		donnee.setDonnee(temp, 0);
		// System.out.println(donnee);
		Gagnee gagnee = createInstance();
		assertTrue(gagnee.isGagnee(donnee));
	}

	@Test
	default void testGagnerHorizontalBas2() {
		int temp[][] = { { 1, 2, 2, 1, 1, 1, 1 }, { 2, 0, 2, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 } };
		GestDonnee donnee = createDonee();
		donnee.setDonnee(temp, 5);
		// System.out.println(donnee);
		Gagnee gagnee = createInstance();
		assertTrue(gagnee.isGagnee(donnee));
	}

	@Test
	default void testGagnerHorizontalhaut1() {
		int temp[][] = { { 1, 2, 1, 2, 1, 2, 1 }, { 1, 2, 1, 2, 1, 2, 1 }, { 1, 2, 1, 2, 1, 2, 1 },
				{ 2, 1, 2, 1, 2, 1, 2 }, { 2, 1, 2, 1, 2, 1, 2 }, { 2, 1, 1, 2, 2, 2, 2 } };
		GestDonnee donnee = createDonee();
		donnee.setDonnee(temp, 6);
		// System.out.println(donnee);
		Gagnee gagnee = createInstance();
		assertTrue(gagnee.isGagnee(donnee));
	}

	@Test
	default void testGagnerHorizontalhaut2() {
		int temp[][] = { { 1, 2, 1, 2, 1, 2, 1 }, { 1, 2, 1, 2, 1, 2, 1 }, { 1, 2, 1, 2, 1, 2, 1 },
				{ 2, 1, 2, 1, 2, 1, 2 }, { 2, 1, 2, 1, 2, 1, 2 }, { 2, 2, 2, 2, 1, 1, 2 } };
		GestDonnee donnee = createDonee();
		donnee.setDonnee(temp, 2);
		// System.out.println(donnee);
		Gagnee gagnee = createInstance();
		assertTrue(gagnee.isGagnee(donnee));
	}

	@Test
	default void testGagnerdiagonalBas2() {
		int temp[][] = { { 1, 2, 1, 1, 1, 2, 1 }, { 1, 1, 1, 2, 0, 0, 1 }, { 0, 0, 1, 2, 0, 0, 0 },
				{ 0, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 } };
		GestDonnee donnee = createDonee();
		donnee.setDonnee(temp, 2);
		// System.out.println(donnee);
		Gagnee gagnee = createInstance();
		assertTrue(gagnee.isGagnee(donnee));
	}
}
