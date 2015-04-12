package br.edu.popjudge.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;

import br.edu.popjudge.bean.UsuarioBean;
import br.edu.popjudge.database.ConnectionFactory;

@ManagedBean
public class UsuarioDAO implements Dao<UsuarioBean> {
	
	private ArrayList<UsuarioBean> lista;
	private Connection connection;
	
	@Override
	public void insert(UsuarioBean value) throws SQLException {
		
		connection = new ConnectionFactory().getConnection();
		
		String sql = "insert into USER (0, username, password, dir) values (?,?,?)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		
		stmt.setString(1, value.getUsername());
		stmt.setString(2, value.getPassword());
		stmt.setString(3, value.getDir());
		
		stmt.execute();
		
		connection.close();
	}
	

	@Override
	public ArrayList<UsuarioBean> getAll() throws SQLException {
		connection = new ConnectionFactory().getConnection();
		try {
	         ArrayList<UsuarioBean> lista = new ArrayList<UsuarioBean>();
	         PreparedStatement stmt = this.connection.prepareStatement("select * from USER");
	         ResultSet rs = stmt.executeQuery();
	 
	         while (rs.next()) {
	             // criando o objeto Contato
	             UsuarioBean usuario = new UsuarioBean();
	             usuario.setIdUser(rs.getInt("id_user"));
	             usuario.setUsername(rs.getString("username"));
	             usuario.setPassword(rs.getString("password"));
	             usuario.setDir(rs.getString("dir"));
	             // adicionando o objeto à lista
	             lista.add(usuario);
	         }
	         
	         rs.close();
	         stmt.close();
	         connection.close();
	         
	         return lista;
	     
		 } catch (SQLException e) {
	         throw new RuntimeException(e);
	     }
	}

	@Override
	public UsuarioBean get(int id) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		try {
			 String sql = "select * from USER where id_user = ?";
	         PreparedStatement stmt = this.connection.prepareStatement(sql);
	         stmt.setInt(1, id);
	         ResultSet rs = stmt.executeQuery();
	
	         UsuarioBean usuario = new UsuarioBean();
	         if(rs.next()){
	             usuario.setIdUser(rs.getInt("id_user"));
	             usuario.setUsername(rs.getString("username"));
	             usuario.setPassword(rs.getString("password"));
	             usuario.setDir(rs.getString("dir"));
	         }else{
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
	
	public UsuarioBean get(String username) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		try {
			 String sql = "select * from USER where username = ?";
	         PreparedStatement stmt = this.connection.prepareStatement(sql);
	         stmt.setString(1, username);
	         ResultSet rs = stmt.executeQuery();
	
	         UsuarioBean usuario = new UsuarioBean();
	         if(rs.next()){
	             usuario.setIdUser(rs.getInt("id_user"));
	             usuario.setUsername(rs.getString("username"));
	             usuario.setPassword(rs.getString("password"));
	             usuario.setDir(rs.getString("dir"));
	         }else{
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
	public void update(UsuarioBean value) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		
		String sql = "update tb_usuario set password=? where id_user=?";
	 
	     try {
	         PreparedStatement stmt = connection
	                 .prepareStatement(sql);
	         stmt.setString(1, value.getPassword());
	         stmt.setInt(2, value.getIdUser());
	         stmt.execute();
	    
	     } catch (SQLException e) {
	         throw new RuntimeException(e);
	     }
	     
	     connection.close();
		
	}

	public ArrayList<UsuarioBean> getLista() {
		return lista;
	}

	public void setLista() throws SQLException {
		this.lista = this.getAll();
	}

}
