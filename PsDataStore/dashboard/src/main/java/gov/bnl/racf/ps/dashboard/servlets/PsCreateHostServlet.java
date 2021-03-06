/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.bnl.racf.ps.dashboard.servlets;


import gov.bnl.racf.ps.dashboard.data_objects.PsHost;
import gov.bnl.racf.ps.dashboard.object_manipulators.JsonConverter;
import gov.bnl.racf.ps.dashboard.object_manipulators.PsObjectCreator;
import gov.bnl.racf.ps.dashboard.object_manipulators.PsObjectShredder;
import gov.bnl.racf.ps.dashboard.object_manipulators.PsObjectUpdater;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


/**
 * Servlet to create a new host
 * Parameters: hostName=requested host name
 * ipv4=host ipv4
 * ipv6=host ipv6
 * if parameters are omitted they will be set to ""
 * servlet returns json object of the created host
 * //TODO add authentication filter
 * @author tomw
 */
//@WebServlet(name = "PsCreateHostServlet", urlPatterns = {"/createHost"})
public class PsCreateHostServlet extends HttpServlet {

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
            /*
             * TODO output your page here. You may use following sample code.
             */
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet PsCreateHostServlet</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet PsCreateHostServlet at " + request.getContextPath() + "</h1>");
            
            PsHost host = PsObjectCreator.createNewHost();
            
            String hostName="";
            if(request.getParameter("hostName")!=null){
                hostName=request.getParameter("hostName");
            }
            host.setHostname(hostName);
            
            String ipv4="";
            if(request.getParameter("ipv4")!=null){
                ipv4=request.getParameter("ipv4");
            }
            host.setIpv4(ipv4);
            
            String ipv6="";
            if(request.getParameter("ipv6")!=null){
                ipv6=request.getParameter("ipv6");
            }
            host.setIpv6(ipv6);
            
            out.println(JsonConverter.toJson(host).toString());
                    
            
            
//            out.println("</body>");
//            out.println("</html>");
            
        }catch(Exception e){
            System.out.println(new Date()+" "+getClass().getName()+" "+e);
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
