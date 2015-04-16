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
import br.edu.popjudge.database.dao.ProblemDAO;
import br.edu.popjudge.database.dao.RankDAO;
import br.edu.popjudge.database.dao.SubmissionDAO;
import br.edu.popjudge.domain.UserRank;

@ManagedBean(name = "submission")
public class SubmissionBean {
	private UploadedFile code;
	private int idProblem;
	private int idLanguage;
	private int idSubmission;
	private int idUser;
	private Timestamp timestamp;
	private String veredict;
	private String fileName;
	private String dir;

	public SubmissionBean() {
	}

	public SubmissionBean(int idSubmission, int idUser, int problem,
			int language, Timestamp timestamp, String fileName, String veredict) {
		super();
		this.idProblem = problem;
		this.idLanguage = language;
		this.idSubmission = idSubmission;
		this.idUser = idUser;
		this.timestamp = timestamp;
		this.veredict = veredict;
		this.fileName = fileName;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String filename) {
		this.fileName = filename;
	}

	public String getVeredict() {
		return veredict;
	}

	public void setVeredict(String veredict) {
		this.veredict = veredict;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public int getIdSubmission() {
		return idSubmission;
	}

	public void setIdSubmission(int idSubmission) {
		this.idSubmission = idSubmission;
	}

	public UploadedFile getCode() {
		return code;
	}

	public void setCode(UploadedFile code) {
		this.code = code;
	}

	public int getIdProblem() {
		return idProblem;
	}

	public void setIdProblem(int problem) {
		this.idProblem = problem;
	}

	public int getIdLanguage() {
		return idLanguage;
	}

	public void setIdLanguage(int language) {
		this.idLanguage = language;
	}

	public void submit() throws IOException {
		if (this.code != null) {

			FacesContext context = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) context.getExternalContext()
					.getSession(true);

			InputStream in = new BufferedInputStream(this.code.getInputstream());

			setIdSubmission(SubmissionIdGenerator.getSubmissionId());// Gets an
																		// id
																		// for a
																		// new
																		// submission.
			dir = (String) session.getAttribute("dir") + "/"
					+ this.getIdSubmission();// Gets the user's directory.
			setIdUser((int) session.getAttribute("idUser"));// Gets user's id.
			setTimestamp(new Timestamp(System.currentTimeMillis()));// Gets the
																	// actual
																	// time.

			File file = new File(dir + "/" + this.code.getFileName());// Creates
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

			setFileName(file.getAbsolutePath());

			Judge j = new Judge();
			setVeredict((j.judge(this).getRotulo1()));

			SubmissionDAO sbmdao = new SubmissionDAO();

			try {
				sbmdao.insert(this);

				RankDAO rd = new RankDAO();
				UserRank ur = rd.get((String) session.getAttribute("username"));

				if (!(j.judge(this).getRotulo1()).equals("Accepted")) {
					switch (this.idProblem) {
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
				} else if (ur.getPoints() <= 0) {
					int currentTime = TimerBean.currentMoment();
					ProblemBean p = new ProblemDAO().get(this.idProblem);
					int score = p.getPoints();
					// TODO adicionar a constante e a relação da pontuação com o
					// tempo.
					ur.setPoints(ur.getPoints() + score);
					switch (this.idProblem) {
					case 1:
						if (ur.getProblem1() <= 0)
							ur.setProblem1(score);
						break;
					case 2:
						if (ur.getProblem1() <= 0)
							ur.setProblem2(score);
						break;
					case 3:
						if (ur.getProblem1() <= 0)
							ur.setProblem3(score);
						break;
					case 4:
						if (ur.getProblem1() <= 0)
							ur.setProblem4(score);
						break;
					case 5:
						if (ur.getProblem1() <= 0)
							ur.setProblem5(score);
						break;
					}
				}
				rd.update(ur);

				this.code = null;
				this.dir = null;
				this.idLanguage = 0;
				this.idProblem = 0;
				this.idUser = 0;

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