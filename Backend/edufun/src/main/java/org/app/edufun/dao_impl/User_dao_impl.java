package org.app.edufun.dao_impl;
//
//
//import javax.persistence.NoResultException;
//import javax.transaction.Transactional;
//
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import org.app.edufun.entity.User;
//
//@Repository
//public class User_dao_impl implements User_dao {
//
//	@Autowired
//	private SessionFactory sessionRef;
//
//	@Override
//	public User getUser(String username, String password) {
//		String query = "select * from users as u  where u.email =:name and u.password=:pswrd";
//		try {
//			User user = (User) sessionRef.getCurrentSession().createNativeQuery(query).addEntity(User.class)
//					.setParameter("name", username).setParameter("pswrd", password).getSingleResult();
//
//			if (user != null) {
//				return user;
//			} else {
//				return null;
//			}
//		}catch(Exception e) {
//			return null;
//		}
//
//	}
//
//	@Override
//	public User getByEmail(String email) {
//		String query = "select * from users as u  where u.email=:mail";
//		try {
//			User user =	(User) sessionRef.getCurrentSession().createNativeQuery(query).setParameter("mail", email)
//					.addEntity(User.class).getSingleResult();
//			return user;
//		}catch(NoResultException e) {
//			return null;
//
//		}catch(Exception e) {
//			return null;
//		}
//	}
//
//
//
//
//	@Override
//	public boolean saveUser(User user) {
//		try {
//			long user_id = (long) sessionRef.getCurrentSession().save(user);
//			System.out.println("New record created :" + user_id);
//			return true;
//
//		} catch (Exception e) {
//			return false;
//		}
//	}
//
//	@Override
//	public boolean update_password(String email, String new_password ) {
//	String query = "update users as u set u.password=:pswrd where u.email=:mail";
//	try {
//		int x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("mail", email)
//				.setParameter("pswrd", new_password).addEntity(User.class).executeUpdate();
//		if (x > 0) {
//			return true;
//		} else {
//			return false;
//		}
//	} catch (Exception e) {
//		return false;
//	}
//	}
//
//	@Transactional
//	public boolean existByEmail(String email) {
//		String query = "select * from users as u where u.email=:mail";
//		try {
//			User user = (User) sessionRef.getCurrentSession().createNativeQuery(query)
//					.setParameter("mail", email)
//					.addEntity(User.class)
//					.getSingleResult();
//			return true;
//		} catch (NoResultException e) {
//			return false;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//
//}
