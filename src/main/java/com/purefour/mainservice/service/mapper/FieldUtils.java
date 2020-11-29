package com.purefour.mainservice.service.mapper;

public final class FieldUtils { // TODO testy
	public static final String EXTRACT_WORDS_REGEX = "([^A-Z])\\w+";
	public static final String EXTRACT_NUMBERS_REGEX = "([^0-9])\\w+";

	public static String getFirstWord(Object object) {
		final String stringValue = (String) object;
		return stringValue.split(EXTRACT_WORDS_REGEX)[0];
	}

	public static int getFirstNumber(Object object) {
		final String stringValue = (String) object;
		return Integer.parseInt(stringValue.split(EXTRACT_NUMBERS_REGEX)[0]);
	}
}
