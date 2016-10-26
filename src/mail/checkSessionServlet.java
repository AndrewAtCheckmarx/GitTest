package mail;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import javax.persistence.*;

import java.io.*;
import java.util.*;



public class checkSessionServlet extends HttpServlet implements Serializable {
	private static final long serialVersionUID = 5915837235549512189L;


	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		synchronized(EMF.LOCK){
			MailUser user=null;
			HttpSession session=null;
			try{
			 session=req.getSession(true);
			user=(MailUser) session.getAttribute(CreateUserServlet.USER_SESSION);
			}catch(Exception e){
				resp.getWriter().print("first");
				return;
			}
			if(user==null){
				session.invalidate();
				resp.getWriter().print("first");
				return;
			}
			else{
				EntityManager em=EMF.get().createEntityManager();
				user = em.find(MailUser.class,user.getKey());
				session.setAttribute(CreateUserServlet.USER_SESSION,user);
				resp.getWriter().print("goBack");
				
			}
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doGet(req,resp);
	}
} 