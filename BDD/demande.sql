
SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

use DaysOffManager;

-- --------------------------------------------------------

--
-- Structure de la table 'demand'
--
DROP TABLE demand;

CREATE TABLE demand (
  id serial NOT NULL,
  employe varchar(255) NOT NULL,
  status enum('pending', 'approved', 'refused') NOT NULL,
  beginDate date NOT NULL,
  endDate date NOT NULL,
  demandDate dateTime NOT NULL DEFAULT NOW(),
  reason varchar(255) NOT NULL,
  duration int(11) NOT NULL,
  comment varchar(255),
  CONSTRAINT fkEmpl FOREIGN KEY (employe) REFERENCES employe(mail) ON DELETE CASCADE,
  CONSTRAINT fkReason FOREIGN KEY (reason) REFERENCES reason(name) ON DELETE CASCADE,
  PRIMARY KEY id
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table 'demand'
--

INSERT INTO demand (employe,status,beginDate,endDate,reason, duration) VALUES ('mheyrend@enssat.fr','pending','2020-02-01','2020-02-02','RTT',1);
INSERT INTO demand (employe,status,beginDate,endDate,reason, duration) VALUES ('a@a.a','approved','2018-02-01','2018-02-02','RTT',1);
INSERT INTO demand (employe,status,beginDate,endDate,reason, duration) VALUES ('e@e.e','refused','2020-02-01','2018-02-03','RTT',1);
INSERT INTO demand (employe,status,beginDate,endDate,reason, duration) VALUES ('e@e.e','pending','2020-02-01','2020-02-02','RTT',1);
INSERT INTO demand (employe,status,beginDate,endDate,reason, duration) VALUES ('e@e.e','pending','2020-02-01','2020-02-02','RTT',1);
INSERT INTO demand (employe,status,beginDate,endDate,reason, duration) VALUES ('e@e.e','pending','2020-02-01','2020-02-02','RTT',1);
