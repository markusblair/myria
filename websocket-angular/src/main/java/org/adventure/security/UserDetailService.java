package org.adventure.security;

import javax.jdo.PersistenceManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailService implements UserDetailsService {

	@Autowired
	private PersistenceManager pm;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		UserAccount userAccount = pm.getObjectById(UserAccount.class, userName);
		
		return userAccount;
	}

	public void createAccount(UserDetails userDetails) {
		pm.makePersistentAll(userDetails);
	}
}
