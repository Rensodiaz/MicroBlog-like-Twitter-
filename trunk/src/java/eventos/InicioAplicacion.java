/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eventos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import usuarios.Permiso;
import usuarios.Usuario;

/**
 *
 * @author Luis
 */
@WebListener
public class InicioAplicacion implements ServletContextListener{
    
    
    @PersistenceContext(unitName = "microBlog")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Inicializando aplicacion fake twitter");
        
        String jpql = "SELECT p FROM Permiso p"; 
        Query query = em.createQuery(jpql);  
        List<Permiso> permisos = query.getResultList();
        Permiso permisoAdministrador = new Permiso();
        
        if(permisos.isEmpty()){
            
            try{
                
                utx.begin();
                
                permisoAdministrador.setNombre("Administrador");
                permisoAdministrador.setDescripcion("Administrador de la aplicacion.");
                permisoAdministrador.setCreadoPor("Aplicacion");
                permisoAdministrador.setModificadoPor("Aplicacion");
                permisoAdministrador.setFechaCreacion(new Date());
                permisoAdministrador.setFechaModificacion(new Date());
                
                Permiso permisoPublicador = new Permiso();
                permisoPublicador.setNombre("Publicador");
                permisoPublicador.setDescripcion("Publicador de entradas en la aplicacion.");
                permisoPublicador.setCreadoPor("Aplicacion");
                permisoPublicador.setModificadoPor("Aplicacion");
                permisoPublicador.setFechaCreacion(new Date());
                permisoPublicador.setFechaModificacion(new Date());
                
                Permiso permisoInvitado = new Permiso();
                permisoInvitado.setNombre("Invitado");
                permisoInvitado.setDescripcion("Invitado de la aplicacion.");
                permisoInvitado.setCreadoPor("Aplicacion");
                permisoInvitado.setModificadoPor("Aplicacion");
                permisoInvitado.setFechaCreacion(new Date());
                permisoInvitado.setFechaModificacion(new Date());
                em.persist(permisoAdministrador);
                em.persist(permisoPublicador);
                em.persist(permisoInvitado);
                utx.commit();
                
            } catch(Exception ex){
                
            }
            
            
        }
        
        try{
                
                utx.begin();
                Usuario usuarioAdmin = new Usuario();
                usuarioAdmin.setUsername("admin");
                usuarioAdmin.setPassword("admin");
                usuarioAdmin.setNombre("admin");
                usuarioAdmin.setApellidos("admin");
                usuarioAdmin.setEmail("admin@gmail.com");
                usuarioAdmin.setUrlImagenPerfil("Default");
                usuarioAdmin.setCreadoPor("Aplicacion");
                usuarioAdmin.setModificadoPor("Aplicacion");
                usuarioAdmin.setFechaCreacion(new Date());
                usuarioAdmin.setFechaModificacion(new Date());
                List<Permiso> permisoAdmin = new ArrayList();
                permisoAdmin.add(permisoAdministrador);
                usuarioAdmin.setPermisos(permisoAdmin);
                em.persist(usuarioAdmin);
                utx.commit();
                
            } catch(Exception ex){
                
            }

            
        
       
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Destruyendo aplicacion fake ");
    }
    
}
