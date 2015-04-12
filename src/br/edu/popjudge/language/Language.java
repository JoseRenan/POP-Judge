package br.edu.popjudge.language;

import br.edu.popjudge.domain.Submission;
import br.edu.popjudge.exceptions.CompilationErrorException;
import br.edu.popjudge.exceptions.TimeLimitExceededException;

public abstract class Language {
	Runtime runtime = Runtime.getRuntime();
	
	public abstract boolean compile(Submission submission) throws CompilationErrorException;
	public abstract boolean execute(Submission submission) throws TimeLimitExceededException;
}
