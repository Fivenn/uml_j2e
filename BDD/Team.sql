 

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

use DaysOffManager;

-- --------------------------------------------------------

--
-- Structure de la table 'reason'
--

CREATE TABLE IF NOT EXISTS team (
  noTeam INT NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  leader varchar(255) NOT NULL,
  description varchar(255),
  PRIMARY KEY (noTeam),
  FOREIGN KEY (leader)
        REFERENCES employe(mail)
        ON DELETE CASCADE
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table 'reason'
--

INSERT INTO team (name, leader, description) VALUES ('Les bests', 'mheyrend@enssat.fr','La team des bests');
INSERT INTO team (name, leader, description) VALUES ('Les RHs', 'clevan@enssat.fr','La respo rh');

UPDATE employe SET team = 2 WHERE fonction = 'EmployeRH';
UPDATE employe SET team = 1 WHERE fonction = 'Employe';
