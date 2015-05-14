package br.edu.popjudge.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import br.edu.popjudge.bean.TimerBean;
import br.edu.popjudge.database.ConnectionFactory;
import br.edu.popjudge.domain.Score;
import br.edu.popjudge.domain.UserRank;

public class RankingDAO implements Dao<UserRank> {
	private Connection connection;
	private ArrayList<UserRank> all = null;

	@Override
	public void insert(UserRank value) throws SQLException {
		if (this.get(value.getUsername()) != null) {
			connection = new ConnectionFactory().getConnection();
			String sql = "insert into RANKING(username, id_problem, tries, passed_time) values (?, ?, ?, ?)";
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setString(1, value.getUsername());

			for (Map.Entry<Integer, Score> entry : value.getProblems()
					.entrySet()) {
				stmt.setInt(2, entry.getKey());
				stmt.setInt(3, entry.getValue().getTries());
				stmt.setInt(4, entry.getValue().getPassedTime());
				stmt.execute();
			}
			stmt.close();
		}

		connection.close();
	}

	@Override
	public ArrayList<UserRank> getAll() throws SQLException {
		boolean rankBlinded = new TimerBean().rankBlinded();

		if (!rankBlinded || all == null) {
			connection = new ConnectionFactory().getConnection();
			this.all = new ArrayList<UserRank>();

			String sql = "select * from RANKING group by(username)";
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				all.add(this.get(rs.getString("username")));
			}
			all.sort(new Comparator<UserRank>() {

				@Override
				public int compare(UserRank o1, UserRank o2) {
					if (o1.getSumAccepted() > o2.getSumAccepted())
						return 1;
					else if(o1.getSumAccepted() < o2.getSumAccepted())
						return -1;
					else if(o1.getSumTries() < o2.getSumTries())
						return 1;
					else if(o1.getSumTries() > o2.getSumTries())
						return -1;
					else if(o1.getSumTime() < o2.getSumTime())
						return 1;
					else if(o2.getSumTime() > o2.getSumTime())
						return -1;
					return 0;
				}
			});
			rs.close();
			stmt.close();
			connection.close();

		}

		return all;
	}

	public UserRank get(String username) throws SQLException {
		connection = new ConnectionFactory().getConnection();

		String sql = "select * from RANKING where username = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);

		stmt.setString(1, username);

		ResultSet rs = stmt.executeQuery();
		UserRank ur = new UserRank();
		ur.setUsername(username);
		Map<Integer, Score> problems = new TreeMap<Integer, Score>();

		while (rs.next()) {
			problems.put(rs.getInt("id_problem"), new Score(rs.getInt("tries"),
					rs.getInt("passed_time")));
		}

		ur.setProblems(problems);

		return ur;
	}

	@Override
	@Deprecated
	public UserRank get(int id) throws SQLException {
		System.out.println("DONT. USE. THIS.");
		return null;
	}

	@Override
	public boolean delete(int id) throws SQLException {
		return false;
	}

	@Override
	public void update(UserRank value) throws SQLException {
		connection = new ConnectionFactory().getConnection();

		String sql = "update RANKING set tries = ?, passed_time = ? where username = ? and id_problem = ? limit 1";
		PreparedStatement stmt = connection.prepareStatement(sql);

		stmt.setString(3, value.getUsername());

		for (Map.Entry<Integer, Score> entry : value.getProblems().entrySet()) {
			stmt.setInt(1, entry.getValue().getTries());
			stmt.setInt(2, entry.getValue().getPassedTime());
			stmt.setInt(4, entry.getKey());
			stmt.execute();
		}

		value.setProblems(this.get(value.getUsername()).getProblems());
		stmt.close();
		connection.close();
	}
}
