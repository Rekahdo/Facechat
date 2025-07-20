package com.rekahdo.facechat.utilities;

import com.rekahdo.facechat._entities.AppUser;
import com.rekahdo.facechat.security.user.AppUserPrincipal;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;

@Getter
@Setter
public class UriData {

	public static Long URI_USER_ID;
	public static Long URI_FRIEND_ID;

}
