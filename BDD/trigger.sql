
--Trigger pour decrementer et reincrementer les jours des demandes quand approuvé ou annulée

DROP TRIGGER decrementeNbDays;

DELIMITER ;
DELIMITER $$
CREATE TRIGGER decrementeNbDays BEFORE UPDATE ON demand
       FOR EACH ROW
       BEGIN
            IF NEW.status = 'approved' and OLD.status = 'pending' THEN
                UPDATE employe SET nbDays=nbDays-NEW.duration WHERE mail=NEW.employe;
            ELSE IF NEW.status = 'canceled' and OLD.status = 'approved' THEN
                UPDATE employe SET nbDays=nbDays+NEW.duration WHERE mail=NEW.employe;
            END IF;
            END IF;
       END$$

       
DROP TRIGGER insertDemand;


DELIMITER $$
CREATE TRIGGER insertDemand BEFORE INSERT ON demand
       FOR EACH ROW 
       	SET NEW.demandDate = SELECT convert(varchar(25), now(), 120) ;

       END$$
DELIMITER ;       
       
SET
       	NEW.status = 'pending' AND
       


       
       
       
