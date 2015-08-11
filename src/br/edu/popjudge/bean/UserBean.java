package br.edu.popjudge.bean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

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

	public String login(){
		UserService service = new UserService();
		
		if (!service.login(this.user)){
			this.user.setPassword("");
			GenericBean.setMessage("Usuário ou senha incorretos", FacesMessage.SEVERITY_ERROR);
			return "";
		}
		
		return "index.xhtml?faces-redirect=true";
	}

	public String logout(){
		
		UserService service = new UserService();
		service.logout();
		
		return "indexUser.xhtml?faces-redirect=true";
	}

	public void newUser() throws SQLException, IOException, NoSuchAlgorithmException {
		try {
			
			String home = System.getProperty("user.home");
			user.setDir(home + "/POPJudge/users/" + this.user.getUsername());
			
			UserDAO userDao = new UserDAO();
			userDao.insert(user);
			
			File userDirectory = new File(this.user.getDir());
			userDirectory.mkdirs();
			
			RankingService rankingService = new RankingService();
			rankingService.insertUser(this.user);
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("/POP-Judge/");
			
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Usuário criado com sucesso", ""));
		} catch (SQLException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Nome de login já utilizado", ""));
		}
	}

	public void changePassword() throws SQLException, UnsupportedEncodingException, NoSuchAlgorithmException {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext()
				.getSession(true);
		
		User u = (User) session.getAttribute("user");
		
		u.setPassword(this.user.getPassword());
		UserDAO ud = new UserDAO();
		ud.update(u);
		
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Senha modificada com sucesso", ""));
	}

}
