package com.rekahdo.facechat.security.user;

import com.rekahdo.facechat._entities.AppUser;
import com.rekahdo.facechat._repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private AppUserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<AppUser> optional = repo.findByUsernameIgnoreCase(username);
		if (optional.isEmpty()) throw new UsernameNotFoundException("User not found: " + username);

		return new AppUserPrincipal(optional.get());
	}
}
