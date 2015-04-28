package br.edu.popjudge.domain;

public class Score {
	private int id_problem;
	private int score;
	
	public Score() {}
	
	public Score(Integer id_problem, Integer score) {
		this.id_problem = id_problem;
		this.score = score;
	}

	public Integer getId_problem() {
		return id_problem;
	}

	public void setId_problem(Integer id_problem) {
		this.id_problem = id_problem;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Override
	public boolean equals(Object obj) {
		Score score = (Score)obj;
		return score.id_problem == id_problem;
	}

}
