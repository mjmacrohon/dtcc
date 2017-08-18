package com.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;

import org.apache.log4j.Logger;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

public class UserAuthoritiesPopulator implements LdapAuthoritiesPopulator {

	@SuppressWarnings("rawtypes")
	@Override
	public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations ctx, String username) {
		_log.info(username);
		List<GrantedAuthority> grantedAuthorities=new ArrayList<GrantedAuthority>();
		Attribute memberOf=ctx.getAttributes().get("memberOf");
		try{
			if (memberOf!=null){
				for(Enumeration e1=memberOf.getAll();e1.hasMoreElements();){
					String[] group=e1.nextElement().toString().split(",");
					String grp=group[0].split("=")[1];
					grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+grp));					
				}
			}
		}catch(NamingException ne){
			ne.printStackTrace();
		}	
		return grantedAuthorities;
	}

	Logger _log=Logger.getLogger(getClass());
}
