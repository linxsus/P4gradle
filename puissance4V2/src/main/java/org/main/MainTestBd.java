package org.main;

import java.util.Scanner;

import org.P4Metier.Factory.Factory;
import org.P4Metier.id.GestIdDonneeLong;
import org.P4Metier.ordi.OrdinateurArbre;
import org.P4Modele_.GestDonnee;
import org.Persistant_.mysql.MysqlConnection;

public class MainTestBd {

	public static void main(String[] args) {
		int temp[][] = { { 0, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 } };
		Factory factory = new Factory(true, true, true, true);
		GestIdDonneeLong test=new GestIdDonneeLong(factory);
		Scanner sc = new Scanner(System.in);
		System.out.println(test.getDonneeId(choisirColone(sc)));
	}

	private static long choisirColone(Scanner sc) {
		long str;
		System.out.println("Veuillez saisir un nombre :");
		str = sc.nextLong();
		return str;
	}

}
