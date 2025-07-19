package com.rekahdo.facechat.security.user;

import com.rekahdo.facechat._entities.AppUser;
import com.rekahdo.facechat.enums.AuthorityRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class AppUserPrincipal implements UserDetails {

	private final AppUser appUser;

	public AppUserPrincipal(AppUser appUser) {
		this.appUser = appUser;
	}

	// Custom method to get user ID
	public Long getId() {
		return appUser.getId();
	}

	public AppUser getAppUser() {
		return appUser;
	}

	@Override
	public String getUsername() {
		return appUser.getUsername();
	}

	@Override
	public String getPassword() {
		return appUser.getPassword();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return appUser.getAuthorities() != null || !appUser.getAuthorities().isEmpty()
				? appUser.fetchAuthoritiesAsGrantedAuthorities() : Collections.emptyList();
	}

	@Override
	public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

	@Override
	public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

	@Override
	public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

	@Override
	public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

	// Helper method to check if user has admin role
	public boolean isAdmin() {
		return getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_" + AuthorityRole.ADMIN.getValue()));
	}

	public boolean isEditor() {
		return getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_" + AuthorityRole.EDITOR.getValue()));
	}

	public boolean isModerator() {
		return getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals("ROLE_" + AuthorityRole.MODERATOR.getValue()));
	}

}