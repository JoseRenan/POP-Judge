package br.edu.popjudge.bean;

import java.io.File;
import java.io.Serializable;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.edu.popjudge.database.dao.UserDAO;
import br.edu.popjudge.domain.User;
import br.edu.popjudge.service.RankingService;
import br.edu.popjudge.service.UserService;

@ManagedBean (name = "userBean")
@ViewScoped
public class UserBean implements Serializable{
	
	private static final long serialVersionUID = -3808221606065195943L;
	
	private User user = new User();

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void login(){
		UserService service = new UserService();
		
		if (!service.login(this.user)){
			this.user.setPassword("");
			GenericBean.setMessage("Usuário ou senha incorretos", FacesMessage.SEVERITY_ERROR);
		} else {
			GenericBean.redirectPage("/POP-Judge/");
		}
	}

	public void logout(){
		
		UserService service = new UserService();
		service.logout();
		
		GenericBean.redirectPage("/POP-Judge/");
	}
	
	
	//TODO - Método ainda não passado para o UserService pois o cadastro agora não é mais feito apenas com usuário e senha, 
	//precisamos tratar melhor os erros e mensagens lançadas
	public void newUser(){
		try {
			
			String home = System.getProperty("user.home");
			user.setDir(home + "/POPJudge/users/" + this.user.getUsername());
			
			UserDAO userDao = new UserDAO();
			userDao.insert(user);
			
			File userDirectory = new File(this.user.getDir());
			userDirectory.mkdirs();
			
			RankingService rankingService = new RankingService();
			rankingService.insertUser(this.user);
			
			GenericBean.setMessage("Usuário criado com sucesso", FacesMessage.SEVERITY_INFO);
			
			GenericBean.redirectPage("/POP-Judge/");
			
		} catch (SQLException e) {
			e.printStackTrace();
			GenericBean.setMessage("Nome de login já utilizado", FacesMessage.SEVERITY_ERROR);
		}
	}

	public void changePassword() {
		
		User user = (User) GenericBean.getSessionValue("user");
		
		user.setPassword(this.user.getPassword());
		
		UserService service = new UserService();
		service.updateUser(user);
		
		this.user = new User();
		
		GenericBean.setMessage("Senha modificada com sucesso", FacesMessage.SEVERITY_INFO);
	}

}
