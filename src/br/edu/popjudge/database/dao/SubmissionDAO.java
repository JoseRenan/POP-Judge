package br.edu.popjudge.database.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.edu.popjudge.bean.SubmissionBean;
import br.edu.popjudge.database.ConnectionFactory;

public class SubmissionDAO implements Dao<SubmissionBean> {
	Connection connection;
	
	@Override
	public void insert(SubmissionBean value) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		
		String sql = String.format("INSERT INTO SUBMISSION(id_submission, id_user, id_problem, id_language, file_name, time_submission, veredict) "
				+ "VALUES(%d, %d, %d, %d, '%s', '%s')", value.getIdSubmission(), value.getIdUser(), value.getIdProblem(), value.getIdLanguage(), value.getSourceCode().getFileName(), value.getTimeSubmission().toString(), value.getVeredict());
		
		Statement statement = connection.createStatement();
				
		statement.execute(sql);
		
		statement.close();
		connection.close();
	}

	@Override
	public ArrayList<SubmissionBean> getAll() throws SQLException {
		connection = new ConnectionFactory().getConnection();
		
		String sql = "SELECT * FROM SUBMISSION";
		
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		ArrayList<SubmissionBean> list = new ArrayList<SubmissionBean>();
		
		while(resultSet.next()){
			//TODO ainda falta pegar o dir do arquivo
			list.add(new SubmissionBean(resultSet.getInt("id_submission"),
										resultSet.getInt("id_user"),
										resultSet.getInt("id_problem"),
										resultSet.getInt("id_language"),
										resultSet.getTimestamp("time_submission"),
										resultSet.getString("veredict")));
		}
		
		resultSet.close();
		statement.close();
		connection.close();
		
		return list;
	}
	
	public ArrayList<SubmissionBean> getAll(int idUser) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		
		String sql = String.format("SELECT * FROM SUBMISSION WHERE id_user = %d", idUser);
		
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		ArrayList<SubmissionBean> list = new ArrayList<SubmissionBean>();
		
		while(resultSet.next()){
			//TODO ainda falta pegar o dir do arquivo
			list.add(new SubmissionBean(resultSet.getInt("id_submission"),
					resultSet.getInt("id_user"),
					resultSet.getInt("id_problem"),
					resultSet.getInt("id_language"),
					resultSet.getTimestamp("time_submission"),
					resultSet.getString("veredict")));
		}
		
		resultSet.close();
		statement.close();
		connection.close();
		
		return list;
	}
	
	@Override
	public SubmissionBean get(int id) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		
		String sql = String.format("SELECT * FROM SUBMISSION WHERE id_submission = %d", id);
		
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		SubmissionBean submissionBean = null;
		
		if(resultSet.next()){
			//TODO ainda falta pegar o dir do arquivo
			submissionBean = new SubmissionBean(resultSet.getInt("id_submission"),
												resultSet.getInt("id_user"),
												resultSet.getInt("id_problem"),
												resultSet.getInt("id_language"),
												resultSet.getTimestamp("time_submission"),
												resultSet.getString("veredict"));
		}
		
		resultSet.close();
		statement.close();
		connection.close();
		
		return submissionBean;
	}

	@Override
	public boolean delete(int id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update(SubmissionBean value) throws SQLException {
		// TODO Auto-generated method stub
	}
}
