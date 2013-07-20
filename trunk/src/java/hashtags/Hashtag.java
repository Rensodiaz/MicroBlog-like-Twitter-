/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hashtags;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import publicaciones.Publicacion;

/**
 *
 * @author Luis
 */
@Table(name="ent_hashtags")
@Entity
public class Hashtag implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    private String nombreDeConsulta;
    @OneToMany
    private List<Publicacion> publicaciones;

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    public void agregarPublicacion(Publicacion publicacion){
        this.publicaciones.add(publicacion);
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hashtag)) {
            return false;
        }
        Hashtag other = (Hashtag) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hashtags.Hashtag[ id=" + id + " ]";
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

    /**
     * @return the nombreDeConsulta
     */
    public String getNombreDeConsulta() {
        return nombreDeConsulta;
    }

    /**
     * @param nombreDeConsulta the nombreDeConsulta to set
     */
    public void setNombreDeConsulta(String nombreDeConsulta) {
        this.nombreDeConsulta = nombreDeConsulta;
    }
    
}
