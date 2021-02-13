package org.P4Donnee_;

import org.P4Metier.GestIdDonnee;
import org.junit.jupiter.api.Test;

public interface GestIdDonneeTest<T> {

	public abstract GestIdDonnee<T> createInstance();

	@Test
	void testGetBaseDonnee();

	@Test
	void testGetIdBaseDonnee();

	@Test
	void testSetIdBaseDonnee();

	@Test
	void testNewBaseDonneeId();

	@Test
	void testGetIdBaseDonneeIntArrayArray();

	@Test
	void testGetDonneeId();

	@Test
	void testImportExport();

	@Test
	void testIsMiroire();

}
