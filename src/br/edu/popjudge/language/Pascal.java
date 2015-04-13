package br.edu.popjudge.language;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import br.edu.popjudge.control.TimedShell;
import br.edu.popjudge.domain.SubmissionBean;
import br.edu.popjudge.exceptions.CompilationErrorException;
import br.edu.popjudge.exceptions.TimeLimitExceededException;

public class Pascal extends Language {

	public Pascal(int idLanguage, String name) {
		super(idLanguage, name);
	}

	@Override
	public boolean compile(SubmissionBean submission)
			throws CompilationErrorException {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(submission.getDir() + "/compile.sh")));

			writer.write("cd \"" + submission.getDir() + "\"\n");
			writer.write("fpc " + submission.getSourceName() + " 2> errors.txt");
			writer.close();

			Process process = runtime.exec("chmod +x " + submission.getDir()
					+ "/compile.sh");
			process.waitFor();

			process = runtime.exec(submission.getDir() + "/compile.sh");
			process.waitFor();

			File file = new File(submission.getSourceName().substring(0,
					submission.getSourceName().length() - 4));

			if (!file.exists()) {
				throw new CompilationErrorException("Compilation Error");
				// TODO
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
	public boolean execute(SubmissionBean submission)
			throws TimeLimitExceededException {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(submission.getDir() + "/run.sh")));
			writer.write("cd \"" + submission.getDir() + "\"\n");
			writer.write("chroot .\n");
			writer.write("./"
					+ submission.getSourceName().substring(0,
							submission.getSourceName().length() - 4) + " < "
					+ submission.getProblem().getInput().getAbsolutePath()
					+ " > output.txt");
			writer.close();

			Process process = runtime.exec("chmod +x " + submission.getDir()
					+ "/run.sh");
			process.waitFor();

			process = runtime.exec(submission.getDir() + "/run.sh");

			TimedShell shell = new TimedShell(process, submission.getProblem()
					.getTimeLimit());
			shell.start();

			if (shell.isTimeOut()) {
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
