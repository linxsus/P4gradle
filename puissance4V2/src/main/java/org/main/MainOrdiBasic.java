package org.main;

import java.util.Scanner;

import org.P4Metier.Gagnee;
import org.P4Metier.Ordinateur;
import org.P4Metier.Factory.Factory;
import org.P4Metier.Factory.TypeOrdinateur;
import org.P4Metier.id.GestIdDonneeLong;

public class MainOrdiBasic {
	static private Scanner sc;

	public static void main(String[] args) {
		// int temp[][] = { { 1, 2, 1, 2, 1, 2, 1 }, { 1, 2, 1, 2, 1, 2, 1 }, { 1, 2, 1,
		// 2, 1, 2, 1 },
		// { 2, 1, 2, 1, 2, 1, 2 }, { 2, 1, 2, 1, 2, 0, 2 }, { 0, 0, 0, 0, 0, 0, 0 } };
		Factory factory = new Factory(false, true, true, true);
		factory.setOrdinateur(TypeOrdinateur.Basic);
		GestIdDonneeLong donnee = (GestIdDonneeLong) factory.getGestIDDonnee();
		Gagnee gagnee = factory.getGagnee();
		Ordinateur<Long> ordi = factory.getOrdinateur(donnee);
		// donnee.setDonnee(temp);
		boolean gg = false;

		sc = new Scanner(System.in);

		int colone;
		for (int i = 0; (i < 2); i++) {
			if (donnee.getJoueur() == 1) {
				System.out.println(donnee);
				do {
					colone = choisirColone(sc);
				} while (!donnee.ajoutPion(colone));
				gg = gagnee.isGagnee(donnee);
			} else {
				int aJouer = ordi.jouer();
				donnee.ajoutPion(aJouer);
				gg = gagnee.isGagnee(donnee);
				System.out.println(i);
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
