package org.app.edufun.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.app.edufun.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.app.edufun.entity.User;

@Component
public class SessionManagement {

	@Autowired
	private UserRepository userRepository;

	public HttpSession getSession(HttpServletRequest request,  String email) {
		// Authenticate the user (you may have a service method for authentication)
		User user = userRepository.searchByEmail(email);

		if (user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user_id", user.getUser_id());
			session.setAttribute("name", user.getUsername());
			session.setAttribute("email", user.getEmail());
			session.setAttribute("contact", user.getContact());
			session.setAttribute("created_by", user.getUsername());
			session.setMaxInactiveInterval(1800);
			return session;
		} else {
			System.out.println("Main yha sa aya hu ji");
			return null; // User not authenticated
		}
	}
}
