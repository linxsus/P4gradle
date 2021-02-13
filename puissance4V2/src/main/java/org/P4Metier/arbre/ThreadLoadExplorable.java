package org.P4Metier.arbre;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Map;
import java.util.function.BiConsumer;

import org.P4Metier.Factory.Factory;
import org.P4Modele_.NeudArbre;
import org.P4Modele_.arbre.CopyTampon;
import org.Persistant_.requette.SqlArbre;

public class ThreadLoadExplorable implements Runnable {

	/**
	 * les nouveau explorable que l'on charge
	 */
	private Map<Long, NeudArbre> newExplorable;
	/**
	 * niveau sur le quelle on recherche des explorable
	 */
	private int niveau;
	/**
	 * maximum de neud qu' on recharge dans le tampon
	 */
	private int maxTampon;
	/**
	 * object pour cree les nouveau objet
	 */
	private Factory factory;
	/**
	 * object qui contien toute les requette sql.
	 */
	private SqlArbre sqlArbre;

	/**
	 * a partir de quelle enregistrement je lit les explorables
	 */
	private CopyTampon copyTampon;

	private Connection cn;

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
	public ThreadLoadExplorable(Factory factory, Map<Long, NeudArbre> newExplorable, int niveau, int maxTampon,
			CopyTampon copyTampon) {
		this.factory = factory;
		this.newExplorable = newExplorable;
		this.niveau = niveau;
		this.maxTampon = maxTampon;
		this.copyTampon = copyTampon;
		sqlArbre = factory.getMysqlArbre();
		cn = factory.getBaseConnection();
	}

	@Override
	public void run() {
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

		// on recupere les neud sans parent ni enfant
		newExplorable.putAll(sqlArbre.getExplorable(niveau, maxTampon, maxTampon));
		// on recharge les parent des neud explorable
		newExplorable.forEach(new BiConsumer<Long, NeudArbre>() {
			@Override
			public void accept(Long key, NeudArbre value) {
				value.addParent(sqlArbre.getParent(key));
			}
		});
		// on genere un tableau vide pour les enfant (c'est un explorable il ne devrait
		// pas avoir d'enfant
		newExplorable.forEach(new BiConsumer<Long, NeudArbre>() {
			@Override
			public void accept(Long key, NeudArbre value) {
				value.addEnfant(new HashSet<>());
			}
		});

		// les newExplorable sont recharger
	}
}
