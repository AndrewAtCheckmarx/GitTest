package mail;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import javax.persistence.*;

import java.io.*;
import java.util.*;



public class SignOutServlet extends HttpServlet implements Serializable {
	private static final long serialVersionUID = 5915837235549512189L;


	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		synchronized(EMF.LOCK){
			MailUser user=null;
			HttpSession session=null;
			session=req.getSession(true);
			user=(MailUser) session.getAttribute(CreateUserServlet.USER_SESSION);
			if(user==null){
				resp.getWriter().print("error");
			}
			else{
				session.invalidate();
				resp.getWriter().print("signOut");
				resp.sendRedirect("mail.html");
			}
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doGet(req,resp);
	}
} 