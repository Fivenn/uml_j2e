 

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

use DaysOffManager;

-- --------------------------------------------------------

--
-- Structure de la table 'employe'
--

CREATE OR REPLACE TABLE employe (
  mail varchar(255) NOT NULL,
  firstName varchar(255) NOT NULL,
  surname varchar(255) NOT NULL,
  address varchar(255) NOT NULL,
  nbDays int(11) NOT NULL,
  fonction enum('Employe', 'EmployeRH', 'TeamLeader', 'RespoRH') NOT NULL,
  pwd varchar(255) NOT NULL,
  team int,
  PRIMARY KEY (mail),
  FOREIGN KEY (team)
        REFERENCES team(noTeam)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table 'employe'
--

INSERT INTO employe (mail, firstName, surname, address, nbDays, fonction, pwd) VALUES ('mheyrend@enssat.fr', 'Maëlle', 'Heyrendt', '2 rue de Taiwan', 25, 'TeamLeader', 'mh');
INSERT INTO employe (mail, firstName, surname, address, nbDays, fonction, pwd) VALUES ('clevan@enssat.fr', 'Clélia', 'Le van', '2 rue du Vietam', 25, 'RespoRH','cv');
INSERT INTO employe (mail, firstName, surname, address, nbDays, fonction, pwd) VALUES ('a@a.a', 'a', 'a', 'a', 25, 'TeamLeader','a');
INSERT INTO employe (mail, firstName, surname, address, nbDays, fonction, pwd) VALUES ('b@b.b', 'b', 'b', 'b', 45, 'RespoRH','b');
INSERT INTO employe (mail, firstName, surname, address, nbDays, fonction, pwd) VALUES ('c@c.c', 'c', 'c', 'c', 45, 'EmployeRH','c');
INSERT INTO employe (mail, firstName, surname, address, nbDays, fonction, pwd) VALUES ('d@d.d', 'd', 'd', 'd', 15, 'Employe','d');
INSERT INTO employe (mail, firstName, surname, address, nbDays, fonction, pwd,team) VALUES ('e@e.e', 'e', 'e', 'e', 15, 'Employe','e',1);
