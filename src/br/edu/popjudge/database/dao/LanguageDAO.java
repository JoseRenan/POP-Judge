package br.edu.popjudge.database.dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.edu.popjudge.database.ConnectionFactory;
import br.edu.popjudge.language.Language;

public class LanguageDAO implements Dao<Language> {

	@Override
	public int insert(Language value) throws SQLException {
		Connection connection = new ConnectionFactory().getConnection();

		String sql = "INSERT INTO LANGUAGE(id_language, name) values (0, ?)";
		PreparedStatement statement = connection.prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS);

		statement.setString(1, value.getName());
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
	public ArrayList<Language> getAll() throws SQLException {
		Connection connection = new ConnectionFactory().getConnection();
		String sql = "select * from LANGUAGE";

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultSet = statement.executeQuery();
		ArrayList<Language> list = new ArrayList<Language>();

		while (resultSet.next()) {
			try {
				/* Requests the constructor of the class named "name". */
				Constructor<?> c = Class.forName(
						"br.edu.popjudge.language."
								+ resultSet.getString("name")).getConstructor(
						int.class, String.class);

				Language l = (Language) c.newInstance(
						resultSet.getInt("id_language"),
						resultSet.getString("name"));// Instantiates the class.
				
				list.add(l);
				
			} catch (NoSuchMethodException | SecurityException
					| ClassNotFoundException | InstantiationException
					| IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
		resultSet.close();
		statement.close();
		connection.close();
		return list;
	}

	@Override
	public Language get(int idLanguage) throws SQLException {
		Connection connection = new ConnectionFactory().getConnection();
		String sql = "select * from LANGUAGE where id_language = ?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, idLanguage);
		ResultSet resultSet = statement.executeQuery();

		Language language = null;

		if (resultSet.next()) {
			try {
				Constructor<?> c = Class.forName(
						"br.edu.popjudge.language." + resultSet.getString("name"))
						.getConstructor(int.class, String.class);// Requests the
																	// constructor
																	// of the
																	// class
																	// named
																	// "name".
				language = (Language) c.newInstance(resultSet.getInt("id_language"),
						resultSet.getString("name"));// Instantiates the class.
			} catch (NoSuchMethodException | SecurityException
					| ClassNotFoundException | InstantiationException
					| IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		resultSet.close();
		statement.close();
		connection.close();
		return language;
	}

	@Override
	@Deprecated
	public boolean delete(int id) throws SQLException {
		return false;
	}

	@Override
	public void update(Language value) throws SQLException {
		Connection connection = new ConnectionFactory().getConnection();
		String sql = "update LANGUAGE set name = ? where id_language = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setString(1, value.getName());
		statement.setInt(2, value.getIdLanguage());
		
		statement.execute();
		statement.close();
		connection.close();
	}

	@Override
	@Deprecated
	public void truncate() throws SQLException {
		// TODO Auto-generated method stub

	}

}
