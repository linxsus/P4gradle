package org.P4Modele_.arbre;

import java.util.HashSet;
import java.util.Set;

import org.P4Metier.Factory.Factory;
import org.P4Metier.id.GestIdDonneeLong;
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
    protected final byte FEUILLE = 0b001;
    protected final byte EXPLORABLE = 0b010;
    protected final byte SUPPRIMABLE = 0b100;
    protected final byte TRON = 0b1000;
    
	protected byte etat=3;
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
		id = 0L;
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
		return new HashSet<>(parent);
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
		return new HashSet<>(enfant);
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
		if ((etat & FEUILLE) == FEUILLE) {
			return true;
		}
		return false;
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
			return isExplorable();
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
		if ((etat & EXPLORABLE) == EXPLORABLE) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbre#setExplorable(boolean)
	 */
	@Override
	public void setExplorable(boolean explorable) {
		if (explorable) {
			etat = (byte) (etat | EXPLORABLE);	
		}else {
			etat = (byte) (etat & (~EXPLORABLE));
		}
		
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbre#addParent(long)
	 */
	@Override
	public void addParent(long newParent) {
		if (isSupprimable()) {
			etat=3; // attention en cas de tron .... (mais normalement un tron n'est pas supprimable)
 		}
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
		setFeuille(false);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbre#removeParent(java.lang.Long)
	 */
	@Override
	public void removeParent(Long oldParent) {
		// je suprime le parent
		if (parent.remove(oldParent)) {
		// je doit suprimer aussi le lien dans le parent
		factory.getMapArbre().get(oldParent).removeEnfant(id);
		};
		//si il n'y a plus de parent et que ce n'est pas le tron  alor le neud est supprimable
		if ((parent.size()==0) && !isTron()) {
			setSupprimable(true);
		}
		
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbre#removeEnfant(java.lang.Long)
	 */
	@Override
	public void removeEnfant(Long oldEnfant) {
		// je suprime le lien enfant
		if (enfant.remove(oldEnfant)) {
		// je doit suprimer aussi le lien dans l'enfant
		factory.getMapArbre().get(oldEnfant).removeParent(id);
		}
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
		String str;
		str="Neud [parent=" + parent + ", enfant=" + enfant + ", feuille=" + isFeuille() + ", explorable=" + isExplorable()+", supprimable="+isSupprimable()
				+ ", id=" + id + ", calculer=" + calculer + ", niveau=" + niveau + "]\n"+new GestIdDonneeLong(factory).getDonneeId(id);
		str+="parent \n";
		for (Long temp : getParent()) {
			str+=new GestIdDonneeLong(factory).getDonneeId(temp)+" "+temp+"\n";

		}
		str+="enfent \n";
		for (Long temp : getEnfant()) {
			str+=new GestIdDonneeLong(factory).getDonneeId(temp)+" "+temp+"\n";

		}
		
		
		return str;
	}

	@Override
	public void setFeuille(boolean feuille) {
		if (feuille) {
			etat = (byte) (etat | FEUILLE);	
		}else {
			etat = (byte) (etat & (~FEUILLE));
		}

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
		if (!isTron()) {
			if ((parent.size() == 0)) {
				setSupprimable(true);
			} else {
				// si le neud est supprimable et qu'on lui rajoute des parent il n'est plus
				// supprimable
				if (isSupprimable()) {
					etat = 3; // on le definit comme feuille et explorable c'est une recreation du neud
				}
			}
		}
		this.parent = parent;

	}

	@Override
	public void addEnfant(Set<Long> enfant) {
		this.enfant = enfant;

	}

	@Override
	public boolean isSupprimable() {
		if ((etat & SUPPRIMABLE) == SUPPRIMABLE) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isSupprimable(int niveau) {
		if (this.niveau <= niveau) {
			return isSupprimable();
		} else {
			return false;
		}
	}

	
	protected void setSupprimable(boolean supprimable ) {
		
		if (supprimable && !isTron()) {
			etat = SUPPRIMABLE;	// si il est supprimable il n'est que supprimable
		}else {
			etat = (byte) (etat & (~SUPPRIMABLE));
		}
		
	}

	@Override
	public int etat() {
		return etat;
	}

	@Override
	public void setTron(boolean tron) {
//		if (tron) {
//			etat = (byte) (etat | TRON);	
//		}else {
//			etat = (byte) (etat & (~TRON));
//		}
		
	}

	@Override
	public boolean isTron() {
		if ((etat & TRON) == TRON) {
			return true;
		}
		return false;
	}
	
	
}
