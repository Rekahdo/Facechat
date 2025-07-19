package com.rekahdo.facechat.mappingJacksonValue;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.rekahdo.facechat._dtos.AppUserDto;
import org.springframework.http.converter.json.MappingJacksonValue;

public final class AppUserMJV {

	private static final FilterProvider PUBLIC_FILTER = new SimpleFilterProvider().addFilter("appUserDtoFilter",
			SimpleBeanPropertyFilter.filterOutAllExcept("id", "username", "roles", "_links"));

	private static final FilterProvider PRIVATE_FILTER = new SimpleFilterProvider().addFilter("appUserDtoFilter",
			SimpleBeanPropertyFilter.serializeAllExcept("adminKey", "password"));

	private static final FilterProvider PRIVATE_SHOW_PASSWORD_FILTER = new SimpleFilterProvider().addFilter("appUserDtoFilter",
			SimpleBeanPropertyFilter.serializeAllExcept("adminKey"));

	public static <T> MappingJacksonValue publicFilter(T dto) {
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(dto);
		mappingJacksonValue.setFilters(PUBLIC_FILTER);
		return mappingJacksonValue;
	}

	public static MappingJacksonValue privateFilter(AppUserDto dto) {
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(dto);
		mappingJacksonValue.setFilters(PRIVATE_FILTER);
		return mappingJacksonValue;
	}

	public static MappingJacksonValue privateFilter(AppUserDto dto, boolean showPassword) {
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(dto);
		mappingJacksonValue.setFilters(showPassword ? PRIVATE_SHOW_PASSWORD_FILTER : PRIVATE_FILTER);
		return mappingJacksonValue;
	}

}
