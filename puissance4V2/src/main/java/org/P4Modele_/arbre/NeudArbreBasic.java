package org.P4Modele_.arbre;

import java.util.HashSet;
import java.util.Set;

import org.P4Metier.Gagnee;
import org.P4Metier.Factory.Factory;
import org.P4Metier.id.GestIdDonneeLong;
import org.P4Modele_.Calculer;
import org.P4Modele_.MapArbre;
import org.P4Modele_.Neud;
import org.P4Modele_.NeudArbre;
import org.P4Modele_.map.MapArbreBD;
import org.quantum.GestDonneeTabByteQuantum;

/**
 * @author xavier
 *
 */
//TODO
public class NeudArbreBasic implements Neud, NeudArbre {
	
	private MapArbre mapArbre;
	protected Set<Long> parent = new HashSet<>();
	protected Set<Long> enfant = new HashSet<>();
    protected final byte FEUILLE = 0b001;
    protected final byte EXPLORABLE = 0b010;
    protected final byte TRON = 0b1000;
    protected final byte SUPPRIMABLE = 0b100;
    
	protected byte etat=3; //par default une feuille explorable
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
	public NeudArbreBasic(Factory factory, GestIdDonneeLong base, Neud parent) {
		super();
		this.parent.add(parent.getId());
		init(factory,base);
	}

	/**
	 * cree un neud avec un id !! ne devrait etre utiliser que lor de la ceation du
	 * tron.
	 *
	 * @param id
	 *            l'id du neud a cree
	 */
	public NeudArbreBasic(Factory factory, GestIdDonneeLong base) {
		super();
		init(factory,base);
		//TODO on devrait calculer le niveau
	}

	/**
	 * cree un neud !! ne devrait etre utiliser que lors de la ceation du tron.
	 */
	public NeudArbreBasic(Factory factory) {
		super();
		init(factory,(GestIdDonneeLong) factory.getGestIDDonnee());
	}

//	public NeudArbreBasic(Factory factory, long id, int niveau) {
//		super();
//		init(factory,id);
//
//	}
	
	protected void init(Factory factory,GestIdDonneeLong base) {
		this.factory = factory;
		this.mapArbre= factory.getMapArbre();
		this.id = base.getIdBaseDonnee();
//		GestIdDonneeLong base=(GestIdDonneeLong) factory.getGestIDDonnee();
//		base.setIdBaseDonnee(id);
		int niveau=base.getNbPionJouer();
		Gagnee gagnee=factory.getGagnee();
		if (id != 0) {
			
			if (gagnee.isGagnee(base)) {
				setCalculer(Calculer.GAGNER);

			}else {
				if (niveau==42) {
					setCalculer(Calculer.EGALITER);
				}
			}
			;
		}
		
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
	public boolean setCalculer(Calculer calculer) {
		if (this.calculer != calculer) {
			if (calculer != Calculer.NONCALCULER) {
				setExplorable(false);
			}
			this.calculer = calculer;
			return true;
		}
		return false;
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
		//parent.toArray();
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
		return ((etat & FEUILLE) == FEUILLE);
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
		return ((etat & EXPLORABLE) == EXPLORABLE);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbre#setExplorable(boolean)
	 */
	@Override
	public boolean setExplorable(boolean explorable) {
		if (explorable != isExplorable()) {
			if (explorable) {
				etat = (byte) (etat | EXPLORABLE);
			} else {
				etat = (byte) (etat & (~EXPLORABLE));
			}
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbre#addParent(long)
	 */
	@Override
	public boolean addParent(long newParent) {
		if (parent.add(newParent)) {
			mapArbre.get(newParent).addEnfant(id);
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbre#addEnfant(long, int)
	 */
	@Override
	public boolean addEnfant(long newEnfant) {
		if (enfant.add(newEnfant)) {
			mapArbre.get(newEnfant).addParent(id);
			setFeuille(false);
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbre#removeParent(java.lang.Long)
	 */
	@Override
	public boolean removeParent(Long oldParent) {
		// je suprime le parent
		if (parent.remove(oldParent)) {
			// je doit suprimer aussi le lien dans le parent
			mapArbre.get(oldParent).removeEnfant(id);
			// si il n'y a plus de parent et que ce n'est pas calculer alor le neud est
			// supprimable
			if ((parent.size() == 0) && (calculer == Calculer.NONCALCULER)) {
				setSupprimable(true);
			}
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbre#removeEnfant(java.lang.Long)
	 */
	@Override
	public boolean removeEnfant(Long oldEnfant) {
		// je suprime le lien enfant
		if (enfant.remove(oldEnfant)) {
		// je doit suprimer aussi le lien dans l'enfant
			mapArbre.get(oldEnfant).removeParent(id);
			return true;
		}
		return false;
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
		str="Neud [parent=" + parent + ", enfant=" + enfant + ", feuille=" + isFeuille() + ", explorable=" + isExplorable()+ ", id=" + id + ", calculer=" + calculer + ", niveau=" + niveau +", Quantum "+ getQuantum() + "]\n"+new GestIdDonneeLong(factory).getDonneeId(id);
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
	public boolean setFeuille(boolean feuille) {
		if (feuille != isFeuille()) {
			if (feuille) {
				etat = (byte) (etat | FEUILLE);
			} else {
				etat = (byte) (etat & (~FEUILLE));
			}
			return true;
		}
		return false;
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


	
	protected boolean setSupprimable(boolean supprimable) {	
			if (supprimable) {
				removeAllEnfant();
				etat = (byte) (etat | SUPPRIMABLE);
				mapArbre.remove(getId());
			} else {
				setExplorable(true);
				etat = (byte) (etat & (~SUPPRIMABLE));
			}
			return true;
	}

	@Override
	public int etat() {
		return etat;
	}

//	@Override
//	public boolean setTron(boolean tron) {
////		if (tron != isTron()) {
////			if (tron) {
////				etat = (byte) (etat | TRON);
////			} else {
////				etat = (byte) (etat & (~TRON));
////			}
////			return true;
////		}
//		return false;
//	}

//	@Override
//	public boolean isTron() {
//		if ((etat & TRON) == TRON) {
//			return true;
//		}
//		return false;
//	}

	@Override
	public int getQuantum() {
		if (isExplorable()) {
		GestDonneeTabByteQuantum result= (GestDonneeTabByteQuantum)((GestIdDonneeLong) factory.getGestIDDonnee()).getDonneeId(id);
		return result.calcQuantum();
		}
		return 0;
	}
	
	protected void removeAllEnfant() {
		for (long enfant : new HashSet<>(enfant)) {
			removeEnfant(enfant);
		} 
	}
	
	public void setMapArbre(MapArbreBD mapArbreBD) {
		this.mapArbre = mapArbreBD;
	}
}
