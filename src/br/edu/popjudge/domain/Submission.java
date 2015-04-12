package br.edu.popjudge.domain;

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
	private String veredict;
	private String dir;
	private String sourceName;

	private User user;
	private Problem problem;
	private Language language;

	public Submission(int idSubmission, String veredict, String dir, User user, String sourceName,
			Problem problem, Language language) {
		super();
		this.idSubmission = idSubmission;
		this.veredict = veredict;
		this.dir = dir;
		this.sourceName = sourceName;
		this.user = user;
		this.problem = problem;
		this.language = language;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public int getIdSubmission() {
		return idSubmission;
	}

	public void setIdSubmission(int idSubmission) {
		this.idSubmission = idSubmission;
	}

	public String getVeredict() {
		return veredict;
	}

	public void setVeredict(String veredict) {
		this.veredict = veredict;
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
}
