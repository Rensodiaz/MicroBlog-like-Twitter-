/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import usuarios.Usuario;
import usuarios.UsuarioSesion;

/**
 *
 * @author Luis
 */
public class updateUsuarioController extends HttpServlet {

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
        String url = request.getParameter("url");
        String nombre = request.getParameter("nombreUsuario");
        String apellido = request.getParameter("apellidoUsuario");
        String email = request.getParameter("emailUsuario");
        String password = request.getParameter("passwordUsuario");
        HttpSession session = request.getSession();
        UsuarioSesion users = new UsuarioSesion();
        Usuario usuario = new Usuario();
        System.out.println(session.getAttribute("usuarioSesion"));
        Object attribute = session.getAttribute("usuarioSesion");
        System.out.println(attribute);
         if (attribute != null) {
             System.out.println("entro");
                if (attribute instanceof UsuarioSesion) {
                    users = (UsuarioSesion) attribute;
                            System.out.println("entro");
                    System.out.println(users.getId());
                }
        }
         if(url.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || password.isEmpty()){
         response.sendRedirect("./profileUsuario.jsp?vacio=1");
         }else{
                try{
                       System.out.println(url);
                       System.out.println(nombre);
                       System.out.println(apellido);
                       System.out.println(email);
                       System.out.println(password);
                       System.out.println(users.getId());
                       utx.begin();
                       usuario = em.find(Usuario.class, users.getId());
                       usuario.setUrlImagenPerfil(url);
                       usuario.setNombre(nombre);
                       usuario.setApellidos(apellido);
                       usuario.setEmail(email);
                       usuario.setPassword(password);
                       users.setUrlImagenPerfil(url);
                       users.setNombre(nombre);
                       users.setApellidos(apellido);
                       users.setEmail(email);
                       users.setPassword(password);
                       em.merge(usuario);
                       session.setAttribute("usuarioSesion", users);
                       utx.commit();
                       response.sendRedirect("./profileUsuario.jsp");
                    } catch (Exception ex) {
                         ex.printStackTrace();
                    }
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
