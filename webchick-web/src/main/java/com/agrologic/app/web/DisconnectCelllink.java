package com.agrologic.app.web;

import com.agrologic.app.dao.CellinkDao;
import com.agrologic.app.dao.DaoType;
import com.agrologic.app.dao.DbImplDecider;
import com.agrologic.app.service.CellinkManagerService;
import com.agrologic.app.service.impl.CellinkManagerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class DisconnectCelllink extends AbstractServlet {
    private CellinkManagerService cellinkManagerService;

    public DisconnectCelllink() {
        this.cellinkManagerService =
                new CellinkManagerServiceImpl(DbImplDecider.use(DaoType.MYSQL).getDao(CellinkDao.class));
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            Long userId = Long.parseLong(request.getParameter("userId"));
            Long cellinkId = Long.parseLong(request.getParameter("cellinkId"));

            try {
                cellinkManagerService.disconnect(cellinkId);
            } catch (SQLException ex) {
                logger.error(ex.getMessage(), ex);
            }
            request.setAttribute("message", "Cellink(s) with ID " + cellinkId + " successfully disconnected");
            request.setAttribute("error", false);
            request.getRequestDispatcher("./my-farms.html?userId=" + userId).forward(request, response);
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
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
    }    // </editor-fold>
}



