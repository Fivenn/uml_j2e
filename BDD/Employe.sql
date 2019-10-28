 

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

use DaysOffManager;

-- --------------------------------------------------------

--
-- Structure de la table 'employe'
--

CREATE TABLE IF NOT EXISTS employe (
  mail varchar(255) NOT NULL,
  firstName varchar(255) NOT NULL,
  surname varchar(255) NOT NULL,
  address varchar(255) NOT NULL,
  nbDays int(11) NOT NULL,
  fonction enum('Employe', 'EmployeRH', 'ChefEquipe', 'RespoRH') NOT NULL,
  
  PRIMARY KEY (mail)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table 'employe'
--

INSERT INTO employe (mail, firstName, surname, address, nbDays, fonction) VALUES ('mheyrend@enssat.fr', 'Maëlle', 'Heyrendt', '2 rue de Taiwan', 25, 'ChefEquipe');
INSERT INTO employe (mail, firstName, surname, address, nbDays, fonction) VALUES ('clevan@enssat.fr', 'Clélia', 'Le van', '2 rue du Vietam', 25, 'RespoRH');
