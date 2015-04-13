package br.edu.popjudge.language;

import br.edu.popjudge.bean.SubmissionBean;
import br.edu.popjudge.exceptions.CompilationErrorException;
import br.edu.popjudge.exceptions.TimeLimitExceededException;

public abstract class Language {
	Runtime runtime;
	int idLanguage;
	String name;

	public Language(int idLanguage, String name) {
		super();
		this.runtime = Runtime.getRuntime();
		this.idLanguage = idLanguage;
		this.name = name;
	}
	
	@Override
	public String toString(){
		return this.name + " " + this.idLanguage;
	}

	public abstract boolean compile(SubmissionBean submission)
			throws CompilationErrorException;

	public abstract boolean execute(SubmissionBean submission)
			throws TimeLimitExceededException;

	public int getIdLanguage() {
		return idLanguage;
	}

	public void setIdLanguage(int idLanguage) {
		this.idLanguage = idLanguage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
