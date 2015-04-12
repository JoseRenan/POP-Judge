package br.edu.popjudge.bean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.edu.popjudge.database.dao.UsuarioDAO;

@ManagedBean(name="usuario")
public class UsuarioBean {
	private String login;
	private String senha;
	private String nome;
	private int id_usuario;

	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String entrar() throws IOException, SQLException{
		if(this.login.equals("Admin") && this.senha.equals("admin123")){
			FacesContext context = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
            session.setAttribute("login", this.login);
	        session.setAttribute("senha", this.senha);
            this.login = null;
    		this.senha = null;
    		return "/webapp/admin/indexAdmin.xhtml?faces-redirect=true";
		}
		
		ArrayList<UsuarioBean> lista = new UsuarioDAO().getAll();
		for(int i = 0; i < lista.size(); i++){
			if(lista.get(i).login.equals(this.login) && lista.get(i).senha.equals(this.senha)){
				FacesContext context = FacesContext.getCurrentInstance();
		        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		        session.setAttribute("login", lista.get(i).getLogin());
		        session.setAttribute("senha", lista.get(i).getSenha());
		        session.setAttribute("id_usuario", lista.get(i).getId_usuario());
		        this.login = null;
				this.senha = null;
		        return "/webapp/user/indexUser.xhtml?faces-redirect=true";
			}
		}
			
		this.login = null;
		this.senha = null;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", " Usuário ou senha incorretos"));	
		return "";
	}
	
	public void sair() throws IOException{
		this.login = null;
		this.senha = null;
		invalidateSession();
		FacesContext.getCurrentInstance().getExternalContext().redirect("/POP-Judge/");
	}
	
	public static void invalidateSession() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
            .getExternalContext().getSession(false);
        session.removeAttribute("usuario");
        session.invalidate();
	}
	
	public void newUser() throws SQLException, IOException{
		try {
			UsuarioBean u = new UsuarioBean();
			u.setLogin(this.login);
			u.setNome(this.nome);
			u.setSenha(this.senha);
			UsuarioDAO ud = new UsuarioDAO();
			ud.insert(u);
			FacesContext.getCurrentInstance().getExternalContext().redirect("/POP-Judge/webapp/admin/newUser.xhtml");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário criado com sucesso", ""));
		} catch(SQLException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nome de login já utilizado", ""));
		}
	}
	
	public void mudarSenha() throws SQLException{
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		UsuarioBean u = new UsuarioBean();
		u.setSenha(this.senha);
		u.setId_usuario((Integer)session.getAttribute("id_usuario"));
		UsuarioDAO ud = new UsuarioDAO();
		ud.update(u);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Senha modificada com sucesso", ""));
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}
	
}
