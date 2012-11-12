package providers;

import com.google.gson.Gson;
import db.DatabaseHandler;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.logging.Logger;

/**
 *
 * @author han
 */
@WebServlet(name = "SaveGameBase", urlPatterns = {"/saveGameBase"})
public class SaveGameBase extends HttpServlet {

    private static Logger log = Logger.getLogger(SaveGameBase.class);

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
        try {
            log.info("request for /saveGame from: " + request.getRemoteHost() + " " + request.getRemoteAddr());

            String game_name = request.getParameter("game_name");
            String game_data = request.getParameter("game_data");
            String game_author = request.getParameter("game_author");

            Gson gson = new Gson();

            if (game_name != null && game_data != null && game_author != null) {

                String sql = "select count(*) FROM GameBase where game_name='" + game_name + "'";
                String sql2 = "insert into GameBase (game_name, game_author, game_data) VALUES ('" + game_name + "', '" + game_author + "', '" + game_data + "')";
                String sql3 = "update GameBase set game_author = '" + game_author + "', game_data = '" + game_data + "' where game_name='" + game_name + "'";

                List<BigInteger> count = DatabaseHandler.Query(sql);
                log.info(count);

                if (count.get(0).intValue() < 1) {
                    DatabaseHandler.ExecuteQuery(sql2);
                } else {
                    DatabaseHandler.ExecuteQuery(sql3);
                }

                log.info("added game: " + game_name);

                out.println(gson.toJson(true));
            } else {
                out.println(gson.toJson(false));
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
