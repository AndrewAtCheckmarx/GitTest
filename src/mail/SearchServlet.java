package mail;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import javax.persistence.*;
import java.util.*;



public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1243139365286224292L;

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String content=req.getParameter("what");
		EntityManager em=EMF.get().createEntityManager();
		HttpSession session=req.getSession(true);
		MailUser prev=(MailUser) session.getAttribute(CreateUserServlet.USER_SESSION);
		if(prev==null){
			session.invalidate();
			resp.sendRedirect("mail.html");
			return;
		}
		MailUser user = em.find(MailUser.class,prev.getKey());
		session.setAttribute(CreateUserServlet.USER_SESSION,user);
		
		synchronized (EMF.LOCK) {
			Vector<Email> box=user.getMailBox();
			Iterator<Email> iter=box.iterator();
			Vector<Email> results=new Vector<Email>();
			String newCont;
			while(iter.hasNext()){
				Email curr=iter.next();
				newCont=curr.getContent().replace("<br />","\n");
				if(newCont.startsWith(content)){
					results.add(curr);
				}	
			}
			if(results.size()==0){
				req.setAttribute("inbox",null);
			}
			RequestDispatcher rd=req.getRequestDispatcher(content);
			req.setAttribute("inbox",results);
			req.setAttribute("search",content);
			
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
