package org.P4Donnee_;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.P4Metier.Factory.Factory;
import org.P4Modele_.GestDonnee;
import org.P4Modele_.jeux.ByteTableauJoueur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GestBaseTabByteTest implements GestBaseDonneeTest {

	Factory factory;

	@BeforeEach
	public void init() {
		factory = new Factory(true, true, true, true);
	}

	@Override
	public GestDonnee createInstance() {
		return factory.getGestDonnee();
	}

	@Test
	void testgetByteTableauJoueur() {
		GestDonnee donnee = createInstance();
		donnee.ajoutPion(3);
		ByteTableauJoueur test = donnee.getByteTableauJoueur();
		byte temp = test.getHorizontal(0, 2);
		assertTrue(temp == 4, "test " + temp + " au lieux de 4");
		temp = test.getDiagonal2(0, 2);
		assertTrue(temp == 1, "test " + temp + " au lieux de 1");
		temp = test.getDiagonal1(0, 2);
		assertTrue(temp == 0, "test " + temp + " au lieux de 0");
		temp = test.getHauteur(0, 2);
		assertTrue(temp == 1, "test " + temp + " au lieux de 1");

	}
	// default public ByteTableauJoueur getByteTableauJoueur();
	//

}
