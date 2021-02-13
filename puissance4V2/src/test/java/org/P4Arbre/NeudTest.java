package org.P4Arbre;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.P4Modele_.NeudArbre;
import org.junit.jupiter.api.Test;

public interface NeudTest {

	abstract NeudArbre creatInstance();

	@Test
	default void testAddEnfant() {
		NeudArbre neud = creatInstance();
		assertTrue(neud.isFeuille(), "le neud initial devrait etre une feuille");
		neud.addEnfant(3L);
		assertTrue(!neud.isFeuille(), "le neud avec un enfant ne devrait pas etre une feuille");
	}

}
