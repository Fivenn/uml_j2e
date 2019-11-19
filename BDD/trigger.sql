
--Trigger pour decrementer et reincrementer les jours des demandes quand approuvé ou annulée

DROP TRIGGER decrementeNbDays;


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
DELIMITER ;
       
DROP TRIGGER insertDemand;


CREATE TRIGGER insertDemand BEFORE INSERT ON demand
       FOR EACH ROW 
       	SET NEW.status = 'pending';    
       	
       


       
       
       
