package iait.eiv.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import iait.eiv.entity.Persona;
import iait.eiv.repository.TipoDocumentoRepository;
import iait.eiv.repository.PersonaRepository;

@RestController
@RequestMapping(path="/eiv/personas")
public class PersonaController {
    
    @Autowired
    private PersonaRepository personaRepository;
    
    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    @GetMapping(path="", produces="application/json")
    public ResponseEntity<Persona> getPersona(@RequestParam("tipodoc") String tipoDoc,
            @RequestParam("numdoc") Integer numDoc) {
        // busca tipo de documento por abreviatura
        Optional<TipoDocumento> tipoDocumentoOp = tipoDocumentoRepository.findByAbreviatura(tipoDoc);
        if (tipoDocumentoOp.isPresent()) {
            Integer tipoDocId = tipoDocumentoOp.get().getId();
            // construye primary key compuesta
            PersonaPK personaPK = new PersonaPK();
            personaPK.setTipoDoc(tipoDocId);
            personaPK.setNumDoc(numDoc);
            Optional<Persona> personaOp = personaRepository.findById(personaPK);
            if (personaOp.isPresent()) {
                return new ResponseEntity<>(personaOp.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path="/all", produces="application/json")
    public ResponseEntity<Iterable<Persona>> getAllPersonas() {
        Iterable<Persona> personas = personaRepository.findAll();
        return new ResponseEntity<>(personas, HttpStatus.OK);
    }

    @PostMapping(path="", consumes="application/json")
    public ResponseEntity<Persona> createPersona(@RequestBody Persona persona) {
        Persona personaResponse = personaRepository.save(persona);
        return new ResponseEntity<>(personaResponse, HttpStatus.OK);
    }

    @PutMapping(path="", consumes="application/json")
    public ResponseEntity<Persona> updatePersona(@RequestParam("tipodoc") String tipoDoc,
            @RequestParam("numdoc") Integer numDoc, @RequestBody Persona personaInput) {
        // busca tipo de documento por abreviatura
        Optional<TipoDocumento> tipoDocumentoOp = tipoDocumentoRepository.findByAbreviatura(tipoDoc);
        if (tipoDocumentoOp.isPresent()) {
            Integer tipoDocId = tipoDocumentoOp.get().getId();
            // construye primary key compuesta
            PersonaPK personaPK = new PersonaPK();
            personaPK.setTipoDoc(tipoDocId);
            personaPK.setNumDoc(numDoc);
            Optional<Persona> personaOp = personaRepository.findById(personaPK);
            if (personaOp.isPresent()) {
                Persona persona = personaOp.get();
                persona.update(personaInput);
                Persona personaResponse = personaRepository.save(persona);
                return new ResponseEntity<>(personaResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(path="", consumes="application/json")
    public ResponseEntity<Persona> partiallyUpdatePersona(@RequestParam("tipodoc") String tipoDoc,
            @RequestParam("numdoc") Integer numDoc, @RequestBody Persona personaInput) {
        // busca tipo de documento por abreviatura
        Optional<TipoDocumento> tipoDocumentoOp = tipoDocumentoRepository.findByAbreviatura(tipoDoc);
        if (tipoDocumentoOp.isPresent()) {
            Integer tipoDocId = tipoDocumentoOp.get().getId();
            // construye primary key compuesta
            PersonaPK personaPK = new PersonaPK();
            personaPK.setTipoDoc(tipoDocId);
            personaPK.setNumDoc(numDoc);
            Optional<Persona> personaOp = personaRepository.findById(personaPK);
            if (personaOp.isPresent()) {
                Persona persona = personaOp.get();
                persona.partiallyUpdate(personaInput);
                Persona personaResponse = personaRepository.save(persona);
                return new ResponseEntity<>(personaResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path="", produces="application/json")
    public ResponseEntity<Persona> deletePersona(@RequestParam("tipodoc") String tipoDoc,
            @RequestParam("numdoc") Integer numDoc) {
        // busca tipo de documento por abreviatura
        Optional<TipoDocumento> tipoDocumentoOp = tipoDocumentoRepository.findByAbreviatura(tipoDoc);
        if (tipoDocumentoOp.isPresent()) {
            Integer tipoDocId = tipoDocumentoOp.get().getId();
            // construye primary key compuesta
            PersonaPK personaPK = new PersonaPK();
            personaPK.setTipoDoc(tipoDocId);
            personaPK.setNumDoc(numDoc);
            Optional<Persona> personaOp = personaRepository.findById(personaPK);
            if (personaOp.isPresent()) {
                personaRepository.deleteById(personaPK);
                return new ResponseEntity<>(personaOp.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}