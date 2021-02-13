/**
 *  ensemble de class pour cree des classes anonyme SQL
 *
 *  le but est d'enlever du program 'principal' toute la gestion d'ouvertur et de fermeture des statement et resultatSet
 *  et de symplifier la gestion des exception
 *
 * on doit y perdre un peut en rapiditer<br>
 *  la lisibiliter est pas tres bonne pour un neophyte<br>
 *  et la modification de variable passer, pas focement tres comprehensible.<br>
 *  par contre on y gagne en maintenance car plus de fuite memeoire<br>
 *  et les exception sont toute rassembler a un endroit<br>
 * @author Xavier Gouraud
 *
 */
package org.Persistant_.ExecuteSql;