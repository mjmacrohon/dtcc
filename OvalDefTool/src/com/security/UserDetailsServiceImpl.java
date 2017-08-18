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
			
			_log.info(ui);
			_log.info(ui.getUsername());
			_log.info(ui.isCredentialsNonExpired());

/*			Attributes attr=ctx.getAttributes();
			attr.get("employeeNumber").get().toString()
			
			UserInfo u=new UserInfo();
		
			userDetails.setEmployeeNumber(attr.get("employeeNumber").get().toString());
			userDetails.setFirstName(attr.get("givenName").get().toString());
			userDetails.setLastName(attr.get("sn").get().toString());
			//userDetails.setDepartment(attr.get("department").get().toString());
			userDetails.setCostCenter(attr.get("iVSystemUserCostCenter").get().toString());
			//userDetails.setTelephoneNo(attr.get("telephoneNumber").get().toString());
			_log.info("CostCenter: " + attr.get("iVSystemUserCostCenter").get().toString());
			userDetails.setCompanyCode(attr.get("iVSystemUserCompanyCode").get().toString());
			userDetails.setEmail(attr.get("mail").get().toString());
			userDetails.setCountryCode(attr.get("c").get().toString());
			userDetails.setMunicipality(attr.get("l").get().toString());
			userDetails.setAccountNonExpired(true);
			userDetails.setAccountNonLocked(true);
			userDetails.setEnabled(true);
			
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
