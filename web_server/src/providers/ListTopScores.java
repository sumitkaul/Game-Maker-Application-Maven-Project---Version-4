package providers;

import com.google.gson.Gson;
import db.DatabaseHandler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.logging.Logger;
import protocol.GameSaveInfo;

@WebServlet(name = "ListTopScores", urlPatterns = {"/listTopScores"})
public class ListTopScores extends HttpServlet {

    private static Logger log = Logger.getLogger(ListTopScores.class);

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
            log.info("request /listTopScores from: " + request.getRemoteHost() + " " + request.getRemoteAddr());

            String gameName = request.getParameter("game_name");

            if (gameName.isEmpty()) {
                return;
            }

            String sql = "select game_name, player_name, score from InProgressGame where game_name='" + gameName + "' order by score desc";

            List<GameSaveInfo> saves = new LinkedList<GameSaveInfo>();

            List<Object[]> r = DatabaseHandler.Query(sql);

            int rank = 1;
            for (Object[] record : r) {
                GameSaveInfo save = new GameSaveInfo(null, (String) record[0], (String) record[1], (Integer) record[2], rank);
                saves.add(save);
                rank++;
            }

            Gson gson = new Gson();
            String json = gson.toJson(saves);

            out.println(json);

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
