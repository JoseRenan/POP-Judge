package br.edu.popjudge.bean;

import java.io.File;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.edu.popjudge.database.dao.ProblemDAO;
import br.edu.popjudge.domain.Problem;

@ManagedBean(name = "problem")
@ViewScoped
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

	public String createProblem() throws SQLException {
		String home = System.getProperty("user.home");
		this.problem.setDir(new File(home + "/POPJudge/problems/"+ this.problem.getTitle()));
		
		ProblemDAO pd = new ProblemDAO();
		pd.insert(this.problem);
		FacesMessage message = new FacesMessage("Criado com sucesso", "");
		FacesContext.getCurrentInstance().addMessage(null, message);
		
		return "addProblem";
	}
	
	public void editProblem() throws SQLException {		
		ProblemDAO pd = new ProblemDAO();
		pd.update(this.selectedProblem);
		FacesMessage message = new FacesMessage("Editado com sucesso", "");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public void deleteProblem() throws SQLException {
		ProblemDAO pd = new ProblemDAO();
		pd.delete(this.selectedProblem.getIdProblem());
		FacesMessage message = new FacesMessage("Apagado com sucesso", "");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
