/**
 *
 */
package org.P4Modele_.arbre;

import java.util.Collection;
import java.util.HashSet;

import org.P4Metier.Factory.Factory;
import org.P4Metier.arbre.SynchronizationBD;
import org.P4Modele_.GestDonnee;
import org.P4Modele_.Neud;

/**
 * 
 * 
 * @author  <a href="mailto:xavier.gouraud@wanadoo.fr">xavier</a> 
 *
 */

// TODO commentaire
public class TamponBD {

	// liste des nouveau neud
	private final static int maxEnregistrement = SynchronizationBD.NBEXPLORABLEMAX * GestDonnee.LARGEUR;

	private Collection<Neud> newNeud = new HashSet<>(maxEnregistrement);

	private Collection<Long> removeNeud = new HashSet<>(SynchronizationBD.NBEXPLORABLEMAX / 3);

	private Collection<Neud> editNeud = new HashSet<>(SynchronizationBD.NBEXPLORABLEMAX);
	private Collection<Lien> newLien = new HashSet<>(maxEnregistrement);
	private Collection<Lien> removeLien = new HashSet<>(maxEnregistrement / 3);

	/**
	 * trace des neud de lancienne demande des neud suprimable permet 2 lecture
	 * getRemoveNeud() avant que isSupprimable renvoie false
	 */
	private Collection<Long> removeNeudAncien = new HashSet<>(1000);

	/**
	 * ajoute un nouveau neud au tampon
	 *
	 * @param neud
	 *            neud a ajouter
	 */
	public void addNeud(Neud neud) {
		newNeud.add(neud);
	}

	/**
	 * ajout un neud en tant que modifier au tampon
	 *
	 * @param neud
	 */
	public void editNeud(Neud neud) {
		if (!newNeud.contains(neud)) {
			editNeud.add(neud);
		}
	}

	/**
	 * ajout un nouveau lien entre parent et enfant au tampon
	 *
	 * @param parent
	 *            parent
	 * @param enfant
	 *            enfant
	 */
	public void newLien(long parent, long enfant) {
		newLien.add(new Lien(parent, enfant));
	}

	/**
	 * ajout un lien a supprimer au tampon
	 *
	 * @param parent
	 *            parent
	 * @param enfant
	 *            enfant
	 */
	public void removeLien(Long parent, Long enfant) {
		Lien lien = new Lien(parent, enfant);
		if (!newLien.remove(lien)) {
			removeLien.add(lien);
			// ici on ajoute dans le total modif cat c'est une modif d'un neud
		}
	}

	/**
	 * ajout un neud a supprimer au tampon
	 *
	 * @param neud
	 *            neud
	 */
	public void removeNeud(Neud neud) {
		Long neudId = neud.getId();
		if (!newNeud.remove(neud)) {
			removeNeud.add(neudId);
			//System.out.println("neud suprimable"+removeNeud.size());
//			if (removeNeud.size()>1000) {
//				System.out.println("test");
//				Factory.getFactory().getSynchronizationBD().synchronization(35);
//			}
		}
		// ici pas d'ajout dans neudTotalModif car suppression
		// mais il faut bien pensser avant une lecture en base a exlure ces
		// enregistrement avec un isSupprimable
	}

	/**
	 * retourne les neud a supprimer qui sont dans le tampon.<br>
	 *
	 * @return neud a supprimer
	 */
	public Collection<Long> getRemoveNeud() {
		return removeNeud;
	}

	/**
	 * @return the removeNeudAncien
	 */
	public Collection<Long> getRemoveNeudAncien() {
		return removeNeudAncien;
	}

	// public boolean isRemoveLien(long parent, long enfant) {
	// boolean result = newLien.contains(new Lien(parent, enfant));
	// if (result==false) {
	// }
	// return result;
	// }

	/**
	 * retourne les lien a suprimer qui sont dans le tampon.<br>
	 *
	 * @return lien a supprimer
	 */
	public Collection<Lien> getRemoveLien() {
		return removeLien;
	}

	/**
	 * retourne les neud a ajouter qui sont dans le tampon.<br>
	 *
	 * @return neud a ajouter
	 */
	public Collection<Neud> getNewNeud() {
		return newNeud;
	}

	/**
	 * retourne les neud a modifier qui sont dans le tampon.<br>
	 *
	 * @return neud a modifier
	 */
	public Collection<Neud> getEditNeud() {
		return editNeud;
	}

	/**
	 * retourne les nouveau lien qui sont dans le tampon.<br>
	 *
	 * @return lien a ajouter
	 */
	public Collection<Lien> getNewLien() {
		return newLien;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TamponBD [newNeud=" + newNeud.size() + ", removeNeud=" + removeNeud.size() + ", editNeud="
				+ editNeud.size() + ", newLien=" + newLien.size() + ", removeLien=" + removeLien.size()
				+ ", removeNeudAncien=" + removeNeudAncien.size() + "]";
	}

	/**
	 * @param removeNeud
	 *            the removeNeud to set
	 */
	public void initRemoveNeud() {
		this.removeNeud = new HashSet<>(1000);
	}

	/**
	 * @param newNeud
	 *            the newNeud to set
	 */
	public void initNewNeud() {
		this.newNeud = new HashSet<>(50000);
	}

	/**
	 * @param editNeud
	 *            the editNeud to set
	 */
	public void initEditNeud() {
		this.editNeud = new HashSet<>(10000);
	}

	/**
	 * @param newLien
	 *            the newLien to set
	 */
	public void initNewLien() {
		this.newLien = new HashSet<>(60000);
	}

	/**
	 * @param removeLien
	 *            the removeLien to set
	 */
	public void initRemoveLien() {
		this.removeLien = new HashSet<>(5000);
	}

	/**
	 * @param removeNeudAncien
	 *            the removeNeudAncien to set
	 */
	public void setRemoveNeudAncien(HashSet<Long> removeNeudAncien) {
		this.removeNeudAncien = removeNeudAncien;
	}

	public boolean isSupprimable(long id) {
		boolean result = removeNeud.contains(id);
		if (result == false) {
			result = removeNeudAncien.contains(id);
		}
		return result;
	}
}
