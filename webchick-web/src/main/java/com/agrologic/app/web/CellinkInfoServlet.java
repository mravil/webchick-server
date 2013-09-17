package com.agrologic.app.web;

import com.agrologic.app.dao.*;
import com.agrologic.app.model.Cellink;
import com.agrologic.app.model.Controller;
import com.agrologic.app.model.Program;
import com.agrologic.app.model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class CellinkInfoServlet extends HttpServlet {

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

        /** Logger for this class and subclasses */
        final Logger logger = Logger.getLogger(CellinkInfoServlet.class);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            if (!CheckUserInSession.isUserInSession(request)) {
                logger.error("Unauthorized access!");
                response.sendRedirect("./login.jsp");
            } else {
                Long userId = Long.parseLong(request.getParameter("userId"));
                Long cellinkId = Long.parseLong(request.getParameter("cellinkId"));

                try {
                    ProgramDao programDao = DbImplDecider.use(DaoType.MYSQL).getDao(ProgramDao.class);
                    List<Program> programs = (List<Program>) programDao.getAll();
                    request.getSession().setAttribute("programs", programs);
                    UserDao userDao = DbImplDecider.use(DaoType.MYSQL).getDao(UserDao.class);
                    User editUser = userDao.getById(userId);
                    CellinkDao cellinkDao = DbImplDecider.use(DaoType.MYSQL).getDao(CellinkDao.class);
                    Cellink c = (Cellink) cellinkDao.getById(cellinkId);
                    ControllerDao controllerDao = DbImplDecider.use(DaoType.MYSQL).getDao(ControllerDao.class);
                    Collection<Controller> controllers = controllerDao.getAllByCellink(c.getId());
                    for (Controller ctrl : controllers) {
                        Program program = programDao.getById(ctrl.getProgramId());
                        ctrl.setProgram(program);
                    }
                    c.setControllers((List) controllers);
                    editUser.addCellink(c);
                    logger.info("retrieve user and user cellinks and all controllers of each cellink");
                    request.getSession().setAttribute("edituser", editUser);
                    List<String> controllernames = (List<String>) controllerDao.getControllerNames();
                    request.getSession().setAttribute("controllernames", controllernames);
                    request.getRequestDispatcher("./cellinkinfo.jsp?userId=" + userId + "&celinkId="
                            + cellinkId).forward(request, response);
                } catch (SQLException ex) {
                    // error page
                    logger.error("Database error.", ex);
                }
            }
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



