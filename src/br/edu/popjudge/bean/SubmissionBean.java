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

@ManagedBean(name="submission")
public class SubmissionBean {
	private UploadedFile code;
	private int problem;
	private int language;
	
	public UploadedFile getCode() {
		return code;
	}

	public void setCode(UploadedFile code) {
		this.code = code;
	}

	public int getProblem() {
		return problem;
	}

	public void setProblem(int problem) {
		this.problem = problem;
	}

	public int getLanguage() {
		return language;
	}

	public void setLanguage(int language) {
		this.language = language;
	}
	
	public void submit() throws IOException{
		if(this.code != null) {
			System.out.println(this.code.getFileName());
			System.out.println(this.language);
			System.out.println(this.problem);
			FacesMessage message = new FacesMessage("Enviado com sucesso","");
            FacesContext.getCurrentInstance().addMessage(null, message);
            
            FacesContext context = FacesContext.getCurrentInstance();
    		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
    		String dir = (String) session.getAttribute("dir");
            
            InputStream in = new BufferedInputStream(this.code.getInputstream());
            
            File file = new File (dir + "/" + this.code.getFileName());
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
