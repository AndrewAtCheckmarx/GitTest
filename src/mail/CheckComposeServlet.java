package mail;
import java.io.IOException;
import javax.servlet.http.*;
import javax.persistence.*;
import java.util.*;



public class CheckComposeServlet extends HttpServlet {
	private static final long serialVersionUID = 1243139365286224292L;

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String temp=req.getParameter("userList");
		String content=req.getParameter("content");
		String subject=req.getParameter("subject");
		String date=req.getParameter("date");

		EntityManager em=EMF.get().createEntityManager();
		HttpSession session=req.getSession(true);
		MailUser prev=(MailUser) session.getAttribute(CreateUserServlet.USER_SESSION);
		if(prev==null){
			session.invalidate();
			resp.sendRedirect("mail.html");
			return;
		}
		synchronized (EMF.LOCK) {
			MailUser user = em.find(MailUser.class,prev.getKey());
			session.setAttribute(CreateUserServlet.USER_SESSION,user);
			List<MailUser> list;
			String [] array=temp.split(",");
			Query query;
			for(int i=0; i<array.length; i++){
				try{
					query=em.createQuery("select user from MailUser user where user.username='"+array[0]+"'");
				}catch(NoResultException e){
					resp.getWriter().print("non existant");
					return;
				}
			}
			MailUser recp;
			for(int j=0; j<array.length; ++j){
				Email newMail=new Email();
				newMail.setAge(user.getAge());
				newMail.setAvatar(user.getAvatar());
				newMail.setContent(content);
				newMail.setDate(date);
				newMail.setFrom(user.getUsername());
				newMail.setFromName(user.getFirstName(), user.getLastName());
				newMail.setLabel("inbox");
				newMail.setSubject(subject);
				newMail.setUnread(true);
				try{
					em.getTransaction().begin();
					query=em.createQuery("select user from MailUser user where user.username='"+array[j]+"'");
					recp=(MailUser)query.getSingleResult();
				}catch(NoResultException e){
					resp.getWriter().print("non existant");
					return;
				}
				recp.getMailBox().add(newMail);
				
				try{	
					em.merge(recp);
					em.getTransaction().commit();
				}finally{
					if (em.getTransaction().isActive()) 
						em.getTransaction().rollback();
				}
			}
			resp.getWriter().print("existant");
		}


	}



	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doGet(req,resp);
	}

}
