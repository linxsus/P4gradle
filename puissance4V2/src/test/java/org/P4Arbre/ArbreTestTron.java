package org.P4Arbre;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.P4Modele_.Arbre;
import org.P4Modele_.NeudArbre;
import org.P4XG.PF;
import org.junit.jupiter.api.Test;

interface ArbreTestTron {

	abstract Arbre creatInstance();

	@SuppressWarnings("unchecked")
	default Map<Long, NeudArbre> getTableau(Arbre test) {
		return (Map<Long, NeudArbre>) PF.privateFonction(test, "getTableau");
	}

	@Test
	default void testGetTron() {
		Arbre test = creatInstance();
		long tron = test.getTron();
		assertTrue(tron == 1L, "erreur creation du tron");
	}

	@Test
	default void testSetTronLong() {
		Arbre test = creatInstance();
		long tron = test.getTron();
		test.addEnfant(tron, 10L);
		test.setTron(10L, 0);
		tron = test.getTron();
		assertTrue(tron == 10L, "erreur lors de l'affectation du tron par id");
	}

}
