package org.P4Modele_.arbre;

import java.util.Collection;
import java.util.HashSet;

import org.P4Modele_.Neud;

/**
 * 
 * class pour la gestion de la copy des tampon
 * 
 * 
 * @author  <a href="mailto:xavier.gouraud@wanadoo.fr">xavier</a> 
 *
 */
public class CopyTampon {

	private Collection<Neud> newNeud;
	private Collection<Neud> editNeud;
	private Collection<Lien> newLien;
	private Collection<Lien> removeLien;
	private Collection<Long> removeNeud;
	protected TamponBD tampon;

	public CopyTampon(TamponBD tampon) {
		super();
		this.tampon = tampon;
		MiseAjourTempon();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CopyTampon [newNeud=" + newNeud.size() + ", editNeud=" + editNeud.size() + ", newLien=" + newLien.size()
				+ ", removeLien=" + removeLien.size() + ", removeNeud=" + removeNeud.size() + "]";
	}

	/**
	 * @return the newNeud
	 */
	public Collection<Neud> getNewNeud() {
		return newNeud;
	}

	/**
	 * @return the editNeud
	 */
	public Collection<Neud> getEditNeud() {
		return editNeud;
	}

	/**
	 * @return the newLien
	 */
	public Collection<Lien> getNewLien() {
		return newLien;
	}

	/**
	 * @return the removeLien
	 */
	public Collection<Lien> getRemoveLien() {
		return removeLien;
	}

	/**
	 * @return the removeNeud
	 */
	public Collection<Long> getRemoveNeud() {
		return removeNeud;
	}

	private void MiseAjourTempon() {
		// on recupere les lien suprimer et on vide le tampon
		removeLien = tampon.getRemoveLien();
		tampon.initRemoveLien();
		// on recupere les neud dernierement suprimer, on vide le tampon pour la liste
		// des futur suprimer
		// et on met a jour la liste des dernier supprimer pour ne pas les recuperer
		// dans la base tant qu'il ne sont pas reelement supprimer
		removeNeud = tampon.getRemoveNeud();
		tampon.setRemoveNeudAncien((HashSet<Long>) removeNeud);
		tampon.initRemoveNeud();
		// on recupere les nouveau neud et on vide le tampon
		newNeud = tampon.getNewNeud();
		tampon.initNewNeud();
		// on recupere les nouveau lien et on vide le tampon
		newLien = tampon.getNewLien();
		tampon.initNewLien();
		// on recupere les neud modifier et on vide le tampon
		editNeud = tampon.getEditNeud();
		tampon.initEditNeud();
	}
}
