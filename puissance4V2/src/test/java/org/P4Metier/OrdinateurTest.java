package org.P4Metier;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;


@RunWith(JUnitPlatform.class)
interface OrdinateurTest<T> {

	abstract GestIdDonnee<T> getDonee();

	abstract Ordinateur<T> getOrdinateur(GestIdDonnee<T> donnee);
	
	private int jouer(int temp[][])
	{
		GestIdDonnee<T> donnee = getDonee();
		donnee.setDonnee(temp);
		Ordinateur<T> ordinateur = getOrdinateur(donnee);
		ordinateur.setTourMax(5);
		return ordinateur.jouer(donnee);
	}

	@Test
	default void testJouer1() {
		int temp[][] = { { 1, 2, 1, 1, 0, 1, 0 }, { 0, 0, 2, 2, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 } };
		int i=jouer(temp);
		assertTrue(5 == i, "pour gagner doit jouer en 5 a jouer en" + i + "\n");
	}

	@Test
	default void testJouer2() {
		int temp[][] = { { 1, 2, 2, 1, 1, 1, 0 }, { 0, 0, 2, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 } };

		int i=jouer(temp);
		// System.out.println(donnee);
		assertTrue(7 == i, "pour gagner doit jouer en 7 a jouer en" + i + "\n");
	}

	@Test
	default void testJouer3() {
		int temp[][] = { { 1, 2, 2, 1, 2, 1, 0 }, { 0, 0, 2, 2, 0, 1, 0 }, { 0, 0, 0, 0, 0, 1, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 } };
		int i=jouer(temp);
		assertTrue(6 == i, "pour gagner doit jouer en 6 a jouer en" + i + "\n");
	}

	@Test
	default void testJouer4() {
		int temp[][] = { { 1, 2, 2, 1, 2, 2, 2 }, { 0, 0, 2, 2, 0, 1, 2 }, { 0, 0, 0, 0, 0, 1, 2 },
				{ 0, 0, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 } };
		int i=jouer(temp);
		assertTrue(5 == i, "pour gagner doit jouer en 5 a jouer en" + i + "\n");
	}

	@Test
	default void testJouer5() {
		int temp[][] = { { 1, 2, 1, 1, 0, 1, 0 }, { 0, 0, 2, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 } };
		int i=jouer(temp);
		assertTrue(5 == i, "pour ne pas perdre doit jouer en 5 a jouer en" + i + "\n");
	}

	@Test
	default void testJouer6() {
		int temp[][] = { { 1, 2, 2, 1, 1, 1, 0 }, { 0, 0, 2, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 } };
		int i=jouer(temp);
		assertTrue(7 == i, "pour ne pas perdre doit jouer en 7 a jouer en" + i + "\n");
	}

	@Test
	default void testJouer7() {
		int temp[][] = { { 1, 2, 2, 1, 2, 1, 0 }, { 0, 0, 2, 2, 0, 1, 0 }, { 0, 0, 0, 0, 0, 1, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 } };
		int i=jouer(temp);
		assertTrue(6 == i, "pour ne pas perdre doit jouer en 6 a jouer en" + i + "\n" );
	}

	@Test
	default void testJouer8() {
		int temp[][] = { { 1, 2, 2, 1, 2, 2, 2 }, { 0, 0, 2, 2, 0, 1, 2 }, { 0, 0, 0, 0, 0, 1, 2 },
				{ 0, 0, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 } };
		int i=jouer(temp);
		assertTrue(5 == i, "pour ne pas perdre doit jouer en 5 a jouer en" + i + "\n" );
	}

	@Test
	default void testJouer9() {
		int temp[][] = { { 1, 1, 2, 2, 1, 2, 0 }, { 2, 1, 1, 2, 1, 0, 0 }, { 0, 2, 1, 1, 1, 0, 0 },
				{ 0, 0, 1, 0, 2, 0, 0 }, { 0, 0, 2, 0, 0, 0, 0 }, { 0, 0, 2, 0, 0, 0, 0 } };
		int i=jouer(temp);
		assertTrue(4 == i, "pour ne pas perdre doit jouer en 5 a jouer en" + i + "\n" );
	}
}
