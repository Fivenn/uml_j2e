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
	
	public Employe getEmploye(String email) {
		return employeDao.findByEmail(email);
	}

	public boolean checkCredentials(String email, String password) {
		return employeDao.checkCredentials(email, password);
	}
	public List<Employe> getEmployesByTitle(String searchText) {
		List<Employe> listEmployes = employeDao.findByTitle(searchText);
		return listEmployes;
	}
}
