package com.rekahdo.facechat.security.user;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AppUserSecurity {
	
	public boolean isUserAuth(Authentication authentication, Long id) {
		Long authId = ((AppUserPrincipal) authentication.getPrincipal()).getId();
		return Objects.equals(authId, id);
	}
	
}
