package br.edu.popjudge.language;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import br.edu.popjudge.control.TimedShell;
import br.edu.popjudge.domain.Problem;
import br.edu.popjudge.domain.Submission;
import br.edu.popjudge.exceptions.CompilationErrorException;
import br.edu.popjudge.exceptions.TimeLimitExceededException;

public class Java extends Language {
	
	public Java(int idLanguage, String name) {
		super(idLanguage, name);
	}

	@Override
	public boolean compile(Submission submission)
			throws CompilationErrorException {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(submission.getDir() + "/compile.sh")));

			writer.write("cd \"" + submission.getDir() + "\"\n");
			writer.write("javac " + submission.getFileName() + " 2> "
					+ submission.getDir() + "/errors.txt");
			writer.close();

			Process process = runtime.exec("chmod +x " + submission.getDir()
					+ "/compile.sh");
			process.waitFor();

			process = runtime.exec(submission.getDir() + "/compile.sh");
			process.waitFor();

			File file = new File(submission.getFileName().substring(0, 
					submission.getFileName().length() - 5) + ".class");

			if (!file.exists()) {
				throw new CompilationErrorException("Compilation Error");
			}
			
			System.out.println(submission.getDir());
			
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
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(submission.getDir() + "/run.sh")));
			Problem p = submission.getProblem();
			writer.write("cd \"" + submission.getDir() + "\"\n");
			writer.write("chroot .\n");
			
			File file = new File(submission.getFileName());
			
			writer.write("java "
					+ file.getName().substring(0, file.getName().length() - 5) + " < "
					+ p.getInput() + " > " + submission.getDir()
					+ "/output.txt");
			writer.close();

			Process process = runtime.exec("chmod +x " + submission.getDir()
					+ "/run.sh");
			process.waitFor();

			process = runtime.exec(submission.getDir() + "/run.sh");

			TimedShell shell = new TimedShell(process, p.getTimeLimit());
			shell.start();
			process.waitFor();

			if (shell.isTimeOut()) {
				throw new TimeLimitExceededException("Time Limit Exceeded");
			}
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
