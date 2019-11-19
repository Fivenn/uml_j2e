


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
  beginDate date NOT NULL,
  endDate date NOT NULL,
  demandDate dateTime NOT NULL DEFAULT NOW(),
  reason varchar(255) NOT NULL,
  duration int(11) NOT NULL,
  comment varchar(255),
  PRIMARY KEY (id),
    FOREIGN KEY (employe)
        REFERENCES employe(mail),
  CONSTRAINT demandUK UNIQUE (id)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table 'demand'
--

INSERT INTO demand (employe,status,beginDate,endDate,reason, duration) VALUES ('mheyrend@enssat.fr','pending','2018-02-01','2018-02-02','RTT',1);
INSERT INTO demand (employe,status,beginDate,endDate,reason, duration) VALUES ('mheyrend@enssat.fr','canceled','2018-02-01','2018-02-02','RTT',2);
INSERT INTO demand (employe,status,beginDate,endDate,reason, duration) VALUES ('a@a.a','pending','2018-02-01','2018-02-02','RTT',1);
INSERT INTO demand (employe,status,beginDate,endDate,reason, duration) VALUES ('e@e.e','refused','2018-02-01','2018-02-03','RTT',1);
INSERT INTO demand (employe,status,beginDate,endDate,reason, duration) VALUES ('e@e.e','pending','2018-02-01','2018-02-02','RTT',1);
INSERT INTO demand (employe,status,beginDate,endDate,reason, duration) VALUES ('e@e.e','pending','2018-02-01','2018-02-02','RTT',1);
INSERT INTO demand (employe,status,beginDate,endDate,reason, duration) VALUES ('e@e.e','pending','2018-02-01','2018-02-02','RTT',1);
