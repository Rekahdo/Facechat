package com.rekahdo.facechat.utilities;

import com.rekahdo.facechat._entities.AppUser;

public final class AuthUser {

	public static Long ID(){
		return ApiSecurityContext.PRINCIPAL().getId();
	}

	public static String USERNAME(){
		return ApiSecurityContext.PRINCIPAL().getUsername();
	}

	public static boolean IS_AN_ADMIN(){
		return ApiSecurityContext.PRINCIPAL().isAdmin();
	}

	public static boolean IS_NOT_AN_ADMIN(){
		return !IS_AN_ADMIN();
	}

	public static boolean IS_A_MODERATOR(){
		return ApiSecurityContext.PRINCIPAL().isModerator();
	}

	public static boolean IS_NOT_A_MODERATOR(){
		return !IS_A_MODERATOR();
	}

	public static boolean IS_AN_EDITOR(){
		return ApiSecurityContext.PRINCIPAL().isEditor();
	}

	public static boolean IS_NOT_AN_EDITOR(){
		return !IS_AN_EDITOR();
	}

	public static AppUser APP_USER(){
		return ApiSecurityContext.PRINCIPAL().getAppUser();
	}
}
