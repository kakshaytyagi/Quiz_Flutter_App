package org.app.edufun.constant;

public interface ResponseConstants {
	
	String REQUEST_SUCCESS = "Congrats ! Request has been proceed successfully.";
	String REQUEST_FAILED = "Error ! Request couldn't be proceed.";
	String USER_VERIFIED = "Congrats ! User has been verified.";
	String USER_NOT_VERIFIED = "Congrats ! User couldn't be verified.";
	
	String RECORD_NOT_FOUND = "Empty ! Record is not exist in database.";
	String RECORDS_NOT_FOUND = "Empty ! Records are not exist in database.";
	
	String DATABASE_ERROR_DURING_INSERTION = "Database Error ! Something went wrong during record insertion";
	String DATABASE_ERROR_DURING_SELCTION = "Database Error ! Something went wrong during record selection";
	String DATABASE_ERROR_DURING_UPDATION = "Database Error ! Something went wrong during record updation";
	
	String USER_ALREADY_EXIST = "Error ! User already exist. please try other email";
	String USER_PASSWORD_NOT_CHANGED = "Error ! User password could not changed.";
	
	String INVALID_USERNAME = "Error ! Invalid Username has been passed.";
	String INVALID_OTP = "Error ! Invalid OTP has been passed.";
	String OTP_VERIFIED = "Congrats ! OTP has been verified.";
	String OLD_PASSWORD_NOTMATCHED = "Error ! Old Password is not matched.";
}
