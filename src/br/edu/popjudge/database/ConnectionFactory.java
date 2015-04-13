package br.edu.popjudge.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	public Connection getConnection() throws SQLException{
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
//		try {
//			return DriverManager.getConnection("jdbc:mysql://poocare.ddns.net:3306/POP_JUDGE", "root", "");
//		} catch (SQLException exe){
			try {
				return DriverManager.getConnection("jdbc:mysql://localhost:3306/POP_JUDGE", "root", "");
			} catch (SQLException exep){
				System.out.println("Nao foi posivel conectar com o Banco de Dados, tente novamente mais tarde");
				return null;
			}
//		}
	}
	
}
