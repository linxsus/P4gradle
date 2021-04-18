/**
 *
 */
package org.P4Modele_.map;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
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
	protected boolean depasser=false;

	//TODO a supprimer pour gestion des faux explorable
	
//	protected static int neudSupprimer=0;
//	protected static int neudCalculer=0;
	
	
	// mini 20*SynchronizationBD.NBEXPLORABLEMAX;
	public final static int MAX_NEUD = 2000000;
	//public final static int MAX_NEUD = 20000;
	protected LinkedList<Long> idsExplorable;

	protected PriorityQueue<NeudArbre> mapExplorable;
	
	protected Map<Long, NeudArbre> mapSupprimable;

	/**
	 *
	 */

	private SqlArbre sqlArbre;
	protected Factory factory;
	//protected TamponBD tampon;
	protected SynchronizationBD synchro;

	protected void init(Factory factory) {
		Comparator<NeudArbre> compNeud = new Comparator<NeudArbre>() {

			@Override
			public int compare(NeudArbre o1, NeudArbre o2) {
				if (o1.getNiveau() - o2.getNiveau() == 0) {
					if (o1.getQuantum() - o2.getQuantum() == 0) {
						if (o1.getId() - o2.getId() > 0) {
							return -1;
						} else {
							return 1;
						}
					} else {
						if (o1.getQuantum() - o2.getQuantum() > 0) {
							return -1;
						}
					}
					return 1;
				} else {
					if (o1.getNiveau() - o2.getNiveau() > 0) {
						return -1;
					}
				}
				return 1;
			}
		};
		this.factory = factory;
		sqlArbre = factory.getMysqlArbre();
		//tampon = factory.getTampon();
		synchro = factory.getSynchronizationBD();

		idsExplorable = new LinkedList<>();
		mapExplorable = new PriorityQueue<NeudArbre>(SynchronizationBD.NBEXPLORABLEMAX,compNeud);
		mapSupprimable = new HashMap<>(SynchronizationBD.NBEXPLORABLEMAX);
	}

	public MapArbreBD(Factory factory) {
		super(MAX_NEUD);
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
			// if (!tampon.isSupprimable(id)) {
			// je le recharge du tampon lecture explorable
			//neudArbre = get(id);
			if (neudArbre == null) {
				neudArbre = sqlArbre.getNeud(id);
			}
			if (neudArbre != null) {
				((NeudArbreBD) neudArbre).setMapArbre(this);
				super.put(id, neudArbre);
			}
			// }
		}
//		if (neudArbre!=null) {
//			if (!neudArbre.isSupprimable()) {
//				return neudArbre;
//			}
//		}
//		return null;
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
		mapExplorable.offer(value);
		return super.put(key, value);
	}

//	public NeudArbre Update(Long key, NeudArbre value) {
//		// TamponBD.addNeud(value); // onle fait deja dans le constructeur Neud
//		super.remove(key);
//		return super.put(key, value);
//	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.P4Arbre.BaseDonnee.MapArbre#remove(java.lang.Object)
	 */
	@Override
	public NeudArbre remove(Object key) {
		NeudArbre result = super.remove(key);		
		if (result != null) {
			mapExplorable.remove(result);
			if (result.getParent().size()!=0) {
				System.out.println("erreur supression");
				System.exit(0);
			};
			if (result.getEnfant().size()!=0) {
				System.out.println("erreur supression");
				System.exit(0);
			};
		
		}

		return result;
	}

	@Override
	public NeudArbre nextExplorable(int niveau) {
		NeudArbreBD neud = null;
		// si on a depasser le max du map
		if (size()>MAX_NEUD)
			{
			//on rechage les explorable ce qui va reinitialiser le map	
			loadMapExplorable(niveau);
			System.out.print("tampon plein :"+ mapExplorable.size()+" quantum "+mapExplorable.peek().getQuantum());
			}
		do {
			if (mapExplorable.isEmpty()) {
				
				// on recupere de la partie synchronisation le map ids,neud des explorables
				// attention cette liste n'est pas jour il peut y a voir dans cette liste des
				// neud supprimer ou déja calculer	
					loadMapExplorable(niveau);
					System.out.print("\n rechargement dans la base des explorable :" + mapExplorable.size()+" quantum "+mapExplorable.peek().getQuantum());
				// on insert dans la queu les nouveaux id explorable
				// System.out.print(" size " + size() + " "); // TODO permet de verifier que
				// l'on ne fait pas de fuite
				// // memoire a supprimer des que possible
				// si il n'y a plus rien d'explorable
				if (mapExplorable.isEmpty()) {
					return null;

				}

			}
			// TODO affichage de suivit a supprimer
			// tous les 500 explorable
			if ((nbtour % 1500) == 0) {
				
				if ((nbtour % 150000) == 0) {
					System.out.println("");
				}
				System.out.print(".");
				//System.out.print("\n neud supprimer : "+ neudSupprimer +" neud calculer : " + neudCalculer);
				//neudSupprimer=0;
				//neudCalculer=0;
			}
			nbtour++;

			// resinchronisation (on rechage l'explorable et on verifie si c'est toujours un
			// explorable)
			neud = (NeudArbreBD) mapExplorable.poll();
			//TODO a supprimer
//			if (neud == null) {neudSupprimer++;}
//			else {
//			if (!neud.isExplorable()) {neudCalculer++;}}
			
		} while ((neud.isSupprimable()) || !neud.isExplorable() );
		if (neud.getId()==0) {return null;}
		return neud;
	}

	private void loadMapExplorable(int niveau) {
		Map<Long,NeudArbre> tempExplorable = synchro.synchronization(niveau);
		mapExplorable.clear();
		mapExplorable.addAll(tempExplorable.values());
		putAll(tempExplorable);
		nbtour=0;
		try {
			if (System.in.available()>0) {
			System.exit(0);;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		//return size() > (maxEnregistrement);
		return false;
	}
	
	public int getNbexplorableTraiter() {
		return nbtour;
	}
	
	
}
