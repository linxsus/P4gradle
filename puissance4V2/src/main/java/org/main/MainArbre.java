package org.main;



import java.text.DateFormat;
import java.util.Date;
import java.util.Scanner;

//import java.util.Scanner;

//import org.P4Metier.Gagnee;
import org.P4Metier.Ordinateur;
import org.P4Metier.Factory.Factory;
import org.P4Metier.id.GestIdDonneeLong;


public class MainArbre {

//	static private Scanner sc;

	public static void main(String[] args) {
		Factory factory = new Factory(true, true, true, true);
		GestIdDonneeLong donnee = (GestIdDonneeLong) factory.getGestIDDonnee();
//		Gagnee gagnee = factory.getGagnee();
		Ordinateur<Long> ordi = factory.getOrdinateur(donnee);
		ordi.setTourMax(50);
//		boolean gg = false;

//		sc = new Scanner(System.in);

//		int colone;
//		donnee.ajoutPion(4);// 1
//		donnee.ajoutPion(7);// 2
//		donnee.ajoutPion(3);// 3
//		donnee.ajoutPion(1);// 4
		
		donnee.ajoutPion(4);// 1
		donnee.ajoutPion(7);// 2
		donnee.ajoutPion(4);// 3
		donnee.ajoutPion(4);// 4
		donnee.ajoutPion(4);// 5
		
		donnee.ajoutPion(3);// 6
		donnee.ajoutPion(2);// 7
		donnee.ajoutPion(3);// 8
		donnee.ajoutPion(3);// 9
		donnee.ajoutPion(4);// 10
		donnee.ajoutPion(2);// 11
		donnee.ajoutPion(1);// 12
//		donnee.ajoutPion(3);// 13
//		donnee.ajoutPion(7);// 14
//		donnee.ajoutPion(3);// 15
//		donnee.ajoutPion(3);// 16
//		donnee.ajoutPion(5);// 17
//		donnee.ajoutPion(6);// 18
//		donnee.ajoutPion(7);// 19
//		donnee.ajoutPion(6);// 20
//		donnee.ajoutPion(5);// 21
//		donnee.ajoutPion(6);//22
//		donnee.ajoutPion(6);//23
//		donnee.ajoutPion(6);//24
//		donnee.ajoutPion(4);
		
		//donnee.ajoutPion(4);// 6
		//donnee.ajoutPion(3);// 7
//		donnee.ajoutPion(2);// 8
//		donnee.ajoutPion(3);// 9
//		donnee.ajoutPion(2);// 10
//		donnee.ajoutPion(6);// 11
//		donnee.ajoutPion(5);// 12
//		donnee.ajoutPion(6);// 13
//		donnee.ajoutPion(5);// 14
//		donnee.ajoutPion(5);// 15
//		
//		donnee.ajoutPion(2);// 16
//		donnee.ajoutPion(2);// 17
//		donnee.ajoutPion(2);// 18
//		donnee.ajoutPion(5);// 19	
//		donnee.ajoutPion(4);// 20
//		donnee.ajoutPion(2);// 21	
//		donnee.ajoutPion(5);// 22
//		donnee.ajoutPion(7);// 23	
//		donnee.ajoutPion(1);// 24
//		donnee.ajoutPion(7);// 25	
//		donnee.ajoutPion(1);// 26
//		donnee.ajoutPion(7);// 27	
//		donnee.ajoutPion(7);// 28
//		for (int i = 0; (i < (7 * 6)) && !gg; i++) {
//			if (donnee.getJoueur() != 1) {
//				System.out.println("\n"+donnee+"\n"+donnee.getIdBaseDonnee());
//				do {
//					colone = choisirColone(sc);
//				} while (!donnee.ajoutPion(colone));
//				gg = gagnee.isGagnee(donnee);
//			} else {
		

				System.out.println("joueur :"+donnee.getJoueur()+"\n"+donnee+"\n"+donnee.getIdBaseDonnee());
				Date debutlancement = new Date();
				DateFormat fullDateFormat = DateFormat.getDateTimeInstance(
				        DateFormat.FULL,
				        DateFormat.FULL);
				int aJouer = ordi.jouer();
				Date finlancement = new Date();
				System.out.println("\n"+fullDateFormat.format(debutlancement));
				System.out.println("\n"+fullDateFormat.format(finlancement));
				donnee.ajoutPion(aJouer);
				System.out.println("\n"+factory.getMysqlArbre().getNeud(donnee.getIdBaseDonnee()));
//				gg = gagnee.isGagnee(donnee);
//			}
//		}
//		if (gg) {
//			System.out.println("gagner\n" + donnee);
//		} else {
//			System.out.println("egalitee\n" + donnee);
//		}
	}

//	private static int choisirColone(Scanner sc) {
//		int str;
//		System.out.println("Veuillez saisir un nombre :");
//		str = sc.nextInt();
//		return str;
//	}

}
