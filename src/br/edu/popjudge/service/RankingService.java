package br.edu.popjudge.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TreeMap;

import br.edu.popjudge.bean.TimerBean;
import br.edu.popjudge.database.dao.ProblemDAO;
import br.edu.popjudge.database.dao.RankingDAO;
import br.edu.popjudge.database.dao.UserDAO;
import br.edu.popjudge.domain.Problem;
import br.edu.popjudge.domain.Score;
import br.edu.popjudge.domain.Submission;
import br.edu.popjudge.domain.User;
import br.edu.popjudge.domain.UserRank;
import br.edu.popjudge.domain.Veredict;

public class RankingService {

	public void insertUser(User user) {
		ProblemDAO problemDao = new ProblemDAO();
		RankingDAO rankingDao = new RankingDAO();

		UserRank userRank = new UserRank();
		ArrayList<Problem> listProblems;

		try {
			listProblems = problemDao.getAll();
			userRank.setUsername(user.getUsername());
			TreeMap<Integer, Score> rankProblems = new TreeMap<Integer, Score>();

			for (Problem problem : listProblems) {
				rankProblems.put(problem.getIdProblem(), new Score(0, 0));
			}
			userRank.setProblems(rankProblems);

			rankingDao.insert(userRank);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertProblem(Problem problem) {
		RankingDAO rankingDao = new RankingDAO();
		UserDAO userDao = new UserDAO();

		ArrayList<User> listUsers;
		UserRank userRankAux = new UserRank("", new TreeMap<Integer, Score>());

		try {
			listUsers = userDao.getAll();
			userRankAux.getProblems().put(problem.getIdProblem(),
					new Score(0, 0));
			for (User user : listUsers) {
				userRankAux.setUsername(user.getUsername());
				rankingDao.insert(userRankAux);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertSubmission(Submission submission) {
		RankingDAO rankingDao = new RankingDAO();
		UserRank userRank;

		try {
			userRank = rankingDao.get(submission.getUser().getUsername());
			Score score = userRank.getProblems().get(
					submission.getProblem().getIdProblem());
			if (score.getPassedTime() == 0) {
				if (submission.getVeredict().equals(
						Veredict.ACCEPTED_ANSWER.getRotulo1()))
					score.setPassedTime(TimerBean.currentMoment());
				else
					score.setTries(score.getTries() + 1);

				userRank.getProblems().put(
						submission.getProblem().getIdProblem(), score);

				rankingDao.update(userRank);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
