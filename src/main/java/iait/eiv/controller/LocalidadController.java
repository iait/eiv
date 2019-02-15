package iait.eiv.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iait.eiv.entity.Localidad;
import iait.eiv.error.EntityNotFoundException;
import iait.eiv.repository.LocalidadRepository;

@RestController
@RequestMapping(path="/eiv/localidades")
public class LocalidadController {
    
    @Autowired
    private LocalidadRepository localidadRepository;

    @GetMapping(path="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Localidad> getLocalidad(@PathVariable Integer id) {
        Optional<Localidad> localidadOp = localidadRepository.findById(id);
        if (localidadOp.isPresent()) {
            return new ResponseEntity<>(localidadOp.get(), HttpStatus.OK);
        } else {
            throw new EntityNotFoundException("localidad");
        }
    }

    @GetMapping(path="/all", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Localidad>> getAllLocalidades() {
        Iterable<Localidad> localidades = localidadRepository.findAll();
        return new ResponseEntity<>(localidades, HttpStatus.OK);
    }

    @PostMapping(path="", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Localidad> createLocalidad(@RequestBody Localidad localidad) {
        Localidad localidadResponse = localidadRepository.save(localidad);
        return new ResponseEntity<>(localidadResponse, HttpStatus.OK);
    }

    @PutMapping(path="/{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Localidad> updateLocalidad(@PathVariable Integer id, @RequestBody Localidad localidadInput) {
        Optional<Localidad> localidadOp = localidadRepository.findById(id);
        if (localidadOp.isPresent()) {
            Localidad localidad = localidadOp.get();
            localidad.update(localidadInput);
            Localidad localidadResponse = localidadRepository.save(localidad);
            return new ResponseEntity<>(localidadResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(path="/{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Localidad> partiallyUpdateLocalidad(@PathVariable Integer id, @RequestBody Localidad localidadInput) {
        Optional<Localidad> localidadOp = localidadRepository.findById(id);
        if (localidadOp.isPresent()) {
            Localidad localidad = localidadOp.get();
            localidad.partiallyUpdate(localidadInput);
            Localidad localidadResponse = localidadRepository.save(localidad);
            return new ResponseEntity<>(localidadResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Localidad> deleteLocalidad(@PathVariable Integer id) {
        Optional<Localidad> localidadOp = localidadRepository.findById(id);
        if (localidadOp.isPresent()) {
            localidadRepository.deleteById(id);
            return new ResponseEntity<>(localidadOp.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}