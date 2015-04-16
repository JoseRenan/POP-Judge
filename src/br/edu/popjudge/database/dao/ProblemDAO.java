package br.edu.popjudge.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.edu.popjudge.bean.ProblemBean;
import br.edu.popjudge.database.ConnectionFactory;

public class ProblemDAO implements Dao<ProblemBean> {

	private Connection connection;

	@Override
	public void insert(ProblemBean value) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		String sql = "insert into PROBLEM(id_problem, time_limit, input, output) VALUES(?, ?, ?, ?, ?)";
		PreparedStatement stmt = connection.prepareStatement(sql);

		stmt.setInt(1, 0);
		stmt.setLong(2, value.getTimeLimit());
		stmt.setInt(3, value.getPoints());
		stmt.setString(4, value.getInput());
		stmt.setString(5, value.getOutput());

		stmt.execute();

		stmt.close();
		connection.close();
	}

	@Override
	public ArrayList<ProblemBean> getAll() throws SQLException {// testar
		connection = new ConnectionFactory().getConnection();
		String sql = "select * from PROBLEM";
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		ArrayList<ProblemBean> list = new ArrayList<ProblemBean>();
		while (rs.next()) {
			list.add(new ProblemBean(rs.getInt("id_problem"), rs
					.getLong("time_limit"), rs.getInt("score_points"), rs
					.getString("input"), rs.getString("output")));
		}

		connection.close();
		return list;
	}

	@Override
	public ProblemBean get(int id) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		String sql = "select * from PROBLEM where id_problem = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			return new ProblemBean(rs.getInt("id_problem"),
					rs.getLong("time_limit"), rs.getInt("score_points"),
					rs.getString("input"), rs.getString("output"));
		}
		return null;
	}

	@Override
	public boolean delete(int id) throws SQLException {
		return false;
	}

	@Override
	public void update(ProblemBean value) throws SQLException {
		//TODO Adicionar score.
		connection = new ConnectionFactory().getConnection();
		String sql = "update PROBLEM set time_limit = ?, score_points = ?, input = ?, output = ? where id_problem = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setLong(1, value.getTimeLimit());
		stmt.setInt(2, value.getPoints());
		stmt.setString(3, value.getInput());
		stmt.setString(4, value.getOutput());
		stmt.setInt(5, value.getIdProblem());
		stmt.execute();
	}
}
