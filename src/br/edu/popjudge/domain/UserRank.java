package br.edu.popjudge.domain;

public class UserRank {
	String username;

	int points;
	int problem1;// Represents the moment that the user solved the problem, in
					// milliseconds.
	int problem2;
	int problem3;
	int problem4;
	int problem5;

	public UserRank(String userId, int points, int problem1, int problem2,
			int problem3, int problem4, int problem5) {
		super();
		this.username = userId;
		this.points = points;
		this.problem1 = problem1;
		this.problem2 = problem2;
		this.problem3 = problem3;
		this.problem4 = problem4;
		this.problem5 = problem5;
	}
	
	public UserRank() {
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}

	public int getProblem1() {
		return problem1;
	}

	public void setProblem1(int problem1) {
		this.problem1 = problem1;
	}

	public int getProblem2() {
		return problem2;
	}

	public void setProblem2(int problem2) {
		this.problem2 = problem2;
	}

	public int getProblem3() {
		return problem3;
	}

	public void setProblem3(int problem3) {
		this.problem3 = problem3;
	}

	public int getProblem4() {
		return problem4;
	}

	public void setProblem4(int problem4) {
		this.problem4 = problem4;
	}

	public int getProblem5() {
		return problem5;
	}

	public void setProblem5(int problem5) {
		this.problem5 = problem5;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
}
