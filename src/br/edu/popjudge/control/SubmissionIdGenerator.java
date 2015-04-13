package br.edu.popjudge.control;

public final class SubmissionIdGenerator {
	static int num = 1;
	public static int getNum() {
		return num;
	}
	public static void setNum(int num) {
		SubmissionIdGenerator.num = num;
	}
	public static int getSubmissionId(){
		return num++;
	}
}
