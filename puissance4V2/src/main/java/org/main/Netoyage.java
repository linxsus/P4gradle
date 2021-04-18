/**
 * 
 */
package org.main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

import org.P4Metier.Factory.Factory;
import org.P4Metier.arbre.SynchronizationBD;
import org.P4Metier.id.GestIdDonneeLong;
import org.P4Modele_.Neud;
import org.P4Modele_.arbre.NeudArbreBD;
import org.Persistant_.ExecuteSql.ExecuteSqlInsert;
import org.Persistant_.ExecuteSql.ExecuteSqlPrim;
import org.test.TestBase;

/**
 * @author  <a href="mailto:xavier.gouraud@wanadoo.fr">xavier</a> 
 *
 */
public class Netoyage {

	protected static Factory factory;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		factory = new Factory(true, true, true, true);
		//TestBase test=new TestBase(factory);
		netoyage();		
	}
	
	public static void netoyage() {
		SynchronizationBD test = factory.getSynchronizationBD();
		for (int i = 0; i < 35; i++) {
			test.synchronization(0);
			Connection cn = factory.getBaseConnection();
			String sql = "UPDATE neud set etat=4  WHERE parent1=9 AND niveau>23;";
			new ExecuteSqlInsert(cn, sql) {
				@Override
				public void run() throws SQLException {

					// removeEnfant(id);
				}
			};
		}
		
	}
}
