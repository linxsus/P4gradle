package org.main;

import java.util.Scanner;

import org.P4Metier.Gagnee;
import org.P4Metier.Ordinateur;
import org.P4Metier.Factory.Factory;
import org.P4Metier.id.GestIdDonneeLong;
import org.P4Metier.ordi.OrdinateurArbre;

public class MainArbre {

	static private Scanner sc;

	public static void main(String[] args) {
		Factory factory = new Factory(true, true, true, true);
		GestIdDonneeLong donnee = (GestIdDonneeLong) factory.getGestIDDonnee();
		Gagnee gagnee = factory.getGagnee();
		Ordinateur<Long> ordi = factory.getOrdinateur(donnee);
		ordi.setTourMax(4);
		boolean gg = false;

		sc = new Scanner(System.in);

		int colone;
		donnee.ajoutPion(4);
		donnee.ajoutPion(1);
		donnee.ajoutPion(3);
		donnee.ajoutPion(7);
		//donnee.ajoutPion(5);
		for (int i = 0; (i < (7 * 6)) && !gg; i++) {
			if (donnee.getJoueur() != 1) {
				System.out.println(donnee);
				do {
					colone = choisirColone(sc);
				} while (!donnee.ajoutPion(colone));
				gg = gagnee.isGagnee(donnee);
			} else {
				System.out.println(donnee);
				int aJouer = ordi.jouer();
				donnee.ajoutPion(aJouer);
				gg = gagnee.isGagnee(donnee);
			}
		}
		if (gg) {
			System.out.println("gagner\n" + donnee);
		} else {
			System.out.println("egalitee\n" + donnee);
		}
	}

	private static int choisirColone(Scanner sc) {
		int str;
		System.out.println("Veuillez saisir un nombre :");
		str = sc.nextInt();
		return str;
	}

}
