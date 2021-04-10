package org.P4Metier.arbre;

import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.BiConsumer;

import org.P4Metier.Factory.Factory;
import org.P4Modele_.MapArbre;
import org.P4Modele_.Neud;
import org.P4Modele_.NeudArbre;
import org.P4Modele_.arbre.CopyTampon;
import org.P4Modele_.arbre.TamponBD;
import org.Persistant_.requette.SqlArbre;

/**
 * class permetant la synchronisation entre une base de donnee et la mapArbre
 *
 * @author  <a href="mailto:xavier.gouraud@wanadoo.fr">xavier</a> 
 *
 */
public class SynchronizationBD {

	/**
	 * nb d'enregistrement maximum a recharger (nbexplorable)
	 */
	public final static int NBEXPLORABLEMAX = 3000;

	/**
	 * @see Factory
	 */
	protected Factory factory;

	/**
	 * @see TamponBD
	 */
	protected TamponBD tampon;

	/**
	 * @see SqlArbre
	 */
	protected SqlArbre sqlArbre;

	/**
	 * enregistrement du temps au debut de la lecture des explorables
	 */
	protected long debut;

	/**
	 * le map des nouveau neud explorable
	 */
	protected Collection<Long> newExplorable;

	/**
	 * thread qui va lancer l'ecriture dans la base et la lecture des nouveau
	 * explorable
	 */
	protected Thread thread;
	/**
	 * @see CopyTampon
	 */
	protected CopyTampon copyTampon;

	/**
	 * @see Connection
	 */
	protected Connection cn;

	/**
	 * compte le nb de tour pour l'affichage
	 */
	protected int affichageNb = 1;

	
	/**
	 * Constructeur
	 *
	 * @param factory
	 */
	public SynchronizationBD(Factory factory) {
		this.factory = factory;
		sqlArbre = factory.getMysqlArbre();
		this.tampon = factory.getTampon();
		debut = System.currentTimeMillis();
		cn = factory.getBaseConnection();
		newExplorable = new HashSet<>(NBEXPLORABLEMAX);
		thread = null;
		copyTampon = factory.getCopyTampon();
		reprise();
	}

	/**
	 * synchronise la base de donnee et retourn la liste des explorables du niveau
	 *
	 *
	 * @param niveau
	 *            niveau sur la quelle on veux les explorable
	 * @return la liste des explorables
	 *
	 */
	public Collection<Long> synchronization(int niveau) {
		//System.out.println("synchro");
		// si ce n'est pas la 1er fois
//		if (thread != null) {
//			// on verifie que le thread precedent est bien finit
//			try {
//				thread.join();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//		 gestion de l'affichage
				if ((affichageNb % 1) == 0) {
					affichage(niveau, tampon);
				}
		// si on n'a pas d'explorable
//		if (newExplorable.size() == 0) {
			// on sauvegarde et on recupere la 1er tranche en directe
			copyTampon = factory.getCopyTampon();
			sauvegarde(copyTampon);
			
			
			//TODO ici le netoyage
			netoyage(niveau);
			
			newExplorable = sqlArbre.getExplorable(niveau, NBEXPLORABLEMAX);
			//System.out.print("\n nb eplorable pas chargee au max : "+newExplorable.size());
//		}
		
		affichageNb++;
//		// on met de coter les explorable recuperer
//		Map<Long, NeudArbre> tempExplorable = newExplorable;
//		// on vide les explorable pour que le thread puissent le remplire
//		newExplorable = new HashMap<>(NBEXPLORABLEMAX);
//		// on fait une copy du tampon
//		copyTampon = factory.getCopyTampon();
//		// on lance le thread sur 2eme tranche
//		thread = new Thread(new ThreadLoadExplorable(factory, newExplorable, niveau, NBEXPLORABLEMAX, copyTampon));
//		thread.start();
		
		// on retourne le map id neud des explorable
		// attention !!! cette liste n'est pas a jour il faut la verifier avant de
		// l'exploiter!!!
		// si il ont ete modifier il sont deja dans le map
		return newExplorable;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SynchronizationBD [newExplorable=" + newExplorable.size() + "\n tampon=" + tampon + "\n copyTampon="
				+ copyTampon + "]";
	}

	/**
	 * gestion de l'affichage
	 *
	 * @param niveau
	 * @param Tampon
	 */
	protected void affichage(int niveau, TamponBD Tampon) {
		if (tampon.getEditNeud().size()>0) {
		long fin = System.currentTimeMillis();
		System.out.println();
		long resultat = 0;
		if ((fin - debut) != 0) {
			resultat = (NBEXPLORABLEMAX * 10000) / (fin - debut);
		}

		String str = tampon.getEditNeud().size() +" object modifier " +   tampon.getNewNeud().size()+" object ajouter "  ;
		System.out.print(" niveau " + (niveau) +" " + str +  " op/10s " + resultat+" ");
//		if (resultat<1400) {
//			System.out.print("\n pause----------------------");
//			try {
//				Thread.sleep(10 * 1000);
//				fin = System.currentTimeMillis();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
		debut = fin;}
	}

	/**
	 * sauvegarde de copy tampon
	 *
	 * @param copyTampon
	 *            copy du tampon a sauvegarder
	 */
	private void sauvegarde(CopyTampon copyTampon) {
		// facon de faire pour gerer la reprise sur erreur / interuption 
		sqlArbre.saveReprise(copyTampon.getEditNeud());
		// sauvegarde
//		sqlArbre.removeLien(copyTampon.getRemoveLien());
		sqlArbre.saveNeud(copyTampon.getNewNeud());
//		sqlArbre.saveLien(copyTampon.getNewLien());
		sqlArbre.editNeud(copyTampon.getEditNeud());
		// tout a ete fait on peut suprimer la gestion de reprise sur erreur / interuption
		sqlArbre.removeReprise();
	}

	/**
	 * gestion apres un arret brutale. on suprime tous les lien enfant et on remet a
	 * explorable tous les neud modifier avant le crash.
	 *
	 * vue que les neud sont explorable il seront de nouveau calculer et les lien
	 * enfant seront recree.
	 */
	protected void reprise() {
		HashSet<Long> ids = sqlArbre.getReprise();
		for (Long id : ids) {
			sqlArbre.reinitNeud(id);
		}
		sqlArbre.removeReprise();

	}
	
	
	protected void netoyage(int niveau) {
		MapArbre tableau = factory.getMapArbre();
		Collection<Long> supprimable;
		long supprimer=0;
		do {
			supprimable = sqlArbre.getSupprimable(SynchronizationBD.NBEXPLORABLEMAX);
			for (Long id : supprimable) {
				suprimableLien(id);
				tampon.removeNeud(tableau.get(id));
			}
			copyTampon = factory.getCopyTampon();
//			sqlArbre.removeLien(copyTampon.getRemoveLien());
			sqlArbre.removeNeud(copyTampon.getRemoveNeud());
			sqlArbre.editNeud(copyTampon.getEditNeud());
			supprimer+= copyTampon.getRemoveNeud().size();
			if (supprimable.size() > 0) {
				System.out.print("x");
			}
			// TODO a voir si utile
			factory.getMapArbre().clear();
		} while (supprimable.size() > 0);
		if (supprimer!=0) {
			System.out.print("\n niveau " + (niveau) + " suppression de : " + supprimer+" ");
		}

	}

	/***
	 * on verifie si il y a des lien enfant suprimable et on marque ces lien en
	 * suprimable
	 *
	 * @param id
	 *            l'id des enfant a verifier
	 */
	protected void suprimableLien(Long parent) {
		MapArbre tableau=factory.getMapArbre();
		// pour tous les enfant de l'id
		Neud curent = tableau.get(parent);
		for (long enfant : curent.getEnfant()) {
			tableau.get(parent).removeEnfant(enfant);
		}
		//TODO ???? n'a pas vrament de sens puisqu,on le fait avec le parent
		if (curent.getParent().size()>0) {
			System.out.print("\n erreur il y a des parent on ne devrait pas le supprimer"+curent);
		}

	}
	
}
