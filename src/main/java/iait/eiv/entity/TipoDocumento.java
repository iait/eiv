package iait.eiv.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(name="tipos_documentos")
public class TipoDocumento {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_tipodocumento")
    private Integer id;

    @Column(name="nombre")
    private String nombre;

    @Column(name="abreviatura")
    private String abreviatura;

    @Column(name="validar_como_cuit")
    @JsonProperty("validar_como_cuit")
    private Boolean validarComoCUIT = false;

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

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public Boolean getValidarComoCUIT() {
        return validarComoCUIT;
    }

    public void setValidarComoCUIT(Boolean validarComoCUIT) {
        this.validarComoCUIT = validarComoCUIT;
    }

    public void update(TipoDocumento other) {
        this.nombre = other.nombre;
        this.abreviatura = other.abreviatura;
        this.validarComoCUIT = other.validarComoCUIT;
    }
    
    public void partiallyUpdate(TipoDocumento other) {
        if (other.nombre != null) {
            this.nombre = other.nombre;
        }
        if (other.abreviatura != null) {
            this.abreviatura = other.abreviatura;
        }
        if (other.validarComoCUIT != null) {
            this.validarComoCUIT = other.validarComoCUIT;
        }
    }

}
