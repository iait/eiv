package iait.eiv.entity;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Entity(name="usuarios")
@IdClass(PersonaPK.class)
public class Usuario implements UserDetails {

    private static final long serialVersionUID = -6858053268577800269L;

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

//    @ElementCollection(fetch=FetchType.EAGER)
//    @Builder.Default
    @JsonIgnore
    @Transient
    private List<String> roles = Arrays.asList("ROLE_USER", "ROLE_ADMIN");

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return pwd;
    }

    @Override
    public String getUsername() {
        return nombre;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    public List<String> getRoles() {
        return roles;
    }

}
