package br.edu.popjudge.exceptions;

public class CompilationErrorException extends Exception {

	private static final long serialVersionUID = 1L;

	public CompilationErrorException(String content){
		super(content);
	}
}
