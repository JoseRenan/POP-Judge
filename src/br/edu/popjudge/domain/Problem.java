package br.edu.popjudge.domain;

import java.io.File;

public class Problem {
	private int idProblem;
	private int scorePoints;
	private String title;
	private long timeLimit;
	private File dir;
	private File testCase;

	public Problem() { }

	public Problem(int idProblem, int scorePoints, String title,
			long timeLimit, File dir) {
		super();
		this.idProblem = idProblem;
		this.scorePoints = scorePoints;
		this.title = title;
		this.timeLimit = timeLimit;
		this.dir = dir;
	}
	
	public Problem(int idProblem, int scorePoints, String title,
			long timeLimit, File dir, File testCase) {
		super();
		this.idProblem = idProblem;
		this.scorePoints = scorePoints;
		this.title = title;
		this.timeLimit = timeLimit;
		this.dir = dir;
		this.testCase = testCase;
	}

	public int getIdProblem() {
		return this.idProblem;
	}

	public void setIdProblem(int idProblem) {
		this.idProblem = idProblem;
	}

	public int getScorePoints() {
		return this.scorePoints;
	}

	public void setScorePoints(int scorePoints) {
		this.scorePoints = scorePoints;
	}

	public String getTitle() {
		return this.title;
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

	public File getDir() {
		return this.dir;
	}

	public void setDir(File dir) {
		this.dir = dir;
	}

	public File getTestCase() {
		return this.testCase;
	}

	public void setTestCase(File testCase) {
		this.testCase = testCase;
	}
}
