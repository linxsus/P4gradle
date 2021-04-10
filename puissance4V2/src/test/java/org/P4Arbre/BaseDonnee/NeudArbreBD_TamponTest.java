package org.P4Arbre.BaseDonnee;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.P4Arbre.NeudTest;
import org.P4Metier.Factory.Factory;
import org.P4Modele_.Calculer;
import org.P4Modele_.MapArbre;
import org.P4Modele_.Neud;
import org.P4Modele_.NeudArbre;
import org.P4Modele_.arbre.TamponBD;
import org.Persistant_.requette.SqlArbre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// test pas tres fiable on  ne test pas toutes les fonctions
// et on ne teste principalement que sur la presence ou non d'un param, pas sur la valeur du param
class NeudArbreBD_TamponTest implements NeudTest {

	protected Factory factory;
	protected TamponBD tampon;
	protected SqlArbre sqlArbre;
	protected NeudArbre neudBase;
	protected MapArbre mapArbre;

	@BeforeEach
	public void init() {
		// true, true, true, true
		factory = new Factory(true, true, true, true);
		tampon = factory.getTampon();
		factory.getTestSql();
		sqlArbre = factory.getMysqlArbre();
		mapArbre = factory.getMapArbre();

	}

	public void creationNeuEnBase() {
		neudBase = factory.getNeudArbre(1L);
		mapArbre.put(1L, neudBase);
		neudBase.addEnfant(2L);
		neudBase.addEnfant(3L);
		neudBase.addEnfant(4L);
		neudBase.addParent(5L);
		neudBase.addParent(6L);
		neudBase.addParent(7L);
		sqlArbre.removeLien(tampon.getRemoveLien());
		sqlArbre.saveNeud(tampon.getNewNeud());
		sqlArbre.saveLien(tampon.getNewLien());
		sqlArbre.editNeud(tampon.getEditNeud());
		;
	}

	@Test
	void constructorArbreTest() {
		tampon.getNewNeud();// on vide les new neud
		assertTrue(tampon.getNewNeud().size() == 0,
				"la liste des newNeud de tampon devrait etre a 0" + tampon.getNewNeud());
		NeudArbre test = factory.getNeudArbre(15L);
		test.isExplorable(); // pour avoir une execution et etre sure que l'objet est bien cree
		assertTrue(tampon.getNewNeud().size() > 0,
				"il n'y a pas de neud dans le tampon apre la creation d'un neud" + tampon.getNewNeud());
	}

	@Test
	void setCalculerArbreTest() {
		tampon.getEditNeud();// on vide les editneud
		assertTrue(tampon.getEditNeud().size() == 0,
				"la liste des editNeud de tampon devrait etre a 0" + tampon.getNewNeud());
		NeudArbre test = factory.getNeudArbre(10L);
		mapArbre.put(10L, test);
		test.setCalculer(Calculer.GAGNER);
		assertTrue(tampon.getEditNeud().size() == 0,
				"le neud ne devrait pas etre dans les editable puisqu'il est dans les new");
		tampon.initNewNeud();
		;
		test.setCalculer(Calculer.PERDU);
		assertTrue(tampon.getEditNeud().size() > 0,
				"il n'y a pas de neud editer dans le tampon apre la modification d'un neud" + tampon.getEditNeud());
	}

	@Test
	void setExplorableArbreTest() {
		tampon.getEditNeud();// on vide les editneud
		assertTrue(tampon.getEditNeud().size() == 0,
				"la liste des editNeud de tampon devrait etre a 0" + tampon.getEditNeud());
		NeudArbre test = factory.getNeudArbre(10L);
		mapArbre.put(10L, test);
		test.setExplorable(false);
		assertTrue(tampon.getEditNeud().size() == 0,
				"le neud ne devrait pas etre dans les editable puisqu'il est dans les new");
		tampon.initNewNeud();
		;
		test.setExplorable(true);
		assertTrue(tampon.getEditNeud().size() > 0,
				"il n'y a pas de neud editer dans le tampon apre la modification d'un neud" + tampon.getEditNeud());
	}

	@Test
	void addParentArbreTest() {
		tampon.getNewLien();// on vide les editneud
		assertTrue(tampon.getNewLien().size() == 0,
				"la liste des editNeud de tampon devrait etre a 0" + tampon.getNewLien());
		NeudArbre test = factory.getNeudArbre(10L);
		mapArbre.put(10L, test);
		test.addParent(15L);
		assertTrue(tampon.getNewLien().size() > 0,
				"il n'y a pas de lien dans le tampon apre la creation d'un lien" + tampon.getNewLien());
	}

	@Test
	void addEnfantArbreTest() {
		tampon.getNewLien();// on vide les editneud
		assertTrue(tampon.getNewLien().size() == 0,
				"la liste des editNeud de tampon devrait etre a 0" + tampon.getNewLien());
		NeudArbre test = factory.getNeudArbre(10L);
		mapArbre.put(10L, test);
		test.addEnfant(15L);
		assertTrue(tampon.getNewLien().size() > 0,
				"il n'y a pas de lien dans le tampon apre la creation d'un lien" + tampon.getNewLien());
	}

	@Test
	void removeEnfantArbreTest() {
		NeudArbre test = factory.getNeudArbre(10L);
		mapArbre.put(10L, test);
		tampon.getRemoveLien();// on vide
		assertTrue(tampon.getRemoveLien().size() == 0,
				"la liste des RemoveLien de tampon devrait etre a 0" + tampon.getRemoveLien());
		tampon.getNewLien();// on vide
		assertTrue(tampon.getNewLien().size() == 0,
				"la liste des NewLien de tampon devrait etre a 0" + tampon.getNewLien());

		test.addEnfant(15L);
		test.removeEnfant(15L);
		assertTrue(tampon.getRemoveLien().size() == 0,
				"apres un ajout et suppression du meme lien sans 'commit' il ne devrait rien y avoir dans remove lien"
						+ tampon.getRemoveLien());
		assertTrue(tampon.getNewLien().size() == 0,
				"apres un ajout et suppression du meme lien sans 'commit' il ne devrait rien y avoir dans new lien"
						+ tampon.getNewLien());

		test.addEnfant(15L);
		tampon.initNewLien();
		;
		test.removeEnfant(15L);
		assertTrue(tampon.getRemoveLien().size() == 1,
				"apres un ajout 'commit' puis  suppression du meme lien il devrait y avoir un remove lien"
						+ tampon.getRemoveLien());
	}

	@Override
	public NeudArbre creatInstance() {
		NeudArbre neud = factory.getNeudArbre();
		mapArbre.put(neud.getId(), neud);
		return neud;
	}

	@Test
	void saveBaseTest() {
		creationNeuEnBase();
		NeudArbre neud = sqlArbre.getNeud(1L);
		neud.getEnfant();
		neud.getParent();
		assertTrue(neud.toString().equals(neudBase.toString()), " erreur" + neud + " n'est pas egale a " + neudBase);
	}

	@Test
	void saveBaseGetTest() {
		creationNeuEnBase();
		MapArbre mapArbre = factory.getMapArbre();
		Neud neud = mapArbre.nextExplorable(0);
		assertTrue(!neud.toString().equals(neudBase.toString()),
				" erreur" + neud + " ne devrait pas etre egalle avent un getEnfant " + neudBase);
		neud.getEnfant();
		neud.getParent();
		assertTrue(neud.toString().equals(neudBase.toString()),
				" erreur" + neud + " n'est pas egale apres un getEnfant " + neudBase);
	}

	@Test
	void saveBaseRemoveTest() {
		creationNeuEnBase();
		MapArbre mapArbre = factory.getMapArbre();
		NeudArbre neud = mapArbre.nextExplorable(0);
		assertTrue(!neud.toString().equals(neudBase.toString()),
				" erreur" + neud + " ne devrait pas etre egalle avent un getEnfant " + neudBase);
		neud.removeEnfant(2L);
		// neud.removeParent(5L);
		neud.getParent();
		neudBase.removeEnfant(2L);
		// neudBase.removeParent(5L);
		assertTrue(neud.toString().equals(neudBase.toString()),
				" erreur" + neud + " n'est pas egale apres un getEnfant " + neudBase);
		sqlArbre.removeLien(tampon.getRemoveLien());
		sqlArbre.saveNeud(tampon.getNewNeud());
		sqlArbre.saveLien(tampon.getNewLien());
		sqlArbre.editNeud(tampon.getEditNeud());
		mapArbre = factory.getMapArbre();
		neud = mapArbre.nextExplorable(0);
		assertTrue(!neud.toString().equals(neudBase.toString()),
				" erreur" + neud + " ne devrait pas etre egalle avent un getEnfant " + neudBase);
		neud.getEnfant();
		neud.getParent();
		assertTrue(neud.toString().equals(neudBase.toString()),
				" erreur" + neud + " n'est pas egale apres un getEnfant " + neudBase);

	}
}
