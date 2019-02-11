package iait.eiv.entity;

import java.sql.Blob;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(name="personas")
@IdClass(PersonaPK.class)
public class Persona {

    @Id
    @Column(name="id_tipodocumento")
    @JsonProperty("tipo_documento")
    private Integer tipoDoc;

    @Id
    @Column(name="numero_documento")
    @JsonProperty("numero_documento")
    private Integer numDoc;

    @Column(name="nombre_apellido")
    @JsonProperty("nombre_apellido")
    private String nombre;

    @Column(name="fecha_nacimiento")
    @JsonProperty("fecha_nacimiento")
    private Date fechaNacimiento;

    @Column(name="genero")
    @JsonProperty("genero")
    private String genero;

    @Column(name="es_argentino")
    @JsonProperty("es_argentino")
    private Boolean esArgentino;

    @Column(name="correo_electronico")
    @JsonProperty("correo_electronico")
    private String correoElectronico;

    @Column(name="foto_cara")
    @JsonProperty("foto_cara")
    private Blob fotoCara;

    @Column(name="id_localidad")
    @JsonProperty("localidad")
    private Integer localidad;

    @Column(name="codigo_postal")
    @JsonProperty("codigo_postal")
    private String codigoPostal;

    public Blob getFotoCara() {
        return fotoCara;
    }

    public void setFotoCara(Blob fotoCara) {
        this.fotoCara = fotoCara;
    }

    public void update(Persona other) {
        this.nombre = other.nombre;
        this.fechaNacimiento = other.fechaNacimiento;
        this.genero = other.genero;
        this.esArgentino = other.esArgentino;
        this.correoElectronico = other.correoElectronico;
        this.localidad = other.localidad;
        this.codigoPostal = other.codigoPostal;
    }

    public void partiallyUpdate(Persona other) {
        if (other.nombre != null) {
            this.nombre = other.nombre;
        }
        if (other.fechaNacimiento != null) {
            this.fechaNacimiento = other.fechaNacimiento;
        }
        if (other.genero != null) {
            this.genero = other.genero;
        }
        if (other.esArgentino != null) {
            this.esArgentino = other.esArgentino;
        }
        if (other.correoElectronico != null) {
            this.correoElectronico = other.correoElectronico;
        }
        if (other.localidad != null) {
            this.localidad = other.localidad;
        }
        if (other.codigoPostal != null) {
            this.codigoPostal = other.codigoPostal;
        }
    }

}
