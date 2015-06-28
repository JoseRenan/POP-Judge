package br.edu.popjudge.control;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import org.primefaces.model.UploadedFile;

public class Util {
	/**
	 * Set of utilities.
	 * @author rerissondaniel
	 */
	private Util() {
	}

	public static void killUnixProcess(Process process)
			throws IllegalArgumentException, NoSuchFieldException,
			SecurityException, IllegalAccessException, IOException {
		/**
		 * This method just works in an Unix environment.
		 * @param process
		 *            An object representing the process to be killed.
		 * 
		 * */
		if (process.getClass().getName().equals("java.lang.UNIXProcess")) {
			Class<?> cl = process.getClass();
			Field field = cl.getDeclaredField("pid");
			field.setAccessible(true);

			Object pidObject = field.get(process);
			Integer pid = (Integer) pidObject;

			String command = "kill -9 " + (pid + 3);
			Runtime.getRuntime().exec(command);
		} else {
			throw new IllegalArgumentException("Needs to be a UNIXProcess");
		}
	}

	public static File generateSubmissionFile(String dir, UploadedFile upFile)
			throws IOException {

		/**
		 * Generates an usable file from an uploaded file.
		 * 
		 * @param dir
		 *            the directory where the file must be created.
		 * @param upFile
		 *            the uploaded file.
		 * */
		File file = new File(dir + upFile.getFileName());

		InputStream in = new BufferedInputStream(upFile.getInputstream());

		FileOutputStream fos = new FileOutputStream(file);

		while (in.available() != 0) {
			fos.write(in.read());
		}

		fos.close();
		return file;
	}
}
