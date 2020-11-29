package com.purefour.mainservice.service.mapper;

public final class FieldUtils { // TODO testy
	public static final String STATUS = "status";
	public static final String EXTRACT_WORDS_REGEX = "([^A-Z])\\w+";
	public static final String EXTRACT_NUMBERS_REGEX = "([^0-9])\\w+";

	public static <T> String getFirstWord(T object) {
		try {
			final String stringValue = (String) object;
			return stringValue.split(EXTRACT_WORDS_REGEX)[0];
		} catch (Exception e) {
			return "";
		}
	}

	public static <T> int getFirstNumber(T object) {
		try {
			final String stringValue = (String) object;
			return Integer.parseInt(stringValue.split(EXTRACT_NUMBERS_REGEX)[0]);
		} catch (Exception e) {
			return 0;
		}
	}
}
