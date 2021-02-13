package org.P4Arbre;

import org.P4Metier.Factory.Factory;
import org.P4Modele_.NeudArbre;

public class NeudArbreBasicTest implements NeudTest {

	Factory factory;

	@Override
	public NeudArbre creatInstance() {
		factory = new Factory(false, true, true, true);
		return factory.getNeudArbre();
	}

}
