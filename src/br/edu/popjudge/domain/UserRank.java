package br.edu.popjudge.domain;

import java.util.Map;

public class UserRank {
	private String username;
	private Map<Integer, Score> problems;
	private int sumAccepted;
	private long sumTime;
	private int sumTries;
	
	public UserRank(String username, Map<Integer, Score> problems) {
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
		for(Map.Entry<Integer, Score> entry : this.problems.entrySet()){
			result += entry.getValue().getTries();
		}
		return result;
	}
	
	private int calculateSumAccepted(){
		int result = 0;
		for(Map.Entry<Integer, Score> entry : this.problems.entrySet()){
			if(entry.getValue().getTries() > 0)
				result += entry.getValue().getTries();
		}
		return result;
	}
	
	private long calculateSumTime(){
		long result = 0;
		for(Map.Entry<Integer, Score> entry : this.problems.entrySet()){
			if(entry.getValue().getPassedTime() > 0)
				result += entry.getValue().getPassedTime();
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

	public long getSumTime() {
		return sumTime;
	}

	public void setSumTime(long sumTime) {
		this.sumTime = sumTime;
	}

	public Map<Integer, Score> getProblems() {
		return problems;
	}

	public void setProblems(Map<Integer, Score> problems) {
		this.problems = problems;
	}

}
