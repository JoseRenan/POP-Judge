package br.edu.popjudge.control;

import java.io.IOException;
import java.lang.reflect.Field;
public class ProcessKiller {
	public static void killUnixProcess(Process process)
			throws IllegalArgumentException, NoSuchFieldException,
			SecurityException, IllegalAccessException, IOException {
		if (process.getClass().getName().equals("java.lang.UNIXProcess")) {
			Class<?> cl = process.getClass();
			Field field = cl.getDeclaredField("pid");
			field.setAccessible(true);
			Object pidObject = field.get(process);
			Integer pid = (Integer) pidObject;
			String command = "kill -9 " + (pid + 3);//Por algum motivo ele não pega o número correto. Mas com + 3 tá funcionando.
			Runtime.getRuntime().exec(command);
		} else {
			throw new IllegalArgumentException("Needs to be a UNIXProcess");
		}
	}
}
