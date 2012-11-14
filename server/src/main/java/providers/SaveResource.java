package providers;

import com.google.gson.Gson;
import db.DatabaseHandler;
import db.Resources;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;

@WebServlet(name = "SaveResourc", urlPatterns = {"/saveResourc"})
public class SaveResource extends HttpServlet {

    private static Logger log = Logger.getLogger(SaveResource.class);

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        try {
            log.info("request /saveResource from: " + request.getRemoteHost() + " " + request.getRemoteAddr());

            String json = request.getParameter("resource");

            //json = "{'name':'test2','type':'test','data':[1,1,2,2],'username':'han'}";

            Resources resource = gson.fromJson(json, Resources.class);

            Session session = DatabaseHandler.getDatabaseHandlerInstance().getHibernateSession();
            Transaction t = session.beginTransaction();
            session.save(resource);
            t.commit();

            session.close();

            out.println(gson.toJson(true));

        } catch (Exception e) {
            log.error(e);
            out.println(gson.toJson(false));
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
