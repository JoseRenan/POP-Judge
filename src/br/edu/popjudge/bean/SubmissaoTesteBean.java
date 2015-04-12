package br.edu.popjudge.bean;

import javax.faces.bean.ManagedBean;

@ManagedBean(name="submi")
public class SubmissaoTesteBean {
	
	private String code;
	private String linguagem;
	private String problema;
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getLinguagem() {
		return linguagem;
	}
	
	public void setLinguagem(String linguagem) {
		this.linguagem = linguagem;
	}
	
	public String getProblema() {
		return problema;
	}
	
	public void setProblema(String problema) {
		this.problema = problema;
	}
	
}
