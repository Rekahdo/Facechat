package com.rekahdo.facechat.security.config;

import com.rekahdo.facechat.security.jjwt.JwtFilter;
import com.rekahdo.facechat.security.user.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class Api_SecurityFilterChainConfig {

	@Autowired
	private JwtFilter jwtFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(request -> request
				.requestMatchers("/api/v*/*/test/mapper").permitAll()
				.requestMatchers("/api/v*/users/register").permitAll()
				.requestMatchers("/api/v*/users/login").permitAll()
				.requestMatchers("/h2-console/**").permitAll()
				.anyRequest().authenticated());
		http.httpBasic(Customizer.withDefaults());
		http.csrf(CsrfConfigurer::disable);
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService(){
		return new AppUserDetailsService();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		return new Api_AuthenticationProvider(userDetailsService(), passwordEncoder());
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationProvider authenticationProvider) {
		return new ProviderManager(authenticationProvider);
	}

}