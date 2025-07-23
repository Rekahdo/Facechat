package com.rekahdo.facechat.mappingJacksonValue;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;

public final class ChatMJV {

	private static final FilterProvider CHAT_FILTER = new SimpleFilterProvider()
			.addFilter("appUserDtoFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id", "username"))
			.addFilter("chatDtoFilter", SimpleBeanPropertyFilter.serializeAllExcept("friendId", "friendshipId", "unread", "receiver"));

	private static final FilterProvider CHAT_GROUP_FILTER = new SimpleFilterProvider()
			.addFilter("appUserDtoFilter", SimpleBeanPropertyFilter.filterOutAllExcept("id", "username"))
			.addFilter("chatDtoFilter", SimpleBeanPropertyFilter.filterOutAllExcept("content", "sentAt", "unread", "friend"));

	public static <T> MappingJacksonValue chatFilter(T dto) {
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(dto);
		mappingJacksonValue.setFilters(CHAT_FILTER);
		return mappingJacksonValue;
	}

	public static <T> MappingJacksonValue chatGroupFilter(T dto) {
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(dto);
		mappingJacksonValue.setFilters(CHAT_GROUP_FILTER);
		return mappingJacksonValue;
	}

}
