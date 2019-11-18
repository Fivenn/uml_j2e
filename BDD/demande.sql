


SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

use DaysOffManager;

-- --------------------------------------------------------

--
-- Structure de la table 'demand'
--

CREATE OR REPLACE TABLE demand (
  id serial NOT NULL,
  employe varchar(255) NOT NULL,
  status enum('pending', 'canceled', 'approved', 'refused') NOT NULL,
  beginDate varchar(255) NOT NULL,
  endDate varchar(255) NOT NULL,
  demandDate dateTime NOT NULL DEFAULT NOW(),
  reason varchar(255) NOT NULL,
  duration int(11) NOT NULL,
  PRIMARY KEY (id),
    FOREIGN KEY (employe)
        REFERENCES employe(mail),
  CONSTRAINT demandUK UNIQUE (id)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table 'demand'
--

INSERT INTO demand (employe,status,beginDate,endDate,reason, duration) VALUES ('mheyrend@enssat.fr','pending','29-12-1998','30-12-1998','RTT',1);
INSERT INTO demand (employe,status,beginDate,endDate,reason, duration) VALUES ('mheyrend@enssat.fr','canceled','29-12-1999','30-12-1999','RTT',2);
INSERT INTO demand (employe,status,beginDate,endDate,reason, duration) VALUES ('a@a.a','pending','29-12-1998','30-12-1998','RTT',1);
INSERT INTO demand (employe,status,beginDate,endDate,reason, duration) VALUES ('e@e.e','refused','1-12-1998','14-12-1998','RTT',1);
INSERT INTO demand (employe,status,beginDate,endDate,reason, duration) VALUES ('e@e.e','pending','10-12-1998','15-12-1998','RTT',1);
INSERT INTO demand (employe,status,beginDate,endDate,reason, duration) VALUES ('e@e.e','pending','21-12-2018','31-12-1998','RTT',1);
INSERT INTO demand (employe,status,beginDate,endDate,reason, duration) VALUES ('e@e.e','pending','29-12-1998','30-12-1998','RTT',1);
