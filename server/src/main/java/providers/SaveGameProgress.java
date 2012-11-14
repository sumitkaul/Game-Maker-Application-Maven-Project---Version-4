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

@WebServlet(name = "SaveGameProgres", urlPatterns = {"/saveGameProgres"})
public class SaveGameProgress extends HttpServlet {

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
            String game_name = request.getParameter("game_name");
            String save_name = request.getParameter("game_save_name");
            String game_data = request.getParameter("game_data");
            String player_name = request.getParameter("game_player_name");
            int game_score = Integer.parseInt(request.getParameter("game_score"));

            Gson gson = new Gson();

            if (save_name != null && game_data != null && player_name != null && game_name != null) {
                String sql1 = "select count(*) FROM InProgressGame where game_name='" + game_name + "'and save_name='" + save_name + "' and player_name='" + player_name + "'";
                String sql2 = "insert into InProgressGame (game_name, player_name, score, game_data, save_name) VALUES ('" + game_name + "', '" + player_name + "'," + game_score + ",'" + game_data + "','" + save_name + "')";
                String sql3 = "update InProgressGame set game_data = '" + game_data + "',score = " + game_score + " where game_name='" + game_name + "' and save_name='" + save_name + "' and player_name='" + player_name + "'";

                List<BigInteger> count = DatabaseHandler.Query(sql1);


                if (count.get(0).intValue() < 1) {
                    DatabaseHandler.ExecuteQuery(sql2);
                } else {
                    DatabaseHandler.ExecuteQuery(sql3);
                }


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
