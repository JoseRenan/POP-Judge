package br.edu.popjudge.domain;

import java.sql.Timestamp;

public class Clarification {
	private int idClarification;
	private User user;
	private Problem problem;
	private String issue;
	private String answer;
	private Timestamp timestamp;
	
	public Clarification() {}

	public Clarification(int idClarification, User user, Problem problem,
			String issue, String answer, Timestamp timestamp) {
		super();
		this.idClarification = idClarification;
		this.user = user;
		this.problem = problem;
		this.issue = issue;
		this.answer = answer;
		this.timestamp = timestamp;
	}

	public int getIdClarification() {
		return idClarification;
	}

	public void setIdClarification(int idClarification) {
		this.idClarification = idClarification;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
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

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}
