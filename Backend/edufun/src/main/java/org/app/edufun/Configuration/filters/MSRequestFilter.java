package org.app.edufun.Configuration.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.app.edufun.Configuration.jwt.JWTConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.app.edufun.constant.MutableHttpServletRequest;

import lombok.extern.slf4j.Slf4j;

//@Component
//@Slf4j
//public class MSRequestFilter extends OncePerRequestFilter {
//
//	@Autowired
//	private JWTConfig jWTConfig;
//
//	Logger log = LoggerFactory.getLogger(MSRequestFilter.class);
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		final String authenticationHeader = request.getHeader("Authentication");
//
//		String username = null;
//		String jwtToken = null;
//
//		if (authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {
//			jwtToken = authenticationHeader.substring(7);
//			username = jWTConfig.extractUsername(jwtToken);
//		}
//
//		MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(request);
//		mutableRequest.putHeader("email", username);
//
//		log.info("Microserivices Request is executed...");
//
//		filterChain.doFilter(mutableRequest, response);
//	}
//
//	public String getAllTokenData(HttpSession session, String jwtToken) {
//    try {
//
//	java.util.Base64.Decoder decoder = java.util.Base64.getUrlDecoder();
//	String[] parts = jwtToken.split("\\."); // split out the "parts" (header, payload and signature)
//
//	String payloadJson = new String(decoder.decode(parts[1]));
//	String payload = "[" + payloadJson + "]";
//	System.out.println(payloadJson);
//
//	boolean checkSession = true;
//
//	JSONArray array1 = new JSONArray(payload);
//	JSONObject object = null;
//	JSONObject object2 = null;
//	for (int i = 0; i < array1.length(); i++) {
//		object = array1.getJSONObject(i);
//		object2 = object.getJSONObject("data");
//		System.out.println(object.getJSONObject("data"));
//
//	}
//	String name = object2.getString("username");
//	String email = object2.getString("email");
//	String contact = object2.getString("contact");
//
//	System.out.println("print name here" + name);
//
////	session.setMaxInactiveInterval(3600);
//
//	session.setAttribute("isSession", "true");
//	session.setAttribute("name", name);
//	session.setAttribute("email", email);
//	session.setAttribute("contact", contact);
//
//	return payloadJson;
//}catch(Exception e) {
//	e.printStackTrace();
//	return null;
//}
//	}
//
//	public void validateToken(HttpSession session, String jwtToken) {
//		try {
//
//			java.util.Base64.Decoder decoder = java.util.Base64.getUrlDecoder();
//			String[] parts = jwtToken.split("\\."); // split out the "parts" (header, payload and signature)
//
//			String payloadJson = new String(decoder.decode(parts[1]));
//			String payload = "[" + payloadJson + "]";
//			System.out.println(payloadJson);
//
//			boolean checkSession = true;
//
//			JSONArray array1 = new JSONArray(payload);
//			JSONObject object = null;
//			JSONObject object2 = null;
//			for (int i = 0; i < array1.length(); i++) {
//				object = array1.getJSONObject(i);
//				object2 = object.getJSONObject("data");
//				System.out.println(object.getJSONObject("data"));
//
//			}
//			String name = object2.getString("username");
//			String email = object2.getString("email");
////			String contact = object2.getString("contact");
//
//			System.out.println("print name here" + name);
//
//			session.setAttribute("isSession", "true");
//			session.setAttribute("name", name);
//			session.setAttribute("email", email);
////			session.setAttribute("contact", contact);
//
//
//		}catch(Exception e) {
//			e.printStackTrace();
//
//		}
//			}
//
//
//}

@Component
@Slf4j
public class MSRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JWTConfig jWTConfig;

	Logger log = LoggerFactory.getLogger(MSRequestFilter.class);

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authenticationHeader = request.getHeader("Authentication");

		String username = null;
		String jwtToken = null;

		if (authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {
			jwtToken = authenticationHeader.substring(7);
			username = jWTConfig.extractUsername(jwtToken);
		}

		MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(request);
		mutableRequest.putHeader("email", username);

		log.info("Microservices Request is executed...");

		// Validate and set session attributes based on the JWT token
		HttpSession session = request.getSession();
		if (session.isNew() && jwtToken != null) {
			validateAndSetSessionAttributes(session, jwtToken);
		}

		filterChain.doFilter(mutableRequest, response);
	}

	public void validateAndSetSessionAttributes(HttpSession session, String jwtToken) {
		try {
			java.util.Base64.Decoder decoder = java.util.Base64.getUrlDecoder();
			String[] parts = jwtToken.split("\\."); // split out the "parts" (header, payload, and signature)

			String payloadJson = new String(decoder.decode(parts[1]));
			JSONArray array1 = new JSONArray("[" + payloadJson + "]");
			JSONObject object = array1.getJSONObject(0);

			JSONObject userData = object.getJSONObject("data");

			String username = userData.optString("username", "Akshay Tyagi"); // Use optString to handle missing keys gracefully
			String email = userData.optString("email", "akshaytyagi3102003@gmail.com");
			long userId = Long.parseLong(userData.optString("user_id", "1"));
			String contact = userData.optString("contact", "9870864763");
			String createdBy = userData.optString("created_by", "Akshay");
			// Add more attributes as needed

			session.setAttribute("isSession", "true");
			session.setAttribute("user_id", userId);
			session.setAttribute("username", username);
			session.setAttribute("email", email);
			session.setAttribute("contact", contact);
			session.setAttribute("created_by", createdBy);
			// Add more attributes as needed

		} catch (Exception e) {
			e.printStackTrace();
			// Handle the exception appropriately, e.g., return an error response
		}
	}


	public String getAllTokenData(HttpSession session, String jwtToken) {
		try {
			java.util.Base64.Decoder decoder = java.util.Base64.getUrlDecoder();
			String[] parts = jwtToken.split("\\."); // split out the "parts" (header, payload, and signature)

			String payloadJson = new String(decoder.decode(parts[1]));
			JSONArray array1 = new JSONArray("[" + payloadJson + "]");
			JSONObject object = array1.getJSONObject(0);
			JSONObject userData = object.getJSONObject("data");

			String username = userData.getString("username");
			String email = userData.getString("email");
			String contact = userData.optString("contact", ""); // Handle the case where "contact" is missing

			// Set session attributes
			session.setAttribute("isSession", "true");
			session.setAttribute("name", username);
			session.setAttribute("email", email);
			session.setAttribute("contact", contact);

			return payloadJson;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
