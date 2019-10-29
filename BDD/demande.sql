 

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

use DaysOffManager;

-- --------------------------------------------------------

--
-- Structure de la table 'demand'
--

CREATE TABLE IF NOT EXISTS demand (
  id serial NOT NULL, 
  statut varchar(255) NOT NULL,
  beginDate varchar(255) NOT NULL,
  beginEnd varchar(255) NOT NULL,
  demandDate varchar(255) NOT NULL,
  reason varchar(255) NOT NULL,
  duree int(11) NOT NULL,
  
  PRIMARY KEY (id),
  CONSTRAINT demandUK UNIQUE (beginDate, endDate)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table 'demand'
--

INSERT INTO demand (mail, firstName, surname, address, nbDays, fonction) VALUES ('mheyrend@enssat.fr', 'Maëlle', 'Heyrendt', '2 rue de Taiwan', 25, 'ChefEquipe');
INSERT INTO demand (mail, firstName, surname, address, nbDays, fonction) VALUES ('clevan@enssat.fr', 'Clélia', 'Le van', '2 rue du Vietam', 25, 'RespoRH');
