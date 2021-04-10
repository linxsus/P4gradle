package org.P4Modele_.arbre;

import java.util.HashSet;
import java.util.Set;

import org.P4Metier.Factory.Factory;
import org.P4Modele_.Calculer;
import org.P4Modele_.Neud;
import org.P4Modele_.map.MapArbreBD;
import org.Persistant_.requette.SqlArbre;

public class NeudArbreBD extends NeudArbreBasic {

	protected TamponBD tampon;
	protected SqlArbre mysqlarbre;
	protected MapArbreBD mapArbre;

	public NeudArbreBD(Factory factory) {
		super(factory);
		init();
		tampon.addNeud(this);
	}

	public NeudArbreBD(Factory factory, long id, Neud n1) {
		super(factory, id, n1);
		init();
		tampon.addNeud(this);
	}

	public NeudArbreBD(Factory factory, long id, int i1) {
		super(factory, id, i1);
		init();
		tampon.addNeud(this);
	}

	public NeudArbreBD(Factory factory, long id) {
		super(factory, id);
		init();
		tampon.addNeud(this);
	}

	public NeudArbreBD(Factory factory, Set<Long> parent, Set<Long> enfant, int etat,
			long id, Calculer calculer, int niveau) {
		super(factory);
		init();
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
		mysqlarbre = factory.getMysqlArbre();
	}

	private void init() {
		tampon = factory.getTampon();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbreBasic#setCalculer(org.P4Arbre.Calculer)
	 */
	@Override
	public void setCalculer(Calculer calculer) {
		if (this.calculer != calculer) {
			super.setCalculer(calculer);
			tampon.editNeud(this);
			mapArbre.Update(id, this);
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbreBasic#setExplorable(boolean)
	 */
	@Override
	public void setExplorable(boolean explorable) {
		if (isExplorable() != explorable) {
			super.setExplorable(explorable);
			tampon.editNeud(this);
			mapArbre.Update(id, this);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbreBasic#addParent(long)
	 */
	@Override
	public void addParent(long newParent) {
//		if (parent == null) {
//			parent = new HashSet<>();
//			parent.addAll(mysqlarbre.getParent(id));
//		}
//		if (parent.size()==0 && isSupprimable()) {
//			setSupprimable(false);
//		}
		tampon.editNeud(this);
		mapArbre.Update(id, this);
		super.addParent(newParent);
		

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbreBasic#addEnfant(long, int)
	 */
	@Override
	public void addEnfant(long newEnfant) {
//		if (enfant == null) {
//			enfant = new HashSet<>();
//			enfant.addAll(mysqlarbre.getEnfant(id));
//		}
		super.addEnfant(newEnfant);
		tampon.editNeud(this);
		mapArbre.Update(id, this);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbreBasic#removeParent(java.lang.Long)
	 */
	@Override
	public void removeParent(Long oldParent) {
//		if (parent == null) {
//			parent = new HashSet<>();
//			parent.addAll(mysqlarbre.getParent(id));
//		}
		super.removeParent(oldParent);
		tampon.editNeud(this);
		mapArbre.Update(id, this);
		// je l,enleve car normalement mis dans le remove enfant du parent.
		// tampon.removeLien(oldParent, id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.NeudArbreBasic#removeEnfant(java.lang.Long)
	 */
	@Override
	public void removeEnfant(Long oldEnfant) {
//		if (enfant == null) {
//			enfant = new HashSet<>();
//			enfant.addAll(mysqlarbre.getEnfant(id));
//		}
		super.removeEnfant(oldEnfant);
		tampon.editNeud(this);
		mapArbre.Update(id, this);
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

	public void setMapArbre(MapArbreBD mapArbreBD) {
		this.mapArbre = mapArbreBD;
	}

	@Override
	public void setSupprimable(boolean supprimable) {
		if (isSupprimable() != supprimable) {
			super.setSupprimable(supprimable);
			tampon.editNeud(this);
			// si le neud est un nouveau
			// je le supprime des nouveau neud
			// sinon je le met dans les updates
//			if (!tampon.getNewNeud().remove(this)) {
//			    mapArbre.Update(id, this);
//			}
		}
		
	}
	
	
	@Override
	public void setQuantum(int quantum) {
		super.setQuantum(quantum);
		mapArbre.Update(id, this);
	}
}
