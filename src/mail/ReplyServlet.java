package mail;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import javax.persistence.*;
import java.io.*;
import java.util.*;



public class ReplyServlet extends HttpServlet implements Serializable {
	private static final long serialVersionUID = 5915837235549512189L;


	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String option=req.getParameter("option");
		String number=req.getParameter("number");
		int index = Integer.parseInt(number.trim());
		HttpSession session=req.getSession(true);
		MailUser user=(MailUser) session.getAttribute(CreateUserServlet.USER_SESSION);
		if(user==null){
			session.invalidate();
			resp.sendRedirect("mail.html");
			return;
		}
		synchronized(EMF.LOCK){
			EntityManager em=EMF.get().createEntityManager();
			user=em.find(MailUser.class,user.getKey());
			session.setAttribute(CreateUserServlet.USER_SESSION, user);
			Vector<Email> inbox=new Vector<Email>();
			Iterator<Email> iter1=user.getMailBox().iterator();
			while(iter1.hasNext()){
				Email curr=iter1.next();
				if((curr.getLabel()).compareTo("inbox")==0){
					inbox.add(curr);
				}
			}
			int vecSize=inbox.size();
			Email mailToReplyOn=inbox.get(vecSize-1-index);
			String subject;
			String content;
			String to;
			if(option.compareTo("reply")==0){
				subject="Re: "+mailToReplyOn.getSubject();
				content=mailToReplyOn.getContent();
				to=mailToReplyOn.getFrom();
			}else{
				subject="Fwd: "+mailToReplyOn.getSubject();
				content=mailToReplyOn.getContent();
				to="";
			}
			
			
			RequestDispatcher rd=req.getRequestDispatcher("compose.jsp");
			
			req.setAttribute("subject",subject);
			req.setAttribute("content",content);
			req.setAttribute("to",to);
			req.setAttribute("from",mailToReplyOn.getFrom());
			req.setAttribute("date",mailToReplyOn.getDate());
			try {
				rd.forward(req,resp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doGet(req,resp);
	}
} 