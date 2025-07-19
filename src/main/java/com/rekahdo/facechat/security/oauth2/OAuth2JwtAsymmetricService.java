package com.rekahdo.facechat.security.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class OAuth2JwtAsymmetricService {

	@Autowired
	private JwtEncoder jwtEncoder;

	public String createToken(Authentication authentication) {
		return create(authentication);
	}

	private String create(Authentication authentication) {
		var claims = JwtClaimsSet.builder()
				.issuer("self")
				.issuedAt(Instant.now())
				.expiresAt(Instant.now().plusSeconds(60 * 30)) // Expire after 60secs * 30 = 30minutes
				.subject(authentication.getName())
				.claim("scope", createScope(authentication))
				.build();
		return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}

	private static List<String> createScope(Authentication authentication) {
		return authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.toList();
	}

}