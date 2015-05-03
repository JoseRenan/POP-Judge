package br.edu.popjudge.bean;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

	public String createProblem() throws SQLException, IOException {
		String home = System.getProperty("user.home");
		this.problem.setDir(new File(home + "/POPJudge/problems/"+ this.problem.getTitle()));
		
		new File (this.problem.getDir() + "/" + "description.txt").getParentFile().mkdirs();
        new File (this.problem.getDir() + "/" + "inputDescription.txt").getParentFile().mkdirs();
        new File (this.problem.getDir() + "/" + "outputDescription.txt").getParentFile().mkdirs();
        new File (this.problem.getDir() + "/" + "inputSample1.txt").getParentFile().mkdirs();
        new File (this.problem.getDir() + "/" + "inputSample2.txt").getParentFile().mkdirs();
        new File (this.problem.getDir() + "/" + "inputSample3.txt").getParentFile().mkdirs();
        new File (this.problem.getDir() + "/" + "ouputSample1.txt").getParentFile().mkdirs();
        new File (this.problem.getDir() + "/" + "ouputSample2.txt").getParentFile().mkdirs();
        new File (this.problem.getDir() + "/" + "ouputSample3.txt").getParentFile().mkdirs();
        new File (this.problem.getDir() + "/" + "notes.txt").getParentFile().mkdirs();
        new File (this.problem.getDir() + "/" + "header.txt").getParentFile().mkdirs();
		
		createFiles(this.problem.getDir() + "/" + "description.txt", this.problem.getDescription());
		createFiles(this.problem.getDir() + "/" + "inputDescription.txt", this.problem.getInputDescription());
		createFiles(this.problem.getDir() + "/" + "outputDescription.txt", this.problem.getOutputDescription());
		createFiles(this.problem.getDir() + "/" + "inputSample1.txt", this.problem.getInputSample1());
		createFiles(this.problem.getDir() + "/" + "inputSample2.txt", this.problem.getInputSample2());
		createFiles(this.problem.getDir() + "/" + "inputSample3.txt", this.problem.getInputSample3());
		createFiles(this.problem.getDir() + "/" + "ouputSample1.txt", this.problem.getOutputSample1());
		createFiles(this.problem.getDir() + "/" + "ouputSample2.txt", this.problem.getOutputSample2());
		createFiles(this.problem.getDir() + "/" + "ouputSample3.txt", this.problem.getOutputSample3());
		createFiles(this.problem.getDir() + "/" + "notes.txt", this.problem.getNotes());
		createFiles(this.problem.getDir() + "/" + "header.txt", this.problem.getTitle() + "\n" +
																this.problem.getAuthor() + "\n" +
																this.problem.getUniversity() + "\n" +
																this.problem.getTimeLimit() + "\n" +
																this.problem.getScorePoints());
		
		ProblemDAO pd = new ProblemDAO();
		pd.insert(this.problem);
		FacesMessage message = new FacesMessage("Criado com sucesso", "");
		FacesContext.getCurrentInstance().addMessage(null, message);
		
		return "addProblem";
	}
	
	private static void createFiles(String dir, String content) throws IOException{
		FileWriter arq = new FileWriter(dir);
		PrintWriter gravarArq = new PrintWriter(arq);
		
		gravarArq.printf("%s", content);
		
		gravarArq.close();
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
