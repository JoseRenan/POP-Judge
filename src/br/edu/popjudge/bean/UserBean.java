package br.edu.popjudge.bean;

import java.io.IOException;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.edu.popjudge.database.dao.UserDAO;
import br.edu.popjudge.domain.User;

@ManagedBean
public class UserBean {

	private User user = new User();

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String login() throws IOException, SQLException {
		UserDAO ud = new UserDAO();
		User u = ud.get(this.user.getUsername());

		if (u == null || !u.getPassword().equals(this.user.getPassword())) {
			this.user.setUsername(null);
			this.user.setPassword(null);

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário ou senha incorretos", ""));
			return "";
		}

		if (this.user.username.equalsIgnoreCase("Admin")) {
			logging(u);
			this.user.setUsername(null);
			this.user.setPassword(null);
			return "/webapp/admin/indexAdmin.xhtml?faces-redirect=true";

		}

		if (u.getUsername().equalsIgnoreCase(this.user.username)) {
			logging(u);
			this.user.setUsername(null);
			this.user.setPassword(null);
			return "/webapp/user/indexUser.xhtml?faces-redirect=true";
		}
		
		return "";
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
			UserDAO ud = new UserDAO();
			ud.insert(user);
			//TODO adicionar o bichin na tabela ranking.

			this.user.setUsername(null);
			this.user.setPassword(null);

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
