package br.edu.popjudge.bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name="contest")
@ApplicationScoped
public class ContestBean {
	private String nome;
	private String info;
	
	public String getNome() throws IOException {
		
		File f = new File("info.txt");
		System.out.println(f.getAbsolutePath());
		
		BufferedReader buffRead = new BufferedReader(new FileReader("info.txt")); 
		
		String linha = "";
		
		for (int i = 0; i < 2; i++) { 
			linha = buffRead.readLine(); 
			if (linha != null && i == 0) {
				this.nome = linha; 
			} else
				break; 
		} 
		
		buffRead.close();
		
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getInfo() throws IOException {
		
		BufferedReader buffRead = new BufferedReader(new FileReader("info.txt")); 
		
		this.info = "";
		String linha = "";
		
		for (int i = 0; true; i++) { 
			linha = buffRead.readLine(); 
			if (linha != null && i != 0) {
				this.info += linha; 
			} else
				if(linha == null)
				    break; 
		} 
		
		buffRead.close();
		
		return info;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}
	
	public void salvar() throws IOException{
		FileWriter arq = new FileWriter("info.txt");
		PrintWriter gravarArq = new PrintWriter(arq);
		
		gravarArq.printf("%s\n", this.nome);
		gravarArq.printf("%s", this.info);
		
		gravarArq.close();
	}
}
