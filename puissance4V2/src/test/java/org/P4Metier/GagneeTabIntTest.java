package org.P4Metier;

import org.P4Metier.Factory.Factory;
import org.P4Modele_.GestDonnee;
import org.junit.jupiter.api.BeforeEach;

public class GagneeTabIntTest implements GagneeTest {

	Factory factory;

	@BeforeEach
	public void init() {
		factory = new Factory(false, true, false, false);
	}

	@Override
	public Gagnee createInstance() {
		return factory.getGagnee();
	}

	@Override
	public GestDonnee createDonee() {
		return factory.getGestDonnee();
	}

}
