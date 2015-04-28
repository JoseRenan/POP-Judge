package br.edu.popjudge.database.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;

import br.edu.popjudge.database.ConnectionFactory;
import br.edu.popjudge.domain.Submission;
import br.edu.popjudge.domain.Veredict;

@ManagedBean
public class SubmissionDAO implements Dao<Submission> {
	Connection connection;

	@Override
	public void insert(Submission value) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		
		String sql = "INSERT INTO SUBMISSION(id_submission, id_user, id_problem, "
				+ "id_language, file_name, time_submission, veredict) VALUES(0, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setInt(1, value.getUser().getIdUser());
		statement.setInt(2, value.getProblem().getIdProblem());
		statement.setInt(3, value.getLanguage().getIdLanguage());
		statement.setString(4, value.getFileName());
		statement.setString(5, value.getTimestamp().toString());
		statement.setString(6, value.getVeredict());
		
		statement.execute();
		
		statement.close();
		connection.close();
	}

	@Override
	public ArrayList<Submission> getAll() throws SQLException {
		connection = new ConnectionFactory().getConnection();
		
		String sql = "SELECT * FROM SUBMISSION ORDER BY time_submission DESC";
		
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		ArrayList<Submission> list = new ArrayList<Submission>();
		
		UserDAO uDAO = new UserDAO();
		ProblemDAO pDAO = new ProblemDAO();
		LanguageDAO lDAO = new LanguageDAO();
		
		while(resultSet.next()){
			list.add(new Submission(resultSet.getInt("id_submission"),
									uDAO.get(resultSet.getInt("id_user")),
									pDAO.get(resultSet.getInt("id_problem")),
									lDAO.get(resultSet.getInt("id_language")),
									new File(resultSet.getString("file_name")),
									resultSet.getTimestamp("time_submission"),
									resultSet.getString("veredict")));
		}
		
		resultSet.close();
		statement.close();
		connection.close();
		
		return list;
	}

	@Override
	public Submission get(int id) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		
		String sql = String.format("SELECT * FROM SUBMISSION WHERE id_submission = %d", id);
		
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		Submission ret = new Submission();
		
		UserDAO uDAO = new UserDAO();
		ProblemDAO pDAO = new ProblemDAO();
		LanguageDAO lDAO = new LanguageDAO();
		
		if(resultSet.next()){
			ret = new Submission(resultSet.getInt("id_submission"),
									uDAO.get(resultSet.getInt("id_user")),
									pDAO.get(resultSet.getInt("id_problem")),
									lDAO.get(resultSet.getInt("id_language")),
									new File(resultSet.getString("file_name")),
									resultSet.getTimestamp("time_submission"),
									resultSet.getString("veredict"));
		}
		
		resultSet.close();
		statement.close();
		connection.close();
		
		return ret;
	}

	@Override
	public boolean delete(int id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update(Submission value) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
}
