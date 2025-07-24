package com.rekahdo.facechat._dtos.paginations;

public final class ChatPageRequestDto extends PageRequestDto{

	public ChatPageRequestDto() {
		super(100, false, "sentAt");
	}

}