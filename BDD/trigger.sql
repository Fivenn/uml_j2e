SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET GLOBAL event_scheduler=ON;

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

       	
       	
DROP TRIGGER insertTeam;

DELIMITER $$
CREATE TRIGGER insertTeam BEFORE INSERT ON team
       FOR EACH ROW BEGIN
       	UPDATE employe SET fonction= 'TeamLeader' where mail = NEW.leader;  
       END$$
DELIMITER ;    
       	
DROP EVENT autoAccept;

DELIMITER $$$
CREATE EVENT autoAccept ON SCHEDULE EVERY 1 DAY DO BEGIN
	UPDATE demand SET status = 'approved', comment='acceptation auto' WHERE ADDDATE(demandDate, INTERVAL 2 DAY) < now() and status='pending';
END $$$
DELIMITER ;
   


       
       
       
