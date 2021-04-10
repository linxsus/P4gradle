package org.P4Metier.arbre;


import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.function.BiConsumer;

import org.P4Metier.Factory.Factory;
import org.P4Modele_.NeudArbre;
import org.P4Modele_.arbre.CopyTampon;
import org.Persistant_.requette.SqlArbre;

/**
 * thread pour la gestion de
 * 
 * 
 * @author  <a href="mailto:xavier.gouraud@wanadoo.fr">xavier</a> 
 *
 */
public class ThreadLoadExplorable implements Runnable {

	/**
	 * les nouveau explorable que l'on charge
	 */
	private Collection<Long> newExplorable;
	/**
	 * niveau sur le quelle on recherche des explorable
	 */
	private int niveau;
	/**
	 * maximum de neud qu' on recharge dans le tampon
	 */
	private int maxTampon;
//	/**
//	 * object pour cree les nouveau objet
//	 */
//	private Factory factory;
	/**
	 * object qui contien toute les requette sql.
	 * @see sqlArbre
	 * 
	 */
	private SqlArbre sqlArbre;

	/**
	 * a partir de quelle enregistrement je lit les explorables
	 * @see copyTampon
	 */
	private CopyTampon copyTampon;



	/**
	 * constructeur du thread
	 *
	 * @param factory
	 *            le factory
	 * @param newExplorable
	 *            le map dans le quelle on va retourner les newExplorable
	 * @param niveau
	 *            niveau sur le quelle on veut les explorable
	 * @param copyTampon
	 *            numero du granule a lire ( si il n'y a pas de donner sur ce
	 *            granule on prendre le precedent jusqu'a en trouver)
	 * @param maxTampon
	 *            maximum d'explorable demander
	 */
	public ThreadLoadExplorable(Factory factory, Collection<Long> newExplorable, int niveau, int maxTampon,
			CopyTampon copyTampon) {
//		this.factory = factory;
		this.newExplorable = newExplorable;
		this.niveau = niveau;
		this.maxTampon = maxTampon;
		this.copyTampon = copyTampon;
		sqlArbre = factory.getMysqlArbre();
	}

	@Override
	public void run() {
		// facon de faire pour gerer la reprise sur erreur / interuption 
		sqlArbre.saveReprise(copyTampon.getEditNeud());
		// sauvegarde
//		Thread r1 =new Thread() {
//			public void run() {sqlArbre.removeLien(copyTampon.getRemoveLien());}
//		};
//		r1.start();
//		try {
//			r1.join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		Thread r1 =new Thread() {
			public void run() {sqlArbre.saveNeud(copyTampon.getNewNeud());}
		};
//		Thread r2 =new Thread() {
//			public void run() {sqlArbre.saveLien(copyTampon.getNewLien());}
//		};
		r1.start();
//		r2.start();
		try {
			r1.join();
//			r2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		sqlArbre.editNeud(copyTampon.getEditNeud());
		// tout a ete fait on peut suprimer la gestion de reprise sur erreur / interuption
		sqlArbre.removeReprise();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// on recupere les neud sans parent ni enfant
		newExplorable.addAll(sqlArbre.getExplorable(niveau, maxTampon, maxTampon));
		// on recharge les parent des neud explorable
		//TODO vraiment utile????
//			for (Long id :newExplorable) {
//				factory.getMap().get(id).addParent(sqlArbre.getParent(id));
//			}
		
		// on genere un tableau vide pour les enfant (c'est un explorable il ne devrait
		// pas avoir d'enfant
//		newExplorable.forEach(new BiConsumer<Long, NeudArbre>() {
//			@Override
//			public void accept(Long key, NeudArbre value) {
//				value.addEnfant(new HashSet<>());
//			}
//		});

		// les newExplorable sont recharger
	}
}
