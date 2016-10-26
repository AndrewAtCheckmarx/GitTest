package mail;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.*;
import javax.persistence.*;



public class CheckUserServlet extends HttpServlet {
	private static final long serialVersionUID = 6689019616490068106L;


	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String login=req.getParameter("name");
		EntityManager em=EMF.get().createEntityManager();
		Query query;
		synchronized (EMF.LOCK) {
			try{
				query=em.createQuery("select user from MailUser user where user.username='"+login+"'");
				query.getSingleResult();
			}catch(NoResultException e){
				resp.getWriter().print("no");
				return;
			}
		}try{
			resp.getWriter().print("yes");
		}catch(IOException e){
			e.printStackTrace();
		}		
	}
	
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doGet(req,resp);
	}
}