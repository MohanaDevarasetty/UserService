package com.farmersbyte.enterprise.usermodule.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class UserPrincipal extends User {

	private String username;
	private String mail;
	private String phone;
	private String organizationId;
	private String userId;
	private String application;

	public UserPrincipal(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username,password, authorities);
	}
}
