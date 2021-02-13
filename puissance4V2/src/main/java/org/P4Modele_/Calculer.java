package org.P4Modele_;

public enum Calculer {
	GAGNER(0), PERDU(1), EGALITER(2), INDEFINI(3), NONCALCULER(4);
	private final int id;

	private Calculer(int value) {
		this.id = value;
	}

	public int getValue() {
		return id;
	}

	public boolean Compare(int i) {
		return id == i;
	}

	public static Calculer GetValue(int id) {
		Calculer[] As = Calculer.values();
		for (int i = 0; i < As.length; i++) {
			if (As[i].Compare(id)) {
				return As[i];
			}
		}
		return null;
	}

}
