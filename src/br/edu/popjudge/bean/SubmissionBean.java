package br.edu.popjudge.bean;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;

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
	private int id_submission;
	private int id_user;
	private Timestamp time_submission;
	private String veredict;
	
	public SubmissionBean(int id_submission, int id_user, int problem, int language,
			Timestamp time_submission, String veredict) {
		this.problem = problem;
		this.language = language;
		this.id_submission = id_submission;
		this.id_user = id_user;
		this.time_submission = time_submission;
		this.veredict = veredict;		
	}
	
	public SubmissionBean() {	
	}

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
	
	public int getId_submission() {
		return id_submission;
	}

	public void setId_submission(int id_submission) {
		this.id_submission = id_submission;
	}

	public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}
	
	public Timestamp getTime_submission() {
		return time_submission;
	}

	public void setTime_submission(Timestamp time_submission) {
		this.time_submission = time_submission;
	}

	public String getVeredict() {
		return veredict;
	}

	public void setVeredict(String veredict) {
		this.veredict = veredict;
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
