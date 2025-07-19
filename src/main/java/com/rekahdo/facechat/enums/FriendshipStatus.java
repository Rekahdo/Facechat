package com.rekahdo.facechat.enums;

import java.util.Arrays;

public enum FriendshipStatus {
	
	PENDING(2),
	ACCEPTED(3),
	BLOCKED(4);
    
	private int index;
    private String value;

	private FriendshipStatus(int index) {
		this.index = index;
		this.value = this.toString();
	}
	
	public int getIndex() {
		return index;
	}

	public String getValue() {
		return value;
	}
	
	public static FriendshipStatus findByIndex(int index) {
		return Arrays.asList(values()).stream().filter(status -> status.index == index).findFirst().orElse(null);
	}

	public static FriendshipStatus findByValue(String value) {
        return Arrays.asList(values()).stream().filter(status -> status.value.equalsIgnoreCase(value)).findFirst().orElse(null);
    }
    
}