package org.P4Modele_.arbre;


import java.util.Set;

import org.P4Metier.Factory.Factory;
import org.P4Metier.arbre.SynchronizationBD;
import org.P4Metier.id.GestIdDonneeLong;
import org.P4Modele_.Calculer;
import org.P4Modele_.Neud;
import org.P4Modele_.map.MapArbreBD;


public class NeudArbreBD extends NeudArbreBasic {

	
	 
	//protected TamponBD tampon;
	//protected SqlArbre mysqlarbre;
	
	protected boolean enBase;
	protected boolean modifier;

	public NeudArbreBD(Factory factory) {
		super(factory);

		//tampon.addNeud(this);
	}

	public NeudArbreBD(Factory factory, GestIdDonneeLong id, Neud n1) {
		super(factory, id, n1);

		//tampon.addNeud(this);
	}


	public NeudArbreBD(Factory factory, GestIdDonneeLong id) {
		super(factory, id);

		//this.tampon.addNeud(this);
	}

	public NeudArbreBD(Factory factory, Set<Long> parent, Set<Long> enfant, int etat,
			long id, Calculer calculer, int niveau) {
		super(factory);

		// if (parent == null) {
		// parentIsUp = false;
		// } else {
		this.parent = parent;
		// }

		// if (enfant == null) {
		// enfantIsUp = false;
		// } else {
		this.enfant = enfant;
		// }
		this.etat=(byte) etat;
		this.id = id;
		this.calculer = calculer;
		this.niveau = niveau;
	}

	protected void init(Factory factory,GestIdDonneeLong id) {
		//this.tampon = factory.getTampon();
		enBase=false;
		modifier=false;
		super.init(factory, id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbreBasic#setCalculer(org.P4Arbre.Calculer)
	 */
	@Override
	public boolean setCalculer(Calculer calculer) {
		if (super.setCalculer(calculer)) {
			modifier=true;		
			//tampon.editNeud(this);
			//mapArbre.Update(id, this);
			return true;
		}
		return false;
	}

	/*
	 * informe que le neud est en base
	 */
	public boolean setEnBase() {
		if (enBase == false) {
			enBase = true;
			modifier = false;
			return true;
		}
		return false;
	}
	
	
	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbreBasic#setExplorable(boolean)
	 */
	@Override
	public boolean setExplorable(boolean explorable) {
		if (super.setExplorable(explorable)) {
			modifier=true;
			//tampon.editNeud(this);
			//mapArbre.Update(id, this);
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbreBasic#addParent(long)
	 */
	@Override
	public boolean addParent(long newParent) {
		if (super.addParent(newParent)) {
			modifier = true;
			//tampon.editNeud(this);
			//mapArbre.Update(id, this);
			if (isSupprimable()) {
				setSupprimable(false);
	 		}
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbreBasic#addEnfant(long, int)
	 */
	@Override
	public boolean addEnfant(long newEnfant) {
		if (super.addEnfant(newEnfant)) {
			modifier = true;
			//tampon.editNeud(this);
			//mapArbre.Update(id, this);
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbreBasic#removeParent(java.lang.Long)
	 */
	@Override
	public boolean removeParent(Long oldParent) {
		if (super.removeParent(oldParent)) {
			modifier = true;

			//tampon.editNeud(this);
			//mapArbre.Update(id, this);
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbreBasic#removeEnfant(java.lang.Long)
	 */
	@Override
	public boolean removeEnfant(Long oldEnfant) {
		if (super.removeEnfant(oldEnfant)) {
			modifier = true;
			//tampon.editNeud(this);
			//mapArbre.Update(id, this);
			return true;
		}
		return false;
	}

	@Override
	public Set<Long> getParent() {
//		if (parent == null) {
//			parent = new HashSet<>();
//			parent.addAll(mysqlarbre.getParent(id));
//		}
		return super.getParent();
	}

	@Override
	public Set<Long> getEnfant() {
//		if (enfant == null) {
//			enfant = new HashSet<>();
//			enfant.addAll(mysqlarbre.getEnfant(id));
//		}
		return super.getEnfant();
	}

	


	public boolean isSupprimable() {
		return ((etat & SUPPRIMABLE) == SUPPRIMABLE);
	}

	
	protected boolean setSupprimable(boolean supprimable) {
		if (supprimable != isSupprimable()) { // si on change l'etat supprimable du neud
			modifier = true;
			if (!enBase) { // si il n'est pas en base
				return super.setSupprimable(supprimable); // il se comporte comme un neud normal
			} else {	// sinon			 
				if (supprimable) { //si je le met en supprimable
					//TODO on fait des suppression d'etat danger!!!!!
					etat = SUPPRIMABLE;// je met son etat en suprimable
										//si il est supprimable il n'est que supprimable
					// si j'ai encore de la place dans le map je supprime les lien ce qui peut suprimer les enfants par cascade
					if (factory.getMapArbre().size()<MapArbreBD.MAX_NEUD) { 
						removeAllEnfant();  
					}
					
				} else { //je repasse le neud en non supprimable
					etat = (byte) (etat & (~SUPPRIMABLE));
				}
				
				//tampon.editNeud(this);
				return true;
			}
		}
		return false;
	}
	

	public boolean addParent(Set<Long> newParent) {
		if (!(this.parent.size() == newParent.size() && (this.parent.containsAll(newParent)))) {
				if ((newParent.size() == 0) && (calculer == Calculer.NONCALCULER)) {
					setSupprimable(true);
				} else {
					// si le neud est supprimable et qu'on lui rajoute des parent il n'est plus
					// supprimable
					if (isSupprimable()) {
						etat = 3; // on le definit comme feuille et explorable c'est une recreation du neud
					}
				}
			
			this.parent = newParent;
			return true;
		}
		return false;
	}



	public boolean addEnfant(Set<Long> newEnfant) {
		if (!(this.enfant.size() == newEnfant.size() && (this.enfant.containsAll(newEnfant)))) {
		this.enfant = newEnfant;
		return true;
		}
		return false;

	}
	
	
	public boolean getEnBase() {
		return enBase;
	}
	
	public boolean setModifier(boolean modifier) {
		if (this.modifier!=modifier) {
			this.modifier=modifier;
			return true;
		}
		return false;
	}
	public boolean getModifier() {
		return modifier;
	}
}
