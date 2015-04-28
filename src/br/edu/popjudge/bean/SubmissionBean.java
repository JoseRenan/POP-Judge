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
	
	public Submission getSubmission() {
		return submission;
	}

	public void setSubmission(Submission submission) {
		this.submission = submission;
	}

	public void submit() throws IOException {
		if (this.submission.getFile() != null) {

			FacesContext context = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) context.getExternalContext()
					.getSession(true);

			InputStream in = new BufferedInputStream(this.submission.getFile().getInputstream());

			this.submission.setDir((String) session.getAttribute("dir") + "/"
					+ this.submission.getIdSubmission());// Gets the user's directory.
			
			this.submission.setUser((User) session.getAttribute("user"));// Gets user's id.
			
			this.submission.setTimestamp(new Timestamp(System.currentTimeMillis()));// Gets the
																	// actual
																	// time.

			File file = new File(this.submission.getDir() + "/" + this.submission.getFile().getFileName());// Creates
																		// a
																		// file
																		// in
																		// the
																		// user's
																		// directory.
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();// Creates the directories if they
												// don't exists.
			FileOutputStream fos = new FileOutputStream(file);
			while (in.available() != 0) {// Writes the content in the user's
											// file.
				fos.write(in.read());
			}
			fos.close();

			this.submission.setFileName(file.getAbsolutePath());

			Judge j = new Judge();
			this.submission.setVeredict((j.judge(this).getRotulo1()));

			SubmissionDAO sbmdao = new SubmissionDAO();

			try {
				sbmdao.insert(this.submission);

				RankDAO rd = new RankDAO();
				UserRank ur = rd.get((String) session.getAttribute("username"));
				
				//TODO Falta concertar essa parte que manipula o ranking
				//Inicia aqui
				
				if (!(j.judge(this).getRotulo1()).equals("Accepted")) {
					switch (this.submission.getProblem().getIdProblem()) {
					case 1:
						if (ur.getProblem1() <= 0)
							ur.setProblem1(ur.getProblem1() - 1);
						break;
					case 2:
						if (ur.getProblem2() <= 0)
							ur.setProblem2(ur.getProblem2() - 1);
						break;
					case 3:
						if (ur.getProblem3() <= 0)
							ur.setProblem3(ur.getProblem3() - 1);
						break;
					case 4:
						if (ur.getProblem4() <= 0)
							ur.setProblem4(ur.getProblem4() - 1);
						break;
					case 5:
						if (ur.getProblem5() <= 0)
							ur.setProblem5(ur.getProblem5() - 1);
						break;
					}
				} else {
					int totalTime = TimerBean.totalTimeContest();
					int currentTime = TimerBean.currentMoment();
					ProblemBean p = new ProblemDAO().get(this.idProblem);
					int score = p.getPoints(); 
					score -= ((p.getPoints()/2)/totalTime)*currentTime;

					switch (this.idProblem) {
					case 1:
						if (ur.getProblem1() <= 0){
							ur.setProblem1(score + (ur.getProblem1()* ((int)(0.01 * p.getPoints()))));
							ur.setPoints(ur.getPoints() + ur.getProblem1());
						}
						break;
					case 2:
						if (ur.getProblem2() <= 0){
							ur.setProblem2(score + (ur.getProblem2()* ((int)(0.01 * p.getPoints()))));
							ur.setPoints(ur.getPoints() + ur.getProblem2());
						}
						break;
					case 3:
						if (ur.getProblem3() <= 0){
							ur.setProblem3(score + (ur.getProblem3()* ((int)(0.01 * p.getPoints()))));
							ur.setPoints(ur.getPoints() + ur.getProblem3());
						}
						break;
					case 4:
						if (ur.getProblem4() <= 0){
							ur.setProblem4(score + (ur.getProblem4()* ((int)(0.01 * p.getPoints()))));
							ur.setPoints(ur.getPoints() + ur.getProblem4());
						}
						break;
					case 5:
						if (ur.getProblem5() <= 0){
							ur.setProblem5(score + (ur.getProblem5()* ((int)(0.01 * p.getPoints()))));
							ur.setPoints(ur.getPoints() + ur.getProblem5());
						}
						break;
					}
				}
				rd.update(ur);
				
				//Termina aqui
				
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
