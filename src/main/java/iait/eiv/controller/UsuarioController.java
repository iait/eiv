package iait.eiv.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import iait.eiv.entity.PersonaPK;
import iait.eiv.entity.TipoDocumento;
import iait.eiv.entity.Usuario;
import iait.eiv.repository.TipoDocumentoRepository;
import iait.eiv.repository.UsuarioRepository;

@RestController
@RequestMapping(path="/eiv/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    @GetMapping(path="", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> getUsuario(@RequestParam("tipodoc") String tipoDoc,
            @RequestParam("numdoc") Integer numDoc) {
        // busca tipo de documento por abreviatura
        Optional<TipoDocumento> tipoDocumentoOp = tipoDocumentoRepository.findByAbreviatura(tipoDoc);
        if (tipoDocumentoOp.isPresent()) {
            Integer tipoDocId = tipoDocumentoOp.get().getId();
            // construye primary key compuesta
            PersonaPK personaPK = new PersonaPK();
            personaPK.setTipoDoc(tipoDocId);
            personaPK.setNumDoc(numDoc);
            Optional<Usuario> usuarioOp = usuarioRepository.findById(personaPK);
            if (usuarioOp.isPresent()) {
                return new ResponseEntity<>(usuarioOp.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path="/all", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Usuario>> getAllUsuarios() {
        Iterable<Usuario> usuarios = usuarioRepository.findAll();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @PostMapping(path="", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioResponse = usuarioRepository.save(usuario);
        return new ResponseEntity<>(usuarioResponse, HttpStatus.OK);
    }

    @PutMapping(path="", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> updateUsuario(@RequestParam("tipodoc") String tipoDoc,
            @RequestParam("numdoc") Integer numDoc, @RequestBody Usuario usuarioInput) {
        // busca tipo de documento por abreviatura
        Optional<TipoDocumento> tipoDocumentoOp = tipoDocumentoRepository.findByAbreviatura(tipoDoc);
        if (tipoDocumentoOp.isPresent()) {
            Integer tipoDocId = tipoDocumentoOp.get().getId();
            // construye primary key compuesta
            PersonaPK personaPK = new PersonaPK();
            personaPK.setTipoDoc(tipoDocId);
            personaPK.setNumDoc(numDoc);
            Optional<Usuario> usuarioOp = usuarioRepository.findById(personaPK);
            if (usuarioOp.isPresent()) {
                Usuario usuario = usuarioOp.get();
                usuario.update(usuarioInput);
                Usuario usuarioResponse = usuarioRepository.save(usuario);
                return new ResponseEntity<>(usuarioResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(path="", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> partiallyUpdateUsuario(@RequestParam("tipodoc") String tipoDoc,
            @RequestParam("numdoc") Integer numDoc, @RequestBody Usuario usuarioInput) {
        // busca tipo de documento por abreviatura
        Optional<TipoDocumento> tipoDocumentoOp = tipoDocumentoRepository.findByAbreviatura(tipoDoc);
        if (tipoDocumentoOp.isPresent()) {
            Integer tipoDocId = tipoDocumentoOp.get().getId();
            // construye primary key compuesta
            PersonaPK personaPK = new PersonaPK();
            personaPK.setTipoDoc(tipoDocId);
            personaPK.setNumDoc(numDoc);
            Optional<Usuario> usuarioOp = usuarioRepository.findById(personaPK);
            if (usuarioOp.isPresent()) {
                Usuario usuario = usuarioOp.get();
                usuario.partiallyUpdate(usuarioInput);
                Usuario usuarioResponse = usuarioRepository.save(usuario);
                return new ResponseEntity<>(usuarioResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path="", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> deleteUsuario(@RequestParam("tipodoc") String tipoDoc,
            @RequestParam("numdoc") Integer numDoc) {
        // busca tipo de documento por abreviatura
        Optional<TipoDocumento> tipoDocumentoOp = tipoDocumentoRepository.findByAbreviatura(tipoDoc);
        if (tipoDocumentoOp.isPresent()) {
            Integer tipoDocId = tipoDocumentoOp.get().getId();
            // construye primary key compuesta
            PersonaPK personaPK = new PersonaPK();
            personaPK.setTipoDoc(tipoDocId);
            personaPK.setNumDoc(numDoc);
            Optional<Usuario> usuarioOp = usuarioRepository.findById(personaPK);
            if (usuarioOp.isPresent()) {
                usuarioRepository.deleteById(personaPK);
                return new ResponseEntity<>(usuarioOp.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}