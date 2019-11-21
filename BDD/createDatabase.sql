DROP DATABASE DaysOffManager;
CREATE DATABASE DaysOffManager;

SET GLOBAL sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));

source ./BDD/demande.sql;
source ./BDD/Employe.sql;
source ./BDD/motif.sql;
source ./BDD/Team.sql;
source ./BDD/trigger.sql;