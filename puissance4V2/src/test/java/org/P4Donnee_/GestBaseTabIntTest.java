package org.P4Donnee_;

import org.P4Metier.Factory.Factory;
import org.P4Modele_.GestDonnee;
import org.junit.jupiter.api.BeforeEach;

class GestBaseTabIntTest implements GestBaseDonneeTest {

	Factory factory;

	@BeforeEach
	public void init() {
		factory = new Factory(false, true, false, true);
	}

	@Override
	public GestDonnee createInstance() {
		return factory.getGestDonnee();
	}
}
