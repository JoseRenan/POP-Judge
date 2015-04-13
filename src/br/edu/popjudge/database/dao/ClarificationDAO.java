package br.edu.popjudge.database.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;

import br.edu.popjudge.bean.ClarificationBean;
import br.edu.popjudge.database.ConnectionFactory;

@ManagedBean(name="issuedao")
public class ClarificationDAO implements Dao<ClarificationBean> {

	private Connection connection;
	private ArrayList<ClarificationBean> all;
	private ArrayList<ClarificationBean> notReplied;
	
	@Override
	public void insert(ClarificationBean value) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		
		String sql = String.format("INSERT INTO CLARIFICATION (id_clarification, id_user, id_problem, issue) "
								 + "VALUES(0, %d, %d, '%s')", value.getIdUser(), value.getIdProblem(),
								 									value.getIssue());
		Statement statement = connection.createStatement();
		
		statement.execute(sql);
		
		statement.close();
		connection.close();
	}
	
	public void setAll(ArrayList<ClarificationBean> all){
		this.all = all;
	}
	
	public ArrayList<ClarificationBean> getNotReplied() throws SQLException {
		connection = new ConnectionFactory().getConnection();
		
		ArrayList<ClarificationBean> list = new ArrayList<ClarificationBean>();
		
		String sql = "SELECT * FROM CLARIFICATION WHERE answer IS NULL ORDER BY id_problem";
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		while(resultSet.next()){
			list.add(new ClarificationBean(resultSet.getInt("id_clarification"),
										   resultSet.getInt("id_user"),
										   resultSet.getInt("id_problem"),
										   resultSet.getString("issue"),
										   resultSet.getString("answer")));
		}
		
		resultSet.close();
		statement.close();
		connection.close();
		
		this.notReplied = list;
		
		return notReplied;
	}
	
	@Override
	public ArrayList<ClarificationBean> getAll() throws SQLException {
		connection = new ConnectionFactory().getConnection();
		
		ArrayList<ClarificationBean> list = new ArrayList<ClarificationBean>();
		
		String sql = "SELECT * FROM CLARIFICATION ORDER BY id_problem";
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		while(resultSet.next()){
			list.add(new ClarificationBean(resultSet.getInt("id_clarification"),
										   resultSet.getInt("id_user"),
										   resultSet.getInt("id_problem"),
										   resultSet.getString("issue"),
										   resultSet.getString("answer")));
		}
		
		resultSet.close();
		statement.close();
		connection.close();
		
		this.all = list;
		
		return all;
	}

	@Override
	public ClarificationBean get(int id) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		
		String sql = String.format("SELECT * FROM CLARIFICATION WHERE id_clarification = %d", id);
	
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		ClarificationBean clarificationBean = null; 
		
		if(resultSet.next()){
			clarificationBean = new ClarificationBean(resultSet.getInt("id_clarification"),
					   								  resultSet.getInt("id_user"),
					   								  resultSet.getInt("id_problem"),
					   								  resultSet.getString("issue"),
					   								  resultSet.getString("answer"));
		}
			
		resultSet.close();
		statement.close();
		connection.close();
		
		return clarificationBean;
	}

	@Override
	public boolean delete(int id) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		
		String sql = String.format("DELETE FROM CLARIFICATION WHERE id_clarification = %d", id);
	
		Statement statement = connection.createStatement();
		
		boolean ret = statement.execute(sql);
	
		statement.close();
		connection.close();
		
		return ret;
	}

	@Override
	public void update(ClarificationBean value) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		
		String sql = String.format("UPDATE CLARIFICATION SET id_user = %d, id_problem = %d, issue = '%s', answer = '%s' "
								 + "WHERE id_clarification = %d", value.getIdUser(), value.getIdProblem(), value.getIssue(), value.getAnswer(), value.getIdClarification());
	
		Statement statement = connection.createStatement();
		
		statement.execute(sql);
		
		statement.close();
		connection.close();
	}
}