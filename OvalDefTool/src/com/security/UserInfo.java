package com.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserInfo implements UserDetails {

	UserDetails ud;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	
	
	public UserInfo() {
		super();
	}
	
	

	public UserInfo(UserDetails ud) {
		super();
		this.ud = ud;
	}



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return ud.getAuthorities();
	}

	@Override
	public String getPassword() {
		return ud.getPassword();
	}

	@Override
	public String getUsername() {
		return ud.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return ud.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return ud.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return ud.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return ud.isEnabled();
	}

	
	
}
