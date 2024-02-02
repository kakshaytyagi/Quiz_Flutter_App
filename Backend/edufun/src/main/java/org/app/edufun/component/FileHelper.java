package org.app.edufun.component;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVWriter;

@Component
public class FileHelper {


	public boolean uploadAnyFile(MultipartFile file, String uploadpath, String filename) {

		boolean status = false;
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				uploadpath = new File(uploadpath).getAbsolutePath();
				File dir = new File(uploadpath);
				if (!dir.exists())
					dir.mkdirs();
				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + filename);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				status = true;
			} catch (Exception e) {
				e.printStackTrace();
				status = false;
			}
		}
		return status;

	}


	public static String longToString(long i) {

		String s = String.valueOf(i);
		if (s == null || s.isEmpty()) {
			return "-";
		} else {
			return s;
		}
	}

	public static String doubleToString(double d) {
		String s = String.valueOf(d);
		if (s == null || s.isEmpty()) {
			return "-";
		} else {
			return s;
		}
	}

	public static String dateToString(Date date) {

		if (date == null) {
			return "-";
		} else {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String strDate = dateFormat.format(date);
			if (strDate.isEmpty() || strDate == null) {

				return "-";
			} else {
				return strDate;
			}
		}
	}

	public static String intToString(int i) {
		String s = String.valueOf(i);
		if (s == null || s.isEmpty()) {
			return "-";
		} else {
			return s;

		}
	}

	public static String stringModifier(String s) {

		if (s == null || s.isEmpty()) {
			return "-";
		} else {
			return s;
		}
	}

	/*public static File getJobFile(List<Jobs> list) {

		File file = new File(
				"F:\\Srn project\\Recruiter update\\Project\\Recruiter\\src\\main\\resources\\download_files\\Job.csv");
//		File file = new File("/home/ubuntu/app/internal/dev/recruiter-app/disk/download_files/Job.csv");
//		File file = new File("/home/ubuntu/app/internal/recruiter/disk/download_files/Job.csv");
		try {
			FileWriter outputfile = new FileWriter(file);

			CSVWriter writer = new CSVWriter(outputfile);

			String[] header = {"job_id","client_id","domain", "job_role","skills","jd","min_yr_exp",
					"max_yr_exp", "max_yr_budget","qualification","location"," job_type",
					"working_mode","head_count", " hired_count", "open","created_by","dt","status" };
			writer.writeNext(header);
			for (Jobs job : list) {
				if (job != null) {

//					long job_id, int client_id, String domain, String job_role, String skills, String jd, double min_yr_exp,
//					double max_yr_exp, double max_yr_budget, String qualification, String location, String job_type,
//					String working_mode, int head_count, int hired_count, String open, String created_by, Date dt, int status				

					String[] data1 = { stringModifier(longToString(job.getJob_id())),
							stringModifier(intToString(job.getClient_id())),stringModifier(job.getDomain()),
							stringModifier(job.getJob_role()), stringModifier(job.getSkills()),
							stringModifier(job.getJd()), stringModifier(doubleToString(job.getMin_yr_exp())),
							stringModifier(doubleToString(job.getMax_yr_exp())), stringModifier(doubleToString(job.getMax_yr_budget())),
							 stringModifier(job.getQualification()),stringModifier(job.getLocation()), stringModifier(job.getJob_type().toString()),
							 stringModifier(job.getWorking_mode().toString()),stringModifier(intToString(job.getHead_count())),stringModifier(intToString(job.getHired_count())),
							 stringModifier(intToString(job.getOpen())), stringModifier(job.getCreated_by()),stringModifier(dateToString(job.getDt())),stringModifier(intToString(job.getStatus()))
							
							  };
					writer.writeNext(data1);
				} else {
					break;

				}
			}
			writer.close();
			return file;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
*/

	public static boolean checkFileFormat(MultipartFile file) {

		String fileContentType = file.getContentType();
		if (fileContentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
			return true;
		} else {
			return false;
		}
	}

	public static int numericParser(String str) {
		if (str.isBlank()) {
			return 0;
		} else {
			return Integer.parseInt(str);
		}
	}

	public static Double doubleParser(String str) {

		try {
			return Double.valueOf(str);

		} catch (NumberFormatException e) {
			return null;
		}
	}


//	long application_id, int profile_id, String applied_for, Date dt, String name, long contact,
//	long alternate_contact, String email, String alternate_email, double exp, String ctc, String ectc, int np,
//	String company, String current_role, String qualification, int year_of_passing, String skills,
//	long client_id, long job_id, String location, int status, int active

//	public static List<Application> getAppExcelFile(InputStream IS) {
//
//		List<Application> fileRecords = new ArrayList<Application>();
//
//		DataFormatter formatter = new DataFormatter();
//		String str = "-";
//		try {
//			XSSFWorkbook wb1 = new XSSFWorkbook(IS);
//			XSSFSheet sheet1 = wb1.getSheetAt(0);
//
//			Iterator<Row> iterator = sheet1.iterator();
//			int rowNumber = 0;
//			while (iterator.hasNext()) {
//
//				Row row = iterator.next();
//
//				if (rowNumber == 0) {
//					rowNumber++;
//					continue;
//				} else {
//					Iterator<Cell> cells = row.iterator();
//
//					int cellNumber = 0;
//
//					Application application = new Application();
//					while (cells.hasNext()) {
//
//						Cell cell = cells.next();
////						String cellValue1 = formatter.formatCellValue(cell);
//						if (cellNumber == 0) {
//							Date date = new Date();
//							application.setApp_creation_date(date);
////							 if (cell == null) {
////					              // This cell is empty/blank/un-used, handle as needed
////					            } else {
////					               String cellStr = fmt.formatCell(cell);
////					               // Do something with the value
////					            }
//							String cellValue = formatter.formatCellValue(cell);
//							if (cellValue.equals(str) || cellValue.isBlank() || cellValue == null) {
//								application.setProfile_id(0);
//							} else {
//								application.setProfile_id(StringToInt(cellValue));
//							}
//						} else if (cellNumber == 1) {
//							String cellValue = formatter.formatCellValue(cell);
//							if (cellValue.equals(str) || cellValue.isBlank() || cellValue == null) {
//								application.setApplied_for(null);
//							} else {
//								application.setApplied_for(cell.getStringCellValue());
//							}
//						} else if (cellNumber == 2) {
//							String cellValue = formatter.formatCellValue(cell);
//
//							if (cellValue.equals(str) || cellValue.isBlank() || cellValue == null) {
//								application.setName(null);
//							} else {
//								application.setName(cell.getStringCellValue());
//							}
//						} else if (cellNumber == 3) {
//							String cellValue = formatter.formatCellValue(cell);
//							if (cellValue.equals(str) || cellValue.isBlank() || cellValue == null) {
//								application.setContact(null);
//							} else {
//								application.setContact(cellValue);
//							}
//						} else if (cellNumber == 4) {
//							String cellValue = formatter.formatCellValue(cell);
//							if (cellValue.equals(str) || cellValue.isBlank() || cellValue == null) {
//								application.setAlternate_contact(null);
//							} else {
//								application.setAlternate_contact(cellValue);
//							}
//						} else if (cellNumber == 5) {
//							String cellValue = formatter.formatCellValue(cell);
//							if (cellValue.equals(str) || cellValue.isBlank() || cellValue == null) {
//								application.setEmail(null);
//							} else {
//								application.setEmail(cell.getStringCellValue());
//							}
//						} else if (cellNumber == 6) {
//							String cellValue = formatter.formatCellValue(cell);
//							if (cellValue.equals(str) || cellValue.isBlank() || cellValue == null) {
//								application.setAlternate_email(null);
//							} else {
//								application.setAlternate_email(cellValue);
//							}
//						} else if (cellNumber == 7) {
//							String cellValue = formatter.formatCellValue(cell);
//							if (cellValue.equals(str) || cellValue.isBlank() || cellValue == null) {
//								application.setExp(null);
//							} else {
//								application.setExp(cellValue);
//							}
//						} else if (cellNumber == 8) {
//							String cellValue = formatter.formatCellValue(cell);
//							if (cellValue.equals(str) || cellValue.isBlank() || cellValue == null) {
//								application.setCtc(null);
//							} else {
//								application.setCtc(cellValue);
//							}
//						} else if (cellNumber == 9) {
//							String cellValue = formatter.formatCellValue(cell);
//							if (cellValue.equals(str) || cellValue.isBlank() || cellValue == null) {
//								application.setEctc(null);
//							} else {
//								application.setEctc(cellValue);
//							}
//						} else if (cellNumber == 10) {
//							String cellValue = formatter.formatCellValue(cell);
//							if (cellValue.equals(str) || cellValue.isBlank() || cellValue == null) {
//								application.setNotice_peroid(null);
//							} else {
//								application.setNotice_peroid(cellValue);
//							}
//						} else if (cellNumber == 11) {
//							String cellValue = formatter.formatCellValue(cell);
//							if (cellValue.equals(str) || cellValue.isBlank() || cellValue == null) {
//								application.setCompany(null);
//							} else {
//								application.setCompany(cell.getStringCellValue());
//							}
//						} else if (cellNumber == 12) {
//							String cellValue = formatter.formatCellValue(cell);
//							if (cellValue.equals(str) || cellValue.isBlank() || cellValue == null) {
//								application.setCurrent_role(null);
//							} else {
//								application.setCurrent_role(cell.getStringCellValue());
//							}
//						} else if (cellNumber == 13) {
//							String cellValue = formatter.formatCellValue(cell);
//							if (cellValue.equals(str) || cellValue.isBlank() || cellValue == null) {
//								application.setQualification(null);
//							} else {
//								application.setQualification(cell.getStringCellValue());
//							}
//						} else if (cellNumber == 14) {
//							String cellValue = formatter.formatCellValue(cell);
//
//							if (cellValue.equals(str) || cellValue.isBlank() || cellValue == null) {
//								application.setYear_of_passing(null);
//							} else {
//								application.setYear_of_passing(cellValue);
//							}
//						} else if (cellNumber == 15) {
//							String cellValue = formatter.formatCellValue(cell);
//							if (cellValue.equals(str) || cellValue.isBlank() || cellValue == null) {
//								application.setDomain(null);
//							} else {
//								application.setDomain(cellValue);
//							}
//						} else if (cellNumber == 16) {
//							String cellValue = formatter.formatCellValue(cell);
//							if (cellValue.equals(str) || cellValue.isEmpty() || cellValue == null) {
//								application.setClient_id(0);
//							} else {
//								application.setClient_id(StringToInt(cellValue));
//							}
//						} else if (cellNumber == 17) {
//							String cellValue = formatter.formatCellValue(cell);
//							if (cellValue.equals(str) || cellValue.isBlank() || cellValue == null) {
//								application.setJob_id(0);
//							} else {
//								application.setJob_id(StringToInt(cellValue));
//							}
//						} else if (cellNumber == 18) {
//							String cellValue = formatter.formatCellValue(cell);
//							if (cellValue.equals(str) || cellValue.isBlank() || cellValue == null) {
//								application.setLocation(null);
//							} else {
//								application.setLocation(cell.getStringCellValue());
//							}
//						} else if (cellNumber == 19) {
//							application.setStatus(1);
//							String cellValue = formatter.formatCellValue(cell);
//							if (cellValue.equals(str) || cellValue.isBlank() || cellValue == null) {
//								application.setActive(null);
//							} else {
//								application.setActive(cellValue);
//							}
//						} else {
//							continue;
//						}
//						cellNumber++;
//					}
//					fileRecords.add(application);
//
//				}
//				rowNumber++;
//			}
//			wb1.close();
//			return fileRecords;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}

	public static long StringToLong(String s) {
		if (s.isBlank() || s == null) {
			return 0;
		} else {
			long l = Long.parseLong(s);
			return l;
		}

	}

	public static double StringToDouble(String s) {
		double d = Double.parseDouble(s);
		return d;
	}

	public static int StringToInt(String s) {
		int i = 0;
//		if (s == null || s.isEmpty() || s.trim().isEmpty()) {
//			return i;
//		}
		i = Integer.parseInt(s);
		return i;
	}

	public static String dateConvertor(String str) {

		Date date1 = null;
		String pattern1 = "((([1-9])|([0][1-9])|([12][0-9])|([3][0-1]))/(([0][1-9])|([1][0-2]))/((\\d{4})|(\\d{2})))";
		String pattern2 = "((([1-9])|([0][1-9])|([12][0-9])|([3][0-1]))-(([JFMJASOND][a-z]+)|([jfmjasond][a-z]+))-((\\d{4})|(\\d{2})))";
		String pattern3 = "((([0][1-9])|([1][0-2])) (([1-9])|([0][1-9])|([12][0-9])|([3][0-1])),((\\d{4})|(\\d{2})))";
		String pattern4 = "(((\\d{4})|(\\d{2}))-(([0][1-9])|([1][0-2]))-(([1-9])|([0][1-9])|([12][0-9])|([3][0-1])))";
		String pattern5 = "((([0][1-9])|([1][0-2]))/(([1-9])|([0][1-9])|([12][0-9])|([3][0-1]))/((\\d{4})|(\\d{2})))";
		String formatedDate;
		try {
			if (str.matches(pattern1) == true) {

				SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
				date1 = formatter1.parse(str);
				Calendar cal = Calendar.getInstance();
				cal.setTime(date1);

				if ((cal.get(Calendar.MONTH) + 1) < 10 && (cal.get(Calendar.DATE)) < 10) {
					formatedDate = cal.get(Calendar.YEAR) + "-0" + (cal.get(Calendar.MONTH) + 1) + "-0"
							+ cal.get(Calendar.DATE);

				} else if ((cal.get(Calendar.MONTH) + 1) < 10) {
					formatedDate = cal.get(Calendar.YEAR) + "-0" + (cal.get(Calendar.MONTH) + 1) + "-"
							+ cal.get(Calendar.DATE);

				} else if ((cal.get(Calendar.DATE)) < 10) {
					formatedDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-0"
							+ cal.get(Calendar.DATE);

				} else {
					formatedDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
							+ cal.get(Calendar.DATE);
				}

				System.out.println("formatedDate : " + formatedDate);
				return formatedDate;
			} else if (str.matches(pattern2) == true) {
				SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MMM-yyyy");
				date1 = formatter1.parse(str);
				Calendar cal = Calendar.getInstance();
				cal.setTime(date1);

				if ((cal.get(Calendar.MONTH) + 1) < 10 && (cal.get(Calendar.DATE)) < 10) {
					formatedDate = cal.get(Calendar.YEAR) + "-0" + (cal.get(Calendar.MONTH) + 1) + "-0"
							+ cal.get(Calendar.DATE);

				} else if ((cal.get(Calendar.MONTH) + 1) < 10) {
					formatedDate = cal.get(Calendar.YEAR) + "-0" + (cal.get(Calendar.MONTH) + 1) + "-"
							+ cal.get(Calendar.DATE);

				} else if ((cal.get(Calendar.DATE)) < 10) {
					formatedDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-0"
							+ cal.get(Calendar.DATE);

				} else {
					formatedDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
							+ cal.get(Calendar.DATE);
				}

				System.out.println("formatedDate : " + formatedDate);
				return formatedDate;
			} else if (str.matches(pattern3) == true) {
				SimpleDateFormat formatter1 = new SimpleDateFormat("MM dd,yyyy");
				date1 = formatter1.parse(str);
				Calendar cal = Calendar.getInstance();
				cal.setTime(date1);

				if ((cal.get(Calendar.MONTH) + 1) < 10 && (cal.get(Calendar.DATE)) < 10) {
					formatedDate = cal.get(Calendar.YEAR) + "-0" + (cal.get(Calendar.MONTH) + 1) + "-0"
							+ cal.get(Calendar.DATE);

				} else if ((cal.get(Calendar.MONTH) + 1) < 10) {
					formatedDate = cal.get(Calendar.YEAR) + "-0" + (cal.get(Calendar.MONTH) + 1) + "-"
							+ cal.get(Calendar.DATE);

				} else if ((cal.get(Calendar.DATE)) < 10) {
					formatedDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-0"
							+ cal.get(Calendar.DATE);

				} else {
					formatedDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
							+ cal.get(Calendar.DATE);
				}

				System.out.println("formatedDate : " + formatedDate);
				return formatedDate;
			} else if (str.matches(pattern4) == true) {
				SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
				date1 = (Date) formatter1.parse(str);

				Calendar cal = Calendar.getInstance();
				cal.setTime(date1);

				if ((cal.get(Calendar.MONTH) + 1) < 10 && (cal.get(Calendar.DATE)) < 10) {
					formatedDate = cal.get(Calendar.YEAR) + "-0" + (cal.get(Calendar.MONTH) + 1) + "-0"
							+ cal.get(Calendar.DATE);

				} else if ((cal.get(Calendar.MONTH) + 1) < 10) {
					formatedDate = cal.get(Calendar.YEAR) + "-0" + (cal.get(Calendar.MONTH) + 1) + "-"
							+ cal.get(Calendar.DATE);

				} else if ((cal.get(Calendar.DATE)) < 10) {
					formatedDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-0"
							+ cal.get(Calendar.DATE);

				} else {
					formatedDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
							+ cal.get(Calendar.DATE);
				}

				System.out.println("formatedDate : " + formatedDate);
				return formatedDate;
			} else if (str.matches(pattern5) == true) {
				SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy");
				date1 = formatter1.parse(str);
				Calendar cal = Calendar.getInstance();
				cal.setTime(date1);

				if ((cal.get(Calendar.MONTH) + 1) < 10 && (cal.get(Calendar.DATE)) < 10) {
					formatedDate = cal.get(Calendar.YEAR) + "-0" + (cal.get(Calendar.MONTH) + 1) + "-0"
							+ cal.get(Calendar.DATE);

				} else if ((cal.get(Calendar.MONTH) + 1) < 10) {
					formatedDate = cal.get(Calendar.YEAR) + "-0" + (cal.get(Calendar.MONTH) + 1) + "-"
							+ cal.get(Calendar.DATE);

				} else if ((cal.get(Calendar.DATE)) < 10) {
					formatedDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-0"
							+ cal.get(Calendar.DATE);

				} else {
					formatedDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
							+ cal.get(Calendar.DATE);
				}

				System.out.println("formatedDate : " + formatedDate);
				return formatedDate;
			} else {
				return null;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date datePatternSelector(String str) {

		Date date1 = null;
		String pattern1 = "((([1-9])|([0][1-9])|([12][0-9])|([3][0-1]))/(([0][1-9])|([1][0-2]))/((\\d{4})|(\\d{2})))";
		String pattern2 = "((([1-9])|([0][1-9])|([12][0-9])|([3][0-1]))-(([JFMJASOND][a-z]+)|([jfmjasond][a-z]+))-((\\d{4})|(\\d{2})))";
		String pattern3 = "((([0][1-9])|([1][0-2])) (([1-9])|([0][1-9])|([12][0-9])|([3][0-1])),((\\d{4})|(\\d{2})))";
		String pattern4 = "(((\\d{4})|(\\d{2}))-(([0][1-9])|([1][0-2]))-(([1-9])|([0][1-9])|([12][0-9])|([3][0-1])))";
		String pattern5 = "((([0][1-9])|([1][0-2]))/(([1-9])|([0][1-9])|([12][0-9])|([3][0-1]))/((\\d{4})|(\\d{2})))";
		String pattern6 = "((([1-9])|([0][1-9])|([12][0-9])|([3][0-1]))-(([0][1-9])|([1][0-2]))-((\\\\d{4})|(\\\\d{2})))";

		try {
			if (str.matches(pattern1) == true) {

				SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
				date1 = formatter1.parse(str);
				System.out.println("1" + date1);
//				String x = "";
				return date1;
			} else if (str.matches(pattern2) == true) {
				SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MMM-yyyy");
				date1 = formatter1.parse(str);
				System.out.println("2" + date1);
				return date1;
			} else if (str.matches(pattern3) == true) {
				SimpleDateFormat formatter1 = new SimpleDateFormat("MM dd,yyyy");
				date1 = formatter1.parse(str);
				System.out.println("3" + date1);
				return date1;
			} else if (str.matches(pattern4) == true) {
				SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
				date1 = (Date) formatter1.parse(str);
				System.out.println("4" + date1);

				Calendar cal = Calendar.getInstance();
				cal.setTime(date1);
//				String formatedDate = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" +         cal.get(Calendar.YEAR);
				String formatedDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
						+ cal.get(Calendar.DATE);
				System.out.println("formatedDate : " + formatedDate);
				return date1;
			} else if (str.matches(pattern5) == true) {
				SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy");
				date1 = formatter1.parse(str);
				System.out.println("5" + date1);
				return date1;
			} else if (str.matches(pattern6) == true) {
				SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
				date1 = formatter1.parse(str);
				System.out.println("6" + date1);
				return date1;
			} else {
				return null;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

}