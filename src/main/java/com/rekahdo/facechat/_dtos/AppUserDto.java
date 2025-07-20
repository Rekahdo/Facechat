package com.rekahdo.facechat._dtos;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.rekahdo.facechat.enums.AuthorityRole;
import com.rekahdo.facechat.utilities.StringFormat;
import com.rekahdo.facechat.validations.annotations.Role;
import com.rekahdo.facechat.validations.annotations.StrongPassword;
import com.rekahdo.facechat.validations.annotations.Username;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonFilter("appUserDtoFilter")
public class AppUserDto extends EntityDto<AppUserDto> {

	private Long id;

	@Username
	private String username;

	@StrongPassword
	private String password;

	@Email(message = "Enter a valid email format")
	private String email;

	private Instant createdAt;
	private Instant updatedAt;

	@Role
	private String roles;

	private String adminKey;

	// HELPER CONSTRUCTORS
	public AppUserDto(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public AppUserDto(Long id, String username) {
		this.id = id;
		this.username = username;
	}

	// GETTERS AND SETTERS
	public String getRoles() {
		return roles == null ? null : roles.toUpperCase();
	}

	public AppUserDto setRoles(String roles) {
		this.roles = roles;
		return this;
	}

	public AppUserDto setAdminKey(String adminKey) {
		this.adminKey = adminKey;
		return this;
	}

	// HELPER METHODS
	public AuthorityRole[] fetchRolesAsAuthorityRoles() {
		return asAuthorityRoles(roles);
	}

	private AuthorityRole[] asAuthorityRoles(String roles) {
		return Arrays.stream(StringFormat.split(roles))
				.map(AuthorityRole::findByValue)
				.toArray(AuthorityRole[]::new);
	}

	public void putRole(AuthorityRole role) {
		roles = roles == null || roles.trim().isEmpty()
				? role.getValue() : roles.concat("," + role.getValue());
	}

	public boolean hasRoles(){
		return roles != null && !List.of(StringFormat.split(this.roles)).isEmpty();
	}

	public boolean adminKeyIsValid(String adminKey){
		return adminKey.equals(this.adminKey);
	}

	public boolean adminKeyIsNotValid(String adminKey){
		return !adminKey.equals(this.adminKey);
	}

}