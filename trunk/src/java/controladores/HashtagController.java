/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import hashtags.ContenidoHashTags;
import hashtags.Hashtag;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import usuarios.Usuario;

/**
 *
 * @author Luis
 */
public class HashtagController extends HttpServlet {
    
    
    @PersistenceContext(unitName = "microBlog")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;

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
        
        System.out.println("ENTRE CONASO");
        
        String accion = request.getParameter("accion");
        
        
        if(accion.equalsIgnoreCase("buscarHashTags")){
            
            List<ContenidoHashTags> listaHashtags = new ArrayList();
            String jpql = "SELECT h FROM Hashtag h"; 
            Query query = em.createQuery(jpql); 
            List<Hashtag> hashtags = query.getResultList();
            
            for(Hashtag h: hashtags){
                listaHashtags.add(new ContenidoHashTags(h.getNombre(), h.getPublicaciones()));
            }
            
            request.setAttribute("listaHashtags", listaHashtags);

            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/buscarHashTags.jsp");
            dispatcher.forward(request, response);
            
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
