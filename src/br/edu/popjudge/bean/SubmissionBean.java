package br.edu.popjudge.bean;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.UploadedFile;

import br.edu.popjudge.control.Judge;
import br.edu.popjudge.database.dao.LanguageDAO;
import br.edu.popjudge.database.dao.SubmissionDAO;
import br.edu.popjudge.domain.Problem;
import br.edu.popjudge.domain.Submission;
import br.edu.popjudge.domain.User;

@ManagedBean(name = "submission")
@ViewScoped
public class SubmissionBean {

	private Submission submission = new Submission();
	private UploadedFile upFile;
	private int idLanguage;
	private Problem selectedProblem;

	public UploadedFile getUpFile() {
		return upFile;
	}

	public void setUpFile(UploadedFile upFile) {
		this.upFile = upFile;
	}

	public Submission getSubmission() {
		return submission;
	}

	public void setSubmission(Submission submission) {
		this.submission = submission;
	}

	public int getIdLanguage() {
		return idLanguage;
	}

	public void setIdLanguage(int idLanguage) {
		this.idLanguage = idLanguage;
	}

	public Problem getSelectedProblem() {
		return selectedProblem;
	}

	public void setSelectedProblem(Problem selectedProblem) {
		this.selectedProblem = selectedProblem;
	}

	public void submit() throws IOException, SQLException {
		if (this.getUpFile() != null) {
				
			FacesContext context = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) context.getExternalContext().getSession(true);

			InputStream in = new BufferedInputStream(this.getUpFile().getInputstream());

			this.submission.setUser((User) session.getAttribute("user"));
			
			/*
			 * Gets user 's id. TODO Isso vai dar merda . Mas vou esperar testar
			 * pra corrigir .
			 */
		
			this.submission.setDir(this.submission.getUser().getDir() + "/" + this.submission.getIdSubmission());
			
			File file = new File(this.submission.getDir() + "/" + this.getUpFile().getFileName());
			this.submission.setFile(file);

			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();// Creates the directories if they don't exists.
			
			FileOutputStream fos = new FileOutputStream(file);
			
			while (in.available() != 0) {// Writes the content in the user's file.
				fos.write(in.read());
			}
			
			fos.close();

			try {
				LanguageDAO ld = new LanguageDAO();
				
				this.submission.setTimestamp(new Timestamp(System.currentTimeMillis()));
				this.submission.setLanguage(ld.get(idLanguage));
				this.submission.setProblem(selectedProblem);
				
				Judge j = new Judge();
				this.submission.setVeredict((j.judge(this.submission).getRotulo1()));

				SubmissionDAO sbmdao = new SubmissionDAO();

				sbmdao.insert(this.submission);

				// TODO Falta fazer toda a parte que manipula o ranking

				this.submission = new Submission();

			} catch (SQLException e) {
				e.printStackTrace();
				FacesMessage message = new FacesMessage(
						"Não entre em pânico.\nAfaste-se do computador e chame os admins.\n Tem algo MUITO errado.",
						"");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
			FacesMessage message = new FacesMessage("Enviado com sucesso", "");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} else {
			FacesMessage message = new FacesMessage("Problema na submissão", "");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
}
