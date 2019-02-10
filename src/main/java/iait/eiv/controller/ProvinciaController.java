package iait.eiv.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import iait.eiv.entity.Provincia;
import iait.eiv.repository.ProvinciaRepository;

@RestController
@RequestMapping(path="/eiv/provincias")
public class ProvinciaController {
    
    @Autowired
    private ProvinciaRepository provinciaRepository;

    @GetMapping(path="/{id}", produces="application/json")
    public ResponseEntity<Provincia> getProvincia(@PathVariable Integer id) {
        Optional<Provincia> provinciaOp = provinciaRepository.findById(id);
        if (provinciaOp.isPresent()) {
            return new ResponseEntity<>(provinciaOp.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path="/all", produces="application/json")
    public ResponseEntity<Iterable<Provincia>> getAllProvincias() {
        Iterable<Provincia> provincias = provinciaRepository.findAll();
        return new ResponseEntity<>(provincias, HttpStatus.OK);
    }

    @PostMapping(path="", consumes="application/json")
    public ResponseEntity<Provincia> createProvincia(@RequestBody Provincia provincia) {
        Provincia provinciaResponse = provinciaRepository.save(provincia);
        return new ResponseEntity<>(provinciaResponse, HttpStatus.OK);
    }

    @PutMapping(path="/{id}", consumes="application/json")
    public ResponseEntity<Provincia> updateProvincia(@PathVariable Integer id, @RequestBody Provincia provinciaInput) {
        Optional<Provincia> provinciaOp = provinciaRepository.findById(id);
        if (provinciaOp.isPresent()) {
            Provincia provincia = provinciaOp.get();
            provincia.update(provinciaInput);
            Provincia provinciaResponse = provinciaRepository.save(provincia);
            return new ResponseEntity<>(provinciaResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(path="/{id}", consumes="application/json")
    public ResponseEntity<Provincia> partiallyUpdateProvincia(@PathVariable Integer id, @RequestBody Provincia provinciaInput) {
        Optional<Provincia> provinciaOp = provinciaRepository.findById(id);
        if (provinciaOp.isPresent()) {
            Provincia provincia = provinciaOp.get();
            provincia.partiallyUpdate(provinciaInput);
            Provincia provinciaResponse = provinciaRepository.save(provincia);
            return new ResponseEntity<>(provinciaResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path="/{id}", produces="application/json")
    public ResponseEntity<Provincia> deleteProvincia(@PathVariable Integer id) {
        Optional<Provincia> provinciaOp = provinciaRepository.findById(id);
        if (provinciaOp.isPresent()) {
            provinciaRepository.deleteById(id);
            return new ResponseEntity<>(provinciaOp.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}