package com.rekahdo.facechat.exceptions.classes;

import com.rekahdo.facechat.enums.Error;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ChatNotFoundException extends Api_Exception {

	private final Long chatId;

	public ChatNotFoundException(Long chatId) {
		super(String.format("CHAT WITH ID '%d' NOT FOUND", chatId), HttpStatus.NOT_FOUND, Error.UNKNOWN_VIOLATION);
		this.chatId = chatId;
	}

}