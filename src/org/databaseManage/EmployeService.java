package org.databaseManage;

import java.util.List;

import org.model.Demand;
import org.model.Employe;

public class EmployeService {
	
	// choose the DAO data source : DB or Mock
	private EmployeDAOImpl employeDao = new EmployeDAOImpl();  
	//Fonction permettant de renvoyer tous les employés
	public List<Employe> getAllEmployes() {
		List<Employe> listEmployes = employeDao.findByAll();
		return listEmployes;
	}
	//Fonction permettant de renvoyer tous les employés sauf les rh
	public List<Employe> getAllEmployesButRH() {
		List<Employe> listEmployes = employeDao.findByAllButRH();
		return listEmployes;
	}
	//Fonction permettant de renvoyer un employé en particulier grâce à son mail
	public Employe getEmploye(String email) {
		return employeDao.findByEmail(email);
	}
	//Fonction permettant de vérifier les identifiants 
	public Employe checkCredentials(String email, String password) {
		return employeDao.checkCredentials(email, password);
	}
	//Fonction permettant de renvoyer la team d'un employé leader
	public List<Employe> getMyTeam(String mail){
		return employeDao.findMyTeam(mail);
	}
	
	//Fonction permettant d'ajotuer un employe
	public void ajoutEmploye(Employe emp) {
		employeDao.addEmploye(emp);
	}
	//Fonction permettant de modifier un employe 
	public void updateEmploye(Employe emp,String mail) {
		employeDao.modifyEmploye(emp,mail);
	}
	//Fonction permettant de mettre à jour un employé
	public void updatePassword(String emp,String pwd) {
		this.employeDao.updatePassword(emp,pwd);
	}
	//Fonction permettant de mettre à jour l'adresse d'un employé
	public void updateAddress(String emp,String addr) {
		this.employeDao.updateAddress(emp,addr);
	}
	//Fonction permettant de supprimer un employé 
	public void deleteEmploye(String mail) {
		employeDao.eraseEmploye(mail);
	}
	//Fonction permettant de renvoyer les employés filtrés
	public List<Employe> getFilteredEmploye(String poste, String team, String mail) {
		return employeDao.findFilteredEmploye(poste,team,mail);
	}
	
	//Fonction permettant de renvoyer tous les emails des employés
	public List<String> getAllMail(){
		return employeDao.findAllMail();
	}
}
