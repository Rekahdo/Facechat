package com.rekahdo.facechat.enums;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@SuppressWarnings("unused")
@AllArgsConstructor
public enum AuthorityRole {
	
	ADMIN,
	MODERATOR,
	EDITOR,
	USER;

    private final String value;

	AuthorityRole() {
		this.value = this.toString();
	}

	public String getValue() {
		return value;
	}

	public static AuthorityRole findByValue(String value) {
        return Arrays.stream(values()).filter(status -> status.value.equalsIgnoreCase(value)).findFirst().orElse(USER);
    }
    
}