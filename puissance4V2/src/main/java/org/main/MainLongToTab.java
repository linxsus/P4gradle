package org.main;

import org.P4Metier.Factory.Factory;
import org.P4Metier.id.GestIdDonneeLong;

public class MainLongToTab {

	public static void main(String[] args) {
		Factory factory = new Factory(false, true, true, true);
		GestIdDonneeLong donnee = (GestIdDonneeLong) factory.getGestIDDonnee();
		donnee.setIdBaseDonnee(22116L);
		System.out.println(donnee);

	}

}
