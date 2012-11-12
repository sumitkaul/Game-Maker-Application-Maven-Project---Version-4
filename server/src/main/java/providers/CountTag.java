package providers;

import db.DatabaseHandler;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jboss.logging.Logger;

@WebServlet(name = "CountTag", urlPatterns = {"/countTa"})
public class CountTag extends HttpServlet {

    private static Logger log = Logger.getLogger(CountTag.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Session session = DatabaseHandler.getDatabaseHandlerInstance().getHibernateSession();
        Query query;
        String tag = request.getParameter("tag");
        if (tag == null) {
            query = session.createSQLQuery("SELECT COUNT(*) FROM a9team3.Resources");
        } else {
            query = session.createSQLQuery("SELECT COUNT(*) FROM a9team3.Resources WHERE resource_name='" + tag + "'");
        }
        out.print(query.list().get(0));
        session.close();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        processRequest(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        processRequest(request, response);
    }
}
