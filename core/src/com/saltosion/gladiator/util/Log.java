/**
 * GladiatorBrawler is a 2D swordfighting game.
 * Copyright (C) 2015 Jeasonfire/Allexit
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.saltosion.gladiator.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
