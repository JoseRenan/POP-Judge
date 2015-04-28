package br.edu.popjudge.domain;

import java.io.File;
import java.sql.Timestamp;

import br.edu.popjudge.language.Language;

/*
 * The storage of the submissions will work in the following way:
 * There will be a folder for each user, in which his submissions
 * will be stored. For each submission will be created a new directory
 * in the user's folder,
 * named with its submission id. Inside of that folder will be the source file,
 * the executable, the compiler and execution scripts, and the output
 * generated. 
 * */

public class Submission {
	private int idSubmission;
	private User user;
	private Problem problem;
	private Language language;
	private File file;
	private String dir;
	private String fileName;
	private Timestamp timestamp;
	private String veredict;
	
	public Submission() {
	}

	public Submission(int idSubmission, User user, Problem problem,
			Language language, File file, Timestamp timestamp, String veredict) {
		super();
		this.idSubmission = idSubmission;
		this.user = user;
		this.problem = problem;
		this.language = language;
		this.file = file;
		this.timestamp = timestamp;
		this.veredict = veredict;
	}

	public int getIdSubmission() {
		return idSubmission;
	}

	public void setIdSubmission(int idSubmission) {
		this.idSubmission = idSubmission;
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

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getVeredict() {
		return veredict;
	}

	public void setVeredict(String veredict) {
		this.veredict = veredict;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
