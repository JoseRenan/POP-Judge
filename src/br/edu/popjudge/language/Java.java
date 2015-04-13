package br.edu.popjudge.language;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;

import br.edu.popjudge.bean.ProblemBean;
import br.edu.popjudge.bean.SubmissionBean;
import br.edu.popjudge.control.TimedShell;
import br.edu.popjudge.database.dao.ProblemDAO;
import br.edu.popjudge.exceptions.CompilationErrorException;
import br.edu.popjudge.exceptions.TimeLimitExceededException;

public class Java extends Language {

	public Java(int idLanguage, String name) {
		super(idLanguage, name);
	}

	@Override
	public boolean compile(SubmissionBean submission)
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
					submission.getFileName().length() - 5));

			if (!file.exists()) {
				throw new CompilationErrorException("Compilation Error");
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
			ProblemBean pb = new ProblemDAO().get(submission.getIdProblem());
			writer.write("cd \"" + submission.getDir() + "\"\n");
			writer.write("chroot .\n");
			writer.write("java "
					+ (submission.getFileName().substring(0,
							submission.getFileName().length() - 5)) + " < "
					+ pb.getInput()
					+ " > " + submission.getDir() + "/output.txt");
			writer.close();

			Process process = runtime.exec("chmod +x " + submission.getDir()
					+ "/run.sh");
			process.waitFor();

			process = runtime.exec(submission.getDir() + "/run.sh");

			TimedShell shell = new TimedShell(process, pb.getTimeLimit());
			shell.start();

			if (shell.isTimeOut()) {
				throw new TimeLimitExceededException("Time Limit Exceeded");
			}

			process.waitFor();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
