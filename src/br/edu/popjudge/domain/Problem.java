package br.edu.popjudge.domain;

import java.io.File;

public class Problem {
	private int idProblem;
	private int scorePoints;
	private String title;
	private long timeLimit;
	private File input;
	private File output;

	public Problem() {

	}

	public Problem(int idProblem, int scorePoints, String title,
			long timeLimit, File input, File output) {
		super();
		this.idProblem = idProblem;
		this.scorePoints = scorePoints;
		this.title = title;
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

	public int getScorePoints() {
		return scorePoints;
	}

	public void setScorePoints(int scorePoints) {
		this.scorePoints = scorePoints;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
