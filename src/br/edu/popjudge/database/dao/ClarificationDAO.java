package br.edu.popjudge.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;

import br.edu.popjudge.database.ConnectionFactory;
import br.edu.popjudge.domain.Clarification;

@ManagedBean(name = "issuedao")
public class ClarificationDAO implements Dao<Clarification> {

	@Override
	public int insert(Clarification value) throws SQLException {
		Connection connection = new ConnectionFactory().getConnection();

		String sql = "INSERT INTO CLARIFICATION(id_clarification, id_user, id_problem, "
				+ "issue, answer) VALUES(0, ?, ?, ?, ?)";

		PreparedStatement statement = connection.prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS);

		statement.setInt(1, value.getUser().getIdUser());
		statement.setInt(2, value.getProblem().getIdProblem());
		statement.setString(3, value.getIssue());
		statement.setString(4, value.getAnswer());

		statement.execute();

		ResultSet resultSet = statement.getGeneratedKeys();
		int key = 0;

		if (resultSet.next()) {
			key = resultSet.getInt(1);
		}
		
		resultSet.close();
		statement.close();
		connection.close();
		
		return key;
	}

	@Override
	public ArrayList<Clarification> getAll() throws SQLException {
		Connection connection = new ConnectionFactory().getConnection();

		String sql = "SELECT * FROM CLARIFICATION ORDER BY id_problem";

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		ArrayList<Clarification> list = new ArrayList<Clarification>();

		UserDAO uDAO = new UserDAO();
		ProblemDAO pDAO = new ProblemDAO();

		while (resultSet.next()) {
			list.add(new Clarification(resultSet.getInt("id_clarification"),
					uDAO.get(resultSet.getInt("id_user")), pDAO.get(resultSet
							.getInt("id_problem")), resultSet
							.getString("issue"), resultSet.getString("answer"),
					resultSet.getTimestamp("time_clarification")));
		}

		resultSet.close();
		statement.close();
		connection.close();

		return list;
	}

	public ArrayList<Clarification> getNotReplied() throws SQLException {
		Connection connection = new ConnectionFactory().getConnection();

		String sql = "SELECT * FROM CLARIFICATION WHERE answer IS NULL ORDER BY id_problem";

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		ArrayList<Clarification> list = new ArrayList<Clarification>();

		UserDAO uDAO = new UserDAO();
		ProblemDAO pDAO = new ProblemDAO();

		while (resultSet.next()) {
			list.add(new Clarification(resultSet.getInt("id_clarification"),
					uDAO.get(resultSet.getInt("id_user")), pDAO.get(resultSet
							.getInt("id_problem")), resultSet
							.getString("issue"), resultSet.getString("answer"),
					resultSet.getTimestamp("time_clarification")));
		}

		resultSet.close();
		statement.close();
		connection.close();

		return list;
	}

	@Override
	public Clarification get(int idClarification) throws SQLException {
		Connection connection = new ConnectionFactory().getConnection();

		String sql = String.format(
				"SELECT * FROM CLARIFICATION WHERE id_clarification = %d", idClarification);

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		Clarification ret = null;
		UserDAO uDAO = new UserDAO();
		ProblemDAO pDAO = new ProblemDAO();

		if (resultSet.next()) {
			ret = new Clarification(resultSet.getInt("id_clarification"),
					uDAO.get(resultSet.getInt("id_user")), pDAO.get(resultSet
							.getInt("id_problem")),
					resultSet.getString("issue"),
					resultSet.getString("answer"),
					resultSet.getTimestamp("time_clarification"));
		}

		resultSet.close();
		statement.close();
		connection.close();

		return ret;
	}

	@Override
	public boolean delete(int idClarification) throws SQLException {
		Connection connection = new ConnectionFactory().getConnection();

		String sql = String.format(
				"DELETE FROM CLARIFICATION WHERE id_clarification = %d", idClarification);

		Statement statement = connection.createStatement();

		boolean ret = statement.execute(sql);

		statement.close();
		connection.close();

		return ret;
	}

	@Override
	public void update(Clarification value) throws SQLException {
		Connection connection = new ConnectionFactory().getConnection();

		String sql = "UPDATE CLARIFICATION SET id_user = ?, id_problem = ?, "
				+ "issue = ?, answer = ?, time_clarification = ? WHERE id_clarification = ?";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setInt(1, value.getUser().getIdUser());
		statement.setInt(2, value.getProblem().getIdProblem());
		statement.setString(3, value.getIssue());
		statement.setString(4, value.getAnswer());
		statement.setString(5, value.getTimestamp().toString());
		statement.setInt(6, value.getIdClarification());

		statement.execute();

		statement.close();
		connection.close();
	}

	@Override
	public void truncate() throws SQLException {
		Connection connection = new ConnectionFactory().getConnection();

		String sql = "truncate table CLARIFICATION";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.execute();
		statement.close();

		connection.close();

	}

	public void deleteByProblem(int idProblem) throws SQLException {
		Connection connection = new ConnectionFactory().getConnection();

		String sql = String.format(
				"DELETE FROM CLARIFICATION WHERE id_problem = %d", idProblem);

		Statement statement = connection.createStatement();

		statement.execute(sql);

		statement.close();
		connection.close();
	}
}