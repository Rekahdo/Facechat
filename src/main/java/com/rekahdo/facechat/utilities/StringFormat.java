package com.rekahdo.facechat.utilities;

public class StringFormat {

	private static String removeWhiteSpace(String str) {
		if(str == null || str.trim().isEmpty()) return "";
		return str.trim().replaceAll("\\s", "");
	}

	public static String[] split(String str) {
		return removeWhiteSpace(str).split(",");
	}

	public static String join(String[] array) {
		return String.join(",", array);
	}

	public static String join(String str) {
		return join(split(str));
	}

	public static boolean isLowercase(String str) {
		return str.chars().allMatch(Character::isLowerCase);
	}

	public static boolean isUppercase(String str) {
		return str.chars().allMatch(Character::isUpperCase);
	}

	public static boolean hasValue(String str) {
		return str != null && !str.trim().isEmpty();
	}



}
