package org.app.edufun.dao_impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.app.edufun.dao.UserMasterDao;
import org.app.edufun.entity.UserMaster;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class UserMasterDaoImpl implements UserMasterDao {

	@Autowired
	private SessionFactory sessionFactory;

	Logger log = LoggerFactory.getLogger(UserMasterDaoImpl.class);

	@Override
	public boolean verifyUser(String email, String password) {

		try {
			this.sessionFactory.getCurrentSession()
					.createQuery("from UserMaster where email =:email and  password =:password")
					.setParameter("email", email).setParameter("password", password).getSingleResult();

			return true;
		} catch (NoResultException e) {
			return false;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	@Override
	public UserMaster getUser(String email) {
		try {
			UserMaster user = (UserMaster) this.sessionFactory.getCurrentSession()
					.createQuery("from UserMaster where email =:email").setParameter("email", email).getSingleResult();
//			 System.out.println(user.toString());
			return user;
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@Override
	public Object getUser(String email, String password) {

		try {
			return this.sessionFactory.getCurrentSession()
					.createQuery("from UserMaster where email =:email and password =:password")
					.setParameter("email", email).setParameter("password", password).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@Override
	public Object getUsers(String email) {
		try {
			return sessionFactory.getCurrentSession().createQuery("from UserMaster where email = :email")
					.setParameter("email", email).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@Override
	public UserMaster varifyPartner(String email) {
		String query = "select * from users as u inner join partners as p on u.partner_id=p.partner_id where u.email=:mail";
		try {
			UserMaster user = (UserMaster) sessionFactory.getCurrentSession().createNativeQuery(query)
					.addEntity(UserMaster.class).setParameter("mail", email).getSingleResult();
			return user;
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public boolean createUser(HttpSession session, UserMaster user) {

		try {
			long user_id = (long) sessionFactory.getCurrentSession().save(user);
			System.out.println("New record created :" + user_id);
			if (user_id > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean getByContactOrEmail(String contact, String email) {
		try {
			String query = "select  * from  users as u where u.contact=:cntct or u.email like :mail";

			@SuppressWarnings("unchecked")
			List<UserMaster> list = sessionFactory.getCurrentSession().createNativeQuery(query)
					.addEntity(UserMaster.class).setParameter("cntct", contact).setParameter("mail", email)
					.getResultList();
			System.out.println(list);
			if (list.isEmpty()) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
