/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package publicaciones;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import usuarios.Permiso;
import usuarios.Usuario;
import usuarios.UsuarioSesion;

/**
 *
 * @author Renso
 */
@WebServlet(name = "MostrarPublicaciones", urlPatterns = {"/MostrarPublicaciones"})
public class MostrarPublicaciones extends HttpServlet {

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
    @PersistenceContext(unitName = "microBlog")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;

    public Usuario Usuario(Long id) {

        Usuario usuario = new Usuario();

        String consulta = "SELECT u FROM Usuario u where u.id=?1";
        Query query = em.createQuery(consulta);
        query.setParameter(1, id);
        usuario = (Usuario) query.getSingleResult();

        return usuario;
    }

    public List<Publicacion> publicaciones() {
        List<Publicacion> p = new ArrayList();

        String jpql = "SELECT p FROM Publicacion p order by p.id desc";
        Query query = em.createQuery(jpql);
        p = query.getResultList();

        return p;
    }
    
    public List<Publicacion> publicacionesFriends(List<Usuario> amigos) {
        List<Publicacion> p = new ArrayList();
        List<Publicacion> pAll = new ArrayList();           

        for(Usuario u : amigos){
            String jpql = "SELECT p FROM Publicacion p where p.publicadoPor = ?1 order by p.id desc";
            Query query = em.createQuery(jpql);
            query.setParameter(1, u);
            p = query.getResultList();
            pAll.addAll(p);
        }
        
        return pAll;
    }
    
    public List<Publicacion> publicacionesInvitado() {
        List<Publicacion> p = new ArrayList();

        String jpql = "SELECT p FROM Publicacion p where p.privada=false order by p.id desc";
        Query query = em.createQuery(jpql);
        p = query.getResultList();

        return p;
    }
    
     public List<Publicacion> publicacionesMias(Usuario u) {
        List<Publicacion> p = new ArrayList();

        String jpql = "SELECT p FROM Publicacion p where p.publicadoPor = ?1 order by p.id desc";
        Query query = em.createQuery(jpql);
        query.setParameter(1, u);
        p = query.getResultList();

        return p;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession sesion = request.getSession();
        UsuarioSesion userLogeado = (UsuarioSesion) sesion.getAttribute("usuarioSesion");
        List<Publicacion> pb = new ArrayList();
         
        if(!userLogeado.getPassword().equals("invitado")){
            Usuario user = Usuario(userLogeado.getId());
            List<Permiso> permisos = user.getPermisos();
            
            for (Permiso p : permisos) {
                if(p.getNombre().equals("Administrador")) {
                    pb = publicaciones();
                    request.setAttribute("publicarAll", pb);
                    request.setAttribute("usuario", user);                
                    RequestDispatcher dispatcher =
                            getServletContext().getRequestDispatcher("/index.jsp");
                    dispatcher.forward(request, response);                
                }
                else if(p.getNombre().equals("Publicador")) {
                    pb = publicacionesMias(user);
                    pb.addAll(publicacionesFriends(user.getSeguidores()));

                    request.setAttribute("usuario", user);
                    request.setAttribute("publicarAll", pb);
                    RequestDispatcher dispatcher =
                            getServletContext().getRequestDispatcher("/index.jsp");
                    dispatcher.forward(request, response);
                    }
                }
            }else {
                pb = publicacionesInvitado();
                request.setAttribute("usuario", userLogeado);
                request.setAttribute("publicarAll", pb);
                RequestDispatcher dispatcher =
                        getServletContext().getRequestDispatcher("/index.jsp");
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
