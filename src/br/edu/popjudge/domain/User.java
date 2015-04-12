package br.edu.popjudge.domain;

public class User {
	private int idUser;
	private String name;
	private String login;
	private String dir;
	
	public User(int idUser, String name, String login, String dir) {
		super();
		this.idUser = idUser;
		this.name = name;
		this.login = login;
		this.dir = dir;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}
}
