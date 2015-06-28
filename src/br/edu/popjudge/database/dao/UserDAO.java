package br.edu.popjudge.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;

import br.edu.popjudge.database.ConnectionFactory;
import br.edu.popjudge.domain.User;

@ManagedBean
public class UserDAO implements Dao<User> {
	private Connection connection;
	
	@Override
	public int insert(User value) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		
		String sql = "INSERT INTO USER(id_user, username, password, email, "
				+ "full_name, city, college, dir)"
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		statement.setInt(1, 0);
		statement.setString(2, value.getUsername());
		statement.setString(3, value.getPassword());
		statement.setString(4, value.getEmail());
		statement.setString(5, value.getFullName());
		statement.setString(6, value.getCity());
		statement.setString(7, value.getCollege());
		statement.setString(8, value.getDir());
		
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
	public ArrayList<User> getAll() throws SQLException {
		connection = new ConnectionFactory().getConnection();
		
		String sql = "SELECT * FROM USER WHERE username <> 'Admin'";
		
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		ArrayList<User> list = new ArrayList<User>();
		
		while(resultSet.next()){
			list.add(new User(resultSet.getInt("id_user"),
							  resultSet.getString("username"),
							  resultSet.getString("password"),
							  resultSet.getString("email"),
							  resultSet.getString("full_name"),
							  resultSet.getString("city"),
							  resultSet.getString("college"),
							  resultSet.getString("dir")));
		}
		
		resultSet.close();
		statement.close();
		connection.close();
		
		return list;
	}

	@Override
	public User get(int id) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		
		String sql = String.format("SELECT * FROM USER WHERE id_user = %d", id);
		
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		User ret = null;
		
		if(resultSet.next()){
			ret = new User(resultSet.getInt("id_user"),
						   resultSet.getString("username"),
						   resultSet.getString("password"),
						   resultSet.getString("email"),
						   resultSet.getString("full_name"),
						   resultSet.getString("city"),
						   resultSet.getString("college"),
						   resultSet.getString("dir"));
		}
		
		resultSet.close();
		statement.close();
		connection.close();
		
		return ret;
	}
	
	public User get(String username) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		
		String sql = String.format("SELECT * FROM USER WHERE username = '%s'", username);
		
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		User ret = null;
		
		if(resultSet.next()){
			ret = new User(resultSet.getInt("id_user"),
						   resultSet.getString("username"),
						   resultSet.getString("password"),
						   resultSet.getString("email"),
						   resultSet.getString("full_name"),
						   resultSet.getString("city"),
						   resultSet.getString("college"),
						   resultSet.getString("dir"));
		}
		
		resultSet.close();
		statement.close();
		connection.close();
		
		return ret;
	}

	@Override
	public boolean delete(int id) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		
		String sql = String.format("DELETE FROM USER WHERE id_user = %d", id);
		
		Statement statement = connection.createStatement();
		
		return statement.execute(sql);
	}

	@Override
	public void update(User value) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		
		String sql = "UPDATE USER SET password = ?, email = ?,"
				+ "full_name = ?, city = ?, college = ?, dir = ? WHERE id_user = ?";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setString(1, value.getPassword());
		statement.setString(2, value.getEmail());
		statement.setString(3, value.getFullName());
		statement.setString(4, value.getCity());
		statement.setString(5, value.getCollege());
		statement.setString(6, value.getDir());
		statement.setInt(7, value.getIdUser());
		
		statement.execute();
		
		statement.close();
		connection.close();
	}

	@Override
	public void truncate() throws SQLException {
		connection = new ConnectionFactory().getConnection();

		String sql = "delete from USER where username <> 'Admin'";
			
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.execute();
		statement.close();
		
		connection.close();
	}
}
