package br.edu.popjudge.domain;

public class Score {
	private int idProblem;
	private int tries;
	private int passedTime;
	
	public Score() {}
	
	public Score(int idProblem, int tries, int passedTime) {
		super();
		this.idProblem = idProblem;
		this.tries = tries;
		this.passedTime = passedTime;
	}

	public int getIdProblem() {
		return idProblem;
	}

	public void setIdProblem(int idProblem) {
		this.idProblem = idProblem;
	}

	public int getTries() {
		return tries;
	}

	public void setTries(int tries) {
		this.tries = tries;
	}

	public int getPassedTime() {
		return passedTime;
	}

	public void setPassedTime(int passedTime) {
		this.passedTime = passedTime;
	}
}
