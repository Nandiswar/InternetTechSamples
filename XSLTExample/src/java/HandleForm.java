/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author nandi_000
 */
@WebServlet(urlPatterns = {"/ProcessForm"})
public class HandleForm extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet HandleForm</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HandleForm at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String feedurl = request.getParameter("feedurl");

        response.setContentType("text/html");
        System.out.println("inn");
        PrintWriter out = response.getWriter();
        try {
            // get the xsl stored in this project 
            ServletContext context = getServletContext();
            InputStream xsl = (InputStream) (context.getResourceAsStream("/XSLTransformerCode.xsl"));

            // We need two source objects and one result
            // get an external xml document using a url in a 
            // string format
            Source xmlDoc = new StreamSource(feedurl);
            Source xslDoc = new StreamSource(xsl);
            Result result = new StreamResult(out);

            // Prepare to transform 
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer trans = factory.newTransformer(xslDoc);
            trans.transform(xmlDoc, result);
//            String docType = "<!DOCTYPE HTML PUBLIC \"//W3C//DTD HTML 4.0 ";
//            docType += "Transitional//EN\">\n";
//            out.println(docType + result);
        } catch (TransformerException e) {
            System.out.println("Transformer Probem" + e);
        }

        // The transformed document is returned to the browser.
    }

    /**
     * Handles the HTTP <code>POST</code> method.
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
