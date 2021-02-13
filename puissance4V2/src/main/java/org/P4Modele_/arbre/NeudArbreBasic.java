package org.P4Modele_.arbre;

import java.util.HashSet;
import java.util.Set;

import org.P4Metier.Factory.Factory;
import org.P4Modele_.Calculer;
import org.P4Modele_.Neud;
import org.P4Modele_.NeudArbre;

/**
 * @author xavier
 *
 */
//TODO
public class NeudArbreBasic implements Neud, NeudArbre {
	
	protected Set<Long> parent = new HashSet<>();
	protected Set<Long> enfant = new HashSet<>();
	protected boolean feuille = true;
	protected boolean explorable = true;
	protected long id;
	protected Calculer calculer = Calculer.NONCALCULER;
	protected int niveau;
	protected Factory factory;
	/**
	 * cree un neud
	 *
	 * @param id
	 * @param parent
	 */
	public NeudArbreBasic(Factory factory, long id, Neud parent) {
		super();
		this.factory = factory;
		this.parent.add(parent.getId());
		this.id = id;
		niveau = parent.getNiveau() + 1;
	}

	/**
	 * cree un neud avec un id !! ne devrait etre utiliser que lor de la ceation du
	 * tron.
	 *
	 * @param id
	 *            l'id du neud a cree
	 */
	public NeudArbreBasic(Factory factory, long id) {
		super();
		this.factory = factory;
		this.id = id;
		//TODO on devrait calculer le niveau
	}

	/**
	 * cree un neud !! ne devrait etre utiliser que lors de la ceation du tron.
	 */
	public NeudArbreBasic(Factory factory) {
		super();
		this.factory = factory;
		id = 1L;//TODO je pense que sa devrait etre 0L
		niveau = 0;
	}

	public NeudArbreBasic(Factory factory, long id, int niveau) {
		super();
		this.factory = factory;
		this.id = id;
		this.niveau = niveau;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudBis#getId()
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbre#getId()
	 */
	@Override
	public long getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbre#setId(long)
	 */
	@Override
	public void setId(long id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbre#setCalculer(org.P4Arbre.Calculer)
	 */
	@Override
	public void setCalculer(Calculer calculer) {
		this.calculer = calculer;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudBis#getNiveau()
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbre#getNiveau()
	 */
	@Override
	public int getNiveau() {
		return niveau;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbre#setNiveau(int)
	 */
	@Override
	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudBis#getCalculer()
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbre#getCalculer()
	 */
	@Override
	public Calculer getCalculer() {
		return calculer;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudBis#getParent()
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbre#getParent()
	 */
	@Override
	public Set<Long> getParent() {
		return parent;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudBis#getEnfant()
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbre#getEnfant()
	 */
	@Override
	public Set<Long> getEnfant() {
		return enfant;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudBis#isFeuille()
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbre#isFeuille()
	 */
	@Override
	public boolean isFeuille() {
		return feuille;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudBis#isExplorable(int)
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbre#isExplorable(int)
	 */
	@Override
	public boolean isExplorable(int niveau) {
		if (this.niveau <= niveau) {
			return explorable;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudBis#isExplorable()
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbre#isExplorable()
	 */
	@Override
	public boolean isExplorable() {
		return explorable;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbre#setExplorable(boolean)
	 */
	@Override
	public void setExplorable(boolean explorable) {
		this.explorable = explorable;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbre#addParent(long)
	 */
	@Override
	public void addParent(long newParent) {
		parent.add(newParent);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbre#addEnfant(long, int)
	 */
	@Override
	public void addEnfant(long newEnfant) {
		enfant.add(newEnfant);
		feuille = false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbre#removeParent(java.lang.Long)
	 */
	@Override
	public void removeParent(Long oldParent) {
		parent.remove(oldParent);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbre#removeEnfant(java.lang.Long)
	 */
	@Override
	public void removeEnfant(Long oldEnfant) {
		enfant.remove(oldEnfant);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudBis#toString()
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbre#toString()
	 */
	@Override
	public String toString() {
		return "Neud [parent=" + parent + ", enfant=" + enfant + ", feuille=" + feuille + ", explorable=" + explorable
				+ ", id=" + id + ", calculer=" + calculer + ", niveau=" + niveau + "]\n";
	}

	@Override
	public void setFeuille(boolean feuille) {
		this.feuille = feuille;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + (int) (id ^ (id >>> 32));
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		NeudArbreBasic other = (NeudArbreBasic) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public void addParent(Set<Long> parent) {
		this.parent = parent;

	}

	@Override
	public void addEnfant(Set<Long> enfant) {
		this.enfant = enfant;

	}
}
