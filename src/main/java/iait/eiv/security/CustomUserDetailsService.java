package iait.eiv.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import iait.eiv.entity.Usuario;
import iait.eiv.repository.UsuarioRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private UsuarioRepository users;

    public CustomUserDetailsService(UsuarioRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
        Usuario usuario = users.findByNombre(nombre)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario " + nombre + " no encontrado"));
        Collection<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetails user = new User(nombre, usuario.getPwd(), authorities);
        return user;
    }
}