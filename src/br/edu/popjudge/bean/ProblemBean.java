package br.edu.popjudge.bean;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.UploadedFile;

import br.edu.popjudge.database.dao.ClarificationDAO;
import br.edu.popjudge.database.dao.ProblemDAO;
import br.edu.popjudge.database.dao.SubmissionDAO;
import br.edu.popjudge.domain.Problem;
import br.edu.popjudge.service.RankingService;

@ManagedBean(name = "problem")
@ViewScoped
public class ProblemBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3184756054376147958L;
	
	private Problem problem = new Problem();
	private Problem selectedProblem;
	private UploadedFile testcases;	
	
	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	
	public UploadedFile getTestcases() {
		return testcases;
	}

	public void setTestcases(UploadedFile testcases) {
		this.testcases = testcases;
	}

	public Problem getSelectedProblem() {
		return selectedProblem;
	}

	public void setSelectedProblem(Problem selectedProblem) {
		this.selectedProblem = selectedProblem;
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		session.setAttribute("Selectedproblem", this.selectedProblem);
	}

	public String createProblem() throws SQLException, IOException {
		String home = System.getProperty("user.home");

		ProblemDAO problemDao = new ProblemDAO();
		this.problem.setDir(new File(""));

		problem.setIdProblem(problemDao.insertGet(this.problem));
		this.problem.setDir(new File(home + "/POPJudge/problems/" + this.problem.getIdProblem()));
		problemDao.update(this.problem);
		
		RankingService rankingService = new RankingService();
		rankingService.insertProblem(this.problem);
		
		
		new File (this.problem.getDir() + "/" + "description.txt").getParentFile().mkdirs();
        new File (this.problem.getDir() + "/" + "inputDescription.txt").getParentFile().mkdirs();
        new File (this.problem.getDir() + "/" + "outputDescription.txt").getParentFile().mkdirs();
        new File (this.problem.getDir() + "/" + "inputSample1.txt").getParentFile().mkdirs();
        new File (this.problem.getDir() + "/" + "inputSample2.txt").getParentFile().mkdirs();
        new File (this.problem.getDir() + "/" + "inputSample3.txt").getParentFile().mkdirs();
        new File (this.problem.getDir() + "/" + "outputSample1.txt").getParentFile().mkdirs();
        new File (this.problem.getDir() + "/" + "outputSample2.txt").getParentFile().mkdirs();
        new File (this.problem.getDir() + "/" + "outputSample3.txt").getParentFile().mkdirs();
        new File (this.problem.getDir() + "/" + "notes.txt").getParentFile().mkdirs();
        new File (this.problem.getDir() + "/" + "header.txt").getParentFile().mkdirs();
		
		createFiles(this.problem.getDir() + "/" + "description.txt", this.problem.getDescription());
		createFiles(this.problem.getDir() + "/" + "inputDescription.txt", this.problem.getInputDescription());
		createFiles(this.problem.getDir() + "/" + "outputDescription.txt", this.problem.getOutputDescription());
		createFiles(this.problem.getDir() + "/" + "inputSample1.txt", this.problem.getInputSample1());
		createFiles(this.problem.getDir() + "/" + "inputSample2.txt", this.problem.getInputSample2());
		createFiles(this.problem.getDir() + "/" + "inputSample3.txt", this.problem.getInputSample3());
		createFiles(this.problem.getDir() + "/" + "outputSample1.txt", this.problem.getOutputSample1());
		createFiles(this.problem.getDir() + "/" + "outputSample2.txt", this.problem.getOutputSample2());
		createFiles(this.problem.getDir() + "/" + "outputSample3.txt", this.problem.getOutputSample3());
		createFiles(this.problem.getDir() + "/" + "notes.txt", this.problem.getNotes());
		createFiles(this.problem.getDir() + "/" + "header.txt", this.problem.getTitle() + "\n" +
																this.problem.getAuthor() + "\n" +
																this.problem.getUniversity() + "\n" +
																this.problem.getTimeLimit() + "\n" +
																this.problem.getScorePoints());
		
		InputStream in = new BufferedInputStream(this.getTestcases().getInputstream());
	
		File file = new File(this.problem.getDir() + "/TestCases/" + this.getTestcases().getFileName());

		if (!file.getParentFile().exists())
			file.getParentFile().mkdirs();// Creates the directories if they don't exists.
		
		FileOutputStream fos = new FileOutputStream(file);
		
		while (in.available() != 0) {// Writes the content in the user's file.
			fos.write(in.read());
		}
		
		fos.close();
		
		Runtime.getRuntime().exec("unzip " + this.problem.getDir() + "/TestCases/" + this.getTestcases().getFileName() + " -d " + this.problem.getDir() + "/TestCases/");
		
		
		FacesMessage message = new FacesMessage("Criado com sucesso", "");
		FacesContext.getCurrentInstance().addMessage(null, message);
		
		return "addProblem.xhtml?faces-redirect=true";
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
		ClarificationDAO cd = new ClarificationDAO();
		cd.deleteByProblem(this.selectedProblem.getIdProblem());
		
		SubmissionDAO sd = new SubmissionDAO();
		sd.deleteByProblem(this.selectedProblem.getIdProblem());
		
		ProblemDAO pd = new ProblemDAO();
		pd.delete(this.selectedProblem.getIdProblem());
		
		FacesMessage message = new FacesMessage("Excluído com sucesso", "");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
