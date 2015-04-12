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
		
		String sql = "insert into tb_usuario (nome_usuario,login,senha) values (?,?,?)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		
		stmt.setString(1, value.getNome());
		stmt.setString(2, value.getLogin());
		stmt.setString(3, value.getSenha());
		
		stmt.execute();
		
		connection.close();
	}

	@Override
	public ArrayList<UsuarioBean> getAll() throws SQLException {
		connection = new ConnectionFactory().getConnection();
		try {
	         ArrayList<UsuarioBean> lista = new ArrayList<UsuarioBean>();
	         PreparedStatement stmt = this.connection.prepareStatement("select * from tb_usuario");
	         ResultSet rs = stmt.executeQuery();
	 
	         while (rs.next()) {
	             // criando o objeto Contato
	             UsuarioBean usuario = new UsuarioBean();
	             usuario.setId_usuario(rs.getInt("id_usuario"));
	             usuario.setNome(rs.getString("nome_usuario"));
	             usuario.setSenha(rs.getString("senha"));
	             usuario.setLogin(rs.getString("login"));
	             
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(int id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update(UsuarioBean value) throws SQLException {
		connection = new ConnectionFactory().getConnection();
		
		String sql = "update tb_usuario set senha=? where id_usuario=?";
	 
	     try {
	         PreparedStatement stmt = connection
	                 .prepareStatement(sql);
	         stmt.setString(1, value.getSenha());
	         stmt.setInt(2, value.getId_usuario());
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
