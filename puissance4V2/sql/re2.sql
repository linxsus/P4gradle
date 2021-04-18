SELECT COUNT(*),niveau FROM neud WHERE etat=3 group BY niveau;
SELECT COUNT(*),niveau FROM neud WHERE etat<>3 group BY niveau;
SELECT COUNT(*) FROM neud WHERE etat=3;
SELECT COUNT(*) FROM neud WHERE calculer<>4;
SELECT COUNT(*),niveau FROM neud WHERE calculer=4 group BY niveau;
SELECT COUNT(*),niveau FROM neud WHERE calculer<>4 group BY niveau;
/*SELECT * FROM `neud` where `etat`=3 order by `quantum` DESC LIMIT 1000;*/
SELECT COUNT(*),niveau FROM neud WHERE calculer<>4 and etat=0 group BY niveau;