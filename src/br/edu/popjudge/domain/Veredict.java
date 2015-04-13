package br.edu.popjudge.domain;

public enum Veredict {
	ACCEPTED_ANSWER(1, "Accepted", "AC"),
	WRONG_ANSWER(2, "Wrong Answer", "WA"),
	TIME_LIMIT_EXCEEDED(3, "Time Limit Exceeded", "TLE"),
	COMPILATION_ERROR(4, "Compilation Error", "CE"),
	SUBMISSION_ERROR(5, "Submission Error", "SE");
	
	private final String rotulo1;
	private final String rotulo2;
	private final int valor;
	
	Veredict(int val, String str1, String str2){
		valor = val;
		rotulo1 = str1;
		rotulo2 = str2;
	}
	public int getValor() {
		return valor;
	}
	
	public String getRotulo1() {
		return rotulo1;
	}
	
	public String getRotulo2() {
		return rotulo2;
	}
	
	@Override
	public String toString(){
		return this.getRotulo1();
	}
}
