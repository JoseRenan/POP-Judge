package br.edu.popjudge.bean;

import javax.annotation.ManagedBean;

@ManagedBean(value = "clarification")
public class ClarificationBean {
	private int idClarification;
	private int idUser;
	private int idProblem;
	private String issue;
	private String answer;
	
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

	public void doIssue() {
		
	}
	
	public void replyIssue() {
		
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
}
