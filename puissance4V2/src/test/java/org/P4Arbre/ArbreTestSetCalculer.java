package org.P4Arbre;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.P4Modele_.Arbre;
import org.P4Modele_.Calculer;
import org.P4Modele_.NeudArbre;
import org.P4XG.PF;
import org.junit.jupiter.api.Test;

interface ArbreTestSetCalculer {

	abstract Arbre creatInstance();

	@SuppressWarnings("unchecked")
	default Map<Long, NeudArbre> getTableau(Arbre test) {
		return (Map<Long, NeudArbre>) PF.privateFonction(test, "getTableau");
	}

	@Test
	default void testsetCalculer1() {
		Arbre test = creatInstance();
		long tron = test.getTron();
		test.setCalculer(tron, Calculer.GAGNER);
		assertTrue(getTableau(test).get(tron).getCalculer() == Calculer.GAGNER,
				"erreur affectation de gagner sur tron");
	}

	@Test
	default void testsetCalculer2() {
		Arbre test = creatInstance();
		long tron = test.getTron();
		test.addEnfant(tron, 2L);
		test.setCalculer(2L, Calculer.GAGNER);
		assertTrue(getTableau(test).get(tron).getCalculer() == Calculer.PERDU,
				"le tron devrait etre a perdu vu que la seul feuille est a gagner");
	}

	@Test
	default void testsetCalculer3() {
		Arbre test = creatInstance();
		long tron = test.getTron();
		test.addEnfant(tron, 2L);
		test.addEnfant(tron, 3L);
		test.addEnfant(tron, 4L);
		test.setCalculer(3L, Calculer.GAGNER);
		assertTrue(getTableau(test).get(tron).getCalculer() == Calculer.PERDU,
				"le tron devrait etre a perdu vu que 1 des 3 feuille est a gagner");
	}

	@Test
	default void testsetCalculer4() {
		Arbre test = creatInstance();
		long tron = test.getTron();
		test.addEnfant(tron, 2L);
		test.setCalculer(2L, Calculer.PERDU);
		assertTrue(getTableau(test).get(tron).getCalculer() == Calculer.GAGNER,
				"le tron devrait etre a Gagner vu que la seul feuille est a perdu");
	}

	@Test
	default void testsetCalculer5() {
		Arbre test = creatInstance();
		long tron = test.getTron();
		test.addEnfant(tron, 2L);
		test.addEnfant(tron, 3L);
		test.addEnfant(tron, 4L);
		test.setCalculer(3L, Calculer.PERDU);
		assertTrue(getTableau(test).get(tron).getCalculer() == Calculer.NONCALCULER,
				"le tron devrait etre a noncalculer vu qu'il n'y a que 1 des 3 feuille est a perdu et que les autre feuille ne sont pas calculer");
	}

	@Test
	default void testsetCalculer6() {
		Arbre test = creatInstance();
		long tron = test.getTron();
		test.addEnfant(tron, 2L);
		test.addEnfant(tron, 3L);
		test.addEnfant(tron, 4L);
		test.setCalculer(2L, Calculer.PERDU);
		test.setCalculer(3L, Calculer.PERDU);
		test.setCalculer(4L, Calculer.PERDU);
		assertTrue(getTableau(test).get(tron).getCalculer() == Calculer.GAGNER,
				"le tron devrait etre a gagner vu qu'il y a les 3 feuille est a perdu");
	}

	@Test
	default void testsetCalculer7() {
		Arbre test = creatInstance();
		long tron = test.getTron();
		test.addEnfant(tron, 2L);
		test.addEnfant(tron, 3L);
		test.addEnfant(tron, 4L);
		test.setCalculer(2L, Calculer.PERDU);
		test.setCalculer(3L, Calculer.EGALITER);
		assertTrue(getTableau(test).get(tron).getCalculer() == Calculer.NONCALCULER,
				"le tron devrait etre a noncalculer vu qu'il n'y a que 1 feuille a perdu qu'il n'y a que 1 feuille a egaliter et une feuille noncalculer");
	}

	@Test
	default void testsetCalculer8() {
		Arbre test = creatInstance();
		long tron = test.getTron();
		test.addEnfant(tron, 2L);
		test.addEnfant(tron, 3L);
		test.addEnfant(tron, 4L);
		test.setCalculer(2L, Calculer.PERDU);
		test.setCalculer(3L, Calculer.EGALITER);
		test.setCalculer(4L, Calculer.EGALITER);
		assertTrue(getTableau(test).get(tron).getCalculer() == Calculer.INDEFINI,
				"le tron devrait etre a indefini vu qu'il y a 1 feuille a perdu et 2 feuille a egaliter");
	}

	@Test
	default void testsetCalculer9() {
		Arbre test = creatInstance();
		long tron = test.getTron();
		test.addEnfant(tron, 2L);
		test.addEnfant(tron, 3L);
		test.addEnfant(tron, 4L);
		test.setCalculer(2L, Calculer.PERDU);
		test.setCalculer(3L, Calculer.PERDU);
		test.setCalculer(4L, Calculer.INDEFINI);
		assertTrue(getTableau(test).get(tron).getCalculer() == Calculer.INDEFINI,
				"le tron devrait etre a indefini vu qu'il y a 2 feuille a perdu et 1 feuille indefini");
	}

}