package com.saltosion.gladiator.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Calendar;

/**
 *
 * @author Jens "Jeasonfire" Pitkänen <jeasonfire@gmail.com>
 */
public class Log {

	public static final File infoLogFile = new File("info.log");
	public static final File errorLogFile = new File("error.log");

	private static String getTime() {
		String result = "";
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		result += (hour < 10 ? "0" + hour : hour) + ":";
		result += (minute < 10 ? "0" + minute : minute) + ":";
		result += second < 10 ? "0" + second : second;
		return result;
	}

	public static void info(String s) {
		info(s, false);
	}

	public static void info(String s, boolean saveToFile) {
		String message = "[" + getTime() + "] <INFO>: " + s;
		System.out.println(message);
		if (saveToFile) {
			try {
				if (!infoLogFile.exists()) {
					infoLogFile.createNewFile();
				}
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(infoLogFile, true), "utf-8"));
				writer.append(message);
				writer.newLine();
				writer.close();
			} catch (IOException ex) {
				System.err.print(ex.getMessage());
			}
		}
	}

	public static void error(String s) {
		error(s, false);
	}

	public static void error(String s, boolean saveToFile) {
		String message = "[" + getTime() + "] <ERROR>: " + s;
		System.err.println(message);
		if (saveToFile) {
			try {
				if (!errorLogFile.exists()) {
					errorLogFile.createNewFile();
				}
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(errorLogFile, true), "utf-8"));
				writer.append(message);
				writer.newLine();
				writer.close();
			} catch (IOException ex) {
				System.err.print(ex.getMessage());
			}
		}
	}

}
