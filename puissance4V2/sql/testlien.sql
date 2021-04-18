DELETE FROM lienenfant;
DELETE FROM lienparent;
INSERT INTO lienenfant SELECT idNeud,enfant1 FROM neud WHERE enfant1<>9;
INSERT INTO lienenfant SELECT idNeud,enfant2 FROM neud WHERE enfant2<>9;
INSERT INTO lienenfant SELECT idNeud,enfant3 FROM neud WHERE enfant3<>9;
INSERT INTO lienenfant SELECT idNeud,enfant4 FROM neud WHERE enfant4<>9;
INSERT INTO lienenfant SELECT idNeud,enfant5 FROM neud WHERE enfant5<>9;
INSERT INTO lienenfant SELECT idNeud,enfant6 FROM neud WHERE enfant6<>9;
INSERT INTO lienenfant SELECT idNeud,enfant7 FROM neud WHERE enfant7<>9;
INSERT INTO lienparent SELECT idNeud,parent1 FROM neud WHERE parent1<>9;
INSERT INTO lienparent SELECT idNeud,parent2 FROM neud WHERE parent2<>9;
INSERT INTO lienparent SELECT idNeud,parent3 FROM neud WHERE parent3<>9;
INSERT INTO lienparent SELECT idNeud,parent4 FROM neud WHERE parent4<>9;
INSERT INTO lienparent SELECT idNeud,parent5 FROM neud WHERE parent5<>9;
INSERT INTO lienparent SELECT idNeud,parent6 FROM neud  WHERE parent6<>9;
INSERT INTO lienparent SELECT idNeud,parent7 FROM neud WHERE parent7<>9;

SELECT  *
FROM    lienenfant t1
WHERE   NOT EXISTS
        (
        SELECT  NULL
        FROM    lienparent t2
        WHERE   t1.idneudparent = t2.idneudenfant AND t2.idneudparent = t1.idneudenfant
        );
        
SELECT  *
FROM    lienparent t1
WHERE   NOT EXISTS
        (
        SELECT  NULL
        FROM    lienenfant t2
        WHERE   t1.idneudparent = t2.idneudenfant AND t2.idneudparent = t1.idneudenfant
        );