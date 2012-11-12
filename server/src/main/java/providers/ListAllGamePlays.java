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

/**
 * Servlet implementation class ListAllGamePlays
 */
@WebServlet(name = "ListAllGamePlays", urlPatterns = "/listAllGamePlays")
public class ListAllGamePlays extends HttpServlet {

    private static Logger log = Logger.getLogger(ListAllGamePlays.class);

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
            log.info("request /listAllGamePlays from: " + request.getRemoteHost() + " " + request.getRemoteAddr());

            String playerName = request.getParameter("player_name");

            if (playerName.isEmpty()) {
                return;
            }

            String sql = "select save_name, game_name, player_name, save_id, score from InProgressGame where player_name='" + playerName + "'";

            List<GameSaveInfo> saves = new LinkedList<GameSaveInfo>();

            List<Object[]> r = DatabaseHandler.Query(sql);

            for (Object[] record : r) {
                GameSaveInfo save = new GameSaveInfo((String) record[0], (String) record[1], (String) record[2], (Integer) record[3]);
                save.setGameScore((Integer) record[4]);                
                saves.add(save);
            }

            Gson gson = new Gson();
            String json = gson.toJson(saves);

            out.println(json);
        } finally {
            out.close();
        }

    }

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
        return "List all available game play saves";
    }
}
