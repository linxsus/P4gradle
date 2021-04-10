package org.Mysql;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.P4Metier.Factory.Factory;
import org.P4Modele_.Calculer;
import org.P4Modele_.MapArbre;
import org.P4Modele_.Neud;
import org.P4Modele_.NeudArbre;
import org.P4Modele_.arbre.Lien;
import org.P4Modele_.arbre.TamponBD;
import org.Persistant_.requette.SqlArbre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.test.TestSql;

class MysqlArbreTest {

	public TestSql sql;
	public SqlArbre sqlArbre;
	public Factory factory;
	public TamponBD tampon;
	public MapArbre mapArbre;

	@BeforeEach
	public void init() {
		factory = new Factory(true, true, true, true);
		sql = factory.getTestSql();
		sqlArbre = factory.getMysqlArbre();
		tampon = factory.getTampon();
		mapArbre = factory.getMapArbre();
	}

	@Test
	void testSaveNeudNeud() {
		Neud neud = factory.getNeudArbre(4L);
		sqlArbre.saveNeud(neud);
		String requette = "select * from neud where idneud=4;";
		assertTrue(sql.isOneLigne(requette), "erreur dans la sauvegarde d'un neud");
	}

	@Test
	void testSaveNeudListOfNeud() {
		Collection<Neud> collection = new ArrayList<>();
		collection.add(factory.getNeudArbre(4L));
		collection.add(factory.getNeudArbre(6L));
		collection.add(factory.getNeudArbre(10L));
		sqlArbre.saveNeud(collection);
		String requette = "select * from neud where 3=(select count(*) from neud) and idneud=4;";
		assertTrue(sql.isOneLigne(requette), "erreur dans la sauvegarde de plusieurs neud");
	}

	@Test
	void testGetExplorable() {
		Collection<Neud> collection = new ArrayList<>();
		collection.add(factory.getNeudArbre(4L, 0));
		collection.add(factory.getNeudArbre(6L, 0));
		collection.add(factory.getNeudArbre(10L, 0));
		sqlArbre.saveNeud(collection);
		Collection<Long> resultat = sqlArbre.getExplorable(0, 2);
		Long[] temp = {10L, 6L };
		assertTrue(Arrays.equals(resultat.toArray(), temp), "erreur dans le GetExplorable"
				+ Arrays.toString(resultat.toArray()) + " au lieux de" + Arrays.toString(temp));
		resultat = sqlArbre.getExplorable(0, 10);
		Long[] temp1 = { 10L, 6L, 4L };
		assertTrue(Arrays.equals(resultat.toArray(), temp1), "erreur dans le GetExplorable"
				+ Arrays.toString(resultat.toArray()) + " au lieux de " + Arrays.toString(temp1));
	}

	@Test
	void testSaveLien() {
		Collection<Lien> collection = new ArrayList<>();
		collection.add(new Lien(4L, 10L));
		collection.add(new Lien(5L, 10L));
		collection.add(new Lien(6L, 10L));
		collection.add(new Lien(4L, 11L));
		sqlArbre.saveLien(collection);
		String requette = "select * from lien where 4=(select count(*) from lien) and enfant=11;";
		assertTrue(sql.isOneLigne(requette), "erreur dans la sauvegarde des lien");

	}

	@Test
	void testEditNeud() {
		NeudArbre neud = factory.getNeudArbre(4L);
		sqlArbre.saveNeud(neud);
		mapArbre.put(neud.getId(), neud);
		String requette = "select * from neud where idneud=4 and explorable=1;";
		assertTrue(sql.isOneLigne(requette), "erreur dans l'update d'un neud");
		neud.setExplorable(false);
		Collection<Neud> collection = new ArrayList<>();
		collection.add(neud);
		sqlArbre.editNeud(collection);
		requette = "select * from neud where idneud=4 and explorable=0;";
		assertTrue(sql.isOneLigne(requette), "erreur dans l'update d'un neud");
	}

	@Test
	void testRemoveNeud() {
		Neud neud = factory.getNeudArbre(4L);
		sqlArbre.saveNeud(neud);
		String requette = "select * from neud where idneud=4;";
		assertTrue(sql.isOneLigne(requette), "erreur dans la sauvegarde d'un neud");
		Collection<Long> collection = new ArrayList<>();
		collection.add(4L);
		sqlArbre.removeNeud(collection);
		requette = "select * from neud where idneud=4;";
		assertTrue(!sql.isOneLigne(requette), "erreur dans la suppresion d'un neud");
	}

	@Test
	void testRemoveLien() {
		Collection<Lien> collection = new ArrayList<>();
		collection.add(new Lien(4L, 10L));
		collection.add(new Lien(5L, 10L));
		collection.add(new Lien(6L, 10L));
		collection.add(new Lien(4L, 11L));
		sqlArbre.saveLien(collection);
		String requette = "select * from lien where 4=(select count(*) from lien) and enfant=11;";
		assertTrue(sql.isOneLigne(requette), "erreur dans la sauvegarde des lien");
		sqlArbre.removeLien(collection);
		requette = "select * from lien where enfant=11;";
		assertTrue(!sql.isOneLigne(requette), "erreur dans la supression des lien");

	}

	@Disabled
	@Test
	void testGetNeud() {
		NeudArbre neud = factory.getNeudArbre(4L, 1);
		neud.setCalculer(Calculer.EGALITER);
		neud.setExplorable(false);
		neud.setFeuille(false);
		// test avec un seul enfant car sinon la relecture peut inverser l'ordre
		neud.addEnfant(10L);
		// test avec un seul parent car sinon la relecture peut inverser l'ordre
		neud.addParent(700L);
		sqlArbre.saveNeud(neud);
		sqlArbre.saveLien(tampon.getNewLien());
		NeudArbre newNeud = sqlArbre.getNeud(4L);
		assertTrue(neud.equals(newNeud), "erreur dans la recuperation d'un neud");
	}
}
