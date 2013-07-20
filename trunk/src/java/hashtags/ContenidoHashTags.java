/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hashtags;

import java.util.ArrayList;
import java.util.List;
import publicaciones.Publicacion;

/**
 *
 * @author Luis
 */
public class ContenidoHashTags {
    
    private String nombre;
    private List<Publicacion> publicaciones = new ArrayList();
    
    public ContenidoHashTags(String nombre, List<Publicacion> publicaciones){
        this.nombre = nombre;
        this.publicaciones = publicaciones;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the publicaciones
     */
    public List<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    /**
     * @param publicaciones the publicaciones to set
     */
    public void setPublicaciones(List<Publicacion> publicaciones) {
        this.publicaciones = publicaciones;
    }
    
    
    
    
}
