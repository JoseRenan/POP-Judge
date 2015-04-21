package br.edu.popjudge.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import br.edu.popjudge.bean.TimerBean;
import br.edu.popjudge.database.ConnectionFactory;
import br.edu.popjudge.domain.UserRank;

@ManagedBean
@ApplicationScoped
public class RankDAO implements Dao<UserRank> {
	Connection connection;
	private ArrayList<UserRank> all = null;
	
	@Override
	public void insert(UserRank value) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		String sql = "insert into "
				+ "RANKING(username, score, problem_1, problem_2, problem_3, problem_4, problem_5)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, value.getUsername());
		stmt.setInt(2, 0);
		stmt.setInt(3, 0);
		stmt.setInt(4, 0);
		stmt.setInt(5, 0);
		stmt.setInt(6, 0);
		stmt.setInt(7, 0);
		stmt.execute();
		stmt.close();
		connection.close();
	}

	@Override
	public ArrayList<UserRank> getAll() throws SQLException {
		
		boolean rankBlinded = new TimerBean().rankBlinded();
		
		if(!rankBlinded || all == null){
			connection = new ConnectionFactory().getConnection();
			this.all = new ArrayList<UserRank>();
			String sql = "select * from RANKING order by(score)";
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				this.all.add(new UserRank(rs.getString("username"), rs
						.getInt("score"), rs.getInt("problem_1"), rs
						.getInt("problem_2"), rs.getInt("problem_3"), rs
						.getInt("problem_4"), rs.getInt("problem_5")));
			}
			
			rs.close();
			stmt.close();
			connection.close();
			all.sort(new Comparator<UserRank>(){
	
				@Override
				public int compare(UserRank o1, UserRank o2) {
					if( o1.getPoints() == o2.getPoints())return 0;
					if( o1.getPoints() > o2.getPoints())return -1;
					return 1;
				}
			});
		}
		
		return all;
	}

	public UserRank get(String username) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		String sql = "select * from RANKING where username = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, username);
		
		ResultSet rs = stmt.executeQuery();
		UserRank ur= null;
		if(rs.next()){
			ur = new UserRank(rs.getString("username"), rs
					.getInt("score"), rs.getInt("problem_1"), rs
					.getInt("problem_2"), rs.getInt("problem_3"), rs
					.getInt("problem_4"), rs.getInt("problem_5"));
		}
		
		rs.close();
		stmt.close();
		connection.close();
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
		String sql = "update RANKING "
				+ "set score = ?, problem_1 = ?, problem_2 = ?, "
				+ "problem_3 = ?, problem_4 = ?, problem_5 = ? "
				+ "where username = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, value.getPoints());
		stmt.setInt(2, value.getProblem1());
		stmt.setInt(3, value.getProblem2());
		stmt.setInt(4, value.getProblem3());
		stmt.setInt(5, value.getProblem4());
		stmt.setInt(6, value.getProblem5());
		stmt.setString(7, value.getUsername());
		
		stmt.execute();
		stmt.close();
		connection.close();
	}

}
