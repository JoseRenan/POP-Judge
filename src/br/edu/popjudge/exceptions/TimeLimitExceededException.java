package br.edu.popjudge.exceptions;

public class TimeLimitExceededException extends Exception {

	private static final long serialVersionUID = 1L;

	public TimeLimitExceededException(String content){
		super(content);
	}
}
