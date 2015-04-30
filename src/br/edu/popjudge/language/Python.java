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

public class Python extends Language {

	public Python(int idLanguage, String name) {
		super(idLanguage, name);
	}

	@Override
	public boolean compile(Submission submission)
			throws CompilationErrorException {
		return true;
	}

	@Override
	public boolean execute(Submission submission)
			throws TimeLimitExceededException {
		try {
			Problem p = submission.getProblem();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(submission.getDir() + "/run.sh")));
			writer.write("cd \"" + submission.getDir() + "\"\n");
			writer.write("chroot .\n");
			writer.write("python " + new File(submission.getFile().getAbsolutePath()).getName() + " < "
					+ p.getTestCase() + "/input.txt" + " > "+ submission.getDir() +"/output.txt");
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
