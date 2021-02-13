package org.P4Metier;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.P4Metier.Factory.Factory;
import org.P4Modele_.GestDonnee;
import org.P4XG.PF;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GagneeTabByteTest implements GagneeTest {

	Factory factory;

	@BeforeEach
	public void init() {
		factory = new Factory(false, true, true, true);
	}

	@Override
	public Gagnee createInstance() {
		return factory.getGagnee();
	}

	@Override
	public GestDonnee createDonee() {
		return factory.getGestBaseDonnee();
	}

	@Test
	public void testTestByte() {
		Gagnee gagnee = createInstance();
		assertTrue((int) PF.privateFonction(gagnee, "test", new Byte("15")) == 1,
				"x00001111 devrait etre a gagner et est a perdu");
		assertTrue((int) PF.privateFonction(gagnee, "test", new Byte("30")) == 1,
				"x00011110 devrait etre a gagner et est a perdu");
		assertTrue((int) PF.privateFonction(gagnee, "test", new Byte("62")) == 1,
				"x00111110 devrait etre a gagner et est a perdu");
		assertTrue((int) PF.privateFonction(gagnee, "test", new Byte("14")) != 1,
				"x00001110 devrait etre a pedue et est a gagner");
	}
}
