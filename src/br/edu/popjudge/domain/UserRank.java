package br.edu.popjudge.domain;

import java.util.ArrayList;

public class UserRank {
	private String username;
	private ArrayList<Score> problems;
	private int totalScore;


	public UserRank(String username, ArrayList<Score> problems) {
		super();
		this.username = username;
		this.problems = problems;
		this.totalScore = calculateScore();
	}
	
	public UserRank() {
	}

	private int calculateScore(){
		//TODO
		return 0;
	}
	
	public int getScore() {
		return totalScore;
	}

	public void setScore() {
		this.totalScore = calculateScore();
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}

	public ArrayList<Score> getProblems() {
		return problems;
	}

	public void setProblems(ArrayList<Score> problems) {
		this.problems = problems;
	}
	
}
