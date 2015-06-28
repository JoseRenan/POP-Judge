package br.edu.popjudge.service;

import java.io.File;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import br.edu.popjudge.control.Judge;
import br.edu.popjudge.database.dao.LanguageDAO;
import br.edu.popjudge.database.dao.SubmissionDAO;
import br.edu.popjudge.domain.Submission;
import br.edu.popjudge.domain.Veredict;

public class SubmissionService {

	/**
	 * Services for submissions. 
	 * @author rerissondaniel
	 * @author gugaribeiro
	 * @author joserenan 
	 * */

	public boolean isSubmissionOk(Submission submission) {
		/**
		 * Checks if a submission is ready to be updated in the database,
		 * considering its parameters.
		 * @param  submission Submission to be checked.
		 * @return true if submission is ready to be inserted, false otherwise.
		 * */
		if (submission.getDir() == null)
			return false;
		if (!submission.getFile().exists())
			return false;
		if (submission.getIdSubmission() == 0)
			return false;
		if (submission.getLanguage() == null)
			return false;
		if (submission.getProblem() == null)
			return false;
		if (submission.getTimestamp() == null)
			return false;
		if (submission.getUser() == null)
			return false;
		if (submission.getVeredict() == null)
			return false;
		return true;
	}

	public void configSubmission(Submission submission, int idLanguage)
			throws SQLException {
		/**
		 * This method has as responsabilities configure a submission. By
		 * configure I mean complete the data needed for a submission to be
		 * complete.
		 * 
		 * @param submission
		 *            Submission object to be configured.
		 * @param idLanguage
		 *            The languages identifier. Will be used get language object
		 *            from database.
		 * @throws SQLException
		 */

		submission.setTimestamp(new Timestamp(System.currentTimeMillis()));

		LanguageDAO languageDAO = new LanguageDAO();
		submission.setLanguage(languageDAO.get(idLanguage));
		submission.setVeredict(Veredict.SUBMISSION_ERROR.getRotulo1());

		SubmissionDAO submissionDAO = new SubmissionDAO();
		submission.setIdSubmission(submissionDAO.insert(submission));

		submission.setDir(submission.getUser().getDir() + "/"
				+ submission.getIdSubmission());

		File submissionDirectory = new File(submission.getDir());
		submissionDirectory.mkdirs();
	}

	public void submit(Submission submission) throws SQLException {
		/**
		 * This method is responsible for insert a submission in the database
		 * and in the ranking.
		 * 
		 * @param submission
		 *            The submission to be considered.
		 * */
		SubmissionDAO submissionDao = new SubmissionDAO();

		submission.setTimestamp(new Timestamp(System.currentTimeMillis()));

		Judge judge = new Judge();
		submission.setVeredict((judge.judge(submission).getRotulo1()));

		submissionDao.update(submission);

		RankingService rankingService = new RankingService();
		rankingService.insertSubmission(submission);
	}
	
	public ArrayList<Submission> getSubmissionsByUser(int idUser) throws SQLException{
		SubmissionDAO submissionDao = new SubmissionDAO();
		return submissionDao.getByUser(idUser);
	}
	
	public ArrayList<Submission> getSubmissions() throws SQLException{
		SubmissionDAO submissionDao = new SubmissionDAO();
		return submissionDao.getAll();
	}
}