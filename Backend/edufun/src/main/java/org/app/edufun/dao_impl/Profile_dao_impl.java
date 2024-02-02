package org.app.edufun.dao_impl;
//
//import java.util.List;
//
//import javax.servlet.http.HttpSession;
//import javax.transaction.Transactional;
//
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import org.springframework.web.client.HttpServerErrorException.InternalServerError;
//import org.app.edufun.entity.Profiles;
//
//
//@Repository
//@Transactional
//public class Profile_dao_impl implements Profile_dao {
//
//	@Autowired
//	private SessionFactory sessionRef;
//
//	@Override
//	public List<Profiles> fetchById(long id) {
//		String query = "select * from profile as p where p.profile_id =:identity";
//		try {
//			List<Profiles> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Profiles.class)
//					.setParameter("identity", id).list();
//			return list;
//		} catch (Exception e) {
//			return null;
//		}
//	}
//
//
//	@Override
//	public Profiles getById(long id) {
//		String query = "select * from profile as p where p.profile_id =:identity";
//		try {
//			Profiles prof = (Profiles) sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Profiles.class)
//					.setParameter("identity", id).getSingleResult();
//			return prof;
//		} catch (Exception e) {
//			return null;
//		}
//	}
//
//
//	@Override
//	public List<Profiles> findAllProfile(long  partner_id) {
//		String query = "select  * from profile as p where p.partner_id=:pid order by profile_id desc";
//		try {
//			List<Profiles> list = sessionRef.getCurrentSession().createNativeQuery(query)
//					.addEntity(Profiles.class).setParameter("pid", partner_id)
//					.list();
//			System.out.println("list content"+list.toString());
//			return list;
//		} catch (Exception e) {
//			return null;
//		}
//	}
//
//	@Override
//	public List<Profiles> findLatestProfile(){
//		String query = "select * from profile order by profile_id desc limit 10; ";
//		try {
//			List<Profiles> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Profiles.class)
//					.list();
//			System.out.println(list);
//			return list;
//		} catch (Exception e) {
//			return null;
//		}
//	}
//
//
//	@Override
//	public boolean saveProfile(Profiles prof) {
//
//		try {
//			long new_profile_id = (long) sessionRef.getCurrentSession().save(prof);
//			System.out.println("New record created :" + new_profile_id);
//			return true;
//		} catch (NullPointerException ne) {
//			return false;
//		} catch (InternalServerError Iserror) {
//			return false;
//		} catch (Exception e) {
//			return false;
//		}
//	}
//
//	@Override
//	public boolean newProfile(Profiles prof) {
//		try {
//			long id = (long) sessionRef.getCurrentSession().save(prof);
//
//			if (id > 0) {
//				System.out.println("Generated  id = " + id);
//				return true;
//			} else {
//				return false;
//			}
//		} catch (Exception e) {
//			System.out.println(e);
//			return false;
//		}
//	}
//
////	@Override
////	public boolean newProfileCreated(int company_id, Date prof_creation_date, String name, String contact,
////			String email, String ctc, String ectc, String current_role, String company, String location, String resume,
////			String domain, String primary_skill, String secondary_skill, String exp, String alternate_contact,
////			String alternate_email, String qualification, String year_of_passing, String notice_period) {
////		try {
////			long id = (long) sessionRef.getCurrentSession().save(ectc);
////
////			if (id > 0) {
////				System.out.println("Generated  id = " + id);
////				return true;
////			} else {
////
////				return false;
////			}
////		} catch (Exception e) {
////
////			return false;
////		}
////	}
//
//
//
//	public List<Profiles> getByEmailAndContact(String email,String contact){
//		try {
//			String query = "select * from profile as p where p.email like :mail or p.contact like :cntct";
//			List<Profiles> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Profiles.class)
//					.setParameter("mail","%"+email +"%").setParameter("cntct", "%"+contact+"%").getResultList();
//			System.out.println("hi "+ list.toString());
//			return list;
//		} catch (Exception e) {
//			return null;
//		}
//	}
//
//	@Override
//	public List<Profiles> getProfileByContactAndEmail(String contact, String email) {
//		try {
//			String query = "select * from profile as p where p.email like :mail or p.contact like :cntct";
//			List<Profiles> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Profiles.class)
//					.setParameter("mail", "%" + email + "%").setParameter("cntct", "%" + contact + "%").getResultList();
//			return list;
//		} catch (Exception e) {
//			return null;
//		}
//	}
//
//	@Override
//	public boolean deleteProfileById(long id) {
//		String query = "delete from profile  where profile.profile_id=:identity";
//		long x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("identity", id).executeUpdate();
//		try {
//			if (x >= 1) {
//				System.out.println("Number of record deleted :" + x);
//				return true;
//			} else {
//				return false;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//
////	@Override
////	public boolean updateProfileById(long identity, String name, String contact, String email, String ctc, String ectc,
////			String current_role, String company, String location, String resume, String domain, String primary_skill,
////			String secondary_skill, String company_id, Date dt, String status, String alternate_contact,
////			String alternate_email, String qualification, String year_of_passing) {
////
////		String query = "update Profile as p set p.name=:Name,p.contact=:cont,p.email=:mail"
////				+ "p.ctc=:costtoc,p.ectc=:ecost,p.np=:notice,p.current_role=:c_role ,p.company=:Comp ,p.location=:locate ,p.resume=:Res , p.skills=:skill,p.dt=:date ,p.status=:st ,p.alternate_contact=:al_contact ,p.alternate_email=:al_email ,p.qualification=:qual ,p.year_of_passing=:passing_year where p.profile_id=:id ";
////		int x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", identity)
////				.setParameter("Name", name).setParameter("cont", contact).setParameter("mail", email)
////				.setParameter("costtoc", ctc).setParameter("ecost", ectc).setParameter("c_role", current_role)
////				.setParameter("Comp", company).setParameter("locate", location).setParameter("Res", resume)
////				.setParameter("main", domain).setParameter("pri_skill", primary_skill)
////				.setParameter("sec_skill", secondary_skill).setParameter("Comp_id", company_id).setParameter("date", dt)
////				.setParameter("st", status).setParameter("al_contact", alternate_contact)
////				.setParameter("al_email", alternate_email).setParameter("qual", qualification)
////				.setParameter("passing_year", year_of_passing).executeUpdate();
////
////		try {
////			if (x >= 1) {
////				return true;
////			} else {
////				return false;
////			}
////		} catch (Exception e) {
////			e.printStackTrace();
////			return false;
////		}
////
////	}
//
//	@Override
//	public boolean updateProfileById(long profile_id, String name, String contact, String email, String location) {
//
//		int x = 0;
//		try {
//			if (!contact.isEmpty() && email.isEmpty() && name.isEmpty() && location.isEmpty()) {
//				String query = "update profile as p set p.contact=:cont where p.profile_id=:id";
//				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", profile_id)
//						.setParameter("cont", contact).executeUpdate();
//			} else if (contact.isEmpty() && email.isEmpty() && !name.isEmpty() && location.isEmpty()) {
//				String query = "update profile as p set p.name=:Name where p.profile_id=:id";
//				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", profile_id)
//						.setParameter("Name", name).executeUpdate();
//			} else if (contact.isEmpty() && !email.isEmpty() && name.isEmpty() && location.isEmpty()) {
//				String query = "update profile as p set p.email=:mail where p.profile_id=:id";
//				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", profile_id)
//						.setParameter("mail", email).executeUpdate();
//			} else if (contact.isEmpty() && email.isEmpty() && name.isEmpty() && !location.isEmpty()) {
//				String query = "update profile as p set p.location=:locate  where p.profile_id=:id";
//				x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", profile_id)
//						.setParameter("locate", location).executeUpdate();
//			}
//		} catch (Exception se) {
//			return false;
//		}
//
//		try {
//			if (x >= 1) {
//				return true;
//			} else {
//				return false;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//
//	}
//
//	@Override
//	public List<Profiles> findByMobile(String contact) {
//		String query = "select * from profile as p where p.contact =:mobileNumber";
//		try {
//			List<Profiles> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Profiles.class)
//					.setParameter("mobileNumber", contact).getResultList();
//			return list;
//		} catch (Exception e) {
//			return null;
//		}
//	}
//
//	@Override
//	public List<Profiles> findByMobile(HttpSession session,String contact) {
//		long partner_id = (long) session.getAttribute("partner_id");
//		if(partner_id==1) {
//			String query = "select * from profile as p where p.contact =:mobileNumber";
//			try {
//				List<Profiles> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Profiles.class)
//						.setParameter("mobileNumber", contact).getResultList();
//				return list;
//			} catch (Exception e) {
//				return null;
//			}
//		}else {
//			String query = "select * from profile as p where p.contact =:mobileNumber and p.partner_id=:pid";
//			try {
//				List<Profiles> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Profiles.class)
//						.setParameter("mobileNumber", contact).setParameter("pid",partner_id ).getResultList();
//				return list;
//			} catch (Exception e) {
//				return null;
//			}
//		}
//
//	}
//	@Override
//	public Profiles getByMobile(String contact) {
//		String query = "select * from profile as p where p.contact =:mobileNumber";
//		try {
//			Profiles prof = (Profiles) sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Profiles.class)
//					.setParameter("mobileNumber", contact).getSingleResult();
//			return prof;
//		} catch (Exception e) {
//			return null;
//		}
//	}
//
//	@Override
//	public boolean checkContactDuplicacy(String contact) {
//
//		String query = "select * from profile as p where p.contact=:cntct";
//
//		List<Profiles> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Profiles.class)
//				.setParameter("cntct", contact).getResultList();
//		if (list.isEmpty()) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	@Override
//	public boolean checkEmailDuplicacy(String email) {
//		String query = "select * from profile as p where p.email=:mail";
//
//		List<Profiles> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Profiles.class)
//				.setParameter("mail", email).getResultList();
//		System.out.println(list);
//		if (list.isEmpty()) {
//			return true;
//		} else {
//			return false;
//		}
//
//	}
//
//	@Override
//	public boolean updateName(long identity, String name) {
//		String query = "update  profile as p set p.name =:Name where p.profile_id=:id ";
//		long x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", identity)
//				.setParameter("Name", name).executeUpdate();
//		if (x > 0) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	@Override
//	public boolean updateContact(long identity, String contact) {
//		String query = "update  profile as p set p.contact =:cntct where p.profile_id=:id ";
//		int x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", identity)
//				.setParameter("cntct", contact).executeUpdate();
//		if (x > 0) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	@Override
//	public boolean updateEmail(long identity, String email) {
//		String query = "update  profile as p set p.email =:mail where p.profile_id=:id ";
//		long x = sessionRef.getCurrentSession().createNativeQuery(query).setParameter("id", identity)
//				.setParameter("mail", email).executeUpdate();
//		if (x > 0) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	@Override
//	public List<Profiles> findByEmail(String email) {
//		String query = "select * from profile as p where p.email =:mail";
//		try {
//			List<Profiles> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Profiles.class)
//					.setParameter("mail", email).getResultList();
//			return list;
//		} catch (Exception e) {
//			return null;
//		}
//	}
//	@Override
//	public List<Profiles> findByEmail(HttpSession session ,String email) {
//		long partner_id = (long) session.getAttribute("partner_id");
//if(partner_id==1) {
//	String query = "select * from profile as p where p.email like :mail";
//	try {
//		List<Profiles> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Profiles.class)
//				.setParameter("mail","%"+ email+"%").getResultList();
//		return list;
//	} catch (Exception e) {
//		return null;
//	}
//}else {
//	String query = "select * from profile as p where p.email like :mail and p.partner_id=:pid";
//	try {
//		List<Profiles> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Profiles.class)
//				.setParameter("mail", "%"+email+"%").setParameter("pid",partner_id ).getResultList();
//		return list;
//	} catch (Exception e) {
//		return null;
//	}
//}
//
//	}
//
//	@Override
//	public Profiles getByEmail(String email) {
//		String query = "select * from profile as p where p.email like :mail";
//		try {
//			Profiles prof = (Profiles) sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Profiles.class)
//					.setParameter("mail", "%" + email + "%").getSingleResult();
//			return prof;
//		} catch (Exception e) {
//			return null;
//		}
//	}
//
//	@Override
//	public List<Profiles> findByName(String name) {
//		String query = "select * from profile as p where p.name like :nm";
//		try {
//			List<Profiles> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Profiles.class)
//					.setParameter("nm", "%" + name + "%").list();
//			return list;
//		} catch (Exception e) {
//			return null;
//		}
//	}
//	@Override
//	public List<Profiles> findByName(HttpSession session,String name) {
//		long partner_id = (long) session.getAttribute("partner_id");
//		if(partner_id==1) {
//			String query = "select * from profile as p where p.name like :nm";
//			try {
//				List<Profiles> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Profiles.class)
//						.setParameter("nm", "%" + name + "%").getResultList();
//				return list;
//			} catch (Exception e) {
//				return null;
//			}
//		}else {
//		String query = "select * from profile as p where p.name like :nm and p.partner_id=:pid";
//		try {
//			List<Profiles> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Profiles.class)
//					.setParameter("nm", "%" + name + "%").setParameter("pid",partner_id).getResultList();
//			return list;
//		} catch (Exception e) {
//			return null;
//		}}
//	}
//
//	@Override
//	public List<Profiles> fetchBySkills(String domain) {
//		String query = "select * from profile as p where p.domain like :main ";
//		try {
//			List<Profiles> list = sessionRef.getCurrentSession().createNativeQuery(query).addEntity(Profiles.class)
//					.setParameter("main", "%" + domain + "%").getResultList();
//			if(list==null || list.isEmpty()) {
//				return null;
//			}else {
//				return list;
//
//			}
//		} catch (Exception e) {
//			return null;
//		}
//	}
//
//	public List<Profiles> getByProfileIds(List<Long> candidateIds) {
//		String query = "select * from profile as p where p.profile_id in :ids";
//		List<Profiles> profiles = null;
//		try {
//			profiles = sessionRef.getCurrentSession()
//					.createNativeQuery(query)
//					.setParameter("ids", candidateIds).getResultList();
//			return profiles;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	@Override
//	public List<Profiles> findAllProfile() {
//
//		return null;
//	}
//}
