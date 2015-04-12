package br.edu.popjudge;

import javax.faces.bean.ManagedBean;

@ManagedBean(name="redirecionaProb")
public class RedirecionaProblemas {
	private String problema = "/resources/problemas/p1.xhtml";
	
	public String getProblema(){
		return this.problema;
	}
	
	public void setProblema(String problema){
		this.problema = problema;
	}
	
	public void setProblema1(){
		this.problema = "/resources/problemas/p1.xhtml";
	}
	
	public void setProblema2(){
		this.problema = "/resources/problemas/p2.xhtml";
	}
	
	public void setProblema3(){
		this.problema = "/resources/problemas/p3.xhtml";
	}
	
	public void setProblema4(){
		this.problema = "/resources/problemas/p4.xhtml";
	}
	
	public void setProblema5(){
		this.problema = "/resources/problemas/p5.xhtml";
	}
}
