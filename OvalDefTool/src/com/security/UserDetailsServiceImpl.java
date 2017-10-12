package com.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.search.LdapUserSearch;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;

public class UserDetailsServiceImpl extends LdapUserDetailsMapper implements UserDetailsService {

	@Autowired
	LdapUserSearch userSearch;
	
	UserInfo ui;
	
	@Override
	public UserInfo loadUserByUsername(String userName) throws UsernameNotFoundException {
		_log.info("loadUserByUsername");
		List<GrantedAuthority> grantedAuthorities=new ArrayList<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		DirContextOperations ctx=userSearch.searchForUser(userName);
		UserDetails ud=super.mapUserFromContext(ctx, userName, grantedAuthorities);
		ui=new UserInfo(ud);
		return ui;
	}

	//LDAP
	@Override
	public UserInfo mapUserFromContext(DirContextOperations ctx, String userName,
			Collection<? extends GrantedAuthority> authorities) {
		
		try{
			UserDetails ud=super.mapUserFromContext(ctx, userName, authorities);
			ui=new UserInfo(ud);
		
			Attributes attr=ctx.getAttributes();
			attr.get("employeeNumber").get().toString();	
		
			ui.setFirstName(attr.get("givenName").get().toString());
			ui.setLastName(attr.get("sn").get().toString());
			ui.setEmail(attr.get("mail").get().toString());
			
			_log.info("FirstName: " + ui.getFirstName());
			_log.info("LastName: " + ui.getLastName());

			
			
/*			
			//userDetails.setAuthorities(mapper.mapAuthorities(authorities));
			userDetails.setAuthorities(readUserGroupMembership(ctx, userDetails));
			_log.info("userName: " + userDetails.getUsername());
			_log.info("lastName: " + userDetails.getLastName());
			_log.info("firstName: " + userDetails.getFirstName());
			_log.info("department: " + userDetails.getDepartment());
*/
		}catch(NamingException e){
			_log.error(e.getMessage());
		}
		
		return ui;
	}

	//LDAP
	@Override
	public void mapUserToContext(UserDetails arg0, DirContextAdapter arg1) {
		// TODO Auto-generated method stub
		
	}
	
	Logger _log=Logger.getLogger(getClass());

}
