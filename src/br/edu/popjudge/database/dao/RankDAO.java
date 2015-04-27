package br.edu.popjudge.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.edu.popjudge.bean.TimerBean;
import br.edu.popjudge.database.ConnectionFactory;
import br.edu.popjudge.domain.Score;
import br.edu.popjudge.domain.UserRank;

@ManagedBean
@ViewScoped
public class RankDAO implements Dao<UserRank> {
	Connection connection;
	private ArrayList<UserRank> all = null;

	@Override
	public void insert(UserRank value) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		
		String sql = "insert into RANKING(username, id_problem, score) values (?, ?, ?)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		
		value.getProblems().removeAll(this.get(value.getUsername()).getProblems());
		
		stmt.setString(1, value.getUsername());
		for (Score p : value.getProblems()) {
			stmt.setInt(2, p.getId_problem());
			stmt.setInt(3, p.getScore());
			stmt.execute();
		}
		value.setProblems(this.get(value.getUsername()).getProblems());
		stmt.close();
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
			rs.close();
			stmt.close();
			connection.close();

		}

		return all;
	}

	public UserRank get(String username) throws SQLException {//Ok.
		connection = new ConnectionFactory().getConnection();
		
		String sql = "select * from RANKING where username = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		
		stmt.setString(1, username);
		
		ResultSet rs = stmt.executeQuery();
		UserRank ur = new UserRank();
		ur.setUsername(username);
		ArrayList<Score> problems = new ArrayList<Score>();
		while (rs.next()) {
			problems.add(new Score(rs.getInt("id_problem"), rs.getInt("score")));
		}
		ur.setProblems(problems);
		ur.setScore();
		return ur;
	}

	@Override
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
		
		String sql = "update RANKING set score = ? where username = ? and id_problem = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		
		stmt.setString(2, value.getUsername());
		for (Score s : value.getProblems()) {
			stmt.setInt(1, s.getScore());
			stmt.setInt(3, s.getId_problem());
			stmt.execute();
		}
		value.setProblems(this.get(value.getUsername()).getProblems());
		stmt.close();
		connection.close();
	}

}
