package br.edu.popjudge.bean;

import javax.faces.bean.ManagedBean;

import br.edu.popjudge.domain.Problem;

@ManagedBean(name = "problem")
public class ProblemBean {

	private Problem problem = new Problem();
	private Problem selectedProblem;

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	
	public Problem getSelectedProblem() {
		return selectedProblem;
	}

	public void setSelectedProblem(Problem selectedProblem) {
		this.selectedProblem = selectedProblem;
	}

	public void createProblem() {
		
	}
	
	public void editProblem() {
		
	}
	
	public void deleteProblem() {
		
	}
}
