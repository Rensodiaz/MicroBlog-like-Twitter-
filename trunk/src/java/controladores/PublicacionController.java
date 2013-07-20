/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import hashtags.Hashtag;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import publicaciones.Publicacion;
import publicaciones.PublicacionImagen;
import usuarios.Permiso;
import usuarios.Usuario;
import usuarios.UsuarioSesion;

/**
 *
 * @author Luis
 */
public class PublicacionController extends HttpServlet {
    
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
    
    public Usuario obtenerUsuario(long id){
        
        Usuario u=em.find(Usuario.class, id);
        return u;
    }
    
    public void agregarPublicacionAUsuario(long id, Publicacion nuevaPublicacion){
        
        Usuario u = em.find(Usuario.class, id);
        List<Publicacion> publicaciones = u.getPublicaciones();
        
        try{
            
            utx.begin();
            publicaciones.add(nuevaPublicacion);
            em.merge(u);        
            utx.commit();
            
        }catch(Exception ex){
            
        }
        
        
    }
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String accion = request.getParameter("accion");
        
        
        if(accion.equalsIgnoreCase("publicandoEntrada")){
            
            
            Boolean privada = true;
            String publicacion = request.getParameter("publicacion").trim();
       
            
            if(publicacion.isEmpty()){
                
                response.sendRedirect("./index.jsp?mensajeValidacionIncorrecto=INCONVENIENTE: El contenido de su publicacion no puede estar vacio.");
                
                 
            } else if(request.getParameter("privada") == null){
                
                response.sendRedirect("./index.jsp?mensajeValidacionIncorrecto=INCONVENIENTE: Debe seleccionar una opcion de visibilidad para su publicacion.");
            
            }else {
                
                privada = Boolean.parseBoolean(request.getParameter("privada"));
                HttpSession sesion = request.getSession();
                UsuarioSesion usuarioSesion = (UsuarioSesion)sesion.getAttribute("usuarioSesion");
                Publicacion nuevaPublicacion = new Publicacion();
                List<PublicacionImagen> imagenes = new ArrayList();
                
                List<String> imagenesSesion = new ArrayList<String>();
                boolean existenciaImagenes = false;
                
                Object attribute = sesion.getAttribute("listaImagenes");
                      if (attribute != null) {
                             if (attribute instanceof List) {
                                 imagenesSesion = (List<String>) attribute;
                                 existenciaImagenes = true;
                             }
                        }
                    for(String imagePath : imagenesSesion){
                        PublicacionImagen nuevaImagen = new PublicacionImagen();
                        nuevaImagen.seturlPath(imagePath);
                        imagenes.add(nuevaImagen);
                    }
                    System.out.println("entro");
                    System.out.println(imagenes);
                try {

                            utx.begin();

                            nuevaPublicacion.setDescripcion(publicacion);
                            nuevaPublicacion.setPrivada(privada);
                            nuevaPublicacion.setFechaPublicacion(new Date());
                            nuevaPublicacion.setCreadoPor(usuarioSesion.getUsername());
                            nuevaPublicacion.setModificadoPor(usuarioSesion.getUsername());
                            nuevaPublicacion.setFechaCreacion(new Date());
                            nuevaPublicacion.setFechaModificacion(new Date());
                            nuevaPublicacion.setPublicadoPor(obtenerUsuario(usuarioSesion.getId()));
                            if(existenciaImagenes == true){
                                 nuevaPublicacion.setUrlImagenes(imagenes);
                            }
                          
                            em.persist(nuevaPublicacion);
                            sesion.removeAttribute("listaImagenes");
                            System.out.println("borrado");
                            System.out.println(sesion.getAttribute("listaImagenes"));
                            utx.commit();



                } catch (Exception ex) {
                    ex.printStackTrace();
                } 

                agregarPublicacionAUsuario(usuarioSesion.getId(), nuevaPublicacion);


                String[] contenidoPublicacion = publicacion.split(" ");
                for(String str: contenidoPublicacion){
                     boolean matchea = Pattern.matches("#\\w+", str);


                     if(matchea==true){

                        String jpql = "SELECT h FROM Hashtag h WHERE h.nombreDeConsulta = ?1"; 
                        Query query = em.createQuery(jpql); 
                        query.setParameter(1, str.toLowerCase());        
                        List<Hashtag> hashtag = query.getResultList();

                        if(hashtag.isEmpty()){
                            try {

                                utx.begin();
                                Hashtag hashT = new Hashtag();
                                hashT.setNombre(str);
                                hashT.setNombreDeConsulta(str.toLowerCase());
                                List<Publicacion> publicaciones = new ArrayList();
                                publicaciones.add(nuevaPublicacion);
                                hashT.setPublicaciones(publicaciones);
                                em.persist(hashT);
                                utx.commit();                      

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }   
                        else{


                            String jpqlh = "SELECT h FROM Hashtag h WHERE h.nombreDeConsulta = ?1"; 
                            Query queryh = em.createQuery(jpqlh); 
                            queryh.setParameter(1, str.toLowerCase());        
                            List<Hashtag> listaHashTags = queryh.getResultList();
                            Hashtag ht = listaHashTags.get(0);

                            List<Publicacion> listaP = ht.getPublicaciones();


                            try{ 
                                utx.begin();
                                listaP.add(nuevaPublicacion);
                                ht.setPublicaciones(listaP);
                                em.merge(ht);
                                utx.commit();                      

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                     }
                }
                
            response.sendRedirect("./MostrarPublicaciones");                
                
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
