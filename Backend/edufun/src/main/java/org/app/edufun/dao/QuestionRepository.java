package org.app.edufun.dao;

import org.app.edufun.dto.QuestionSearchDto;
import org.app.edufun.dto.QuestionUpdateDto;
import org.app.edufun.entity.Questions;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
@Transactional
public class QuestionRepository {
    @Autowired
    private SessionFactory sessionFactory;
    private final String SELECT = "select * from questions as q ";
    private final String WHERE = " where ";
    private final String AND = " and ";
    private final String UPDATE = "UPDATE questions SET ";
    private final String COMMA = ", ";

    public List<Questions> searchQuestion(QuestionSearchDto questionSearchDto) {

        String q = SELECT + WHERE;

        if (questionSearchDto.getQuestion_id() != null)
            q += "q.question_id = " + questionSearchDto.getQuestion_id() + " " + AND;
//
//        if (!StringUtils.isBlank(userSearchDto.getName()))
//            q += "u.name LIKE '%" + userSearchDto.getName() + "%'" + AND;
//
//        if (!StringUtils.isBlank(userSearchDto.getEmail()))
//            q += "u.email LIKE '%" + userSearchDto.getEmail() + "%'" + AND;
//
//        if (!StringUtils.isBlank(userSearchDto.getContact()))
//            q += "u.contact LIKE '%" + userSearchDto.getContact() + "%'" + AND;
//
//        if (!StringUtils.isBlank(userSearchDto.getPassword()))
//            q += "u.password LIKE '%" + userSearchDto.getPassword() + "%'" + AND;
//
//        if (!StringUtils.isBlank(userSearchDto.getCreator_by()))
//            q += "u.creator_by LIKE '%" + userSearchDto.getCreator_by() + "%'" + AND;
//
//        if (!StringUtils.isBlank(userSearchDto.getDt()))
//            q += "cast(u.dt as date) = '" + userSearchDto.getDt() + "' " + AND;
//
//        if (userSearchDto.getStatus() != null)
//            q += "u.status = " + userSearchDto.getStatus() + " " + AND;


        if (q.endsWith(AND)) {
            q = q.substring(0, q.lastIndexOf(AND));
        } else if (q.endsWith(WHERE)) {
            q = q.substring(0, q.lastIndexOf(WHERE));
        }
        q += " ORDER BY q.question_id desc";

        try {
            List<Questions> list = sessionFactory.getCurrentSession().createNativeQuery(q, Questions.class).getResultList();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Questions> searchQues(HttpSession session, QuestionSearchDto questionSearchDto) {
        Long userId = (Long) session.getAttribute("user_id");

        if (userId == null) {
            return new ArrayList<>();
        }

        String q = "SELECT q.question_id, q.user_id, q.question, q.option1, q.option2, q.option3, " +
                "q.option4, q.answer, q.isoption, q.statement1, q.statement2, q.statement3, " +
                "q.image, q.created_by, q.category, q.dt, CASE WHEN s.status = 0 THEN 1 ELSE COALESCE(s.status, q.status) END AS status " +
                "FROM questions q " +
                "LEFT JOIN submission s ON q.question_id = s.question_id AND s.user_id = :userId " +
                "ORDER BY q.question_id DESC";

        try {
            List<Questions> list = sessionFactory.getCurrentSession()
                    .createNativeQuery(q, Questions.class)
                    .setParameter("userId", userId)
                    .getResultList();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }



    public boolean saveQuestion(Questions questions) {
        long new_Question_id = (long) sessionFactory.getCurrentSession().save(questions);
        if (new_Question_id > 0) {
            return true;
        } else {
            return false;
        }
    }

    public int updateQuestion(QuestionUpdateDto questionUpdateDto) {

        String q = UPDATE;

//        if (!StringUtils.isBlank(userUpdateDto.getName()))
//            q += " name = '" + userUpdateDto.getName() + "'" + COMMA;
//
//        if (!StringUtils.isBlank(userUpdateDto.getContact()))
//            q += " contact = '" + userUpdateDto.getContact() + "'" + COMMA;
//
//        if (!StringUtils.isBlank(userUpdateDto.getEmail()))
//            q += " email = '" + userUpdateDto.getEmail() + "'" + COMMA;
//
//        if (!StringUtils.isBlank(userUpdateDto.getPassword()))
//            q += " password = '" + userUpdateDto.getPassword() + "'" + COMMA;
//
//        if (!StringUtils.isBlank(userUpdateDto.getCreated_by()))
//            q += " creator_by = '" + userUpdateDto.getCreated_by() + "'" + COMMA;
//
        if (questionUpdateDto.getStatus() != null)
            q += " status = " + questionUpdateDto.getStatus() + COMMA;

        if (q.endsWith(COMMA)) {
            q = q.substring(0, q.lastIndexOf(COMMA));
        }
        q = q + " WHERE question_id = " + questionUpdateDto.getQuestion_id();

        try {
            return sessionFactory.getCurrentSession().createNativeQuery(q).executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Questions searchById(long question_id) {
        String q = SELECT + WHERE + "q.question_id = " + question_id;
        try {
            Questions list = sessionFactory.getCurrentSession().createNativeQuery(q, Questions.class).getSingleResult();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
