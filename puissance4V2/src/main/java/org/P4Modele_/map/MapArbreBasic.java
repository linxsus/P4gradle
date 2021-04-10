package org.P4Modele_.map;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

import org.P4Modele_.MapArbre;
import org.P4Modele_.NeudArbre;

public class MapArbreBasic extends HashMap<Long, NeudArbre> implements MapArbre {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public NeudArbre nextExplorable(int niveau) {
		Optional<NeudArbre> resultat = values().stream().filter(new Predicate<NeudArbre>() {
			@Override
			public boolean test(NeudArbre x) {
				//TODO il faut tester que l'on est pas suprimable.
				return x.isExplorable(niveau);
			}
		}).findFirst();
		if (resultat.isPresent()) {
			return resultat.get();
		}
		return null;
	}

}
