package org.P4Modele_;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * interface pour desactiver les fonction Map qui ne devrait pas etre utiliser
 * car non coder pour le lien avec la base de donnee
 *
 * @author Xavier Gouraud
 *
 */
public interface MapArbre extends Map<Long, NeudArbre> {

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#size()
	 */

	@Override
	abstract int size();

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#isEmpty()
	 */
	@Deprecated
	@Override
	abstract boolean isEmpty();

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	@Deprecated
	@Override
	abstract boolean containsKey(Object key);

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	@Deprecated
	@Override
	abstract boolean containsValue(Object value);

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	abstract NeudArbre get(Object key);

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	abstract NeudArbre put(Long key, NeudArbre value);

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	abstract NeudArbre remove(Object key);

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	@Override
	abstract void putAll(Map<? extends Long, ? extends NeudArbre> m);

	/**
	 * ne supprime que les elements memoire tout ce qui est en base de donnee reste
	 * en base de donnee
	 *
	 * @see java.util.Map#clear()
	 **/
	@Override
	abstract void clear();

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#keySet()
	 */
	@Deprecated
	@Override
	abstract Set<Long> keySet();

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#values()
	 */
	@Override
	abstract Collection<NeudArbre> values();

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#entrySet()
	 */
	@Override
	abstract Set<Entry<Long, NeudArbre>> entrySet();

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#equals(java.lang.Object)
	 */
	@Deprecated
	@Override
	abstract boolean equals(Object o);

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#getOrDefault(java.lang.Object, java.lang.Object)
	 */
	@Deprecated
	@Override
	abstract NeudArbre getOrDefault(Object key, NeudArbre defaultValue);

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#forEach(java.util.function.BiConsumer)
	 */
	@Deprecated
	@Override
	abstract void forEach(BiConsumer<? super Long, ? super NeudArbre> action);

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#replaceAll(java.util.function.BiFunction)
	 */
	@Deprecated
	@Override
	abstract void replaceAll(BiFunction<? super Long, ? super NeudArbre, ? extends NeudArbre> function);

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#putIfAbsent(java.lang.Object, java.lang.Object)
	 */
	@Deprecated
	@Override
	abstract NeudArbre putIfAbsent(Long key, NeudArbre value);

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#remove(java.lang.Object, java.lang.Object)
	 */
	@Deprecated
	@Override
	abstract boolean remove(Object key, Object value);

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#replace(java.lang.Object, java.lang.Object,
	 * java.lang.Object)
	 */
	@Deprecated
	@Override
	abstract boolean replace(Long key, NeudArbre oldValue, NeudArbre newValue);

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#replace(java.lang.Object, java.lang.Object)
	 */
	@Deprecated
	@Override
	abstract NeudArbre replace(Long key, NeudArbre value);

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#computeIfAbsent(java.lang.Object,
	 * java.util.function.Function)
	 */
	@Deprecated
	@Override
	abstract NeudArbre computeIfAbsent(Long key, Function<? super Long, ? extends NeudArbre> mappingFunction);

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#computeIfPresent(java.lang.Object,
	 * java.util.function.BiFunction)
	 */
	@Deprecated
	@Override
	abstract NeudArbre computeIfPresent(Long key,
			BiFunction<? super Long, ? super NeudArbre, ? extends NeudArbre> remappingFunction);

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#compute(java.lang.Object, java.util.function.BiFunction)
	 */
	@Deprecated
	@Override
	abstract NeudArbre compute(Long key,
			BiFunction<? super Long, ? super NeudArbre, ? extends NeudArbre> remappingFunction);

	/*
	 * (non-Javadoc)
	 *
	 * @see java.util.Map#merge(java.lang.Object, java.lang.Object,
	 * java.util.function.BiFunction)
	 */
	@Deprecated
	@Override
	abstract NeudArbre merge(Long key, NeudArbre value,
			BiFunction<? super NeudArbre, ? super NeudArbre, ? extends NeudArbre> remappingFunction);

	/**
	 * retourne un neud explorable du 'niveau'
	 *
	 * @param niveau
	 *            niveau sur le quelle on veut un explorable
	 * @return un neud
	 */
	NeudArbre nextExplorable(int niveau);

}
