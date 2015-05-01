package br.edu.popjudge.bean;

import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import br.edu.popjudge.database.dao.ProblemDAO;
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
	
	public void editProblem() throws SQLException {
		ProblemDAO pd = new ProblemDAO();
		pd.update(this.selectedProblem);
		FacesMessage message = new FacesMessage("Editado com sucesso", "");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public void deleteProblem() {
		
	}
}
