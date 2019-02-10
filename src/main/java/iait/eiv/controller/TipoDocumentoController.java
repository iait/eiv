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

import iait.eiv.entity.TipoDocumento;
import iait.eiv.repository.TipoDocumentoRepository;

@RestController
@RequestMapping(path="/eiv/tiposdocumento")
public class TipoDocumentoController {
    
    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    @GetMapping(path="/{id}", produces="application/json")
    public ResponseEntity<TipoDocumento> getTipoDocumento(@PathVariable Integer id) {
        Optional<TipoDocumento> tipoDocOp = tipoDocumentoRepository.findById(id);
        if (tipoDocOp.isPresent()) {
            return new ResponseEntity<>(tipoDocOp.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path="/all", produces="application/json")
    public ResponseEntity<Iterable<TipoDocumento>> getAllTiposDocumento() {
        Iterable<TipoDocumento> tiposDoc = tipoDocumentoRepository.findAll();
        return new ResponseEntity<>(tiposDoc, HttpStatus.OK);
    }

    @PostMapping(path="", consumes="application/json")
    public ResponseEntity<TipoDocumento> createTipoDocumento(@RequestBody TipoDocumento tipoDoc) {
        TipoDocumento tipoDocResponse = tipoDocumentoRepository.save(tipoDoc);
        return new ResponseEntity<>(tipoDocResponse, HttpStatus.OK);
    }

    @PutMapping(path="/{id}", consumes="application/json")
    public ResponseEntity<TipoDocumento> updateTipoDoc(@PathVariable Integer id, @RequestBody TipoDocumento tipoDocInput) {
        Optional<TipoDocumento> tipoDocOp = tipoDocumentoRepository.findById(id);
        if (tipoDocOp.isPresent()) {
            TipoDocumento tipoDoc = tipoDocOp.get();
            tipoDoc.update(tipoDocInput);
            TipoDocumento tipoDocResponse = tipoDocumentoRepository.save(tipoDoc);
            return new ResponseEntity<>(tipoDocResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(path="/{id}", consumes="application/json")
    public ResponseEntity<TipoDocumento> partiallyUpdateTipoDoc(@PathVariable Integer id, @RequestBody TipoDocumento tipoDocInput) {
        Optional<TipoDocumento> tipoDocOp = tipoDocumentoRepository.findById(id);
        if (tipoDocOp.isPresent()) {
            TipoDocumento tipoDoc = tipoDocOp.get();
            tipoDoc.partiallyUpdate(tipoDocInput);
            TipoDocumento tipoDocResponse = tipoDocumentoRepository.save(tipoDoc);
            return new ResponseEntity<>(tipoDocResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path="/{id}", produces="application/json")
    public ResponseEntity<TipoDocumento> deleteProvincia(@PathVariable Integer id) {
        Optional<TipoDocumento> tipoDocOp = tipoDocumentoRepository.findById(id);
        if (tipoDocOp.isPresent()) {
            tipoDocumentoRepository.deleteById(id);
            return new ResponseEntity<>(tipoDocOp.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}