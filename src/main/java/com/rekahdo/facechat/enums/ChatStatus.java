package com.rekahdo.facechat.enums;

import java.util.Arrays;

@SuppressWarnings("unused")
public enum ChatStatus {
	
	SENT(1), // single gray tick
    DELIVERED(2), // double gray tick
    SEEN(3); // double blue tick
    
	private int index;
    private String value;

	private ChatStatus(int index) {
		this.index = index;
		this.value = this.toString();
	}
	
	private int getIndex() {
		return index;
	}

	public String getValue() {
		return value;
	}
	
	private static ChatStatus findByIndex(int index) {
		return Arrays.asList(values()).stream().filter(status -> status.index == index).findFirst().orElse(SENT);
	}

	public static ChatStatus findByValue(String value) {
        return Arrays.asList(values()).stream().filter(status -> status.value.equalsIgnoreCase(value)).findFirst().orElse(SENT);
    }
    
}