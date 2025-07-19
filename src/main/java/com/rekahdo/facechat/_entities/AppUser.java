package com.rekahdo.facechat._entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rekahdo.facechat.enums.AuthorityRole;
import com.rekahdo.facechat.utilities.StringFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class AppUser {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String username;

	private String password;

	@Column(unique = true)
	private String email;

	private Instant createdAt;
	private Instant updatedAt;

	@OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Authority> authorities = new ArrayList<Authority>();

	@OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Friendship> sentFriendships = new ArrayList<>();

	@OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Friendship> receivedFriendships = new ArrayList<>();

	@OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Chat> sentChats = new ArrayList<Chat>();

	@OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Chat> receivedChats = new ArrayList<Chat>();

	// HELPER CONSTRUCTORS
	public AppUser(Long id, String username, String password, String email, Instant createdAt) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.createdAt = createdAt;
	}

	public AppUser(Long id, String username) {
		this.id = id;
		this.username = username;
	}

	public AppUser(Long id) {
		this.id = id;
	}

	// GETTERS AND SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	public List<Friendship> getSentFriendships() {
		return sentFriendships;
	}

	public void setSentFriendships(List<Friendship> sentFriendships) {
		this.sentFriendships = sentFriendships;
	}

	public List<Friendship> getReceivedFriendships() {
		return receivedFriendships;
	}

	public void setReceivedFriendships(List<Friendship> receivedFriendships) {
		this.receivedFriendships = receivedFriendships;
	}

	public List<Chat> getSentChats() {
		return sentChats;
	}

	public void setSentChats(List<Chat> sentChats) {
		this.sentChats = sentChats;
	}

	public List<Chat> getReceivedChats() {
		return receivedChats;
	}

	public void setReceivedChats(List<Chat> receivedChats) {
		this.receivedChats = receivedChats;
	}

	// HELPER METHODS
	public void addAuthoritiesByRoles(AuthorityRole... roles) {
		List<Authority> newAuthorities = Arrays.stream(roles)
				.map(role -> new Authority(role, this))
				.toList();
		authorities.addAll(newAuthorities);
	}

	public void addAuthoritiesByRole(AuthorityRole role) {
		addAuthoritiesByRoles(role);
	}

	public void removeAuthoritiesByRoles(AuthorityRole... roles) {
		List<AuthorityRole> rolesList = Arrays.asList(roles);
		List<Authority> removeAuthorities = authorities.stream()
				.filter(authority -> rolesList.contains(authority.getRole()))
				.toList();
		authorities.removeAll(removeAuthorities);
	}

	public void removeAuthoritiesByRole(AuthorityRole role) {
		removeAuthoritiesByRoles(role);
	}

	public String fetchAuthoritiesAsRoles() {
		String[] array = authorities.stream()
				.map(authority -> authority.getRole().getValue())
				.toArray(String[]::new);
		return StringFormat.join(array);
	}

	public Collection<? extends GrantedAuthority> fetchAuthoritiesAsGrantedAuthorities() {
		return authorities.stream().map(authority -> {
					String role = authority.getRole().getValue();
					return new SimpleGrantedAuthority(role.startsWith("ROLE_") ? role : "ROLE_" + role);
				}).toList();
	}

	// TO STRING
	@Override
	public String toString() {
		return "AppUser{" +
				"id=" + id +
				", username='" + username + '\'' +
				'}';
	}
}
