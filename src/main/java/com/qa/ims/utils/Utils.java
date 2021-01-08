package com.qa.ims.utils;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {
	private static Logger LOGGER = LogManager.getLogger();

	private final Scanner scanner;

	public Utils(Scanner scanner) {
		super();
		this.scanner = scanner;
	}

	public Utils() {
		scanner = new Scanner(System.in);
	}

	public Long getLong() {
		String input = getString();
		Long longInput = null;
		do {
			try {
				longInput = Long.parseLong(input);
			} catch (NumberFormatException nfe) {
				LOGGER.info("Error - Please enter a number");
			}
		} while (longInput == null);
		return longInput;
	}

	public String getString() {
		return scanner.nextLine();
	}

	public Double getDouble() {
		String input = getString();
		Double doubleInput = null;
		do {
			try {
				doubleInput = Double.parseDouble(input);
			} catch (NumberFormatException nfe) {
				LOGGER.info("Error - Please enter a number");
			}
		} while (doubleInput == null);
		return doubleInput;
	}

	public Boolean getBoolean() {
		String trueString = "true";
		String falseString = "false";
		Boolean booleanOut = false;
		
		String input = getString().toLowerCase(); 
		
		if (input.equals(trueString)) {
			return true;
		} else if (input.equals(falseString)) {
			return false;
		} else {
			LOGGER.info("Error - Please enter true/false");
			getBoolean();
		}
		return booleanOut;
	}
}


//	public Boolean getBoolean() {
//		String input = getString();
//		Boolean bool = null;
//		do {
//			
//			try {
//				bool = Boolean.parseBoolean(input);
//			} catch (Exception e) {
//				LOGGER.info("Error - Please enter TRUE or FALSE");
//			}
//		} while (bool == null);
//		return bool;
//	}
//
