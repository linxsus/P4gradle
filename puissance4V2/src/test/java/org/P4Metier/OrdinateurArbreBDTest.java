package org.P4Metier;

import org.P4Metier.Factory.Factory;
import org.P4Metier.id.GestIdDonneeLong;
import org.P4Metier.ordi.OrdinateurArbreBD;
import org.Persistant_.requette.SqlArbre;
import org.junit.jupiter.api.BeforeEach;
import org.test.TestSql;

public class OrdinateurArbreBDTest implements OrdinateurTest<Long> {

	public TestSql sql;
	public SqlArbre sqlArbre;

	Factory factory;

	@BeforeEach
	public void init() {
		factory = new Factory(true, true, true, true);
		sql = factory.getTestSql();
		sqlArbre = factory.getMysqlArbre();
	}

	@Override
	public GestIdDonnee<Long> getDonee() {
		return (GestIdDonneeLong) factory.getGestIDDonnee();
	}

	@Override
	public Ordinateur<Long> getOrdinateur(GestIdDonnee<Long> donnee) {
		OrdinateurArbreBD ordi = (OrdinateurArbreBD) factory.getOrdinateur(donnee);
		ordi.setTourMax(5);
		return ordi;
	}

}
