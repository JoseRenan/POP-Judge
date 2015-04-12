package br.edu.popjudge.language;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import br.edu.popjudge.control.TimedShell;
import br.edu.popjudge.domain.Submission;
import br.edu.popjudge.exceptions.CompilationErrorException;
import br.edu.popjudge.exceptions.TimeLimitExceededException;

public class Java extends Language {
	
	@Override
	public boolean compile(Submission submission)
			throws CompilationErrorException {
		try {
			BufferedWriter writer = new BufferedWriter(
					 new OutputStreamWriter(
					 new FileOutputStream(submission.getDir() + "/compile.sh")));
			
			writer.write("cd \"" + submission.getDir() + "\"\n");
			writer.write("javac " + submission.getSourceName() + " 2> errors.txt");
			writer.close();
			
			Process process = runtime.exec("chmod +x " + submission.getDir() + "/compile.sh");
			process.waitFor();
			
			process = runtime.exec(submission.getDir() + "/compile.sh");
			process.waitFor();
			
			File file = new File(submission.getSourceName().substring(0, submission.getSourceName().length() - 5));
			
			if (!file.exists()){
				throw new CompilationErrorException("Compilation Error");
				//TODO
			}
			
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean execute(Submission submission)
			throws TimeLimitExceededException {
		try {
			BufferedWriter writer = new BufferedWriter(
									new OutputStreamWriter(
									new FileOutputStream(submission.getDir() + "/run.sh")));
			writer.write("cd \"" + submission.getDir() + "\"\n");
			writer.write("chroot .\n");
			writer.write("java " + submission.getSourceName().substring(0, submission.getSourceName().length() - 5) + " < " 
						 + submission.getProblem().getInput().getAbsolutePath() + " > output.txt");
			writer.close();
			
			Process process = runtime.exec("chmod +x " + submission.getDir() + "/run.sh");
			process.waitFor();
			
			process = runtime.exec(submission.getDir() + "/run.sh");
			
			TimedShell shell = new TimedShell(process, submission.getProblem().getTimeLimit());
			shell.start();
			
			if(shell.isTimeOut()){
				throw new TimeLimitExceededException("Time Limit Exceeded");
				// TODO return the TLE enum field
			}
			
			process.waitFor();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
}
