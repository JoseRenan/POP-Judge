package br.edu.popjudge.database.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.edu.popjudge.database.ConnectionFactory;
import br.edu.popjudge.domain.Problem;

public class ProblemDAO implements Dao<Problem> {
	private Connection connection;
	
	@Override
	public void insert(Problem value) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		
		String sql = "INSERT INTO PROBLEM(id_problem, title, score_points, "
				+ "time_limit, input, output) VALUES(0, ?, ?, ?, ?, ?)";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setString(1, value.getTitle());
		statement.setInt(2, value.getScorePoints());
		statement.setLong(3, value.getTimeLimit());
		statement.setString(4, value.getInput().getAbsolutePath());
		statement.setString(5, value.getOutput().getAbsolutePath());
		
		statement.execute();
		
		statement.close();
		connection.close();
	}

	@Override
	public ArrayList<Problem> getAll() throws SQLException {
		connection = new ConnectionFactory().getConnection();
		
		String sql = "SELECT * FROM PROBLEM";
		
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		ArrayList<Problem> list = new ArrayList<Problem>();
		
		while(resultSet.next()){
			list.add(new Problem(resultSet.getInt("id_problem"),
								 resultSet.getInt("score_points"),
								 resultSet.getString("title"),
								 resultSet.getLong("time_limit"),
								 new File(resultSet.getString("input")),
								 new File(resultSet.getString("output"))));
		}
		
		resultSet.close();
		statement.close();
		connection.close();
		
		return list;
	}

	@Override
	public Problem get(int id) throws SQLException {
connection = new ConnectionFactory().getConnection();
		
		String sql = String.format("SELECT * FROM PROBLEM WHERE id_problem = %d", id);
		
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		Problem ret = null;
		
		if(resultSet.next()){
			ret = new Problem(resultSet.getInt("id_problem"),
						   	  resultSet.getInt("score_points"),
							  resultSet.getString("title"),
							  resultSet.getLong("time_limit"),
							  new File(resultSet.getString("input")),
							  new File(resultSet.getString("output")));
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
	public void update(Problem value) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		
		String sql = "UPDATE PROBLEM SET title = ?, score_points = ?, "
				+ "time_limit = ?, input = ?, output = ? WHERE id_problem = ?";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setString(1, value.getTitle());
		statement.setInt(2, value.getScorePoints());
		statement.setLong(3, value.getTimeLimit());
		statement.setString(4, value.getInput().getAbsolutePath());
		statement.setString(5, value.getOutput().getAbsolutePath());
		statement.setInt(6, value.getIdProblem());
		
		statement.execute();
		
		statement.close();
		connection.close();
	}

}
