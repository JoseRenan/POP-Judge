package br.edu.popjudge.bean;

import java.io.IOException;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.edu.popjudge.database.dao.ClarificationDAO;
import br.edu.popjudge.domain.Clarification;
import br.edu.popjudge.domain.User;

@ManagedBean(name = "clarification")
public class ClarificationBean {
	
	private Clarification clarification = new Clarification();
	
	public Clarification getClarification() {
		return clarification;
	}

	public void setClarification(Clarification clarification) {
		this.clarification = clarification;
	}

	public void doIssue() throws SQLException, IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		clarification.setUser((User) session.getAttribute("user"));
		
		ClarificationDAO cd = new ClarificationDAO();
		
		cd.insert(this.clarification);
		
		this.clarification = new Clarification();
		
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Enviado!",""));
	}
	
	public void replyIssue() throws SQLException {
		ClarificationDAO cd = new ClarificationDAO();
		cd.update(this.clarification);
		this.clarification = new Clarification();
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Respondido!",""));
	}
	
}
