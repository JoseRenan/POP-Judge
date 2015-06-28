package br.edu.popjudge.bean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.UploadedFile;

import br.edu.popjudge.control.Util;
import br.edu.popjudge.domain.Problem;
import br.edu.popjudge.domain.Submission;
import br.edu.popjudge.domain.User;
import br.edu.popjudge.service.SubmissionService;

@ManagedBean(name = "submission")
@ViewScoped
public class SubmissionBean implements Serializable {
	/**
	 * The interface between the services and the view.
	 * @author rerissondaniel
	 * @author gugaribeiro
	 * @author joserenan
	 * */
	private static final long serialVersionUID = -7086813068237158227L;
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

	public void submit() {
		if (this.getUpFile() != null) {
			FacesContext context = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) context.getExternalContext()
					.getSession(true);

			this.submission.setUser((User) session.getAttribute("user"));
			this.submission.setFile(new File(""));
			this.submission.setProblem(selectedProblem);
			this.submission.setIdSubmission(0);
			/* Configures the submission with the data available */

			try {
				SubmissionService submissionService = new SubmissionService();

				submissionService.configSubmission(submission, idLanguage);
				this.submission.setFile(Util.generateSubmissionFile(this.submission.getDir(), upFile));
				
				if(submissionService.isSubmissionOk(submission))
					submissionService.submit(submission);
				
				submission = new Submission();

				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("/POP-Judge/webapp/user/mySubmissions.xhtml");
			} catch (SQLException | IOException e) {
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