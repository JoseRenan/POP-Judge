package br.edu.popjudge.domain;

/* Each user has a directory that records 
 * all of your submissions*/

public class User {
	public int idUser;
	public String username;
	public String password;
	public String email;
	public String fullName;
	public String city;
	public String college;
	public String dir;
	
	public User() {}

	public User(int idUser, String username, String password, String email,
			String fullName, String city, String college, String dir) {
		super();
		this.idUser = idUser;
		this.username = username;
		this.password = password;
		this.email = email;
		this.fullName = fullName;
		this.city = city;
		this.college = college;
		this.dir = dir;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}
}
