/**
 *
 */
package org.P4Modele_.map;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.P4Metier.Factory.Factory;
import org.P4Metier.arbre.SynchronizationBD;
import org.P4Modele_.MapArbre;
import org.P4Modele_.NeudArbre;
import org.P4Modele_.arbre.NeudArbreBD;
import org.P4Modele_.arbre.TamponBD;
import org.Persistant_.requette.SqlArbre;

/**
 * @author Xavier Gouraud
 *
 */
public class MapArbreBD extends LinkedHashMap<Long, NeudArbre> implements MapArbre {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// gestion affichage
	protected static int nbtour = 1;
	protected static int nbtour1 = 1;

	//TODO a supprimer pour gestion des faux explorable
	
//	protected static int neudSupprimer=0;
//	protected static int neudCalculer=0;
	
	
	// mini 20*SynchronizationBD.NBEXPLORABLEMAX;
	protected final static int maxEnregistrement = 200 * SynchronizationBD.NBEXPLORABLEMAX;
	protected LinkedList<Long> idsExplorable;

	protected Map<Long, NeudArbre> mapExplorable;

	/**
	 *
	 */

	private SqlArbre sqlArbre;
	protected Factory factory;
	protected TamponBD tampon;
	protected SynchronizationBD synchro;

	protected void init(Factory factory) {
		this.factory = factory;
		sqlArbre = factory.getMysqlArbre();
		tampon = factory.getTampon();
		synchro = factory.getSynchronizationBD();

		idsExplorable = new LinkedList<>();
		mapExplorable = new HashMap<>(SynchronizationBD.NBEXPLORABLEMAX);
	}

	public MapArbreBD(Factory factory) {
		super(maxEnregistrement);
		init(factory);
	}

	public MapArbreBD(Factory factory, int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
		init(factory);
	}

	public MapArbreBD(Factory factory, int initialCapacity) {
		super(initialCapacity);
		init(factory);
	}

	public MapArbreBD(Factory factory, Map<? extends Long, ? extends NeudArbre> m) {
		super(m);
		init(factory);
	}

	@Override
	public NeudArbre get(Object key) {
		NeudArbre neudArbre = super.get(key);
		if (neudArbre == null) {
			long id = (long) key;
			if (!tampon.isSupprimable(id)) {
				// je le recharge du tampon lecture explorable
				neudArbre = mapExplorable.get(id);
				if (neudArbre == null) {
					neudArbre = sqlArbre.getNeud(id);
				}
				if (neudArbre != null) {
					((NeudArbreBD) neudArbre).setMapArbre(this);
					super.put(id, neudArbre);
				}
			}
		}
		return neudArbre;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.BaseDonnee.MapArbre#put(java.lang.Long,
	 * org.P4Arbre.NeudArbre)
	 */
	@Override
	public NeudArbre put(Long key, NeudArbre value) {
		// TamponBD.addNeud(value); // onle fait deja dans le constructeur Neud
		((NeudArbreBD) value).setMapArbre(this);
		return super.put(key, value);
	}

	public NeudArbre Update(Long key, NeudArbre value) {
		// TamponBD.addNeud(value); // onle fait deja dans le constructeur Neud
		super.remove(key);
		return super.put(key, value);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.BaseDonnee.MapArbre#remove(java.lang.Object)
	 */
	@Override
	public NeudArbre remove(Object key) {
		NeudArbre result = super.remove(key);
		if (result != null) {
			tampon.removeNeud(result);
		}

		return result;
	}

	@Override
	public NeudArbre nextExplorable(int niveau) {
		NeudArbre neud = null;
		do {
			if (idsExplorable.isEmpty()) {
				// on recupere de la partie synchronisation le map ids,neud des explorables
				// attention cette liste n'est pas jour il peut y a voir dans cette liste des
				// neud supprimer ou déja calculer
				mapExplorable = synchro.synchronization(niveau);
				// on insert dans la queu les nouveaux id explorable
				idsExplorable.addAll(mapExplorable.keySet());
				// System.out.print(" size " + size() + " "); // TODO permet de verifier que
				// l'on ne fait pas de fuite
				// // memoire a supprimer des que possible
				// si il n'y a plus rien d'explorable
				if (idsExplorable.isEmpty()) {
					return null;

				}

			}
			// TODO affichage de suivit a supprimer
			// tous les 500 explorable
			if ((nbtour % 500) == 0) {
				System.out.print(".");
				
				//System.out.print("\n neud supprimer : "+ neudSupprimer +" neud calculer : " + neudCalculer);
				//neudSupprimer=0;
				//neudCalculer=0;
			}
			nbtour++;

			// resinchronisation (on rechage l'explorable et on verifie si c'est toujours un
			// explorable)
			neud = get(idsExplorable.pollLast());
			//TODO a supprimer
//			if (neud == null) {neudSupprimer++;}
//			else {
//			if (!neud.isExplorable()) {neudCalculer++;}}
			
		} while ((neud == null) || !neud.isExplorable());
		return neud;
	}

	@Override
	public NeudArbre getOrDefault(Object key, NeudArbre defaultValue) {

		return super.getOrDefault(key, defaultValue);
	}

	@Override
	public NeudArbre putIfAbsent(Long key, NeudArbre value) {

		return super.putIfAbsent(key, value);
	}

	@Override
	public boolean remove(Object key, Object value) {

		return super.remove(key, value);
	}

	@Override
	public boolean replace(Long key, NeudArbre oldValue, NeudArbre newValue) {

		return super.replace(key, oldValue, newValue);
	}

	@Override
	public NeudArbre replace(Long key, NeudArbre value) {

		return super.replace(key, value);
	}

	@Override
	public NeudArbre computeIfAbsent(Long key, Function<? super Long, ? extends NeudArbre> mappingFunction) {

		return super.computeIfAbsent(key, mappingFunction);
	}

	@Override
	public NeudArbre computeIfPresent(Long key,
			BiFunction<? super Long, ? super NeudArbre, ? extends NeudArbre> remappingFunction) {

		return super.computeIfPresent(key, remappingFunction);
	}

	@Override
	public NeudArbre compute(Long key,
			BiFunction<? super Long, ? super NeudArbre, ? extends NeudArbre> remappingFunction) {

		return super.compute(key, remappingFunction);
	}

	@Override
	public NeudArbre merge(Long key, NeudArbre value,
			BiFunction<? super NeudArbre, ? super NeudArbre, ? extends NeudArbre> remappingFunction) {

		return super.merge(key, value, remappingFunction);
	}

	//ici on definit que l'element le plus ancien doit etre supprimer si le map depasse maxEnregistrement
	@Override
	protected boolean removeEldestEntry(Map.Entry<Long, NeudArbre> eldest) {
		return size() > (maxEnregistrement);
	}
}
