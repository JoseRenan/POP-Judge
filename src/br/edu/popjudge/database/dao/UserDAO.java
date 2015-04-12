package br.edu.popjudge.database.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;

import br.edu.popjudge.bean.UserBean;
import br.edu.popjudge.database.ConnectionFactory;

@ManagedBean
public class UserDAO implements Dao<UserBean> {
	private Connection connection;
	
	@Override
	public void insert(UserBean value) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		
		String sql = String.format("INSERT INTO USER(id_user, username, password, dir) "
				+ "VALUES(0, '%s', '%s', '%s')", value.getUsername(), value.getPassword(), value.getDir());
		
		Statement statement = connection.createStatement();
		
		statement.execute(sql);
		
		statement.close();
		connection.close();
	}

	@Override
	public ArrayList<UserBean> getAll() throws SQLException {
		connection = new ConnectionFactory().getConnection();
		ArrayList<UserBean> list = new ArrayList<UserBean>();
		
		String sql = "SELECT * FROM USER";
		
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		while(resultSet.next()){
			UserBean userBean = new UserBean();
			userBean.setIdUser(resultSet.getInt("id_user"));
			userBean.setUsername(resultSet.getString("username"));
			userBean.setPassword(resultSet.getString("password"));
			userBean.setDir(resultSet.getString("dir"));
			
			list.add(userBean);
		}
		
		resultSet.close();
		statement.close();
		connection.close();
		
		return list;
	}

	@Override
	public UserBean get(int id) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		
		String sql = String.format("SELECT * FROM USER WHERE id_user = %d", id);
		
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		UserBean userBean = new UserBean();

		if(resultSet.next()){
			userBean.setIdUser(resultSet.getInt("id_user"));
			userBean.setUsername(resultSet.getString("username"));
			userBean.setPassword(resultSet.getString("password"));
			userBean.setDir(resultSet.getString("dir"));
		}
		
		resultSet.close();
		statement.close();
		connection.close();
		
		return userBean;
	}

	public UserBean get(String username) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		
		String sql = String.format("SELECT * FROM USER WHERE username = '%s'", username);
		
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		UserBean userBean = new UserBean();

		if(resultSet.next()){
			userBean.setIdUser(resultSet.getInt("id_user"));
			userBean.setUsername(resultSet.getString("username"));
			userBean.setPassword(resultSet.getString("password"));
			userBean.setDir(resultSet.getString("dir"));
		}
		
		resultSet.close();
		statement.close();
		connection.close();
		
		return userBean;
	}

	@Override
	public boolean delete(int id) throws SQLException {
		return false;
	}

	@Override
	public void update(UserBean value) throws SQLException {
		connection = new ConnectionFactory().getConnection();

		String sql = String.format("UPDATE USER SET password = '%s' WHERE id_user = %d", value.getPassword(), value.getIdUser());
		Statement statement = connection.createStatement();
		
		statement.execute(sql);
		
		statement.close();
		connection.close();
	}
}
