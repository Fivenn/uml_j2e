
--Trigger pour decrementer et reincrementer les jours des demandes quand approuvé ou annulée

DROP TRIGGER decrementeNbDays;

DELIMITER ;
DELIMITER $$
CREATE TRIGGER decrementeNbDays BEFORE UPDATE ON demand
       FOR EACH ROW
       BEGIN
            IF NEW.statut = 'approuved' && OLD.statut = 'pending' THEN
                UPDATE employe SET nbDays=nbDays-NEW.duree WHERE mail=NEW.employe;
            ELSE IF NEW.statut = 'canceled' && OLD.statut = 'approuved' THEN
                UPDATE employe SET nbDays=nbDays+NEW.duree WHERE mail=NEW.employe;
            END IF;
            END IF;
       END$$

       
       
       
