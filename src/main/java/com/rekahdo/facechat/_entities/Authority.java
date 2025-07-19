package com.rekahdo.facechat._entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rekahdo.facechat.enums.AuthorityRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "authorities")
public class Authority {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private AuthorityRole role;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	private AppUser appUser;

	public Authority(AuthorityRole role, AppUser appUser) {
		super();
		this.appUser = appUser;
		this.role = role;
	}

}
