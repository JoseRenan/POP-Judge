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

public class Python extends Language {

	public Python(int idLanguage, String name) {
		super(idLanguage, name);
	}

	@Override
	public boolean compile(SubmissionBean submission)
			throws CompilationErrorException {
		return true;
	}

	@Override
	public boolean execute(SubmissionBean submission)
			throws TimeLimitExceededException {
		try {
			ProblemBean pb = new ProblemDAO().get(submission.getIdProblem());
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(submission.getDir() + "/run.sh")));
			writer.write("cd \"" + submission.getDir() + "\"\n");
			writer.write("chroot .\n");
			writer.write("python " + new File(submission.getFileName()).getName() + " < "
					+ pb.getInput() + " > "+ submission.getDir() +"/output.txt");
			writer.close();

			Process process = runtime.exec("chmod +x " + submission.getDir()
					+ "/run.sh");
			process.waitFor();

			process = runtime.exec(submission.getDir() + "/run.sh");

			TimedShell shell = new TimedShell(process, pb.getTimeLimit());
			shell.start();

			if (shell.isTimeOut()) {
				throw new TimeLimitExceededException("TLE");
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
