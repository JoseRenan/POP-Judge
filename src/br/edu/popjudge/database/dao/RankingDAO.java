package br.edu.popjudge.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;

import br.edu.popjudge.database.ConnectionFactory;
import br.edu.popjudge.domain.Score;
import br.edu.popjudge.domain.UserRank;

@ManagedBean
public class RankingDAO implements Dao<UserRank> {

	@Override
	public int insert(UserRank value) throws SQLException {
		if (this.get(value.getUsername()) != null) {
			Connection connection = new ConnectionFactory().getConnection();
			String sql = "insert into RANKING(username, id_problem, tries, passed_time) values (?, ?, ?, ?)";
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setString(1, value.getUsername());

			for (Map.Entry<Integer, Score> entry : value.getProblems()
					.entrySet()) {
				stmt.setInt(2, entry.getKey());
				stmt.setInt(3, entry.getValue().getTries());
				stmt.setLong(4, entry.getValue().getPassedTime());
				stmt.execute();
			}
			stmt.close();
			connection.close();
		}

		return 0;

	}

	@Override
	public ArrayList<UserRank> getAll() throws SQLException {
		Connection connection = new ConnectionFactory().getConnection();
		ArrayList<UserRank> all = new ArrayList<UserRank>();

		String sql = "select * from RANKING group by(username)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet resultSet = stmt.executeQuery();

		while (resultSet.next()) {
			all.add(this.get(resultSet.getString("username")));
		}

		resultSet.close();
		stmt.close();
		connection.close();

		return all;
	}

	public UserRank get(String username) throws SQLException {
		Connection connection = new ConnectionFactory().getConnection();
		String sql = "select * from RANKING where username = ?";
		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, username);

		ResultSet resultSet = statement.executeQuery();

		Map<Integer, Score> problems = new TreeMap<Integer, Score>();

		while (resultSet.next()) {
			problems.put(
					resultSet.getInt("id_problem"),
					new Score(resultSet.getInt("tries"), resultSet
							.getLong("passed_time")));
		}

		UserRank ur = new UserRank(username, problems);

		resultSet.close();
		statement.close();
		connection.close();

		return ur;
	}

	@Override
	@Deprecated
	public UserRank get(int id) throws SQLException {
		System.out.println("DONT. USE. THIS.");
		return null;
	}

	@Override
	public boolean delete(int idProblem) throws SQLException {
		Connection connection = new ConnectionFactory().getConnection();
		String sql = "delete from RANKING where id_problem = ?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, idProblem);

		statement.execute();
		return false;
	}

	@Override
	public void update(UserRank value) throws SQLException {
		Connection connection = new ConnectionFactory().getConnection();

		String sql = "update RANKING set tries = ?, passed_time = ? where username = ? and id_problem = ? limit 1";
		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(3, value.getUsername());

		for (Map.Entry<Integer, Score> entry : value.getProblems().entrySet()) {
			statement.setInt(1, entry.getValue().getTries());
			statement.setLong(2, entry.getValue().getPassedTime());
			statement.setInt(4, entry.getKey());
			statement.execute();
		}

		value.setProblems(this.get(value.getUsername()).getProblems());
		statement.close();
		connection.close();
	}

	@Override
	public void truncate() throws SQLException {
		Connection connection = new ConnectionFactory().getConnection();

		String sql = "truncate table RANKING";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.execute();

		statement.close();
		connection.close();

	}
}
