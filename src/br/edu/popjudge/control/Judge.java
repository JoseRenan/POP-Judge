package br.edu.popjudge.control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.sql.Timestamp;

import br.edu.popjudge.database.dao.LanguageDAO;
import br.edu.popjudge.database.dao.ProblemDAO;
import br.edu.popjudge.database.dao.UserDAO;
import br.edu.popjudge.domain.Problem;
import br.edu.popjudge.domain.Submission;
import br.edu.popjudge.domain.Veredict;
import br.edu.popjudge.exceptions.CompilationErrorException;
import br.edu.popjudge.exceptions.TimeLimitExceededException;
import br.edu.popjudge.language.Language;

/* Python Solutions will receive WA (Wrong Answer) 
 * instead of CE (Compilation Error) in some cases.*/

public class Judge {
	
	public Veredict judge(Submission submission){
		Language lang = submission.getLanguage();
		Problem problem = submission.getProblem();
		Runtime runtime;
		
		try{
			lang.compile(submission);
		}catch(CompilationErrorException ceex) {
			return Veredict.COMPILATION_ERROR;
		}
		
		try{
			for(int i = 1; ; i++){
				/* Creates a file test case with the current test case*/
				File testCase = new File(problem.getDir() + "/TestCases/" + i);
				
				/* If the file test case specified above don't 
				 * exists then no more test cases to be processed  */
				if(!testCase.exists()) break;
				
				/* Set the current test case*/
				problem.setTestCase(testCase);  
				
				/* Execute the sourcecode submission with the current 
				 * test case*/
				lang.execute(submission);
				
				/* Creates a script that will run the diff operation:
				 * The diff operation is described as follows:
				 * 1 - Uses diff UNIX command to check the diferences between two text files
				 * 	   (The output file test case and the submission output file for this test case)
				 * 2 - After this uses the '>' UNIX operator to redirect the result of the diff 
				 * 	   operation to other text file (diff.txt) */
				BufferedWriter writer = new BufferedWriter(
										new OutputStreamWriter(
										new FileOutputStream(submission.getDir() + "/diff.sh"))); 
				
				writer.write("diff " + submission.getDir() + "/output.txt" + " " + 
						 problem.getTestCase() + "/output.txt > " + submission.getDir() + "/diff.txt");
				
				writer.close();
				
				runtime = Runtime.getRuntime();
				
				/* Gives permission to executing of the diff script */
				Process process = runtime.exec("chmod +x " + submission.getDir() + "/diff.sh");
				process.waitFor();
				
				/* Executes the diff script*/
				process = runtime.exec(submission.getDir() + "/diff.sh");
				process.waitFor();
				
				/* This file is the same file diff specified above*/
				File diff = new File(submission.getDir() + "/diff.txt");
				
				/* If the content of the diff file is not empty then
				 * the submission should be labeled with WA veredict */
				if(diff.length() > 0) return Veredict.WRONG_ANSWER;
			}
		} catch (TimeLimitExceededException e) {
			return Veredict.TIME_LIMIT_EXCEEDED;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		/* If execution come to that then there was no error, in other words
		 * the submission should be labeled with the AC veredict */
		return Veredict.ACCEPTED_ANSWER;
	}
}
