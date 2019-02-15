package iait.eiv.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
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

import iait.eiv.entity.Localidad;
import iait.eiv.entity.Persona;
import iait.eiv.entity.PersonaPK;
import iait.eiv.entity.TipoDocumento;
import iait.eiv.error.EntityNotFoundException;
import iait.eiv.error.InvalidInputException;
import iait.eiv.repository.LocalidadRepository;
import iait.eiv.repository.PersonaRepository;
import iait.eiv.repository.TipoDocumentoRepository;

@RestController
@RequestMapping(path="/eiv/personas")
public class PersonaController {
    
    @Autowired
    private PersonaRepository personaRepository;
    
    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    @Autowired
    private LocalidadRepository localidadRepository;

    @GetMapping(path="", produces=MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping(path="/all", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Persona>> getAllPersonas() {
        Iterable<Persona> personas = personaRepository.findAll();
        return new ResponseEntity<>(personas, HttpStatus.OK);
    }

    private void fillCodigoPostal(Persona persona) {
        if (persona.getCodigoPostal() == null && persona.getLocalidad() != null) {
            Optional<Localidad> localidadOp = localidadRepository.findById(persona.getLocalidad());
            if (localidadOp.isPresent()) {
                persona.setCodigoPostal(localidadOp.get().getCodigoPostal());
            }
        }
    }

    private void validatePersona(Persona persona) {
        if (!Arrays.asList(null, "M", "F").contains(persona.getGenero())) {
            throw new InvalidInputException("El género debe ser M o F. Valor: " + persona.getGenero());
        }
    }

    @PostMapping(path="", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Persona> createPersona(@RequestBody Persona persona) {
        validatePersona(persona);
        fillCodigoPostal(persona);
        Persona personaResponse = personaRepository.save(persona);
        return new ResponseEntity<>(personaResponse, HttpStatus.OK);
    }

    @PutMapping(path="", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Persona> updatePersona(@RequestParam("tipodoc") String tipoDoc,
            @RequestParam("numdoc") Integer numDoc, @RequestBody Persona personaInput) {
        validatePersona(personaInput);
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
                fillCodigoPostal(persona);
                Persona personaResponse = personaRepository.save(persona);
                return new ResponseEntity<>(personaResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(path="", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Persona> partiallyUpdatePersona(@RequestParam("tipodoc") String tipoDoc,
            @RequestParam("numdoc") Integer numDoc, @RequestBody Persona personaInput) {
        validatePersona(personaInput);
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

    private Blob convertStreamToBlob(InputStream dataStream) {
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int len;
            while ((len = dataStream.read(buffer)) > 0) {
                output.write(buffer, 0, len);
            }
            Blob blob = new SerialBlob(output.toByteArray());
            return blob;
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir el archivo", e);
        }
    }

    @PatchMapping(path="/foto", consumes=MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Persona> updateFotoCara(@RequestParam("tipodoc") String tipoDoc,
            @RequestParam("numdoc") Integer numDoc, InputStream dataStream) {
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
                Blob fotoCara = convertStreamToBlob(dataStream);
                persona.setFotoCara(fotoCara);
                personaRepository.save(persona);
                return new ResponseEntity<>(persona, HttpStatus.OK);
            } else {
                throw new EntityNotFoundException("persona");
            }
        } else {
            throw new EntityNotFoundException("tipo de documento");
        }
    }

    private ByteArrayResource convertBlobToResource(Blob blob) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[4096];
            InputStream is = blob.getBinaryStream();
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                baos.write(buffer, 0, len);
            }
            ByteArrayResource resource = new ByteArrayResource(baos.toByteArray());
            return resource;
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir el archivo", e);
        }
    }

    @GetMapping(path="/foto", produces=MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> getPersonaFoto(@RequestParam("tipodoc") String tipoDoc,
            @RequestParam("numdoc") Integer numDoc) throws SQLException, IOException {
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
                Blob fotoCara = personaOp.get().getFotoCara();
                Resource resource = convertBlobToResource(fotoCara);
                return new ResponseEntity<>(resource, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path="", produces=MediaType.APPLICATION_JSON_VALUE)
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