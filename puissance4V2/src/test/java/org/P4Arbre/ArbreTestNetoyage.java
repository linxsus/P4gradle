package org.P4Arbre;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.P4Modele_.Arbre;
import org.P4Modele_.Calculer;
import org.P4Modele_.NeudArbre;
import org.P4XG.PF;
import org.junit.jupiter.api.Test;

interface ArbreTestNetoyage {

	abstract Arbre creatInstance();

	@SuppressWarnings("unchecked")
	default Map<Long, NeudArbre> getTableau(Arbre test) {
		return (Map<Long, NeudArbre>) PF.privateFonction(test, "getTableau");
	}

	@Test
	default void testNetoyage() {
		Arbre test = creatInstance();
		long tron = test.getTron();
		test.addEnfant(tron, 2L);
		test.addEnfant(tron, 3L);
		test.addEnfant(2L, 4L);
		test.addEnfant(3L, 4L);
		test.addEnfant(3L, 5L);
		test.setCalculer(4L, Calculer.GAGNER);
		assertTrue(getTableau(test).get(tron).getCalculer() == Calculer.GAGNER,
				"le tron devrait etre a gagner vu la tructure");
		assertTrue(getTableau(test).get(3L) != null, "la feuille 3L devrait exister");
		assertTrue(getTableau(test).get(5L) == null, "la feuille 5L ne devrait plus exister");
		assertTrue(getTableau(test).get(2L) != null, "la feuille 2L devrait exister");
		assertTrue(getTableau(test).get(4L) != null, "la feuille 4L devrait exister");
	}

	@Test
	default void testNetoyage1() {
		Arbre test = creatInstance();
		long tron = test.getTron();
		test.addEnfant(tron, 2L);
		test.addEnfant(tron, 3L);
		test.addEnfant(2L, 4L);
		test.addEnfant(3L, 4L);
		test.addEnfant(3L, 5L);
		test.setCalculer(4L, Calculer.PERDU);
		assertTrue(getTableau(test).get(tron).getCalculer() == Calculer.PERDU,
				"le tron devrait etre a perdu vu la tructure");
		assertTrue(getTableau(test).get(1L) != null, "la feuille 1L devrait exister");
		assertTrue(getTableau(test).get(2L) != null, "la feuille 2L devrait exister");
		assertTrue(getTableau(test).get(3L) == null, "la feuille 3L ne devrait plus exister");
		assertTrue(getTableau(test).get(4L) != null, "la feuille 4L devrait exister");
		assertTrue(getTableau(test).get(5L) == null, "la feuille 5L ne devrait plus exister");
	}

	@Test
	default void testNetoyage2() {
		Arbre test = creatInstance();
		long tron = test.getTron();
		test.addEnfant(tron, 2L);
		test.addEnfant(tron, 3L);
		test.addEnfant(tron, 4L);
		test.addEnfant(2L, 5L);
		test.addEnfant(2L, 6L);
		test.addEnfant(2L, 7L);
		test.addEnfant(3L, 6L);
		test.addEnfant(3L, 7L);
		test.setCalculer(6L, Calculer.GAGNER);
		assertTrue(getTableau(test).get(3L).getCalculer() == Calculer.PERDU,
				"le 3L devrait etre a perdu vu la tructure");
		assertTrue(getTableau(test).get(2L).getCalculer() == Calculer.PERDU,
				"le 2L devrait etre a perdu vu la tructure");
		assertTrue(getTableau(test).get(2L) != null, "la feuille 2L devrait exister");
		assertTrue(getTableau(test).get(3L) != null, "la feuille 3L devrait exister");
		assertTrue(getTableau(test).get(4L) != null, "la feuille 4L devrait exister");
		assertTrue(getTableau(test).get(5L) == null, "la feuille 5L ne devrait plus exister");
		assertTrue(getTableau(test).get(6L) != null, "la feuille 6L devrait exister");
		assertTrue(getTableau(test).get(7L) == null, "la feuille 7L ne devrait plus exister");
	}

	@Test
	default void testNetoyage3() {
		Arbre test = creatInstance();
		long tron = test.getTron();
		test.addEnfant(tron, 2L);
		test.addEnfant(tron, 3L);
		test.addEnfant(tron, 4L);
		test.addEnfant(2L, 5L);
		test.addEnfant(2L, 6L);
		test.addEnfant(2L, 7L);
		test.addEnfant(3L, 6L);
		test.addEnfant(3L, 7L);
		test.setCalculer(4L, Calculer.GAGNER);
		assertTrue(getTableau(test).get(1L).getCalculer() == Calculer.PERDU,
				"le 1L devrait etre a perdu vu la tructure");
		assertTrue(getTableau(test).get(2L) == null, "la feuille 2L ne devrait plus exister");
		assertTrue(getTableau(test).get(3L) == null, "la feuille 3L ne devrait plus exister");
		assertTrue(getTableau(test).get(4L) != null, "la feuille 4L devrait exister");
		assertTrue(getTableau(test).get(5L) == null, "la feuille 5L ne devrait plus exister");
		assertTrue(getTableau(test).get(6L) == null, "la feuille 6L ne devrait plus exister");
		assertTrue(getTableau(test).get(7L) == null, "la feuille 7L ne devrait plus exister");
	}

	// test sur une structure qui ne devrait pas exister mais cela peut quand meme
	// valider le bon fonctionnement du system
	@Test
	default void testNetoyage4() {
		Arbre test = creatInstance();
		long tron = test.getTron();
		test.addEnfant(tron, 2L);
		test.addEnfant(tron, 6L);
		test.addEnfant(tron, 7L);
		test.addEnfant(2L, 3L);
		test.addEnfant(2L, 4L);
		test.addEnfant(2L, 5L);
		test.addEnfant(6L, 4L);
		test.addEnfant(6L, 5L);
		test.setCalculer(7L, Calculer.GAGNER);
		assertTrue(getTableau(test).get(1L).getCalculer() == Calculer.PERDU,
				"le 1L devrait etre a perdu vu la tructure");
		assertTrue(getTableau(test).get(2L) == null, "la feuille 2L ne devrait plus exister");
		assertTrue(getTableau(test).get(6L) == null, "la feuille 3L ne devrait plus exister");
		assertTrue(getTableau(test).get(7L) != null, "la feuille 4L devrait exister");
		assertTrue(getTableau(test).get(3L) == null, "la feuille 5L ne devrait plus exister");
		assertTrue(getTableau(test).get(4L) == null, "la feuille 5L ne devrait plus exister");
		assertTrue(getTableau(test).get(5L) == null, "la feuille 6L ne devrait plus exister");
	}

	@Test
	default void testNetoyage5() {
		Arbre test = creatInstance();
		long tron = test.getTron();
		test.addEnfant(tron, 2L);
		test.addEnfant(tron, 3L);
		test.addEnfant(tron, 4L);
		test.addEnfant(2L, 5L);
		test.addEnfant(2L, 6L);
		test.addEnfant(4L, 6L);
		test.addEnfant(3L, 6L);
		test.addEnfant(3L, 7L);
		test.addEnfant(4L, 7L);
		test.setCalculer(7L, Calculer.GAGNER);
		test.setCalculer(5L, Calculer.GAGNER);
		// System.out.println(test);
		assertTrue(getTableau(test).get(1L) != null, "la feuille 1L devrait exister");
		assertTrue(getTableau(test).get(2L) != null, "la feuille 2L devrait exister");
		assertTrue(getTableau(test).get(3L) != null, "la feuille 3L devrait exister");
		assertTrue(getTableau(test).get(4L) != null, "la feuille 4L devrait exister");
		assertTrue(getTableau(test).get(5L) != null, "la feuille 5L devrait exister");
		assertTrue(getTableau(test).get(6L) == null, "la feuille 5L ne devrait plus exister");
		assertTrue(getTableau(test).get(7L) != null, "la feuille 6L devrait exister");
	}
}
