package br.edu.popjudge.control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;

import br.edu.popjudge.bean.ProblemBean;
import br.edu.popjudge.bean.SubmissionBean;
import br.edu.popjudge.database.dao.LanguageDAO;
import br.edu.popjudge.database.dao.ProblemDAO;
import br.edu.popjudge.domain.Veredict;
import br.edu.popjudge.exceptions.CompilationErrorException;
import br.edu.popjudge.exceptions.TimeLimitExceededException;
import br.edu.popjudge.language.Language;

public class Judge {
	
	public Veredict judge(SubmissionBean submission){
		try{
			Language lang = new LanguageDAO().get(submission.getIdLanguage()); 
			lang.compile(submission);
			lang.execute(submission);
			
			ProblemBean problem = new ProblemDAO().get(submission.getIdProblem());
			Runtime runtime = Runtime.getRuntime();
			
			BufferedWriter writer = new BufferedWriter(
									new OutputStreamWriter(
									new FileOutputStream(submission.getDir() + "/diff.sh")));
			
			writer.write("diff " + submission.getDir() + "/output.txt" + " " + 
						 problem.getOutput() + " > " + submission.getDir() + "/diff.txt"); 
			writer.close();
			
			Process process = runtime.exec("chmod +x " + submission.getDir() + "/diff.sh");
			process.waitFor();
			
			process = runtime.exec(submission.getDir() + "/diff.sh");
			process.waitFor();
			
			File diff = new File(submission.getDir() + "/diff.txt");
			
			if(diff.length() > 0) return Veredict.WRONG_ANSWER;
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			return Veredict.SUBMISSION_ERROR;
		}catch(CompilationErrorException cep){
			return Veredict.COMPILATION_ERROR;
		} catch (TimeLimitExceededException e) {
			return Veredict.TIME_LIMIT_EXCEEDED;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return Veredict.ACCEPTED_ANSWER;
	}
}
