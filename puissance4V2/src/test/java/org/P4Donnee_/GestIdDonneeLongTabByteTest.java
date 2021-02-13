package org.P4Donnee_;

import org.P4Metier.Factory.Factory;
import org.P4Metier.id.GestIdDonneeLong;
import org.junit.jupiter.api.BeforeEach;

class GestIdDonneeLongTabByteTest extends GestIdDonneeLongTest {

	@Override
	@BeforeEach
	public void init() {
		factory = new Factory(false, true, true, true);
		donnee = (GestIdDonneeLong) factory.getGestIDDonnee();
		donnee.ajoutPion(1);
		donnee.ajoutPion(3);
		donnee.ajoutPion(2);
		donnee.ajoutPion(5);
		donnee.ajoutPion(4);
		donnee.ajoutPion(4);
		donnee.ajoutPion(3);
		donnee.ajoutPion(4);
		donnee.ajoutPion(7);
		donnee.ajoutPion(2);
		donnee.ajoutPion(1);

	}
}
