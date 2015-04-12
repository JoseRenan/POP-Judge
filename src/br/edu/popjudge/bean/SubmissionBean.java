package br.edu.popjudge.bean;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.UploadedFile;

@ManagedBean(name="submit")
public class SubmissionBean {
	private UploadedFile codigo;
	private String problema;
	private String linguagem;
	
	public UploadedFile getCodigo() {
		return codigo;
	}

	public void setCodigo(UploadedFile codigo) {
		this.codigo = codigo;
	}

	public String getProblema() {
		return problema;
	}

	public void setProblema(String problema) {
		this.problema = problema;
	}

	public String getLinguagem() {
		return linguagem;
	}

	public void setLinguagem(String linguagem) {
		this.linguagem = linguagem;
	}
	
	public void submeter() throws IOException{
		if(this.codigo != null) {
			System.out.println(this.codigo.getFileName());
			System.out.println(this.linguagem);
			System.out.println(this.problema);
			FacesMessage message = new FacesMessage("Enviado com sucesso","");
            FacesContext.getCurrentInstance().addMessage(null, message);
            
            FacesContext context = FacesContext.getCurrentInstance();
    		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
    		String dir = (String) session.getAttribute("dir");
            
            InputStream in = new BufferedInputStream(this.codigo.getInputstream());
            
            File file = new File (dir + this.codigo.getFileName());
            if(!file.getParentFile().exists())
            	file.getParentFile().mkdirs();
            
            FileOutputStream fos = new FileOutputStream(file);
            while(in.available() != 0){
                fos.write(in.read());
            }
            fos.close();
        } else {
        	FacesMessage message = new FacesMessage("Problema na submissão", "");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
	}
}
