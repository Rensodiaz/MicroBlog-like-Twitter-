/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import usuarios.Permiso;
import usuarios.Usuario;
import usuarios.UsuarioSesion;

/**
 *
 * @author Luis
 */
public class UsuarioController extends HttpServlet {

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
    
    public boolean comprobarUsername(String username){
        
        String jpql = "SELECT u FROM Usuario u WHERE u.username = ?1"; 
        Query query = em.createQuery(jpql); 
        query.setParameter(1, username); 
        
        List<Usuario> user = query.getResultList();

        if(user.isEmpty()){
        
            return false;
        }
        else{
        
            return true;
        }
      
    }
    
    public long obtenerIdUsuario(String username){
        
        long id = 0;
        String jpql = "SELECT u FROM Usuario u WHERE u.username = ?1"; 
        Query query = em.createQuery(jpql); 
        query.setParameter(1, username); 
        
        List<Usuario> user = query.getResultList();

        for(Usuario u: user){
            id = u.getId();
            break;
        }        
        
        return id;
    }
    
    public boolean verificarCredenciales(String username, String password){
        
        String jpql = "SELECT u FROM Usuario u WHERE u.username = ?1 AND u.password = ?2"; 
        Query query = em.createQuery(jpql); 
        query.setParameter(1, username); 
        query.setParameter(2, password);
        
        List<Usuario> user = query.getResultList();
        
        if(user.isEmpty()){
            return false;
        } else {
            return true;
        }
    }
    
    public UsuarioSesion obtenerObjetoUsuarioSesion(String username){
        
        UsuarioSesion usuarioSesion = new UsuarioSesion();
        
        String jpql = "SELECT u FROM Usuario u WHERE u.username = ?1"; 
        Query query = em.createQuery(jpql); 
        query.setParameter(1, username); 
        
        List<Usuario> user = query.getResultList();
        for(Usuario u: user){
            usuarioSesion.setId(u.getId());
            usuarioSesion.setUsername(u.getUsername());
            usuarioSesion.setPassword(u.getPassword());
            usuarioSesion.setNombre(u.getNombre());
            usuarioSesion.setApellidos(u.getApellidos());
            usuarioSesion.setUrlImagenPerfil(u.getUrlImagenPerfil());
            break;
            
        }
        
        return usuarioSesion;
        
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
     
        String accion = request.getParameter("accion");
        
        if(accion != null){
    
            if(accion.equalsIgnoreCase("logIn")){
                
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                
                if(username.isEmpty() || password.isEmpty()){
                    String mensajeValidacion = "INCONVENIENTE: Todos los campos del formulario deben ser completados.";
                    response.sendRedirect("./login.jsp?mensajeValidacion=" + mensajeValidacion);
                } else if(!verificarCredenciales(username, password)){
                    String mensajeValidacion = "INCONVENIENTE: Username y/o Contrasena son invalidos";
                    response.sendRedirect("./login.jsp?mensajeValidacion=" + mensajeValidacion);
                } else {                    
                    HttpSession sesion = request.getSession();
                    sesion.setAttribute("usuarioSesion", obtenerObjetoUsuarioSesion(username));
                    response.sendRedirect("./MostrarPublicaciones");
                    
                }
                
            } else if(accion.equalsIgnoreCase("entrarComoInvitado")){
                
                    UsuarioSesion usuarioSesion = new UsuarioSesion();
                   /// usuarioSesion.setId(0);
                    usuarioSesion.setUsername("Invitado");
                    usuarioSesion.setPassword("invitado");
                    usuarioSesion.setNombre("Invitado");
                    usuarioSesion.setApellidos("Invitado");
                    usuarioSesion.setEmail("invitado@gmail.com");
                    usuarioSesion.setUrlImagenPerfil("default");
              
                    String jpql = "SELECT p FROM Permiso p WHERE p.nombre = ?1"; 
                    Query query = em.createQuery(jpql); 
                    query.setParameter(1, "Invitado"); 
                    List<Permiso> permisos = query.getResultList();        
                    usuarioSesion.setPermisos(permisos);
                    HttpSession sesion = request.getSession();
                    sesion.setAttribute("usuarioSesion", usuarioSesion);
                    response.sendRedirect("./MostrarPublicaciones");
                    
            
            }else if(accion.equalsIgnoreCase("registrar")){
                
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String nombre = request.getParameter("nombre");
                String apellidos = request.getParameter("apellidos");
                String email = request.getParameter("email");
                
                if(username.isEmpty() || password.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() || email.isEmpty()){
                    
                    String mensajeValidacion = "INCONVENIENTE: Todos los campos del formulario deben ser completados.";
                    response.sendRedirect("./registro.jsp?mensajeValidacion=" + mensajeValidacion);
                
                } else if(comprobarUsername(username)){
                    
                    String mensajeValidacion = "INCONVENIENTE: El username seleccionado ya existe.";
                    response.sendRedirect("./registro.jsp?mensajeValidacion=" + mensajeValidacion);  
                    
                    
                } else {
                               
                    try {

                        utx.begin();
                        Usuario usuario = new Usuario();
                        usuario.setUsername(username);
                        usuario.setPassword(password);
                        usuario.setNombre(nombre);
                        usuario.setApellidos(apellidos);
                        usuario.setEmail(email);
                        usuario.setUrlImagenPerfil("default");
                        usuario.setCreadoPor(username);
                        usuario.setModificadoPor(username);
                        usuario.setFechaCreacion(new Date());
                        usuario.setFechaModificacion(new Date());
                        List<Permiso> listaPermisos = new ArrayList();
                        String jpql = "SELECT per FROM Permiso per WHERE per.nombre = ?1"; 
                        Query query = em.createQuery(jpql); 
                        query.setParameter(1, "Publicador"); 
                        List<Permiso> permiso = query.getResultList();
                        usuario.setPermisos(permiso);
                        em.persist(usuario);
                        utx.commit();
                        
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }   
                    
                    UsuarioSesion usuarioSesion = new UsuarioSesion();
                    usuarioSesion.setId(obtenerIdUsuario(username));
                    usuarioSesion.setUsername(username);
                    usuarioSesion.setPassword(password);
                    usuarioSesion.setNombre(nombre);
                    usuarioSesion.setApellidos(apellidos);
                    usuarioSesion.setEmail(email);
                    usuarioSesion.setUrlImagenPerfil("default");
                    
                    HttpSession sesion = request.getSession();
                    sesion.setAttribute("usuarioSesion", usuarioSesion);
                    
                    
                    response.sendRedirect("./MostrarPublicaciones");
                }
            }else if(accion.equalsIgnoreCase("logOut")){
               
                HttpSession sesion = request.getSession();
                if(sesion.getAttribute("usuarioSesion") == null){
                    response.sendRedirect("./login.jsp");
                } else {
                    
                    sesion.invalidate();
                    response.sendRedirect("./login.jsp");
                    
                }
                
            
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
