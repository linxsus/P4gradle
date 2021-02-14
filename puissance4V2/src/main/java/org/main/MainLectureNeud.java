package org.main;

import java.util.Scanner;

import org.P4Metier.Factory.Factory;
import org.P4Metier.id.GestIdDonneeLong;
import org.P4Metier.ordi.OrdinateurArbre;
import org.P4Modele_.GestDonnee;
import org.P4Modele_.Neud;
import org.Persistant_.mysql.MysqlConnection;
import org.Persistant_.requette.SqlArbre;

public class MainLectureNeud {

	public static void main(String[] args) {
		SqlArbre sqlArbre;
		Factory factory = new Factory(true, true, true, true);
		sqlArbre = factory.getMysqlArbre();
		Scanner sc = new Scanner(System.in);
		while (true) {
		Neud neudArbre = sqlArbre.getNeud(choisirColone(sc));
		System.out.println(neudArbre);}
	}

	private static long choisirColone(Scanner sc) {
		long str;
		System.out.println("Veuillez saisir un nombre :");
		str = sc.nextLong();
		return str;
	}

}
