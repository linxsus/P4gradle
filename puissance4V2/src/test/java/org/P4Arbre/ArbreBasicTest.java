package org.P4Arbre;

import java.util.Map;

import org.P4Metier.Factory.Factory;
import org.P4Modele_.Arbre;
import org.P4Modele_.NeudArbre;

public class ArbreBasicTest implements ArbreTestTron, ArbreTestSetCalculer, ArbreTestNetoyage {

	Factory factory;

	@Override
	public Arbre creatInstance() {
		factory = new Factory(false, true, true, true);
		return factory.getArbre();
	}

	@Override
	public Map<Long, NeudArbre> getTableau(Arbre test) {
		return ArbreTestSetCalculer.super.getTableau(test);
	}

}
