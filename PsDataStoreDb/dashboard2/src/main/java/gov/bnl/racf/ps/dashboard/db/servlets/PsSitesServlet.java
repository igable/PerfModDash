/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.bnl.racf.ps.dashboard.db.servlets;

import gov.bnl.racf.ps.dashboard.db.data_objects.PsSite;
import gov.bnl.racf.ps.dashboard.db.data_store.PsDataStore;
import gov.bnl.racf.ps.dashboard.db.object_manipulators.JsonConverter;
import gov.bnl.racf.ps.dashboard.db.object_manipulators.PsObjectShredder;
import gov.bnl.racf.ps.dashboard.db.object_manipulators.PsObjectUpdater;
import gov.bnl.racf.ps.dashboard.db.object_manipulators.PsSiteManipulator;
import gov.bnl.racf.ps.dashboard.db.session_factory_store.PsSessionFactoryStore;
import gov.bnl.racf.ps.dashboard.db.utils.UrlUnpacker;
import gov.racf.bnl.ps.dashboard.PsApi.PsApi;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author tomw
 */
public class PsSitesServlet extends HttpServlet {

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
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PsSitesServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PsSitesServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        //throw new UnsupportedOperationException("Not yet implemented");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        //boilerplate code to open session
        SessionFactory sessionFactory =
                PsSessionFactoryStore.getSessionFactoryStore().getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            ArrayList<String> parameters = UrlUnpacker.unpack(request.getPathInfo());

            if (parameters.size() > 0) {
                String idAsString = parameters.get(0);
                Integer siteIdInteger = Integer.parseInt(idAsString);
                int siteId = siteIdInteger.intValue();
                PsSite site = PsDataStore.getSite(session, siteId);
                JSONObject hostJson = JsonConverter.toJson(site);
                out.println(hostJson.toString());
            } else {

                List<PsSite> listOfSites = PsDataStore.getAllSites(session);
                JSONArray jsonArray = new JSONArray();
                for (PsSite site : listOfSites) {
                    JSONObject siteJson = JsonConverter.toJson(site);
                    jsonArray.add(siteJson);
                }
                out.println(jsonArray.toString());
            }

            // commit transaction and close session
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println(new Date() + " " + getClass().getName() + " " + e);
            Logger.getLogger(PsSitesServlet.class).error(e);
            out.println("Error occured in " + getClass().getName() + " plase check the logs<BR>" + e);
        } finally {
            session.close();
            out.close();
        }
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
        //throw new UnsupportedOperationException("Not yet implemented");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();


        //boilerplate code to open session
        SessionFactory sessionFactory =
                PsSessionFactoryStore.getSessionFactoryStore().getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            // parse data part of the code
            JSONObject jsonObject = PostRequestDataExtractor.extractJson(request);

            if (jsonObject != null) {
                // the input data is a valid JSON object

                // create new site
                PsSite site = new PsSite();
                session.save(site);

                // fill the host with JSON parameters
                PsObjectUpdater.update(site, jsonObject);

                // convert host to json
                JSONObject finalSite = JsonConverter.toJson(site);

                out.println(finalSite.toString());
            }
            // commit transaction and close session
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println(new Date() + " Error in " + getClass().getName() + " " + e);
            Logger.getLogger(PsSitesServlet.class).error(e);
            out.println("Error occured in " + getClass().getName() + " plase check the logs<BR>" + e);
        } finally {
            session.close();
            out.close();
        }


    }

    /**
     * Handles the HTTP
     * <code>PUT</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //throw new UnsupportedOperationException("Not yet implemented");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // first order of business is to open session
        //boilerplate code to open session
        SessionFactory sessionFactory =
                PsSessionFactoryStore.getSessionFactoryStore().getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            // second order of business is to unpack parameters from url
            ArrayList<String> parameters = UrlUnpacker.unpack(request.getPathInfo());

            //if there are parameters
            if (parameters.size() == 1) {
                String idAsString = parameters.get(0);
                Integer siteIdInteger = Integer.parseInt(idAsString);
                int siteId = siteIdInteger.intValue();
                PsSite site = PsDataStore.getSite(session, siteId);

                //unpack json object from data part
                // parse data part of the code
                JSONObject jsonObject = PostRequestDataExtractor.extractJson(request);
                if (jsonObject != null) {
                    //update the host
                    PsObjectUpdater.update(site, jsonObject);

                    // save the updated host
                    session.save(site);

                    JSONObject siteJson = JsonConverter.toJson(site);
                    out.println(siteJson.toString());
                } else {
                    out.println("JSON object is not valid");
                    Logger.getLogger(PsSitesServlet.class).error("JSON object is not valid");
                }
            } else {
                if (parameters.size() == 2) {
                    int siteId = Integer.parseInt(parameters.get(0));
                    String userCommand = parameters.get(1);
                    out.println(siteId + " " + userCommand);
                    PsSite site = (PsSite) session.get(PsSite.class, siteId);
                    if (site == null) {
                        out.println("site " + siteId + " not found");
                        Logger.getLogger(PsSitesServlet.class).warn("Site with id=" + siteId + " not found");
                    } else {
                        // this is a valid site

                        // unpack data content
                        JSONArray jsonArray =
                                PostRequestDataExtractor.extractJsonArray(request);

                        if (PsApi.SITE_ADD_HOST_IDS.equals(userCommand)) {
                            // user wants to add hosts to site

                            // add those hosts
                            PsSiteManipulator.addHosts(session, site, jsonArray);
                        }

                        if (PsApi.SITE_REMOVE_HOST_IDS.equals(userCommand)) {
                            // user wants to remove hosts from site

                            // remove those hosts
                            PsSiteManipulator.removeHosts(session, site, jsonArray);
                        }
                        //save the changes to the site (actually this command should be redundant)
                        session.save(site);
                        out.println(JsonConverter.toJson(site).toString());
                    }
                }
            }

            // commit transaction and close session
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println(new Date() + " Error in " + getClass().getName() + " " + e);
            Logger.getLogger(PsSitesServlet.class).error(e);
            out.println("Error occured in " + getClass().getName() + " please check the logs <BR>" + e);
        } finally {
            session.close();
            out.close();
        }
    }

    /**
     * Handles the HTTP
     * <code>DELETE</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //throw new UnsupportedOperationException("Not yet implemented");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // first order of business is to open session
        //boilerplate code to open session
        SessionFactory sessionFactory =
                PsSessionFactoryStore.getSessionFactoryStore().getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            // second order of business is to unpack parameters from url
            ArrayList<String> parameters = UrlUnpacker.unpack(request.getPathInfo());

            //if there are parameters
            if (parameters.size() > 0) {
                String idAsString = parameters.get(0);
                Integer siteIdInteger = Integer.parseInt(idAsString);
                int siteId = siteIdInteger.intValue();
                PsSite site = PsDataStore.getSite(session, siteId);

                PsObjectShredder.delete(session, site);
            }

            // commit transaction and close session
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println(new Date() + " Error in " + getClass().getName() + " " + e);
            Logger.getLogger(PsSitesServlet.class).error(e);
            out.println("Error occured in " + getClass().getName() + " please check the logs <BR>" + e);
        } finally {
            session.close();
            out.close();
        }



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
