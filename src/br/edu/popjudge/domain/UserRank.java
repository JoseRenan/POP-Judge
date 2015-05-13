package br.edu.popjudge.domain;

import java.util.ArrayList;

public class UserRank {
	private String username;
	private ArrayList<Score> problems;
	private int sumAccepted;
	private int sumTime;
	private int sumTries;
	
	public UserRank(String username, ArrayList<Score> problems) {
		super();
		this.username = username;
		this.problems = problems;
		this.setSumAccepted(this.calculateSumAccepted());
		this.setSumTime(this.calculateSumTime());
		this.setSumTries(this.calculateSumTries());
	}
	
	public int problemsCount(){
		return problems.size();
	}
	
	private int calculateSumTries(){
		int result = 0;
		for(Score score : this.problems){
			result += score.getTries();
		}
		return result;
	}
	
	private int calculateSumAccepted(){
		int result = 0;
		for(Score score : this.problems){
			if(score.getTries() > 0)
				result += score.getTries();
		}
		return result;
	}
	
	private int calculateSumTime(){
		int result = 0;
		for(Score score : this.problems){
			if(score.getPassedTime() > 0)
				result += score.getPassedTime();
		}
		return result;
	}
	
	public UserRank() {

	}
	
	public int getSumTries() {
		return sumTries;
	}

	public void setSumTries(int sumTries) {
		this.sumTries = sumTries;
	}

	public int getSumAccepted() {
		return sumAccepted;
	}

	public void setSumAccepted(int sumAccepted) {
		this.sumAccepted = sumAccepted;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public int getSumTime() {
		return sumTime;
	}

	public void setSumTime(int sumTime) {
		this.sumTime = sumTime;
	}

	public ArrayList<Score> getProblems() {
		return problems;
	}

	public void setProblems(ArrayList<Score> problems) {
		this.problems = problems;
	}

}
