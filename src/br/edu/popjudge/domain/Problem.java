package br.edu.popjudge.domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Problem {
	private int idProblem;
	private int scorePoints;
	private String title;
	private long timeLimit;
	private File dir;
	private File testCase;
	private String author;
	private String university;
	private String inputDescription;
	private String outputDescription;
	private String description;
	private String notes;
	private String inputSample1;
	private String inputSample2;
	private String inputSample3;
	private String outputSample1;
	private String outputSample2;
	private String outputSample3;

	public Problem() { }

	public Problem(int idProblem, int scorePoints, String title,
			long timeLimit, File dir) {
		super();
		this.idProblem = idProblem;
		this.scorePoints = scorePoints;
		this.title = title;
		this.timeLimit = timeLimit;
		this.dir = dir;
	}
	
	public Problem(int idProblem, int scorePoints, String title,
			long timeLimit, File dir, File testCase) {
		super();
		this.idProblem = idProblem;
		this.scorePoints = scorePoints;
		this.title = title;
		this.timeLimit = timeLimit;
		this.dir = dir;
		this.testCase = testCase;
	}
	
	public String getAuthor() throws IOException {        		      
		BufferedReader buffRead = new BufferedReader(new FileReader(this.getDir() + "/header.txt")); 
		
		this.author = "";
		String linha = "";
		
		for (int i = 0; true; i++) { 
			linha = buffRead.readLine(); 
			if (linha != null && i == 1) {
				this.author += linha; 
			} else
				if(linha == null || i > 1)
				    break;
		} 
		
		buffRead.close();
		
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getUniversity() throws IOException {
		
		BufferedReader buffRead = new BufferedReader(new FileReader(this.getDir() + "/header.txt")); 
		
		this.university = "";
		String linha = "";
		
		for (int i = 0; true; i++) { 
			linha = buffRead.readLine(); 
			if (linha != null && i == 2) {
				this.university += linha; 
			} else
				if(linha == null || i > 2)
				    break;
		} 
		
		buffRead.close();
		
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getInputDescription() throws IOException {
		
		inputDescription = getFileContent(this.dir + "/inputDescription.txt");
		
		return inputDescription;
	}

	public void setInputDescription(String inputDescription) {
		this.inputDescription = inputDescription;
	}

	public String getOutputDescription() throws IOException {
		
		outputDescription = getFileContent(this.dir + "/outputDescription.txt");
		
		return outputDescription;
	}

	public void setOutputDescription(String outputDescription) {
		this.outputDescription = outputDescription;
	}

	public String getDescription() throws IOException {
		
		description = getFileContent(this.dir + "/description.txt");
		
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNotes() throws IOException {
		
		notes = getFileContent(this.dir + "/notes.txt");
		
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getInputSample1() throws IOException {
		
		inputSample1 = getFileContent(this.dir + "/inputSample1.txt");
		
		return inputSample1;
	}

	public void setInputSample1(String inputSample1) {
		this.inputSample1 = inputSample1;
	}

	public String getInputSample2() throws IOException {
		
		inputSample2 = getFileContent(this.dir + "/inputSample2.txt");
		
		return inputSample2;
	}

	public void setInputSample2(String inputSample2) {
		this.inputSample2 = inputSample2;
	}

	public String getInputSample3() throws IOException {
		
		inputSample3 = getFileContent(this.dir + "/inputSample3.txt");
		
		return inputSample3;
	}

	public void setInputSample3(String inputSample3) {
		this.inputSample3 = inputSample3;
	}

	public String getOutputSample1() throws IOException {
		
		outputSample1 = getFileContent(this.dir + "/outputSample1.txt");
		
		return outputSample1;
	}

	public void setOutputSample1(String outputSample1) {
		this.outputSample1 = outputSample1;
	}

	public String getOutputSample2() throws IOException {
		
		outputSample2 = getFileContent(this.dir + "/outputSample2.txt");
		
		return outputSample2;
	}

	public void setOutputSample2(String outputSample2) {
		this.outputSample2 = outputSample2;
	}

	public String getOutputSample3() throws IOException {
		
		outputSample3 = getFileContent(this.dir + "/outputSample3.txt");
		
		return outputSample3;
	}

	public void setOutputSample3(String outputSample3) {
		this.outputSample3 = outputSample3;
	}

	public int getIdProblem() {
		return this.idProblem;
	}

	public void setIdProblem(int idProblem) {
		this.idProblem = idProblem;
	}

	public int getScorePoints() throws IOException {
		
		BufferedReader buffRead = new BufferedReader(new FileReader(this.getDir() + "/header.txt"));
		
		String linha = "";
		
		for (int i = 0; true; i++) { 
			linha = buffRead.readLine(); 
			if (linha != null && i == 4) {
				this.scorePoints = Integer.parseInt(linha); 
			} else
				if(linha == null || i > 4)
				    break;
		} 
		
		buffRead.close();
		
		return this.scorePoints;
	}

	public void setScorePoints(int scorePoints) {
		this.scorePoints = scorePoints;
	}

	public String getTitle() throws IOException {
		
		BufferedReader buffRead = new BufferedReader(new FileReader(this.getDir() + "/header.txt")); 
		
		this.title = "";
		String linha = "";
		
		for (int i = 0; true; i++) { 
			linha = buffRead.readLine(); 
			if (linha != null && i == 0) {
				this.title += linha; 
			} else
				if(linha == null || i > 0)
				    break;
		} 
		
		buffRead.close();
		
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getTimeLimit() throws IOException {
		
		BufferedReader buffRead = new BufferedReader(new FileReader(this.getDir() + "/header.txt"));
		
		String linha = "";
		
		for (int i = 0; true; i++) { 
			linha = buffRead.readLine(); 
			if (linha != null && i == 3) {
				this.timeLimit = Long.parseLong(linha); 
			} else
				if(linha == null || i > 3)
				    break;
		} 
		
		buffRead.close();
		
		return timeLimit;
	}

	public void setTimeLimit(long timeLimit) {
		this.timeLimit = timeLimit;
	}

	public File getDir() {
		return this.dir;
	}

	public void setDir(File dir) {
		this.dir = dir;
	}

	public File getTestCase() {
		return this.testCase;
	}

	public void setTestCase(File testCase) {
		this.testCase = testCase;
	}
	
	private static String getFileContent(String dir) throws IOException{
		BufferedReader buffRead = new BufferedReader(new FileReader(dir)); 
		String string = buffRead.readLine(); 
		
		while (string != null) { 
			string = buffRead.readLine(); 
		}
		
		buffRead.close();
		
		return string;
	}
}
