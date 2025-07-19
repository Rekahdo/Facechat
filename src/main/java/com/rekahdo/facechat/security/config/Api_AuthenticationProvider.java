package com.rekahdo.facechat.security.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Api_AuthenticationProvider implements AuthenticationProvider {

	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;

	public Api_AuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		super();
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) {
		String username = authentication.getName();
		String rawPassword = authentication.getCredentials().toString();
		
		UserDetails user = userDetailsService.loadUserByUsername(username);
		if (passwordEncoder.matches(rawPassword, user.getPassword()))
			return new UsernamePasswordAuthenticationToken(
					user, null, user.getAuthorities());

		throw new BadCredentialsException("Invalid password");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}