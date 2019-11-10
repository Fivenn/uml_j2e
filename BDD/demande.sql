


SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

use DaysOffManager;

-- --------------------------------------------------------

--
-- Structure de la table 'demand'
--

CREATE OR REPLACE TABLE demand (
  id serial NOT NULL, 
  employe varchar(255) NOT NULL,
  statut enum('pending', 'canceled', 'approuved', 'refused') NOT NULL,
  beginDate varchar(255) NOT NULL,
  endDate varchar(255) NOT NULL,
  demandDate varchar(255) NOT NULL,
  reason varchar(255) NOT NULL,
  duree int(11) NOT NULL,
  PRIMARY KEY (id),
    FOREIGN KEY (employe)
        REFERENCES employe(mail),
  CONSTRAINT demandUK UNIQUE (employe, beginDate, endDate)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table 'demand'
--

INSERT INTO demand (employe,statut,beginDate,endDate,demandDate, reason, duree) VALUES ('mheyrend@enssat.fr','pending','29-12-1998','30-12-1998','25-12-1998','RTT',1);
INSERT INTO demand (employe,statut,beginDate,endDate,demandDate, reason, duree) VALUES ('mheyrend@enssat.fr','canceled','29-12-1999','30-12-1999','25-12-1999','RTT',2);
INSERT INTO demand (employe,statut,beginDate,endDate,demandDate, reason, duree) VALUES ('a@a.a','pending','29-12-1998','30-12-1998','25-12-1998','RTT',1);
INSERT INTO demand (employe,statut,beginDate,endDate,demandDate, reason, duree) VALUES ('e@e.e','refused','29-12-1998','30-12-1998','25-12-1998','RTT',1);
