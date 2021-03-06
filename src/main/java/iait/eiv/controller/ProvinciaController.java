package iait.eiv.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import iait.eiv.entity.Provincia;
import iait.eiv.error.InvalidInputException;
import iait.eiv.repository.ProvinciaRepository;

@RestController
@RequestMapping(path="/eiv/provincias")
public class ProvinciaController {
    
    @Autowired
    private ProvinciaRepository provinciaRepository;

    @GetMapping(path="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Provincia> getProvincia(@PathVariable Integer id) {
        Optional<Provincia> provinciaOp = provinciaRepository.findById(id);
        if (provinciaOp.isPresent()) {
            return new ResponseEntity<>(provinciaOp.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path="/all", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Provincia>> getAllProvincias() {
        Iterable<Provincia> provincias = provinciaRepository.findAll();
        return new ResponseEntity<>(provincias, HttpStatus.OK);
    }

    private void validateProvincia(Provincia provincia) {
        List<String> regiones = Arrays.asList("NOA", "NEA", "CUY", "PAM", "GBA", "PAT");
        if (provincia.getRegion() != null && !regiones.contains(provincia.getRegion())) {
            String regionesStr = regiones.stream().collect(Collectors.joining(", "));
            throw new InvalidInputException("La región debe ser " + regionesStr + ". Valor: " + provincia.getRegion());
        }
    }

    @PostMapping(path="", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Provincia> createProvincia(@RequestBody Provincia provincia) {
        validateProvincia(provincia);
        Provincia provinciaResponse = provinciaRepository.save(provincia);
        return new ResponseEntity<>(provinciaResponse, HttpStatus.OK);
    }

    @PutMapping(path="/{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Provincia> updateProvincia(@PathVariable Integer id, @RequestBody Provincia provinciaInput) {
        validateProvincia(provinciaInput);
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

    @PatchMapping(path="/{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Provincia> partiallyUpdateProvincia(@PathVariable Integer id, @RequestBody Provincia provinciaInput) {
        validateProvincia(provinciaInput);
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

    @DeleteMapping(path="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
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