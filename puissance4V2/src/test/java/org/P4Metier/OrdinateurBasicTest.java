package org.P4Metier;

import org.P4Metier.Factory.Factory;
import org.P4Metier.Factory.TypeOrdinateur;
import org.P4Metier.id.GestIdDonneeLong;
import org.junit.jupiter.api.BeforeEach;
import org.test.TestSql;

public class OrdinateurBasicTest implements OrdinateurTest<Long> {

	public TestSql sql;

	Factory factory;

	@BeforeEach
	public void init() {
		factory = new Factory(true, true, true, true);
		factory.setOrdinateur(TypeOrdinateur.Basic);
		sql = factory.getTestSql();
	}

	@Override
	public GestIdDonnee<Long> getDonee() {
		return (GestIdDonneeLong) factory.getGestIDDonnee();
	}

	@Override
	public Ordinateur<Long> getOrdinateur(GestIdDonnee<Long> donnee) {
		Ordinateur<Long> ordi = factory.getOrdinateur(donnee);
		return ordi;
	}

}
