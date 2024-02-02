package org.app.edufun.Configuration.batch_config;
/*
import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.srn.web.Recruiter.dao_impl.Profile_dao_impl;
import org.srn.web.Recruiter.entity.Profile;

@Component
public class Batch_Config {

	@Autowired
	private Profile_dao_impl repos;
	

	@SuppressWarnings("resource")
	public boolean addRecordDB(String fileAddress) {
		String str;
		try {
			FileReader file1 = new FileReader(fileAddress);
			BufferedReader br = new BufferedReader(file1);
			String[] data = new String[14];
			while ((str = br.readLine()) != null ) {
				if(!str.isBlank()) {
				// name,contact,email,exp,ctc,ectc,np,current_role,company,location,resume,skills,dt,status
				 data = str.split(",");
				Profile profile = new Profile();
				profile.setName(data[0]);
				profile.setContact(parseLong(data[1]));
				profile.setEmail(data[2]);
				profile.setExp(parseDouble(data[3]));
				profile.setCtc(parseDouble(data[4]));
				profile.setEctc(parseInt(data[5]));
				profile.setNp(parseInt(data[6]));
				profile.setCurrent_role(data[7]);
				profile.setCompany(data[8]);
				profile.setLocation(data[9]);
				profile.setResume(data[10]);
				profile.setSkills(data[11]);
				profile.setDt(parseDate(data[12]));
				profile.setStatus(parseInt(data[13]));

				repos.saveProfile(profile);
				}	else {
					break;
				}
			}
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private double parseDouble(String exp) {
		double res = Double.parseDouble(exp);
		return res;
	}

	private long parseLong(String contact) {
		String s = contact;
		long l = Long.parseLong(s);
		return l;
	}

	private Date parseDate(String dt) throws ParseException {
		String sDate1 = dt;
		java.util.Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(sDate1);

		System.out.println("Util date in Java : " + date1);
		java.sql.Date sqlDate = new java.sql.Date(date1.getTime());
		return sqlDate;

	}

	/*
	 * public boolean reader(String fileAddress) {
	 * 
	 * String jdbcUrl = "jdbc:mysql://localhost:3306/recruiter_ms?useSSL=false";
	 * String username = "root"; String password = "MYSQLtuk67@";
	 * 
	 * String filePath = fileAddress;
	 * 
	 * int batchSize = 3; Connection connection = null; try { connection =
	 * DriverManager.getConnection(jdbcUrl, username, password);
	 * connection.setAutoCommit(false);
	 * 
	 * String sql =
	 * "insert into Profile(name,contact,email,exp,ctc,ectc,np,current_role,company,"
	 * + "location,resume,skills,dt,status)  values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	 * 
	 * PreparedStatement statement = connection.prepareStatement(sql);
	 * 
	 * BufferedReader lineReader = new BufferedReader(new FileReader(filePath));
	 * 
	 * String lineText = null; int count = 0; lineReader.readLine(); String[]
	 * data=null; while ((lineText = lineReader.readLine()) != null) {
	 * 
	 * 
	 * data = lineText.split(","); String name = data[0]; String contact = data[1];
	 * String email = data[2]; String exp = data[3]; String ctc = data[4]; String
	 * ectc = data[5]; String np = data[6]; String current_role = data[7]; String
	 * company = data[8]; String location = data[9]; String resume = data[10];
	 * String skills = data[11]; String dt = data[12]; String status = data[13];
	 * 
	 * // setting value to statement
	 * 
	 * statement.setString(1, name); statement.setLong(2, parseLong(contact));
	 * statement.setString(3, email); statement.setDouble(4, parseDouble(exp));
	 * statement.setDouble(5, parseDouble(ctc)); statement.setInt(6,
	 * parseInt(ectc)); statement.setInt(7, parseInt(np)); statement.setString(8,
	 * current_role); statement.setString(9, company); statement.setString(10,
	 * location); statement.setString(11, resume); statement.setString(12, skills);
	 * statement.setDate(13, parseDate(dt)); statement.setInt(14, parseInt(status));
	 * } statement.addBatch(); // count= count+1; if (count % batchSize == 0) {
	 * 
	 * statement.executeBatch();
	 * 
	 * }
	 * 
	 * lineReader.close(); statement.executeBatch();
	 * 
	 * connection.commit(); connection.close();
	 * 
	 * System.out.println("Data has been inserted Successfully");
	 * 
	 * return true;
	 * 
	 * }catch(
	 * 
	 * Exception exception) { exception.printStackTrace(); return false; }
	 * 
	 * }
	 * 
	 * private Date parseDate(String dt) throws ParseException { String sDate1 = dt;
	 * java.util.Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(sDate1);
	 * 
	 * System.out.println("Util date in Java : " + date1); java.sql.Date sqlDate =
	 * new java.sql.Date(date1.getTime()); return sqlDate;
	 * 
	 * // SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd"); // String
	 * dateInString = dt;; // // try { // // Date date = (Date)
	 * formatter.parse(dateInString); // System.out.println(date); //
	 * System.out.println(formatter.format(date)); //return date; // } catch
	 * (ParseException e) { // e.printStackTrace(); // return null; // } // //String
	 * strDate = dt; //// LocalDate date = LocalDate.parse(strDate); // // Date
	 * date1 = (Date) new SimpleDateFormat("yyyy/mm/dd").parse(strDate); // return
	 * date1; }
	 * 
	 * private double parseDouble(String exp) { double res =
	 * Double.parseDouble(exp); return res; }
	 * 
	 * private long parseLong(String contact) { // Long longObj =
	 * Long.valueOf(contact); String s = contact; long l = Long.parseLong(s); return
	 * l; }
	 */

