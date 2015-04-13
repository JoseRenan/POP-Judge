package br.edu.popjudge.control;

import java.sql.SQLException;

import br.edu.popjudge.bean.SubmissionBean;
import br.edu.popjudge.database.dao.LanguageDAO;
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
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			return Veredict.SUBMISSION_ERROR;
		}catch(CompilationErrorException cep){
			return Veredict.COMPILATION_ERROR;
		} catch (TimeLimitExceededException e) {
			return Veredict.TIME_LIMIT_EXCEEDED;
		}
		return Veredict.ACCEPTED_ANSWER;
	}
}
