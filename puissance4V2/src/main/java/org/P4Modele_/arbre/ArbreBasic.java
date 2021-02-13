package org.P4Modele_.arbre;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.P4Metier.Factory.Factory;
import org.P4Metier.id.GestIdDonneeLong;
import org.P4Modele_.Arbre;
import org.P4Modele_.Calculer;
import org.P4Modele_.MapArbre;
import org.P4Modele_.Neud;
import org.P4Modele_.NeudArbre;

public class ArbreBasic implements Arbre {

	protected MapArbre tableau;//TODO = new MapArbreBasic();
	protected long tron = -1L;
	private List<Long> neudSuprimable = new ArrayList<>();

	private List<Long[]> lienSuprimable = new ArrayList<>();
	protected Factory factory;

	public ArbreBasic(Factory factory) {
		super();
		this.factory = factory;
		tableau = factory.getMapArbre();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.Arbre1#getTron()
	 */
	@Override
	public long getTron() {
		// si il n'y a pas de tron declarer
		if (tron == -1L) {
			// je cree le tron.
			NeudArbre neud = factory.getNeudArbre();
			tron = neud.getId();
			tableau.put(tron, neud);
		}
		return tron;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.Arbre1#setTron(java.lang.Long, int)
	 */
	@Override
	public Neud setTron(Long tron, int niveau) {

		return setTron(tron, tableau.get(tron), niveau);
	}

	/**
	 * initilaise le tron avec l'id et le neud fournit en parametre avant de
	 * l'affecter on verifie que le neud existe sinon on ne fait rien.
	 *
	 * @param tron
	 *            the tron to set
	 * @param neud
	 *            the neud to set
	 */
	protected Neud setTron(long tron, NeudArbre neud, int niveau) {
		if (neud == null) {
			neud = factory.getNeudArbre(tron, niveau);
		}
		if ((tron != -1)) {
			tableau.clear();// TODO un peut violent a voir si pas possible de faire mieux
			/*
			 * ce qui devrait etre. Sur nouveau tron on suprime les lien parent. on met
			 * l'ancien tron a supprimer. et on lance la procedure de suppression.
			 */
			this.tron = tron;
			tableau.put(tron, neud);
		}
		return neud;
	}

	/**
	 * retourn le tableau indispensable pour les test
	 *
	 * @return the tableau
	 */
	protected Map<Long, NeudArbre> getTableau() {
		return tableau;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.Arbre1#toString()
	 */
	@Override
	public String toString() {
		String str="Arbre [arbre=\n " + tableau + "]\n";
		for (Map.Entry<Long, NeudArbre> entry : tableau.entrySet())
		{
			GestIdDonneeLong gestDonnee=(GestIdDonneeLong) factory.getGestIDDonnee();
			str+=gestDonnee.getDonneeId(entry.getKey());	
		}
		return str;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.Arbre1#setCalculer(java.lang.Long, org.P4Arbre.Calculer)
	 */
	@Override
	public void setCalculer(Long idCalculer, Calculer calculer) {
		setCalculerPrivate(idCalculer, calculer);
		netoyage();
	}

	/**
	 * affecte la valeur caculer pour l'id idCalculer mais ne fait pas le netoyage
	 *
	 * @param idCalculer
	 *            the id for set calculer
	 * @param calculer
	 *            the calculer to set
	 */
	protected void setCalculerPrivate(Long idCalculer, Calculer calculer) {
		NeudArbre neud = tableau.get(idCalculer);
		// on met que le neud est calculer
		neud.setCalculer(calculer);
		// si on a bien calculer
		if (calculer != Calculer.NONCALCULER) {
			// le neud n'est plus explorable
			neud.setExplorable(false);
			// pour tous les parents
			for (Long tempParent : neud.getParent()) {
				calculer(tempParent);

			}
			// on verifie si il y a des lien enfant suprimable et on marque ces lien en
			// suprimable
			enfantSuprimable(idCalculer);

		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.Arbre1#addEnfant(java.lang.Long, java.lang.Long, int)
	 */
	@Override
	public Neud addEnfant(Long parent, Long enfant) {
		NeudArbre feuilleEnfant;
		// recuperation de la feuille parent on espere qu'elle existe sinon le programe
		// sortira une erreur
		NeudArbre feuilleParent = tableau.get(parent);
		feuilleParent.addEnfant(enfant);
		// recuperation de la feuille Enfant
		// ici bug???
		feuilleEnfant = tableau.get(enfant); // TODO ici a verifier
		// si la feuille Enfant n'existe pas
		if (feuilleEnfant == null) {
			// creation de l'enfant
			feuilleEnfant = factory.getNeudArbre(enfant, feuilleParent);
			tableau.put(enfant, feuilleEnfant);
		} else {
			// ajout de parent dans l'enfant.
			feuilleEnfant.addParent(parent);
		}
		return feuilleEnfant;
	}

	/**
	 * netoyage de l'arbre
	 *
	 */
	protected void netoyage() {
		List<Long> totalSuprimable = new ArrayList<>();

		while (!neudSuprimable.isEmpty()) {
			// pour tous les neud suprimable
			for (Long id : neudSuprimable) {
				// je marque tous ses lien comme suprimable
				suprimableLien(id);
			}
			// je sauvegarde les neud suprimable
			totalSuprimable.addAll(neudSuprimable);
			neudSuprimable.clear();
			// pour tous les lien de la table lienSuprimable
			for (Long[] lien : lienSuprimable) {
				// je suprime le lien
				removeLien(lien[0], lien[1]);
				// si l'enfant n'a plus de parent
				if ((tableau.get(lien[1]).getParent().size() == 0)) {
					// je met l'enfant dans les neud suprimable
					neudSuprimable.add(lien[1]);
				}
			}
			// je vide les lien suprimable
			lienSuprimable.clear();

			// si il y a des neud suprimable
			// (nouveau neud suprimable suite a la mise en suprimable des lien)
			// je reboucle
		}

		// je suprime tous les neud inutile
		for (Long id : totalSuprimable) {
			tableau.remove(id);
		}

	}

	/**
	 * si on peut calculer le param on le fait et on affecte le param
	 *
	 * @param idCalculer
	 *            l'id a calculer.
	 */
	protected void calculer(long idCalculer) {
		boolean nonCalculer = false; // a voir si utile
		boolean gagner = false;
		int perdu = 0;
		int egaliter = 0;
		Calculer enfantCalculer;
		int nbEnfant = tableau.get(idCalculer).getEnfant().size();
		// si il y a des enfant
		if ((nbEnfant > 0) && (tableau.get(idCalculer).getCalculer() == Calculer.NONCALCULER)) {
			// pour tous les enfants
			for (Long aCalculer : tableau.get(idCalculer).getEnfant()) {
				enfantCalculer = tableau.get(aCalculer).getCalculer();
				// je calul si gagner, si j'ai bien tous les enfant de calculer le nb de perdu,
				// le nb d'egaliter
				switch (enfantCalculer) {
				case GAGNER:
					gagner = true;
					break;
				case PERDU:
					perdu++;
					break;
				case EGALITER:
					egaliter++;
					break;
				case INDEFINI:
					break;
				case NONCALCULER:
				default:
					nonCalculer = true;
					break;
				}
			}
			Calculer resultatCalculer;
			// si sur un de mes enfant j'ai un gagner
			// (mon adversaire a la possibiliter de gagner)
			if (gagner) {
				// alors j'ai perdu
				resultatCalculer = Calculer.PERDU;
			}
			// sinon si tout n'est pas calculer
			else if (nonCalculer) {
				// alors je ne peut pas calculer
				resultatCalculer = Calculer.NONCALCULER;
			}
			// sionon si sur toute le enfant j'ai perdu
			// (peut import ou joue mon adversaire il perd)
			else if ((perdu / nbEnfant) == 1) {
				// alors je gagne
				resultatCalculer = Calculer.GAGNER;
			}
			// sionon si sur toute le enfant j'ai egaliter
			// (peut import ou joue mon adversaire on tombe sur une egalit�)
			else if ((egaliter / nbEnfant) == 1) {

				// alors on a egalit�
				resultatCalculer = Calculer.EGALITER;
			}
			// sinon tous les enfant on bien ete calculer et en fonction du chemin j'ai des
			// param different
			// (je peut gagner perdre ou avoir une egualiter en fonction du jeux de mon
			// adversaire)
			else {
				resultatCalculer = Calculer.INDEFINI;
			}
			// j'affecte le resutat calculer
			setCalculerPrivate(idCalculer, resultatCalculer);
		}

	}

	/***
	 * on verifie si il y a des lien enfant suprimable et on marque ces lien en
	 * suprimable
	 *
	 * @param id
	 *            l'id des enfant a verifier
	 */
	protected void suprimableLien(Long id) {
		// pour tous les enfant de l'id
		Neud curent = tableau.get(id);
		for (long enfant : curent.getEnfant()) {

			// je cree une image du lien
			Long[] lien = new Long[2];
			lien[0] = id;
			lien[1] = enfant;
			// je le met dans la table des lien a supprimer
			lienSuprimable.add(lien);
		}
		for (long parent : curent.getParent()) {
			{
				// je cree une image du lien
				Long[] lien = new Long[2];
				lien[0] = parent;
				lien[1] = id;
				// je le met dans la table des lien a supprimer
				lienSuprimable.add(lien);
			}
		}

	}

	/**
	 * suppresion effective du lien parent enfant fournis en parametre
	 *
	 * @param parent
	 *            le parent
	 * @param enfant
	 *            l'enfant
	 */
	protected void removeLien(Long parent, long enfant) {
		// recuperation des feuilles parent et enfant on espere qu'elle existe sinon le
		// programe sortira une erreur
		NeudArbre feuilleParent = tableau.get(parent);
		NeudArbre feuilleEnfant = tableau.get(enfant);
		feuilleParent.removeEnfant(enfant);
		feuilleEnfant.removeParent(parent);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.Arbre1#removeExplorable(long)
	 */
	@Override
	public void removeExplorable(long l) {
		tableau.get(l).setExplorable(false);

	}

	/**
	 * si le neud(id) n'est pas calculer et n'a pas de parent non calculer (branche
	 * morte) alors on le met dans le tableau neudSuprimable
	 *
	 * @param id
	 *            id du neud a verifier
	 */
	protected void neudSuprimable(Long id) {
		Neud courant = tableau.get(id);
		boolean suprimable = true;
		// si le neud(id) n'est pas calculer
		if (courant.getCalculer() == Calculer.NONCALCULER) {
			// un des parent
			for (Long parent : courant.getParent()) {
				// est non calculer
				if (tableau.get(parent).getCalculer() == Calculer.NONCALCULER) {
					// l'id n'est pas suprimable
					suprimable = false;
				}
			}
		}
		// neud calculer
		else {
			// on ne le suprime pas
			suprimable = false;
		}
		// affectation reel dans le tableau suprimable
		if (suprimable) {
			neudSuprimable.add(id);
		}
	}

	/**
	 * pour tout les enfant de neud(id) je lance neudSuprimable
	 *
	 * @param id
	 *            id du neud sur le quelle travailler
	 */
	protected void enfantSuprimable(Long id) {
		for (Long enfant : tableau.get(id).getEnfant()) {
			neudSuprimable(enfant);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.Arbre1#getNeud(java.lang.Long)
	 */
	@Override
	public Neud getNeud(Long id) {
		return tableau.get(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.Arbre1#setExplorableFalse(long)
	 */
	@Override
	public void setExplorableFalse(long id) {
		NeudArbre neud = tableau.get(id);
		neud.setExplorable(false);
	}

	@Override
	public Neud nextExplorable(int niveau) {
		return tableau.nextExplorable(niveau);
	}

}
