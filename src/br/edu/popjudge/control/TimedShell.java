package br.edu.popjudge.control;

import java.io.IOException;

/*
 * Codejudge
 * Copyright 2012, Sankha Narayan Guria (sankha93@gmail.com)
 * Licensed under MIT License.
 *
 * Codejudge Timer Shell that executes a command with a timeout period
 * Last Modification: Rerisson Matos
 * Revision: Gustavo Ribeiro
 */
public class TimedShell extends Thread {
	private Process process;
	private long timeLimit;
	private boolean timeOut;

	public TimedShell(Process process, long timeLimit) {
		super();
		this.process = process;
		this.timeLimit = timeLimit;
	}
	
	@Override
	public void run() {
		try{
			sleep(timeLimit);
		}catch(InterruptedException iex) {
			iex.printStackTrace();
		}
		
		try{
			process.exitValue();
			setTimeOut(false);
		}catch(IllegalThreadStateException iex) {
			setTimeOut(true);
			try{
				Util.killUnixProcess(process);
			}catch(IllegalArgumentException ilaex){
				ilaex.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean isTimeOut() {
		return timeOut;
	}

	public void setTimeOut(boolean timeOut) {
		this.timeOut = timeOut;
	}
}
