package br.edu.popjudge.domain;

public class Score {
	private int tries;
	private int passedTime;
	
	public Score() {}
	
	public Score(int tries, int passedTime) {
		super();
		this.tries = tries;
		this.passedTime = passedTime;
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
