package org.app.edufun.dao;

import org.apache.commons.lang3.StringUtils;
import org.app.edufun.dto.UserSearchDto;
import org.app.edufun.dto.UserUpdateDto;
import org.app.edufun.entity.User;
import org.app.edufun.exceptions.RepositoryException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
@Transactional
public class UserRepository {
    @Autowired
    private SessionFactory sessionFactory;
    private final String SELECT = "select * from users as u ";
    private final String WHERE = " where ";
    private final String AND = " and ";
    private final String UPDATE = "UPDATE users SET ";
    private final String COMMA = ", ";

    public boolean saveUser(User user) {
        long new_User_id = (long) sessionFactory.getCurrentSession().save(user);
        if (new_User_id > 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<User> searchUser(HttpSession session, UserSearchDto userSearchDto) {

        String q = SELECT + WHERE;

        if (userSearchDto.getUser_id() != null)
            q += "u.user_id = " + userSearchDto.getUser_id() + " " + AND;

        if (!StringUtils.isBlank(userSearchDto.getName()))
            q += "u.username LIKE '%" + userSearchDto.getName() + "%'" + AND;

        if (!StringUtils.isBlank(userSearchDto.getEmail()))
            q += "u.email LIKE '%" + userSearchDto.getEmail() + "%'" + AND;

        if (!StringUtils.isBlank(userSearchDto.getContact()))
            q += "u.contact LIKE '%" + userSearchDto.getContact() + "%'" + AND;

        if (!StringUtils.isBlank(userSearchDto.getPassword()))
            q += "u.password LIKE '%" + userSearchDto.getPassword() + "%'" + AND;

        if (!StringUtils.isBlank(userSearchDto.getCreator_by()))
            q += "u.creator_by LIKE '%" + userSearchDto.getCreator_by() + "%'" + AND;

        if (!StringUtils.isBlank(userSearchDto.getDt()))
            q += "cast(u.dt as date) = '" + userSearchDto.getDt() + "' " + AND;

        if (userSearchDto.getStatus() != null)
            q += "u.status = " + userSearchDto.getStatus() + " " + AND;


        if (q.endsWith(AND)) {
            q = q.substring(0, q.lastIndexOf(AND));
        } else if (q.endsWith(WHERE)) {
            q = q.substring(0, q.lastIndexOf(WHERE));
        }
        q += " ORDER BY u.user_id desc";

        try {
            List<User> list = sessionFactory.getCurrentSession().createNativeQuery(q, User.class).getResultList();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public int updateUser(HttpSession session, UserUpdateDto userUpdateDto) {
        String q = UPDATE;

        if (!StringUtils.isBlank(userUpdateDto.getUsername()))
            q += " username = '" + userUpdateDto.getUsername() + "'" + COMMA;

        if (!StringUtils.isBlank(userUpdateDto.getContact()))
            q += " contact = '" + userUpdateDto.getContact() + "'" + COMMA;

        if (!StringUtils.isBlank(userUpdateDto.getEmail()))
            q += " email = '" + userUpdateDto.getEmail() + "'" + COMMA;

        if (userUpdateDto.getCount() != null)
            q += "question = question + 1" + COMMA;

        if (!StringUtils.isBlank(userUpdateDto.getPassword()))
            q += " password = '" + userUpdateDto.getPassword() + "'" + COMMA;

        if (!StringUtils.isBlank(userUpdateDto.getCreated_by()))
            q += " creator_by = '" + userUpdateDto.getCreated_by() + "'" + COMMA;

        if (userUpdateDto.getStatus() != null)
            q += " status = " + userUpdateDto.getStatus() + COMMA;

        if (q.endsWith(COMMA)) {
            q = q.substring(0, q.lastIndexOf(COMMA));
        }
        q += " WHERE user_id = " + session.getAttribute("user_id");

        try {
            return sessionFactory.getCurrentSession().createNativeQuery(q).executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public User searchById(long user_id) {
        String q = SELECT + WHERE + "u.user_id = " + user_id;
        try {
            User list = sessionFactory.getCurrentSession().createNativeQuery(q, User.class).getSingleResult();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isRecordExist(String email, String contact) {
        String q = SELECT + WHERE;

        if (!StringUtils.isBlank(email))
            q += "u.email = '" + email + "' " + AND;

        if (!StringUtils.isBlank(contact))
            q += "u.contact = '" + contact + "' " + AND;

        if (q.endsWith(AND)) {
            q = q.substring(0, q.lastIndexOf(AND));
        } else if (q.endsWith(WHERE)) {
            q = q.substring(0, q.lastIndexOf(WHERE));
        }

        q = q + "order by user_id desc";

        try {
            List<User> list = sessionFactory.getCurrentSession().createNativeQuery(q, User.class).getResultList();
            if (list.size() > 0)
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RepositoryException("SAVE", e);
        }
    }

    public boolean existByEmail(String email) {
        String query = SELECT + WHERE + "u.email=:mail";
        try {
            User user = (User) sessionFactory.getCurrentSession().createNativeQuery(query)
                    .setParameter("mail", email)
                    .addEntity(User.class)
                    .getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User searchByEmail(String email) {
        String q = "select * from users as u where u.email=?";
        try {
            User user = (User) sessionFactory.getCurrentSession()
                    .createNativeQuery(q, User.class)
                    .setParameter(1, email)
                    .getSingleResult();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}