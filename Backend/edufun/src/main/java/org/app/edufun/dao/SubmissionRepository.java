package org.app.edufun.dao;

import org.app.edufun.dto.SubmissionSearchDto;
import org.app.edufun.entity.Submission;
import org.app.edufun.entity.TopPeformer;
import org.app.edufun.entity.TopperInfo;
import org.app.edufun.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
@Repository
@SuppressWarnings("unchecked")
@Transactional
public class SubmissionRepository {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private UserRepository userRepository;

    private final String SELECT = "select * from submission as s ";
    private final String WHERE = " where ";
    private final String AND = " and ";
    private final String UPDATE = "UPDATE submission SET ";
    private final String COMMA = ", ";

    public boolean saveSubmission(Submission submission) {
        long new_Submission_id = (long) sessionFactory.getCurrentSession().save(submission);
        if (new_Submission_id > 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<Submission> searchSubmission(HttpSession session, SubmissionSearchDto submissionSearchDto) {

        String q = SELECT + WHERE;

//        if (userSearchDto.getUser_id() != null)
//            q += "u.user_id = " + userSearchDto.getUser_id() + " " + AND;
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
        q += " ORDER BY u.user_id desc";

        try {
            List<Submission> list = sessionFactory.getCurrentSession().createNativeQuery(q, Submission.class).getResultList();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    public List<TopperInfo> searchQuestionTopper(HttpSession session, SubmissionSearchDto submissionSearchDto) {
        String q = "SELECT s.* FROM Submission s WHERE s.question_id = " + submissionSearchDto.getQuestion_id() + " and s.status = 1 ORDER BY s.submissionTime ASC, s.dt ASC";

        try {
            List<Submission> submissionList = sessionFactory.getCurrentSession().createNativeQuery(q, Submission.class).getResultList();

            List<TopperInfo> topperInfoList = new ArrayList<>();

            for (Submission submission : submissionList) {
                Long userId = submission.getUser_id();
                User list = userRepository.searchById(userId);
                String submissionTime = submission.getSubmissionTime();

                TopperInfo topperInfo = new TopperInfo();
                topperInfo.setUsername(list.getUsername());
                topperInfo.setAvatar(list.getAvatar());
                topperInfo.setSubmissionTime(submissionTime);

                topperInfoList.add(topperInfo);
            }

            return topperInfoList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<TopPeformer> searchTopPerformer(HttpSession session) {
        String q = "SELECT user_id " +
                "FROM Submission " +
                "WHERE dt >= DATE_SUB(NOW(), INTERVAL 1 MONTH) " +
                "AND status = 1 " +
                "GROUP BY user_id " +
                "HAVING COUNT(question_id) = (" +
                "SELECT MAX(question_count) " +
                "FROM (" +
                " SELECT user_id, COUNT(question_id) AS question_count " +
                "FROM Submission " +
                "WHERE dt >= DATE_SUB(NOW(), INTERVAL 1 MONTH) " +
                "AND status = 1 " +
                "GROUP BY user_id " +
                ") AS counts " +
                ") " +
                "ORDER BY MIN(submissionTime) ASC " +
                "LIMIT 100;";

        try {
            List<BigInteger> userIdList = sessionFactory.getCurrentSession().createNativeQuery(q).getResultList();

            List<TopPeformer> topperformerList = new ArrayList<>();

            for (BigInteger userIdBigInteger : userIdList) {
                Long userId = userIdBigInteger.longValue(); // Convert BigInteger to Long
                User user = userRepository.searchById(userId);

                if (user != null) {
                    TopPeformer topPerformer = new TopPeformer();
                    topPerformer.setUsername(user.getUsername());
                    topPerformer.setAvatar(user.getAvatar());
                    topPerformer.setQuestion(user.getQuestion());

                    topperformerList.add(topPerformer);
                }
            }

            System.out.println(topperformerList);

            return topperformerList;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Submission searchById(long submission_id) {
        String q = SELECT + WHERE + "s.submission_id = " + submission_id;
        try {
            Submission list = sessionFactory.getCurrentSession().createNativeQuery(q, Submission.class).getSingleResult();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}





