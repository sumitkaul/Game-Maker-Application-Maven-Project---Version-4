package providers;

import com.google.gson.Gson;
import db.DatabaseHandler;
import db.Resources;
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

@WebServlet(name = "ListPageImages", urlPatterns = {"/listPageResources"})
public class ListPageResources extends HttpServlet {

    private static Logger log = Logger.getLogger(ListPageResources.class);

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
            log.info("request /listPageResources from: " + request.getRemoteHost() + " " + request.getRemoteAddr());

            String pageNumberS = request.getParameter("page_number");
            String pageLengthS = request.getParameter("page_length");
            String resourceName = request.getParameter("resource_name");

            if (pageNumberS != null && pageLengthS != null) {
                Session session = DatabaseHandler.getDatabaseHandlerInstance().getHibernateSession();
                int pageNumber = Integer.parseInt(pageNumberS);
                int pageLength = Integer.parseInt(pageLengthS);

                Criteria criteria = session.createCriteria(Resources.class);

                if (resourceName != null) {
                    criteria.add(Restrictions.eq("resourceName", resourceName));
                }

                criteria.setFirstResult((pageNumber - 1) * pageLength);
                criteria.setMaxResults(pageLength);

                @SuppressWarnings("unchecked")
                List<Resources> r = criteria.list();

                session.close();
                out.println(gson.toJson(r));
            }

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
