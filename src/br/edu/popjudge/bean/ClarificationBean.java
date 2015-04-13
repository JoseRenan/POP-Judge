package br.edu.popjudge.bean;

import java.io.IOException;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.edu.popjudge.database.dao.ClarificationDAO;

@ManagedBean(name = "clarification")
public class ClarificationBean {
	private int idClarification;
	private int idUser;
	private int idProblem;
	private String issue;
	private String answer;
	private ClarificationBean selectedClarification;
	
	public ClarificationBean(int idClarification, int idUser, int idProblem,
			String issue, String answer) {
		super();
		this.idClarification = idClarification;
		this.idUser = idUser;
		this.idProblem = idProblem;
		this.issue = issue;
		this.answer = answer;
	}
	
	public ClarificationBean() {
	}

	public void doIssue() throws SQLException, IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		int id = (Integer) session.getAttribute("idUser");
		
		ClarificationBean c = new ClarificationBean();
		c.setIssue(this.issue);
		c.setIdProblem(this.idProblem);
		c.setIdUser(id);
		
		ClarificationDAO cd = new ClarificationDAO();
		
		cd.insert(c);
		
		this.issue = null;
		this.idProblem = 0;
		
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Enviado!",""));
	}
	
	public void replyIssue() throws SQLException {
		this.selectedClarification.answer = this.answer;
		ClarificationDAO cd = new ClarificationDAO();
		cd.update(this.selectedClarification);
		this.answer = null;
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Respondido!",""));
	}
	
	public int getIdClarification() {
		return idClarification;
	}
	
	public void setIdClarification(int idClarification) {
		this.idClarification = idClarification;
	}
	
	public int getIdUser() {
		return idUser;
	}
	
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	public int getIdProblem() {
		return idProblem;
	}
	
	public void setIdProblem(int idProblem) {
		this.idProblem = idProblem;
	}
	
	public String getIssue() {
		return issue;
	}
	
	public void setIssue(String issue) {
		this.issue = issue;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public ClarificationBean getSelectedClarification() {
		return selectedClarification;
	}

	public void setSelectedClarification(ClarificationBean selectedClarification) {
		this.selectedClarification = selectedClarification;
	}
}
