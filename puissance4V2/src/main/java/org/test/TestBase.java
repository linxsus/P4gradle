/**
 * 
 */
package org.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.P4Metier.Factory.Factory;
import org.P4Modele_.Calculer;
import org.P4Modele_.NeudArbre;
import org.P4Modele_.arbre.NeudArbreBD;
import org.P4Modele_.arbre.TamponBD;
import org.Persistant_.ExecuteSql.ExecuteSqlInsert;
import org.Persistant_.ExecuteSql.ExecuteSqlPrim;
import org.Persistant_.ExecuteSql.ExecuteSqlSelect;

/**
 * @author  <a href="mailto:xavier.gouraud@wanadoo.fr">xavier</a> 
 *
 */
public class TestBase {

		
		private TamponBD tampon;
		private Connection cn;
		private Factory factory;

		public TestBase(Factory factory) {
			this.factory = factory;
			cn = factory.getBaseConnection();
			tampon = factory.getTampon();
			creatlien();
		}

		public void creatlien() {
			String[] sqlMultiple = { "DELETE FROM lienenfant;\r\n"
					,"DELETE FROM lienparent;\r\n"
					, "INSERT INTO lienenfant SELECT idNeud,enfant1 FROM neud WHERE enfant1<>9;\r\n"
					, "INSERT INTO lienenfant SELECT idNeud,enfant2 FROM neud WHERE enfant2<>9;\r\n"
					, "INSERT INTO lienenfant SELECT idNeud,enfant3 FROM neud WHERE enfant3<>9;\r\n"
					, "INSERT INTO lienenfant SELECT idNeud,enfant4 FROM neud WHERE enfant4<>9;\r\n"
					, "INSERT INTO lienenfant SELECT idNeud,enfant5 FROM neud WHERE enfant5<>9;\r\n"
					, "INSERT INTO lienenfant SELECT idNeud,enfant6 FROM neud WHERE enfant6<>9;\r\n"
					, "INSERT INTO lienenfant SELECT idNeud,enfant7 FROM neud WHERE enfant7<>9;\r\n"
					, "INSERT INTO lienparent SELECT idNeud,parent1 FROM neud WHERE parent1<>9;\r\n"
					, "INSERT INTO lienparent SELECT idNeud,parent2 FROM neud WHERE parent2<>9;\r\n"
					, "INSERT INTO lienparent SELECT idNeud,parent3 FROM neud WHERE parent3<>9;\r\n"
					, "INSERT INTO lienparent SELECT idNeud,parent4 FROM neud WHERE parent4<>9;\r\n"
					, "INSERT INTO lienparent SELECT idNeud,parent5 FROM neud WHERE parent5<>9;\r\n"
					, "INSERT INTO lienparent SELECT idNeud,parent6 FROM neud  WHERE parent6<>9;\r\n"
					, "INSERT INTO lienparent SELECT idNeud,parent7 FROM neud WHERE parent7<>9;"};
			for (String sql :sqlMultiple)
			new ExecuteSqlInsert(cn, sql) {
				@Override
				public void run() throws SQLException {
					
				}
			};
			
			ExecuteSqlPrim<Integer> result = new ExecuteSqlPrim<>(null);
			String sql = "SELECT  count(*)\r\n"
					+ "FROM    lienenfant t1\r\n"
					+ "WHERE   NOT EXISTS\r\n"
					+ "        (\r\n"
					+ "        SELECT  NULL\r\n"
					+ "        FROM    lienparent t2\r\n"
					+ "        WHERE   t1.idneudparent = t2.idneudenfant AND t2.idneudparent = t1.idneudenfant\r\n"
					+ "        );";
			new ExecuteSqlSelect(cn, sql) {
				@Override
				public void forEach(ResultSet rs) throws SQLException {
					int temp = rs.getInt(1);
					result.set(temp);
					
				}
			};
			if (result.get()!=0) {
				System.out.println("erreur dans les lien parent "+result.get());
			}
			ExecuteSqlPrim<Integer> result1 = new ExecuteSqlPrim<>(null);
			String sql1 = "SELECT  count(*)\r\n"
					+ "FROM    lienparent t1\r\n"
					+ "WHERE   NOT EXISTS\r\n"
					+ "        (\r\n"
					+ "        SELECT  NULL\r\n"
					+ "        FROM    lienenfant t2\r\n"
					+ "        WHERE   t1.idneudparent = t2.idneudenfant AND t2.idneudparent = t1.idneudenfant\r\n"
					+ "        );";
			new ExecuteSqlSelect(cn, sql1) {
				@Override
				public void forEach(ResultSet rs) throws SQLException {
					int temp = rs.getInt(1);
					result1.set(temp);
					
				}
			};
			if (result1.get()!=0) {
				System.out.println("erreur dans les lien enfant "+result1.get());
				System.exit(0);
			}
		
		}
}
