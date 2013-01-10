/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.bnl.racf.ps.dashboard.servlets;

import gov.bnl.racf.ps.dashboard.data_objects.PsServiceResult;
import gov.bnl.racf.ps.dashboard.data_store.PsDataStore;
import gov.bnl.racf.ps.dashboard.object_manipulators.Json2ServiceResultConverter;
import gov.bnl.racf.ps.dashboard.object_manipulators.PsServiceUpdater;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Servlet for uploading job results Each servlet takes parameter result which
 * should contain string representation of JSON object corresponding to PsResult
 * object, which contains result of a job The servlet should unpack the result
 * JSON object and pass it to manipulator which should then update the
 * corresponding service
 *
 * @author tomw
 */
//@WebServlet(name = "PsUploadResultsServlet", urlPatterns = {"/results"})
public class PsUploadResultsServlet extends HttpServlet {

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


        
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet PsUploadResultsServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servlet PsUploadResultsServlet at " + request.getContextPath() + "</h1>");

        // results are posted in data part of the request, therefore we must
        // open the data reader
        
        String inputDataString="";

        BufferedReader br = null;
        try {
            String sCurrentLine;        
            br = request.getReader();

            while ((sCurrentLine = br.readLine()) != null) {
                out.println(sCurrentLine + "<BR>");
                inputDataString=inputDataString+sCurrentLine;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        //convert input data string to JSON
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        try {
            json = (JSONObject)parser.parse(inputDataString);
        } catch (ParseException ex) {
            out.println("Error occured while parsing json data<BR>");
            out.println(ex);
            Logger.getLogger(PsUploadResultsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        // convert json to service result
        PsServiceResult serviceResult = 
                Json2ServiceResultConverter.convert(json);
        // update the corresponding service
        PsServiceUpdater.update(serviceResult);
        
        //System.out.println(new Date()+" result received="+json.toString());
        
        out.println("</body>");
        out.println("</html>");


//        try {
//            /*
//             * TODO output your page here. You may use following sample code.
//             */
//
//
//            PsDataStore psDataStore = PsDataStore.getDataStore();
//
//            JSONObject json = null;
//            JSONParser parser = new JSONParser();
//
//            String resultAsString = request.getParameter("result");
//            if (resultAsString != null && resultAsString != "") {
//                try {
//                    //unpack result string and convert it to json
//                    json = (JSONObject) parser.parse(resultAsString);
//                    
//                    //convert json object to PsServiceResult object
//                    PsServiceResult serviceResult = 
//                            Json2ServiceResultConverter.convert(json);
//
//                    // update service                                   
//                    PsServiceUpdater.update(serviceResult);
//                    
//                    //TODO set proper exit code
//                    
//                    out.println("<html>");
//                    out.println("<head>");
//                    out.println("<title>Servlet PsUploadResultsServlet</title>");
//                    out.println("</head>");
//                    out.println("<body>");
//                    out.println("<h1>Servlet PsUploadResultsServlet at " + request.getContextPath() + "</h1>");
//                    out.println("</body>");
//                    out.println("</html>");
//
//                } catch (Exception e) {
//                    // json object parsing failed
//
//                    //TODO return proper error
//                    Date date = new Date();
//                    String currentClass = getClass().getName();
//                    System.out.println(date + " " + currentClass + " Bad request");
//                    System.out.println(date + " " + currentClass + "request=" + request);
//                    System.out.println(date + " " + currentClass + "Exception: " + e);
//                }
//            }
//
//
//
//
//
//        } finally {
//            out.close();
//        }
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