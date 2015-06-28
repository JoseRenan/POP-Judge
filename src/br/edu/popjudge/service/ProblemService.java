package br.edu.popjudge.service;

import java.sql.SQLException;
import java.util.ArrayList;

import br.edu.popjudge.database.dao.ProblemDAO;
import br.edu.popjudge.domain.Problem;

public class ProblemService {
	
	public ArrayList<Problem> getProblems() throws SQLException{
		ProblemDAO problemDAO = new ProblemDAO();
		return problemDAO.getAll();
	}
	
}
