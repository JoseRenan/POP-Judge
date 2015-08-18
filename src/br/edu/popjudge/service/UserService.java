package br.edu.popjudge.service;

import java.sql.SQLException;

import br.edu.popjudge.bean.GenericBean;
import br.edu.popjudge.database.dao.UserDAO;
import br.edu.popjudge.domain.User;

public class UserService {
	
	public boolean login(User user) {
		
		UserDAO ud = new UserDAO();
		User u = null;
		
		try {
			u = ud.get(user.getUsername());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (u != null &&
			u.getUsername().equalsIgnoreCase(user.getUsername()) && 
			u.getPassword().equals(user.getPassword())) {
			
			GenericBean.setSessionValue("user", u);
			
			return true;
		}
		
		return false;
	}
	
	public void logout(){
		GenericBean.invalidateSession();
	}
	
	public void updateUser(User user){
		
		UserDAO userDAO = new UserDAO();
		
		try {
			userDAO.update(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
