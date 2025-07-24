package com.rekahdo.facechat._mappers;

import com.rekahdo.facechat._controllers.AppUserController;
import com.rekahdo.facechat._dtos.entities.AppUserDto;
import com.rekahdo.facechat._entities.AppUser;
import com.rekahdo.facechat._entities.Authority;
import com.rekahdo.facechat.enums.AuthorityRole;
import org.mapstruct.*;

import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

	@Mappings({
		@Mapping(target = "password", ignore = true),
		@Mapping(target = "roles", ignore = true),
		@Mapping(target = "adminKey", ignore = true)
	})
	AppUserDto toDto(AppUser appUser);

	@Mapping(target = "authorities", ignore = true)
	AppUser toEntity(AppUserDto appUserDto);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "authorities", ignore = true)
	void updateEntity(AppUserDto source, @MappingTarget AppUser target);

	@AfterMapping
	default void afterMappingToEntity(@MappingTarget AppUser target, AppUserDto source) {
		// Map source roles to target authorities
		if(source.getRoles() != null) {
			List<Authority> authorities = Arrays.stream(source.fetchRolesAsAuthorityRoles())
					.map(role -> new Authority(role, target))
					.toList();
			target.setAuthorities(authorities);
		}
	}

	@AfterMapping
	default void afterMappingToDto(@MappingTarget AppUserDto target, AppUser source) {
		// Map source authorities to target roles
		if(source.getAuthorities() != null)
			target.setRoles(source.fetchAuthoritiesAsRoles());

		target.putRole(AuthorityRole.USER);

		// Add links to dto
		target.add(linkTo(methodOn(AppUserController.class).getUser(source.getId())).withSelfRel());
	}

}
