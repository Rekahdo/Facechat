package com.rekahdo.facechat.utilities;

import com.rekahdo.facechat._entities.AppUser;
import com.rekahdo.facechat.security.user.AppUserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

public final class ApiSecurityContext {

	public static boolean USER_IS_AUTHENTICATED(){
		return (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof AppUserPrincipal);
	}

	public static AppUserPrincipal PRINCIPAL(){
		return USER_IS_AUTHENTICATED()
				? (AppUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()
				: new AppUserPrincipal(new AppUser());
	}

}
