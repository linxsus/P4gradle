package org.P4XG;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PF {

	// il doit bien exister une fonction plus propre qui fait ça mais j'ai pas
	// trouver
	/**
	 * fonction pour appeler une fonction 'name' private de l'object 'object' les
	 * arguments sont a fournir a la suite
	 *
	 * !!! attention il faut bien caster les arguments car il n'y a pas de
	 * transformation possible
	 *
	 * !!! les arguments primitif (int,char,byte..) ne sont pas possible par contre
	 * leur object (Integer,Char,Byte...) fonctionne
	 *
	 * si il n'y a pas d'argument on peut utiliser la fonction
	 * PF.privateFonction(Object object, String name)
	 *
	 * @param object
	 *            object qui contien la fonction
	 * @param name
	 *            nom de la fonction
	 * @param args
	 *            arguments que l'on veut transmetre a la fonction
	 * @return retourne le resultat que lui a renvoyer la fonction
	 */
	public static Object privateFonction(Object object, String name, Object... args) {
		Object resultat = null;
		try {
			Class[] cArg = new Class[args.length];
			// on cree le tableau du type des argements
			int i = 0;
			for (Object arg : args) {
				cArg[i] = arg.getClass();
				i++;
			}
			// on recupere la method du nom 'name' qui a pour arguments cArg
			Method method = object.getClass().getDeclaredMethod(name, cArg);
			// on la rend executable
			method.setAccessible(true);
			// on l'execute
			resultat = method.invoke(object, args);

		} catch (SecurityException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		// on retourn le resultat ou null si il y a eu un souci.
		// remarque se sera aussi null si la fonction ne renvoie rien.
		return resultat;
	}
}
