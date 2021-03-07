package org.P4Metier.arbre;

import java.sql.Connection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.P4Metier.Factory.Factory;
import org.P4Modele_.NeudArbre;
import org.P4Modele_.arbre.CopyTampon;
import org.P4Modele_.arbre.TamponBD;
import org.Persistant_.requette.SqlArbre;

/**
 * class permetant la synchronisation entre une base de donnee et la mapArbre
 *
 * @author Xavier Gouraud
 *
 */
public class SynchronizationBD {

	/**
	 * nb d'enregistrement maximum a recharger (nbexplorable)
	 */
	public final static int NBEXPLORABLEMAX = 10000;

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
	protected Map<Long, NeudArbre> newExplorable;

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
		newExplorable = new HashMap<>(NBEXPLORABLEMAX);
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
	public Map<Long, NeudArbre> synchronization(int niveau) {

		// si ce n'est pas la 1er fois
		if (thread != null) {
			// on verifie que le thread precedent est bien finit
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// gestion de l'affichage
				if ((affichageNb % 1) == 0) {
					affichage(niveau, tampon);
				}
		// si on n'a pas d'explorable
		if (newExplorable.size() == 0) {
			// on sauvegarde et on recupere la 1er tranche en directe
			copyTampon = factory.getCopyTampon();
			sauvegarde(copyTampon);
			newExplorable = sqlArbre.getExplorable(niveau, NBEXPLORABLEMAX);
		}
		
		affichageNb++;
		// on met de coter les explorable recuperer
		Map<Long, NeudArbre> tempExplorable = newExplorable;
		// on vide les explorable pour que le thread puissent le remplire
		newExplorable = new HashMap<>(NBEXPLORABLEMAX);
		// on fait une copy du tampon
		copyTampon = factory.getCopyTampon();
		// on lance le thread sur 2eme tranche
		thread = new Thread(new ThreadLoadExplorable(factory, newExplorable, niveau, NBEXPLORABLEMAX, copyTampon));
		thread.start();

		// on retourne le map id neud des explorable
		// attention !!! cette liste n'est pas a jour il faut la verifier avant de
		// l'exploiter!!!
		// si il ont ete modifier il sont deja dans le map
		return tempExplorable;
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
			resultat = (tampon.getEditNeud().size() * 10000) / (fin - debut);
		}

		String str = "object modifier " + tampon.getEditNeud().size() + " object ajouter " + tampon.getNewNeud().size()
				+ " object suprimer " + tampon.getRemoveNeud().size();
		System.out.print(" niveau " + (niveau + 1) +" " + str +  " op/s " + resultat);
		debut = fin;}
	}

	/**
	 * sauvegarde de copy tampon
	 *
	 * @param copyTampon
	 *            copy du tampon a sauvegarder
	 */
	private void sauvegarde(CopyTampon copyTampon) {
		// facon de faire pour desactiver l'autocomit :(
		sqlArbre.saveReprise(copyTampon.getEditNeud());
		// sauvegarde
		sqlArbre.removeLien(copyTampon.getRemoveLien());
		sqlArbre.removeNeud(copyTampon.getRemoveNeud());
		sqlArbre.saveNeud(copyTampon.getNewNeud());
		sqlArbre.saveLien(copyTampon.getNewLien());
		sqlArbre.editNeud(copyTampon.getEditNeud());
		// le commit
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
}
