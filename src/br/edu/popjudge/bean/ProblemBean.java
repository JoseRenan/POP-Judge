package br.edu.popjudge.bean;

import javax.faces.bean.ManagedBean;

@ManagedBean(name = "problem")
public class ProblemBean {

	private int idProblem;
	long timeLimit;
	String input;
	String output;

	public ProblemBean(int idProblem, long timeLimit, String input,
			String output) {
		super();
		this.idProblem = idProblem;
		this.timeLimit = timeLimit;
		this.input = input;
		this.output = output;
	}
	
	
	public int getIdProblem() {
		return idProblem;
	}

	public void setIdProblem(int idProblem) {
		this.idProblem = idProblem;
	}

	public long getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(long timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

}
