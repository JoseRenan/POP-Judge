package br.edu.popjudge.bean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.edu.popjudge.database.dao.UserDAO;
import br.edu.popjudge.domain.User;
import br.edu.popjudge.service.RankingService;

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

	public String login() throws IOException, SQLException {
		
		String redirect = null;
		
		UserDAO ud = new UserDAO();
		User u = ud.get(this.user.getUsername());

		if (u == null || !u.getPassword().equals(this.user.getPassword())) {
			
			FacesContext.getCurrentInstance().addMessage(
					null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							"Usuário ou senha incorretos", ""));
			return redirect;
		}

		if (this.user.username.equalsIgnoreCase("Admin")) {
			
			logging(u);
			
			return "/webapp/admin/indexAdmin.xhtml?faces-redirect=true";
		}

		if (u.getUsername().equalsIgnoreCase(this.user.username)) {
			
			logging(u);
			
			return "/webapp/user/indexUser.xhtml?faces-redirect=true";
		}
		
		return redirect;
	}

	public void logout() throws IOException {
		invalidateSession();
		FacesContext.getCurrentInstance().getExternalContext().redirect("/POP-Judge/");
	}
	
	private static void logging (User u) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context
				.getExternalContext().getSession(true);
		session.setAttribute("user", u);
	}
	
	private static void invalidateSession() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.removeAttribute("user");
		session.invalidate();
	}

	public void newUser() throws SQLException, IOException {
		try {
			String home = System.getProperty("user.home");
			user.setDir(home + "/POPJudge/users/" + this.user.getUsername());
			
			UserDAO userDao = new UserDAO();
			userDao.insert(user);
			
			File userDirectory = new File(this.user.getDir());
			userDirectory.mkdirs();
			
			RankingService rankingService = new RankingService();
			rankingService.insertUser(this.user);
			
			this.user.setUsername(null);
			this.user.setPassword(null);
			
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

	public void changePassword() throws SQLException {
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
