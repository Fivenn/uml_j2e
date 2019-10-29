 

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

use DaysOffManager;

-- --------------------------------------------------------

--
-- Structure de la table 'reason'
--

CREATE TABLE IF NOT EXISTS reason (
  name varchar(255) NOT NULL,
  description varchar(255) NOT NULL,
  
  PRIMARY KEY (name)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table 'reason'
--

INSERT INTO reason (name, description) VALUES ('RTT', '');
INSERT INTO reason (name, description) VALUES ('Congés annuels', '');
INSERT INTO reason (name, description) VALUES ('Enfants malades', '');
INSERT INTO reason (name, description) VALUES ('Raisons familiales', '');