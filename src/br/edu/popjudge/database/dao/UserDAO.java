package br.edu.popjudge.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

		String sql = "insert into USER (id_User, username, password, dir) values (0, ?,?,?)";
		PreparedStatement stmt = connection.prepareStatement(sql);

		stmt.setString(1, value.getUsername());
		stmt.setString(2, value.getPassword());
		stmt.setString(3, value.getDir());

		stmt.execute();

		connection.close();
	}

	@Override
	public ArrayList<UserBean> getAll() throws SQLException {
		connection = new ConnectionFactory().getConnection();
		ArrayList<UserBean> list = new ArrayList<UserBean>();
		PreparedStatement stmt = this.connection
				.prepareStatement("select * from USER");
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			// criando o objeto Contato
			UserBean usuario = new UserBean();
			usuario.setIdUser(rs.getInt("id_user"));
			usuario.setUsername(rs.getString("username"));
			usuario.setPassword(rs.getString("password"));
			usuario.setDir(rs.getString("dir"));
			// adicionando o objeto à lista
			list.add(usuario);
		}

		rs.close();
		stmt.close();
		connection.close();

		return list;
	}

	@Override
	public UserBean get(int id) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		try {
			String sql = "select * from USER where id_user = ?";
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			UserBean usuario = new UserBean();
			if (rs.next()) {
				usuario.setIdUser(rs.getInt("id_user"));
				usuario.setUsername(rs.getString("username"));
				usuario.setPassword(rs.getString("password"));
				usuario.setDir(rs.getString("dir"));
			} else {
				return null;
			}

			rs.close();
			stmt.close();
			connection.close();

			return usuario;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public UserBean get(String username) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		try {
			String sql = "select * from USER where username = ?";
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();

			UserBean usuario = new UserBean();
			if (rs.next()) {
				usuario.setIdUser(rs.getInt("id_user"));
				usuario.setUsername(rs.getString("username"));
				usuario.setPassword(rs.getString("password"));
				usuario.setDir(rs.getString("dir"));
			} else {
				return null;
			}

			rs.close();
			stmt.close();
			connection.close();

			return usuario;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean delete(int id) throws SQLException {
		return false;
	}

	@Override
	public void update(UserBean value) throws SQLException {
		connection = new ConnectionFactory().getConnection();

		String sql = "update tb_usuario set password=? where id_user=?";

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, value.getPassword());
			stmt.setInt(2, value.getIdUser());
			stmt.execute();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		connection.close();

	}

}
