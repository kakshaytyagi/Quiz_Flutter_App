package org.app.edufun.dao;

import javax.servlet.http.HttpSession;

import org.app.edufun.entity.UserMaster;

public interface UserMasterDao{
	
	public boolean verifyUser(String email, String password);
	public UserMaster getUser(String email);
	public Object getUser(String email, String password);
	public Object getUsers(String email);
	public UserMaster varifyPartner(String email);
	public boolean createUser(HttpSession session, UserMaster user);
	public boolean  getByContactOrEmail(String contact, String email);
}
