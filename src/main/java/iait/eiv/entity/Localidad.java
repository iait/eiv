package iait.eiv.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(name="localidades")
public class Localidad {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_localidad")
    private Integer id;

    @Column(name="nombre")
    private String nombre;

    @Column(name="id_provincia")
    private Integer provincia;

    @Column(name="codigo_postal")
    @JsonProperty("codigo_postal")
    private String codigoPostal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getProvincia() {
        return provincia;
    }

    public void setProvincia(Integer provincia) {
        this.provincia = provincia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public void update(Localidad other) {
        this.nombre = other.nombre;
        this.provincia = other.provincia;
        this.codigoPostal = other.codigoPostal;
    }
    
    public void partiallyUpdate(Localidad other) {
        if (other.nombre != null) {
            this.nombre = other.nombre;
        }
        if (other.provincia != null) {
            this.provincia = other.provincia;
        }
        if (other.codigoPostal != null) {
            this.codigoPostal = other.codigoPostal;
        }
    }

}
