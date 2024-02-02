package org.app.edufun.service_impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.app.edufun.Configuration.filters.MSRequestFilter;
import org.app.edufun.constant.ResponseConstants;
import org.app.edufun.dao.UserMasterDao;
import org.app.edufun.dto.ResponseDto;
import org.app.edufun.dto.ResponseDtoLogin;
import org.app.edufun.entity.UserMaster;
import org.app.edufun.service.UserMasterService;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Service
@Transactional
public class UserMasterServiceImpl implements UserMasterService {

	@Autowired
	private UserMasterDao masterDao;
	

	@Autowired
	private MSRequestFilter msRequestFilter;


	public Object getUser(String email) {
		ResponseDtoLogin response = new ResponseDtoLogin();
		response.reset();

		Object obj = masterDao.getUser(email);

		if (obj != null) {
			return obj;
		} else {
			response.setStatus(false);
			response.setMessage(ResponseConstants.RECORD_NOT_FOUND);
			response.setData(null);
		}

		return null;
	}

	@Override
	public ResponseEntity<ResponseDto> addUser(HttpSession session, String token, String name, String password, String email, String contact) {
		return null;
	}

//	public void sendEmail(HttpSession sessin, String message, String subject, String to, String from, File file) {
//
//		// Variable for gmail
//		String host = "smtp.gmail.com";
//
//		// get the system properties
//		Properties properties = System.getProperties();
//		System.out.println("PROPERTIES " + properties);
//
//		// setting important information to properties object
//
//		// host set
//		properties.put("mail.smtp.host", host);
//		properties.put("mail.smtp.port", "465");
//		properties.put("mail.smtp.ssl.enable", "true");
//		properties.put("mail.smtp.auth", "true");
//
//		// Step 1: to get the session object..
//		Session session = Session.getInstance(properties, new Authenticator() {
//			@Override
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication("akshaytyagi3102003@gmail.com", "vfbifvqpwhstpcae");
//			}
//
//		});
//
//		session.setDebug(true);
//
//		// Step 2 : compose the message [text,multi media]
//		MimeMessage m = new MimeMessage(session);
//
//		try {
//
//			// from email
//			m.setFrom(from);
//
//			// adding recipient to message
//			m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//
//			// adding subject to message
//			m.setSubject(subject);
//
//			// adding text to message
//			m.setText(message);
//
//			// Create multi-part content
//			MimeMultipart mimeMultipart = new MimeMultipart();
//
//			// Add text part
//			MimeBodyPart textBodyPart = new MimeBodyPart();
//			textBodyPart.setText(message);
//			mimeMultipart.addBodyPart(textBodyPart);
//
//			// Add file part
//			if (file != null) {
//				MimeBodyPart fileBodyPart = new MimeBodyPart();
//				fileBodyPart.attachFile(file);
//				mimeMultipart.addBodyPart(fileBodyPart);
//			}
//
//			// Set message content
//			m.setContent(mimeMultipart);
//
//			// Step 3 : send the message using Transport class
//			Transport.send(m);
//			System.out.println("Successfully send mail");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println(e);
////
//		}
//
//	}
//
////	File file =new File ( "F:\\recruiter without jwt\\Recruiter\\src\\main\\resources\\upload_file\\resume04.pdf");
////	File file = new File(
////			"F:\\recruiter without jwt\\Recruiter\\src\\main\\resources\\download_files\\JOB_DESCRIPTION.pdf");
//	public void mailService(HttpSession session, String email, String password) {
//
////
//		String info = "username: " + email + "\n" + "password: " + password;
//		System.out.println(info);
////		String filePath = "F:\\recruiter without jwt\\Recruiter\\src\\main\\resources\\download_files\\Authentication.pdf";
//		String filePath = "/home/ubuntu/app/internal/dev/recruiter-app/disk/upload_files/Authentication.pdf";
//		Document document = new Document();
//		try {
//			PdfWriter.getInstance(document, new FileOutputStream(filePath));
//			document.open();
//			Paragraph paragraph = new Paragraph(info);
//			document.add(paragraph);
//			document.close();
//
//			System.out.println("PDF file created and data written successfully.");
//		} catch (DocumentException | IOException err) {
//			System.out.println("An error occurred while creating the PDF file.");
//			err.printStackTrace();
//		}
//		String message = "Hello user you have successfully registered !";
//		String subject = "Access credential of HRLancer !";
//		String to = email;
//		String from = "pankaj3214sharma@gmail.com";
////		File file = new File(
////				"F:\\recruiter without jwt\\Recruiter\\src\\main\\resources\\download_files\\Authentication.pdf");
//			 File file = new File(
//					"/home/ubuntu/app/internal/dev/recruiter-app/disk/upload_files/Authentication.pdf");
//		sendEmail(session, message, subject, to, from, file);
//
//	}


}
