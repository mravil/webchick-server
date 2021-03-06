package com.agrologic.app.web;

import com.agrologic.app.dao.DaoType;
import com.agrologic.app.dao.DataDao;
import com.agrologic.app.dao.DbImplDecider;
import com.agrologic.app.model.Data;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TranslateData extends AbstractServlet {


    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Expires", "-1");
        PrintWriter out = response.getWriter();
        try {
            Long langId = Long.parseLong(request.getParameter("langId"));
            Long dataId = Long.parseLong(request.getParameter("dataId"));
            long type = dataId;           // type of value (like 4096)
            if ((type & 0xC000) != 0xC000) {
                dataId = (type & 0xFFF);    // remove type to get an index 4096&0xFFF -> 0
            } else {
                dataId = (type & 0xFFFF);
            }

            DataDao dataDao = DbImplDecider.use(DaoType.MYSQL).getDao(DataDao.class);
            Data translate = dataDao.getById(dataId, langId);
            out.print("<html>");
            out.print("<message>");
            if (translate.getId() != null) {
                out.print(translate.getUnicodeLabel());
            } else {
                out.print("Translation does not exist");
            }
            out.println("</message>");
            out.print("</html>");
        } catch (Exception ex) {
            out.print("<message>");
            out.print("Error occur during execution the query. \n" +
                    "Perhaps translation for this data type does not exist .");
            out.println("</message>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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



