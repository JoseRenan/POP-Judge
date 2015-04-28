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
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.UploadedFile;

import br.edu.popjudge.control.Judge;
import br.edu.popjudge.control.SubmissionIdGenerator;
import br.edu.popjudge.database.dao.LanguageDAO;
import br.edu.popjudge.database.dao.ProblemDAO;
import br.edu.popjudge.database.dao.RankDAO;
import br.edu.popjudge.database.dao.SubmissionDAO;
import br.edu.popjudge.domain.Submission;
import br.edu.popjudge.domain.User;
import br.edu.popjudge.domain.UserRank;

@ManagedBean(name = "submission")
public class SubmissionBean {

	private Submission submission = new Submission();
	private UploadedFile upFile;

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

	public void submit() throws IOException {
		if (this.getUpFile() != null) {

			FacesContext context = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) context.getExternalContext()
					.getSession(true);

			InputStream in = new BufferedInputStream(this.getUpFile()
					.getInputstream());

			this.submission.setUser((User) session.getAttribute("user"));
			/*
			 * Gets user 's id. TODO Isso vai dar merda . Mas vou esperar testar
			 * pra corrigir .
			 */

			this.submission.setTimestamp(new Timestamp(System
					.currentTimeMillis()));

			File file = new File(this.submission.getDir() + "/"
					+ this.getUpFile().getFileName());
			submission.setFile(file);

			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();// Creates the directories if they
												// don't exists.
			FileOutputStream fos = new FileOutputStream(file);
			while (in.available() != 0) {// Writes the content in the user's
											// file.
				fos.write(in.read());
			}
			fos.close();

			try {
				this.submission.setProblem(new ProblemDAO().get((int)session.getAttribute("idProblem")));
				this.submission.setDir((String) session.getAttribute("dir") + "/"
						+ this.submission.getIdSubmission());
				this.submission.setFileName(file.getAbsolutePath());
				this.submission.setLanguage(new LanguageDAO()
						.get(this.submission.getLanguage().getIdLanguage()));
				Judge j = new Judge();
				this.submission.setVeredict((j.judge(this.submission)
						.getRotulo1()));

				SubmissionDAO sbmdao = new SubmissionDAO();

				sbmdao.insert(this.submission);

				RankDAO rd = new RankDAO();
				UserRank ur = rd.get((String) session.getAttribute("username"));

				// TODO Falta concertar essa parte que manipula o ranking
				// Inicia aqui

				// Termina aqui

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
