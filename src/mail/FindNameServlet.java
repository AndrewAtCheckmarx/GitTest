package mail;
import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.*;
import javax.persistence.*;

import java.util.*;



public class FindNameServlet extends HttpServlet implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = -266986199704574892L;





	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		List list;
		StringBuffer result = new StringBuffer();
		synchronized(EMF.LOCK){
			String toComplete=req.getParameter("term");
			EntityManager em = EMF.get().createEntityManager();
			Query q = em.createQuery("select from MailUser where username like '"+toComplete+"%'");
			result.append("[");
			list = q.getResultList();
			for (int i=0;i<list.size();i++){
				result.append("\""+((MailUser)list.get(i)).getUsername()+"\",");
			}
		}
		result.append("]");
		resp.getWriter().print(result);
	}



	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doGet(req,resp);
	}
}