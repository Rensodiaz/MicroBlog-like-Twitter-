/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seguirUsuarios;

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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import usuarios.Usuario;
import usuarios.UsuarioSesion;

/**
 *
 * @author Renso
 */
@WebServlet(name = "ListaUsuarios", urlPatterns = {"/ListaUsuarios"})
public class ListaUsuarios extends HttpServlet {

    @PersistenceContext(unitName = "microBlog")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;

    public List<Usuario> UsuariosRegistrados() {

        List<Usuario> usuarioReg = new ArrayList();

        String consulta = "SELECT u FROM Usuario u";
        Query query = em.createQuery(consulta);
        usuarioReg = query.getResultList();
        
        return usuarioReg;
    }
    
    public Usuario Usuario(Long id) {

        Usuario usuario = new Usuario();

        String consulta = "SELECT u FROM Usuario u where u.id="+id;
        Query query = em.createQuery(consulta);
        usuario = (Usuario)query.getSingleResult();
        
        return usuario;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        HttpSession sesion = request.getSession();
        UsuarioSesion userLogeado = (UsuarioSesion) sesion.getAttribute("usuarioSesion");
        
        List<Usuario> usuariosReg = UsuariosRegistrados();        
        List<Usuario> usuariosRegNoAmigos = new ArrayList();
        
        if(!userLogeado.getPassword().equals("invitado")){
            System.out.println(userLogeado.getPassword());
            Usuario user = Usuario(userLogeado.getId());
            List<Usuario> usuariosRegAmigos = user.getSeguidores();
            
            for (Usuario s : usuariosReg) {
                if (!usuariosRegAmigos.contains(s) && !s.equals(user)) {
                usuariosRegNoAmigos.add(s);
                }
            }   
        }else{
            usuariosRegNoAmigos = UsuariosRegistrados();
        }       

        request.setAttribute("usuarios", usuariosRegNoAmigos);
        
        RequestDispatcher dispatcher =
                getServletContext().getRequestDispatcher("/seguirUsuarioLista.jsp");
        dispatcher.forward(request, response);

//        try {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet ListaUsuarios</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet ListaUsuarios at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
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
