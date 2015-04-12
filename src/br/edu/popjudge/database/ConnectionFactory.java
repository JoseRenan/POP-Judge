package br.edu.popjudge.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	public Connection getConnection() throws SQLException{
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		try {
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/pop_judge", "root", "admin123");
		} catch (SQLException exe){
			System.out.println("Nao foi posivel conectar com o Banco de Dados, tente novamente mais tarde");
			return null;
		}
	}
	
}
