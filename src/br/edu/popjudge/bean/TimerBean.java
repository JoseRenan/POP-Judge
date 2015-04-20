package br.edu.popjudge.bean;

import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import br.edu.popjudge.control.SubmissionIdGenerator;

@ManagedBean(name="timer")
@ApplicationScoped
public class TimerBean {
	private int horaRestante;
	private int minRestante;
	private Date inicioContestD;
	private Date fimContestD;
	private static Calendar inicioContest = Calendar.getInstance();;
	private static Calendar fimContest = Calendar.getInstance();
	
	public Calendar getInicioContest() {
		return inicioContest;
	}
	
	public Calendar getFimContest() {
		return fimContest;
	}
	
	public void criaContest(){
		fimContest.setTime(fimContestD);
		inicioContest.setTime(inicioContestD);
		FacesMessage message = new FacesMessage("Horário inserido", "");
		SubmissionIdGenerator.setNum(1);
        FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public static int currentMoment(){
		Calendar c = Calendar.getInstance();
		return ((c.get(Calendar.MINUTE) + (c.get(Calendar.HOUR_OF_DAY) * 60) - (inicioContest.get(Calendar.MINUTE) + (inicioContest.get(Calendar.HOUR_OF_DAY) * 60))));
	}
	
	public static boolean validaHorario(){
		Calendar c = Calendar.getInstance();
		if(inicioContest.get(Calendar.HOUR_OF_DAY) > c.get(Calendar.HOUR_OF_DAY) || fimContest.get(Calendar.HOUR_OF_DAY) <  c.get(Calendar.HOUR_OF_DAY))
			return false;
		
		if(inicioContest.get(Calendar.HOUR_OF_DAY) == c.get(Calendar.HOUR_OF_DAY)){
			if(inicioContest.get(Calendar.MINUTE) > c.get(Calendar.MINUTE))	
				return false;
		}
		
		if(fimContest.get(Calendar.HOUR_OF_DAY) ==  c.get(Calendar.HOUR_OF_DAY)){
			if(fimContest.get(Calendar.MINUTE) <=  c.get(Calendar.MINUTE))
				return false;
		}
		
		return true;
	}

	public Date getInicioContestD() {
		return inicioContestD;
	}

	public void setInicioContestD(Date inicioContestD) {
		this.inicioContestD = inicioContestD;
	}

	public Date getFimContestD() {
		return fimContestD;
	}

	public void setFimContestD(Date fimContestD) {
		this.fimContestD = fimContestD;
	}
	
	public int getMinRestante() {
		Calendar c = Calendar.getInstance();
		this.minRestante = ( (fimContest.get(Calendar.MINUTE) + (fimContest.get(Calendar.HOUR_OF_DAY) * 60)) - (c.get(Calendar.MINUTE) + (c.get(Calendar.HOUR_OF_DAY) * 60))) % 60;
		return this.minRestante;
	}

	public void setMinRestante(int minRestante) {
		this.minRestante = minRestante;
	}

	public int getHoraRestante() {
		Calendar c = Calendar.getInstance();
		this.horaRestante = ((fimContest.get(Calendar.MINUTE) + (fimContest.get(Calendar.HOUR_OF_DAY) * 60)) - (c.get(Calendar.MINUTE) + (c.get(Calendar.HOUR_OF_DAY) * 60))) / 60;
		return this.horaRestante;
	}

	public void setHoraRestante(int horaRestante) {
		this.horaRestante = horaRestante;
	}

	public static int totalTimeContest() {
		return ((fimContest.get(Calendar.MINUTE) + (fimContest.get(Calendar.HOUR_OF_DAY) * 60) - (inicioContest.get(Calendar.MINUTE) + (inicioContest.get(Calendar.HOUR_OF_DAY) * 60))));
	}

}
