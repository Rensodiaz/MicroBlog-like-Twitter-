/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package publicaciones;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import usuarios.Usuario;

/**
 *
 * @author Luis
 */
@Table(name="ent_publicaciones")
@Entity
public class Publicacion implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String descripcion; //TODO Poner un oconstraint que el limite sea de 200 caracteres.
     @ManyToOne
    private Usuario publicadoPor;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaPublicacion;
    @OneToMany
    private List<PublicacionImagen> urlImagenes;
     
    private String creadoPor;
    private String modificadoPor;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCreacion;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaModificacion;
    
    private boolean privada = true; // Para controlar si la publicacion es vibible para usuarios seguidores, o para visitantes tambein
    
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

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Publicacion)) {
            return false;
        }
        Publicacion other = (Publicacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "publicaciones.Publicacion[ id=" + id + " ]";
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the publicadoPor
     */
    public Usuario getPublicadoPor() {
        return publicadoPor;
    }

    /**
     * @param publicadoPor the publicadoPor to set
     */
    public void setPublicadoPor(Usuario publicadoPor) {
        this.publicadoPor = publicadoPor;
    }

    /**
     * @return the fechaPublicacion
     */
    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * @param fechaPublicacion the fechaPublicacion to set
     */
    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }


    /**
     * @return the creadoPor
     */
    public String getCreadoPor() {
        return creadoPor;
    }

    /**
     * @param creadoPor the creadoPor to set
     */
    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

    /**
     * @return the modificadoPor
     */
    public String getModificadoPor() {
        return modificadoPor;
    }

    /**
     * @param modificadoPor the modificadoPor to set
     */
    public void setModificadoPor(String modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the fechaModificacion
     */
    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    /**
     * @param fechaModificacion the fechaModificacion to set
     */
    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    /**
     * @return the privada
     */
    public boolean isPrivada() {
        return privada;
    }

    /**
     * @param privada the privada to set
     */
    public void setPrivada(boolean privada) {
        this.privada = privada;
    }

    /**
     * @return the urlImagenes
     */
    public List<PublicacionImagen> getUrlImagenes() {
        return urlImagenes;
    }

    /**
     * @param urlImagenes the urlImagenes to set
     */
    public void setUrlImagenes(List<PublicacionImagen> urlImagenes) {
        this.urlImagenes = urlImagenes;
    }
    
}
