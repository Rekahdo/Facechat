package com.rekahdo.facechat.utilities;

import com.rekahdo.facechat._dtos.ChatDto;

import java.util.Comparator;

// Ascend
public class SortChatDtoByDate implements Comparator<ChatDto>{
	
	@Override
	public int compare(ChatDto s1, ChatDto s2) {
		return s2.getSentAt().compareTo(s1.getSentAt());
	}
	
}