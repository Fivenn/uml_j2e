package org.databaseManage;

import java.util.List;

import org.model.Demand;
import org.model.Employe;

public class EmployeService {
	
	// choose the DAO data source : DB or Mock
	private EmployeDAOImpl employeDao = new EmployeDAOImpl();  

	public List<Employe> getAllEmployes() {
		List<Employe> listEmployes = employeDao.findByAll();
		return listEmployes;
	}
	
	public List<Employe> getAllEmployesButRH() {
		List<Employe> listEmployes = employeDao.findByAllButRH();
		return listEmployes;
	}
	
	public Employe getEmploye(String email) {
		return employeDao.findByEmail(email);
	}

	public Employe checkCredentials(String email, String password) {
		return employeDao.checkCredentials(email, password);
	}
	
	public List<Employe> getMyTeam(String mail){
		return employeDao.findMyTeam(mail);
	}
	
	
	public void ajoutEmploye(Employe emp) {
		employeDao.addEmploye(emp);
	}
	
	public void updateEmploye(Employe emp,String mail) {
		employeDao.modifyEmploye(emp,mail);
	}
	
	public void updatePassword(String emp,String pwd) {
		this.employeDao.updatePassword(emp,pwd);
	}
	
	public void updateAddress(String emp,String addr) {
		this.employeDao.updateAddress(emp,addr);
	}
	
	public void deleteEmploye(String mail) {
		employeDao.eraseEmploye(mail);
	}
	
	public List<Employe> getFilteredEmploye(String poste, String team, String mail) {
		return employeDao.findFilteredEmploye(poste,team,mail);
	}
	
	public List<String> getAllMail(){
		return employeDao.findAllMail();
	}
}
