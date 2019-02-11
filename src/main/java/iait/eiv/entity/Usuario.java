package iait.eiv.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(name="usuarios")
@IdClass(PersonaPK.class)
public class Usuario {

    @Id
    @Column(name="id_tipodocumento")
    @JsonProperty("tipo_documento")
    private Integer tipoDoc;

    @Id
    @Column(name="numero_documento")
    @JsonProperty("numero_documento")
    private Integer numDoc;

    @Column(name="nombre_usuario")
    private String nombre;

    @Column(name="hashed_pwd")
    private String pwd;

    public Integer getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(Integer tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public Integer getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(Integer numDoc) {
        this.numDoc = numDoc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void update(Usuario other) {
        this.nombre = other.nombre;
        this.pwd = other.pwd;
    }
    
    public void partiallyUpdate(Usuario other) {
        if (other.nombre != null) {
            this.nombre = other.nombre;
        }
        if (other.pwd != null) {
            this.pwd = other.pwd;
        }
    }

}
