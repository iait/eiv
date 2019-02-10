package iait.eiv;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="provincias")
public class Provincia {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_provincia")
    private Integer id;

    @Column(name="nombre")
    private String nombre;

    @Column(name="region")
    private String region;

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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
    
    public void update(Provincia other) {
        this.nombre = other.nombre;
        this.region = other.region;
    }
    
    public void partiallyUpdate(Provincia other) {
        if (other.nombre != null) {
            this.nombre = other.nombre;
        }
        if (other.region != null) {
            this.region = other.region;
        }
    }

}
