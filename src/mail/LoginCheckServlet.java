package mail;
import java.io.IOException;
import javax.servlet.http.*;
import javax.persistence.*;



public class LoginCheckServlet extends HttpServlet {

	private static final long serialVersionUID = -9013322758465402671L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String login=req.getParameter("user");
		String pass=req.getParameter("pass");
		EntityManager em=EMF.get().createEntityManager();
		Query query;
		MailUser u=null;
		MailUser sessUser=null;
		synchronized (EMF.LOCK) {
			HttpSession session;
			session=req.getSession(true);
			sessUser=(MailUser) session.getAttribute(CreateUserServlet.USER_SESSION);
			if(sessUser!=null){
				session.invalidate();
				resp.getWriter().print("noUser");
				return;
			}		
			try{
				query=em.createQuery("select user from MailUser user where user.username='"+login+"'");
				u=(MailUser)query.getSingleResult();
			}catch(NoResultException e){
				resp.getWriter().print("noUser");
				return;
			}
			if(u.getPassword().compareTo(pass)!=0){
				resp.getWriter().print("noUser");
				return;
			}
			try{
				session.setAttribute(CreateUserServlet.USER_SESSION,u);
				resp.getWriter().print("correct");
			}catch(IOException e){
				e.printStackTrace();
			}		
		}
	}


		public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
			doGet(req,resp);
		}
	}