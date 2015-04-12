package br.edu.popjudge.language;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import br.edu.popjudge.control.TimedShell;
import br.edu.popjudge.domain.Submission;
import br.edu.popjudge.exceptions.CompilationErrorException;
import br.edu.popjudge.exceptions.TimeLimitExceededException;

public class Python extends Language {
	
	@Override
	public boolean compile(Submission submission)
			throws CompilationErrorException {
		return true;
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
			writer.write("python " + submission.getSourceName() + " < " 
						 + submission.getProblem().getInput().getAbsolutePath() + " >> output.txt");
			writer.close();
			
			Process process = runtime.exec("chmod +x " + submission.getDir() + "/run.sh");
			process.waitFor();
			
			process = runtime.exec(submission.getDir() + "/run.sh");
			
			TimedShell shell = new TimedShell(process, submission.getProblem().getTimeLimit());
			shell.start();
			
			if(shell.isTimeOut()){
				throw new TimeLimitExceededException("TLE");
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