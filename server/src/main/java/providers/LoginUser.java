package providers;

import com.google.gson.Gson;
import db.DatabaseHandler;
import db.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.jboss.logging.Logger;

@WebServlet(name = "LoginUser", urlPatterns = {"/loginUser"})
public class LoginUser extends HttpServlet {

    private static Logger log = Logger.getLogger(LoginUser.class);

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
            log.info("request for /loginUser from: " + request.getRemoteHost() + " " + request.getRemoteAddr());

            String username = request.getParameter("username");
            String password = request.getParameter("password");

            if (username.isEmpty() || password.isEmpty()) {
                out.println(gson.toJson(false));
                return;
            }

            Session s = DatabaseHandler.getDatabaseHandlerInstance().getHibernateSession();
            Criteria criteria = s.createCriteria(User.class);
            criteria.add(Restrictions.eq("username", username));
            criteria.add(Restrictions.eq("password", password));

            List r = criteria.list();
            if (r.isEmpty()) {
                out.println(gson.toJson(false));
            } else {
                out.println(gson.toJson(true));
            }
            s.close();

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
