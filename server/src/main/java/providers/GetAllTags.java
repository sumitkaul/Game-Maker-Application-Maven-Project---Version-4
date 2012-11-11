package providers;

import com.google.gson.Gson;
import db.DatabaseHandler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Query;
import org.hibernate.Session;

@WebServlet(name = "GetAllTags", urlPatterns = {"/getAllTags"})
public class GetAllTags extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Session session = DatabaseHandler.getDatabaseHandlerInstance().getHibernateSession();
        Query query;

        query = session.createSQLQuery("SELECT resource_name FROM a9team3.Resources");
        @SuppressWarnings("unchecked")
        List<String> list = query.list();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        out.print(json);
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
