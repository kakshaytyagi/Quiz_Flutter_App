package org.app.edufun.service_impl;

import java.util.ArrayList;

import org.app.edufun.dao.UserMasterDao;
import org.app.edufun.entity.UserMaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserMasterDao masterDao;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	Logger log = LoggerFactory.getLogger(MyUserDetailsService.class);
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserMaster user = masterDao.getUser(username);
		if(user != null){
			String password = bCryptPasswordEncoder.encode(user.getPassword());
			return new User(user.getEmail(), password, new ArrayList<>());
			//return new User("chandan.singh@sroniyan.com", "$2a$10$2EZTDVd/t0VI1jpxGj1oheWE7iWyGJI03e0Zd/PBzdSec15Fou5ta", new ArrayList<>());
		}
		else{
			return null; 
		}
		//return new User("root", "$2a$10$2EZTDVd/t0VI1jpxGj1oheWE7iWyGJI03e0Zd/PBzdSec15Fou5ta", new ArrayList<>());
		
	}
	
	public boolean validateRole(String username) throws UsernameNotFoundException {
		UserMaster user = masterDao.getUser(username);
		if(user != null){
			
			if(user.getEmail().equals(username)) {
				return true;
			}
			else {
				return false;
			}
		}
		else{
			throw new UsernameNotFoundException("Invalid username has passed"); 
		}	
	}

}