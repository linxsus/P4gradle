package org.P4Modele_.arbre;

public class Lien {
	private Long parent;
	private Long enfant;

	public Lien(long parent, long enfant) {
		super();
		this.parent = parent;
		this.enfant = enfant;
	}

	/**
	 * @return the parent
	 */
	public Long getParent() {
		return parent;
	}

	/**
	 * @return the enfant
	 */
	public Long getEnfant() {
		return enfant;
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
		result = (prime * result) + ((enfant == null) ? 0 : enfant.hashCode());
		result = (prime * result) + ((parent == null) ? 0 : parent.hashCode());
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
		Lien other = (Lien) obj;
		if (enfant == null) {
			if (other.enfant != null) {
				return false;
			}
		} else if (!enfant.equals(other.enfant)) {
			return false;
		}
		if (parent == null) {
			if (other.parent != null) {
				return false;
			}
		} else if (!parent.equals(other.parent)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Lien [parent=" + parent + ", enfant=" + enfant + "]";
	}

}
