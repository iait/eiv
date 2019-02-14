package iait.eiv.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import iait.eiv.repository.UsuarioRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private UsuarioRepository users;

    public CustomUserDetailsService(UsuarioRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
        return users.findByNombre(nombre)
            .orElseThrow(() -> new UsernameNotFoundException("Username: " + nombre + " not found"));
    }
}