package org.main;

import org.P4Metier.Factory.Factory;
import org.P4Metier.id.GestIdDonneeLong;
import org.P4Metier.ordi.OrdinateurAlphaBeta;

public class MainMinMax {

	public static void main(String[] args) {
		int temp[][] = { { 0, 0, 1, 1, 0, 0, 0 }, { 0, 0, 2, 2, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 } };
		Factory factory = new Factory(true, true, true, true);
		// MysqlConnection mysqlconnexion = factory.getMysqlConnection();

		// mysqlconnexion.setUrl("jdbc:mysql://192.168.1.16/Puissance4?autoReconnect=true&useSSL=false");
		GestIdDonneeLong donnee = (GestIdDonneeLong) factory.getGestIDDonnee();
		donnee.setDonnee(temp);
		OrdinateurAlphaBeta ordi = new OrdinateurAlphaBeta(factory, donnee);
		System.out.println(ordi.jouer());

	}

}
