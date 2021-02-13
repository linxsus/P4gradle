package org.P4Metier.ordi;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.P4Metier.Gagnee;
import org.P4Metier.GestIdDonnee;
import org.P4Metier.Ordinateur;
import org.P4Metier.Factory.Factory;
import org.P4Modele_.GestDonnee;
import org.P4Modele_.map.MapOrdiBasic;

/**
 * @author Xavier Gouraud
 *
 *         classe pour calculer le pion a jouer pour gagner
 *
 */
public class OrdinateurBasic implements Ordinateur<Long> {
	private GestIdDonnee<Long> donnee;
	private int tourMax = (7 * 6) - 1;
	protected Gagnee gagnee;
	protected Factory factory;
	protected MapOrdiBasic mapResult;
	protected Map<Long, Integer> resultatColonne;

	/**
	 * creation de la class avec comme donnee donnee
	 *
	 * @param donnee
	 *            Donnee
	 */
	public OrdinateurBasic(Factory factory, GestIdDonnee<Long> donnee) {
		super();
		this.donnee = donnee;
		this.factory = factory;
		init();

	}

	private void init() {
		gagnee = factory.getGagnee();
		mapResult = new MapOrdiBasic(factory);
		resultatColonne = new HashMap<>();
	}

	/**
	 * retourn la colone a jouer pour les donnee donnee !!! reinitialise les donnee
	 * lors de la creation !!!
	 *
	 * @param donnee
	 *            Donnee
	 * @return colone a jouer pour les donnee donnee
	 */
	@Override
	public int jouer(GestIdDonnee<Long> donnee) {
		this.donnee = donnee; // reinitialise donnee
		return jouer();
	};

	/**
	 * retourne la colone a jouer
	 *
	 * @return colone a jouer
	 */
	@Override
	public int jouer() {
		int resultat = 0;
		// calcul du tableau des poids
		calculPoids(donnee, tourMax);
		// on recupere le param
		resultat = meilleurChoix();
		if (resultat == 0) // la je suis pas sure qu'il n'y a pas une erreur
		{
			resultat = auHazard();
			System.out.println("possible erreur dans OrdinateurBasic");
		}
		// mapResult.clear();
		return resultat;
	}

	/**
	 * calcul une colone au hasard dans les choix possible
	 *
	 * @return colone au hasard dans les choix possible
	 */
	private int auHazard() {
		int[] coloneJouable = donnee.getColoneJouable();
		Random rnd = new Random();
		return coloneJouable[rnd.nextInt(coloneJouable.length)] + 1; // une colone hasard dans le tableau coloneJouable
	}

	/**
	 * calcul le tableau des poids
	 *
	 * @param donnee
	 *            donnee a annalyser
	 * @param nbTour
	 *            nb de tour restant a effectuer
	 * @return le tableau des poids calculer
	 */
	protected int[] calculPoids(GestIdDonnee<Long> donnee, int nbTour) {
		// init des variable pour le calcul
		int[] coloneJouable = donnee.getColoneJouable();
		// init du resulta (mise a 0 de tous les champs)
		int[] resultat = new int[coloneJouable.length];

		// affichage pour un suivit lors de l'execution
		if ((nbTour > 22)) {
			System.out.println(nbTour);
			System.out.println(donnee);
			int[] pionJouer = donnee.getDernierJouerPion();
			int nbPionJouer = donnee.getNbPionJouer();
			for (int i = 5; i < nbPionJouer; i++) {
				System.out.print((pionJouer[i] + 1) + " ");
			}
			System.out.println(" ");

		}

		// calcul si on peut gagner a un endroie.
		if (nbTour > 0) { // si on est pas au max de tour
			nbTour--;
			for (int i = 0; i < coloneJouable.length; i++) { // pour chaque possibiliter restante.
				donnee.ajoutPion(coloneJouable[i] + 1); // j'ajoute un pion
				long id = donnee.getIdBaseDonnee();
				// si l'id nexiste pas (jamais calculer)
				if (!mapResult.containsKey(id)) {

					// si gagne
					if (gagnee.isGagnee(donnee)) {
						// je sauvegade le lien colonne,enfant que pour le niveau initial
						if (nbTour == (tourMax - 1)) {
							resultatColonne.put(id, coloneJouable[i]);
						}
						// je sauvegarde
						mapResult.put(id, 1000);
						// je remet donnee comme je l'ai trouver
						donnee.enleverPion();
						// et je termine la boucle en disant qu'ici on gagne
						return new int[] { 1000 };
						// si je n'ai pas gagner
					} else {
						// on calcul les poids pour un niveau superieur
						int[] temps = {};
						if (donnee.getNbPionJouer() < (GestDonnee.HAUTEUR * GestDonnee.LARGEUR)) {
							temps = calculPoids(donnee, nbTour);
							resultat[i] = 0 - calculResultat(temps);
						} else {
							// TODO ici on met un -1000 pour considere que un null c'est perdu pour le 1er
							// joueur.
							resultat[i] = 0;
						}

						mapResult.put(id, resultat[i]);
					}
				} else {
					resultat[i] = mapResult.get(id);
					if (mapResult.get(id) == 1000) {
						donnee.enleverPion();
						return new int[] { 1000 };
					}
				}
				// je sauvegade le lien colonne,enfant que pour le niveau initial.
				if (nbTour == (tourMax - 1)) {
					resultatColonne.put(id, coloneJouable[i]);
				}
				donnee.enleverPion();
			}

		}
		return resultat;
	}

	@Override
	public String toString() {
		return "OrdinateurBasic [donneeS=" + donnee + "]";
	}

	/**
	 * calcul le meilleur choix dans le tableau tabResultat
	 *
	 * @param tabResultat
	 *            tableau de forme DonneeOrdinateurOld
	 * @return le meilleur choix dans le tableau tabResultat
	 */
	protected int meilleurChoix() {
		int resultat = 0;
		int meilleurChois = -1000;
		// String message="";
		for (Entry<Long, Integer> entry : resultatColonne.entrySet()) // pour chaque element du tableau
		{
			int calculer = mapResult.get(entry.getKey());
			if (calculer > meilleurChois) { // si la valeur est meilleur que la precedente
				meilleurChois = calculer; // on enregistre la valeur
				resultat = entry.getValue() + 1;
			}
		}
		resultatColonne.clear();
		return resultat; // on retourne le meilleur choix dans le tableau tabResultat
	}

	/**
	 * calcul le param d'un tableau (somme des poids du tableau/nb d'element du
	 * tableau)
	 *
	 * @param tabResultat
	 *            tableau de DonneeOrdinateurOld[] a calculer
	 * @return le param du tableau
	 */
	protected int calculResultat(int[] tabResultat) {
		int resultat = 0;
		for (int i = 0; i < tabResultat.length; i++) // pour toutes les valeur du tableau
		{
			resultat += tabResultat[i]; // je les aditionnes
		}
		resultat = resultat / tabResultat.length; // je le divise par le nb d'element du tableau.
		return resultat;
	}
	
	//TODO
	public void setTourMax(int i) {
		tourMax = i;
	}
};
