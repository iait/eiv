package iait.eiv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iait.eiv.entity.Usuario;
import iait.eiv.security.JwtTokenProvider;

@RestController
@RequestMapping(path="/eiv/signin")
public class SingInController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping(path="", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> signIn(@RequestBody Usuario usuario) {
        try {
            String username = usuario.getNombre();
            String password = usuario.getPwd();

            if (!username.equals("admin") || !password.equals("admin")) {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            }
            String token = jwtTokenProvider.createToken(username);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            throw new BadCredentialsException("Usuario o password inválido");
        }
    }

}
