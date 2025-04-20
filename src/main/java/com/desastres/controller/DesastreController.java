package com.desastres.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desastres.model.Desastre;
import com.desastres.service.DesastreService;


@SpringBootApplication
@EntityScan(basePackages = "com.desastres.model")
@RestController
@RequestMapping("/desastres")
public class DesastreController {
	@Autowired
	private DesastreService DesastreService;
    
    @GetMapping("/{id}")
    public ResponseEntity<Desastre> getDesastreById(@PathVariable Long id) throws Exception {
        Desastre desastre = DesastreService.findById(id);
        if (desastre == null) {
            throw new Exception("Desastre não encontrado com ID: " + id);
        }
        return ResponseEntity.ok(desastre);
    }
    
    @PostMapping
    public ResponseEntity<Desastre> createDesastre(@Valid @RequestBody Desastre desastre) {
        Desastre novoDesastre = DesastreService.createDesastre(desastre);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoDesastre);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Desastre> updateDesastre(@PathVariable Long id, @Valid @RequestBody Desastre desastre) throws Exception {
        Desastre atualizadoDesastre = DesastreService.updateDesastre(id, desastre);
        if (atualizadoDesastre == null) {
            throw new Exception("Desastre não encontrado com ID: " + id);
        }
        return ResponseEntity.ok(atualizadoDesastre);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDesastre(@PathVariable Long id) throws Exception {
        if (!DesastreService.deleteDesastre(id)) {
            throw new Exception("Desastre não encontrado com ID: " + id);
        }
        return ResponseEntity.noContent().build();
    }

}
