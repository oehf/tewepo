/*
 * Copyright 2012 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openehealth.tewepo.twp.dmp.dmc.client.utils;



/**
 * This class is for validating the user input.
 * 
 * @author devmis
 * 
 */
public class GuiValidator {

	/**
	 * Validates the input.
	 * 
	 * @param value
	 *            entered text
	 * @param minLength
	 *            minimum length of the entered text
	 * @param maxLength
	 *            maximum length of the entered text
	 * 
	 * @return false; if value null, longer than max. length or shorter than
	 *         min. length true; if validation successful.
	 * 
	 */
	public static boolean validate(String value, int minLength, int maxLength) {

		// check null
		if (value == null)
			return false;
		// check length
		if (value.length() > maxLength || value.length() < minLength)
			return false;

		return Pattern.matches("[a-zA-Z_0-9]", value);

	}

	/**
	 * Validates the date.
	 * 
	 * @param date
	 * 
	 * @return true; if date is validate false; if date is not validate
	 */
	public static boolean validateDate(String date) {
		if (!validate(date, 10, 10)){
			return false;
		}
		String dayString = date.substring(0, 2);
		int dayInt = -1;
		String monthString = date.substring(3, 5);
		int monthInt = -1;
		String yearString = date.substring(6, 10);
		int yearInt = -1;

		if (date.substring(2, 3).compareTo(".") != 0
				|| date.substring(5, 6).compareTo(".") != 0){
			return false;
		}
		try {
			dayInt = Integer.parseInt(dayString);
			monthInt = Integer.parseInt(monthString);
			yearInt = Integer.parseInt(yearString);
		} catch (NumberFormatException e) {
			return false;
		}
		

		if (dayInt < 1 || dayInt > 31){
			return false;
		}
		
		if (monthInt < 1 || monthInt > 12){
			return false;
		}
		
		int currYear=0;
		
		if (yearInt < 1900 || yearInt > 2020){ 
			/*
			currYear = FileTools.getCurrYear();
			
			if(currYear < yearInt){
				return false;
			}*/
		}
		return true;
	}

}
