package br.edu.popjudge.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

import br.edu.popjudge.database.dao.ProblemDAO;
import br.edu.popjudge.database.dao.RankingDAO;
import br.edu.popjudge.database.dao.UserDAO;
import br.edu.popjudge.domain.Problem;
import br.edu.popjudge.domain.Score;
import br.edu.popjudge.domain.User;
import br.edu.popjudge.domain.UserRank;

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
			userRankAux.getProblems().put(problem.getIdProblem(), new Score(0, 0));
			for (User user : listUsers) {
				userRankAux.setUsername(user.getUsername());
				rankingDao.insert(userRankAux);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
