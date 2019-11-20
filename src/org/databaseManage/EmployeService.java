package org.databaseManage;

import java.util.List;

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
	
	public void updateEmploye(Employe emp) {
		employeDao.modifyEmploye(emp);
	}
	
	public void deleteEmploye(String mail) {
		employeDao.eraseEmploye(mail);
	}
}
