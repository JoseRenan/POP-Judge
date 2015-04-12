package br.edu.popjudge.domain;

import java.io.File;

public class Problem {
	private int idProblem;
	private long timeLimit;
	private File input;
	private File output;
	
	public Problem(int idProblem, long timeLimit, File input, File output) {
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
	
	public File getInput() {
		return input;
	}
	
	public void setInput(File input) {
		this.input = input;
	}
	
	public File getOutput() {
		return output;
	}
	
	public void setOutput(File output) {
		this.output = output;
	}
}
