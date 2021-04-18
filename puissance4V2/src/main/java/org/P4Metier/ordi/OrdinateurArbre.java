package org.P4Metier.ordi;


import java.util.Random;


import org.P4Metier.Gagnee;
import org.P4Metier.GestIdDonnee;
import org.P4Metier.Ordinateur;
import org.P4Metier.Factory.Factory;
import org.P4Metier.id.GestIdDonneeLong;
import org.P4Modele_.Arbre;
import org.P4Modele_.Calculer;
import org.P4Modele_.GestDonnee;
import org.P4Modele_.Neud;
import org.P4Modele_.NeudArbre;

/**
 * @author Xavier Gouraud
 *
 *         classe pour calculer le pion a jouer pour gagner
 *
 */
public class OrdinateurArbre implements Ordinateur<Long> {
	protected GestIdDonnee<Long> donnee;
	// private boolean mysql = false;
	protected int tourMax = 5;
	protected Gagnee gagnee;
	protected Factory factory;
	protected Arbre arbre;
	protected long tron;

	/**
	 * creation de la class avec comme donnee donnee
	 *
	 * @param donnee
	 *            Donnee
	 */
	public OrdinateurArbre(Factory factory, GestIdDonnee<Long> donnee) {
		super();
		this.factory = factory;
		this.donnee = donnee;
		this.tron = donnee.getIdBaseDonnee();
		NeudArbre neud = factory.getMapArbre().get(donnee.getIdBaseDonnee());
		//si le neud tron n'existe pas je le cree
		if (neud == null) {
			neud = factory.getNeudArbre(donnee);
			factory.getMapArbre().put(tron, neud);
		}
		
		arbre = factory.getArbre();
		gagnee = factory.getGagnee();
		//if (tron!=0) {
		//arbre.setTron((GestIdDonneeLong) donnee, donnee.getNbPionJouer());
		//}
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
	 * retourne la colone a jouer avec un reglage sur la profondeur de l'arbre
	 *
	 * @param profondeur
	 * @return
	 */
	public int jouer(int profondeur) {
		tourMax = profondeur;
		return jouer();
	}

	/**
	 * retourne la colone a jouer
	 *
	 * @return colone a jouer
	 */
	@Override
	public int jouer() {
		int niveauInitial = donnee.getNbPionJouer();
		int niveau = niveauInitial;
		long idParent = 0;
		Neud next;
		GestIdDonnee<Long> donneeTravaille;
		int niveauMax = niveauInitial + tourMax;
		// je lui affecte le tron
		this.tron= donnee.getIdBaseDonnee();
		//si le neud tron n'existe pas je le cree
		NeudArbre neud = factory.getMapArbre().get(donnee.getIdBaseDonnee());
		if (neud == null) {
			neud = factory.getNeudArbre(donnee);
			factory.getMapArbre().put(tron, neud);
		}
		
		
		
		boolean ok = false;
		while (!ok) {
			// je recupere le neud suivant explorable
			next = arbre.nextExplorable(niveau);
			if (next == null) {
				//TODO a refaire car la notion de niveau n'existe pas ou est a definir autre part
				// niveau--;
				niveau++;
				if (niveau == niveauMax) {
					ok = true;
				}
				if (arbre.getNeud(tron).getCalculer() != Calculer.NONCALCULER) {
					next = arbre.nextExplorable(niveau);
					// ok=true;
					//TODO lancer une synchro
				}
			} else {

				// System.out.print(" "+next.getId());

				// TODO next.getId() puis donneeTravaille.getIdBaseDonnee() pourquoi recalculer
				// l'id??
				// je recupere le neud de travaille
				donneeTravaille = donnee.newBaseDonneeId(next.getId());
				idParent = donneeTravaille.getIdBaseDonnee();
				// pour toute les possibilter du neud
				for (int i :donneeTravaille.getColoneJouable()) {
					// j'ajoute le pion au jeux
					donneeTravaille.ajoutPion(i + 1);
					//j'ajoute le resultat au map en tant qu'enfant du jeux que je teste
					 arbre.addEnfant(idParent, (GestIdDonneeLong) donneeTravaille);
					// j'enleve le pion pour revenir a l'etat du jeux que je veux tester  
					 donneeTravaille.enleverPion();
				}
				//je calcule le resultat du neud que je vien traiter
				arbre.calculer(idParent);
				//TODO devrait etre dans le neud
				arbre.setExplorableFalse(idParent);
			}
		}
		// je calcul et je retourne la colonne a jouer 
		return calculer();
	}

	@Override
	public String toString() {
		return "OrdinateurBasic [donneeS=" + donnee + "]";
	}

	/**
	 * calcul le param pour le neud de l'arbre
	 *
	 * @param neud
	 *            neud a calculer.
	 * @param arbre
	 *            arbre
	 * @return la colonne a jouer
	 */
	private int calculer() {
		long resultat = 0;
		int nbPerdu = 0;
		int nbGagner = 0;
		int nbNonCalculer = 0; // a voir si utile
		int nbEgaliter = 0;
		long[] perduTab = new long[GestDonnee.LARGEUR];
		long[] gagnerTab = new long[GestDonnee.LARGEUR];
		long[] nonCalculerTab = new long[GestDonnee.LARGEUR];
		long[] egaliterTab = new long[GestDonnee.LARGEUR];
		Calculer enfantCalculer;
		
		//TODO pourquoi le if  (nbEnfant > 0) alors que le for devrais le faire
		int nbEnfant=arbre.getNeud(donnee.getIdBaseDonnee()).getEnfant().size();
		
		// si il y a des enfant
		if (nbEnfant > 0) {
			// pour tous les enfants
			for ( Long aCalculer : arbre.getNeud(donnee.getIdBaseDonnee()).getEnfant()) {
				Neud neudEnfant = arbre.getNeud(aCalculer);
				// au cas ou la gestion de l'arbre aurrais 'supprimer' l'enfant
				if (neudEnfant != null) {
					enfantCalculer = arbre.getNeud(aCalculer).getCalculer();
					// je calul si gagner, si j'ai bien tous les enfant de calculer le nb de perdu,
					// le nb d'egaliter
					switch (enfantCalculer) {
					case GAGNER:
						gagnerTab[nbGagner] = aCalculer; // la faut trouver autre chose
						nbGagner++;
						break;
					case INDEFINI:
					case NONCALCULER:
						nonCalculerTab[nbNonCalculer] = aCalculer; // la faut trouver autre chose
						nbNonCalculer++;
						break;
					case EGALITER:
						egaliterTab[nbEgaliter] = aCalculer; // la faut trouver autre chose
						nbEgaliter++;
						break;
					case PERDU:
					default:
						perduTab[nbPerdu] = aCalculer; // la faut trouver autre chose
						nbPerdu++;
						break;
					}
				}
			}

			// si sur un de mes enfant j'ai un gagner
			if (nbGagner > 0) {
				// je le choisie
				resultat = gagnerTab[0];
				// sinon si je perd sur tout saufe sur 1
			} else if ((nbPerdu + 1) == nbEnfant) {
				// je choisie celui ci
				if (nbNonCalculer > 0) {
					resultat = nonCalculerTab[0];
				} else {
					resultat = egaliterTab[0];
				}
				// sinon si j'ai une ou plusieur possibiliter sur un non calculer
			} else if (nbNonCalculer > 0) {
				// j'en choisie une au hazard
				resultat = auHazard(nonCalculerTab, nbNonCalculer);
				// sinon j'ai perdu donc j'en choisi une au hazard
			} else {
				resultat = auHazard(perduTab, nbPerdu);
			}
		}
		
		if (donnee.isMiroire()) {
			return (GestDonnee.LARGEUR - (difLong(tron,resultat)));
		} else {
			return difLong(tron,resultat) + 1;
		}
	}

	/**
	 * choisi un nb au hazard dans le tableau (tab) je ne choisi que dans les (nb)
	 * premier choix
	 *
	 * @param tab
	 *            tableaux des choix
	 * @param nb
	 *            nb premier choix
	 * @return le nb choisi
	 */
	private long auHazard(long[] tab, int nb) {
		Random rnd = new Random();
		int i = rnd.nextInt(nb);
		return tab[i];
	}

	public void setTourMax(int i) {
		tourMax = i;
	}

	/**
	 * 
	 */
	//TODO a deplacer dans une la bonne class
	//TODO a rendre comprensible
	private int difLong(long l1,long l2)
	{
		int resultat=0;
		int[] nbPionColone1=new int[7];
		int[] nbPionColone2=new int[7];
		//for (int i = LARGEUR - 1; i >= 0; i--) {
		for (int i = 7- 1; i >= 0; i--) {
			// je recupere le nb de pion mis
			nbPionColone1[i] = (int) (l1 % 7);
			nbPionColone2[i] = (int) (l2 % 7);
			// je decale pour pouvoir lire l'info suivante
			l1 /= 7;
			l2 /= 7;
			for (int j = 0; j < nbPionColone1[i]; j++) {
				l1 /= 2;
			}
			for (int j = 0; j < nbPionColone2[i]; j++) {
				l2 /= 2;
			}
		}
		int colDif=0;
		int resColDif=-1;
		int colDifMiroire=0;
		int resColDifMiroire=-1;
		for (int i=0;i<7;i++)
		{
			if (nbPionColone1[i]!=nbPionColone2[i]) {
				colDif++;
				resColDif=i;
			}
			if (nbPionColone1[i]!=nbPionColone2[6-i]) {
				colDifMiroire++;
				resColDifMiroire=i;
			}
		}
		if (colDif==1)
		{
			resultat=resColDif;
		}
		if (colDifMiroire==1)
		{
			resultat=resColDifMiroire;
		}
		
		return resultat;
	}
}
