package br.edu.popjudge.domain;



public class Score{
	private int tries;
	private long passedTime;
	
	public Score() {}
	
	public Score(int tries, long passedTime) {
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

	public long getPassedTime() {
		return passedTime;
	}

	public void setPassedTime(long passedTime) {
		this.passedTime = passedTime;
	}
	
}
