package com.agrologic.app.web;

import com.agrologic.app.dao.*;
import com.agrologic.app.dao.mysql.impl.MedicineDaoImpl;
import com.agrologic.app.model.Controller;
import com.agrologic.app.model.Flock;
import com.agrologic.app.model.Medicine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class RemoveMedicineServlet extends AbstractServlet {

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
            Long cellinkId = Long.parseLong(request.getParameter("cellinkId"));
            Long flockId = Long.parseLong(request.getParameter("flockId"));
            Long medicineId = Long.parseLong(request.getParameter("medicineId"));

            try {
                MedicineDao medicineDao = DbImplDecider.use(DaoType.MYSQL).getDao(MedicineDaoImpl.class);
                Medicine medicine = medicineDao.getById(medicineId);

                if (medicine == null) {
                    logger.info("Medicine " + medicineId + " can't be removed");
                    request.getRequestDispatcher("./rmctrl-add-medicine.jsp?celinkId=" + cellinkId + "&flockId="
                            + flockId).forward(request, response);
                } else {
                    medicineDao.remove(medicine.getId());
                    logger.info("Medicine removed successfully from the datebase");

                    List<Medicine> medicines = medicineDao.getAllByFlockId(flockId);
                    float totalMedicine = 0;

                    for (Medicine m : medicines) {
                        totalMedicine += m.getTotal();
                    }

                    FlockDao flockDao = DbImplDecider.use(DaoType.MYSQL).getDao(FlockDao.class);
                    Flock flock = flockDao.getById(flockId);

                    flock.setTotalMedic(totalMedicine);
                    flockDao.update(flock);

                    ControllerDao controllerDao = DbImplDecider.use(DaoType.MYSQL).getDao(ControllerDao.class);
                    Collection<Controller> controllers = controllerDao.getAllByCellink(cellinkId);

                    for (Controller controller : controllers) {
                        Collection<Flock> flocks = flockDao.getAllFlocksByController(controller.getId());
                        controller.setFlocks(flocks);
                    }

                    request.setAttribute("controllers", controllers);
                    request.getRequestDispatcher("./rmctrl-add-medicine.jsp?celinkId=" + cellinkId + "&flockId="
                            + flockId).forward(request, response);
                }
            } catch (SQLException ex) {
                logger.info("Error occurs durring removing medicine");
                request.getRequestDispatcher("./rmctrl-add-medicine.jsp?celinkId=" + cellinkId + "&flockId="
                        + flockId).forward(request, response);
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



