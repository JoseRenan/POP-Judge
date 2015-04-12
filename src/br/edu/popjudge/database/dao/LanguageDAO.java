package br.edu.popjudge.database.dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.edu.popjudge.database.ConnectionFactory;
import br.edu.popjudge.language.Language;

public class LanguageDAO implements Dao<Language> {

	private Connection connection;

	@Override
	public void insert(Language value) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		String sql = "insert into LANGUAGE(id_language, name) values (0, ?)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, value.getName());
		stmt.execute();
		stmt.close();
		connection.close();
	}

	@Override
	public ArrayList<Language> getAll() throws SQLException {
		connection = new ConnectionFactory().getConnection();
		String sql = "select * from LANGUAGE";

		PreparedStatement stmt = connection.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();
		ArrayList<Language> list = new ArrayList<Language>();

		while (rs.next()) {
			try {
				Constructor<?> c = Class.forName(
						"br.edu.popjudge.language." + rs.getString("name"))
						.getConstructor(int.class, String.class);// Requests the
																	// constructor
																	// of the
																	// class
																	// named
																	// "name".
				Language l = (Language) c.newInstance(rs.getInt("id_language"),
						rs.getString("name"));// Instantiates the class.
				list.add(l);
			} catch (NoSuchMethodException | SecurityException
					| ClassNotFoundException | InstantiationException
					| IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		rs.close();
		stmt.close();
		connection.close();
		return list;
	}

	@Override
	public Language get(int id) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		String sql = "select * from LANGUAGE where id_language = ?";

		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		
		Language l = null;
		
		if (rs.next()) {
			try {
				Constructor<?> c = Class.forName(
						"br.edu.popjudge.language." + rs.getString("name"))
						.getConstructor(int.class, String.class);// Requests the
																	// constructor
																	// of the
																	// class
																	// named
																	// "name".
				l = (Language) c.newInstance(rs.getInt("id_language"),
						rs.getString("name"));// Instantiates the class.
			} catch (NoSuchMethodException | SecurityException
					| ClassNotFoundException | InstantiationException
					| IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		rs.close();
		stmt.close();
		connection.close();
		return l;
	}

	@Override
	public boolean delete(int id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update(Language value) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		String sql = "update LANGUAGE set name = ? where id_language = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, value.getName());
		stmt.setInt(2, value.getIdLanguage());
		stmt.execute();
		stmt.close();
		connection.close();
	}

}
