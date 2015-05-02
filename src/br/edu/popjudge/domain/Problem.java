package br.edu.popjudge.domain;

import java.io.File;

public class Problem {
	private int idProblem;
	private int scorePoints;
	private String title;
	private long timeLimit;
	private File dir;
	private File testCase;
	private String author;
	private String university;
	private String inputDescription;
	private String outputDescription;
	private String description;
	private String notes;
	private String inputSample1;
	private String inputSample2;
	private String inputSample3;
	private String outputSample1;
	private String outputSample2;
	private String outputSample3;

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
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getInputDescription() {
		return inputDescription;
	}

	public void setInputDescription(String inputDescription) {
		this.inputDescription = inputDescription;
	}

	public String getOutputDescription() {
		return outputDescription;
	}

	public void setOutputDescription(String outputDescription) {
		this.outputDescription = outputDescription;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getInputSample1() {
		return inputSample1;
	}

	public void setInputSample1(String inputSample1) {
		this.inputSample1 = inputSample1;
	}

	public String getInputSample2() {
		return inputSample2;
	}

	public void setInputSample2(String inputSample2) {
		this.inputSample2 = inputSample2;
	}

	public String getInputSample3() {
		return inputSample3;
	}

	public void setInputSample3(String inputSample3) {
		this.inputSample3 = inputSample3;
	}

	public String getOutputSample1() {
		return outputSample1;
	}

	public void setOutputSample1(String outputSample1) {
		this.outputSample1 = outputSample1;
	}

	public String getOutputSample2() {
		return outputSample2;
	}

	public void setOutputSample2(String outputSample2) {
		this.outputSample2 = outputSample2;
	}

	public String getOutputSample3() {
		return outputSample3;
	}

	public void setOutputSample3(String outputSample3) {
		this.outputSample3 = outputSample3;
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
