package com.desastres.controller;

import javax.validation.Valid;
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

import com.desastres.model.Localizacao;
import com.desastres.service.LocalizacaoService;

@SpringBootApplication
@EntityScan(basePackages = "com.desastres.model")
@RestController
@RequestMapping("/localizacoes")
public class LocalizacaoController {

    private final LocalizacaoService localizacaoService;

    // Injetando o serviço de localizações
    public LocalizacaoController(LocalizacaoService localizacaoService) {
        this.localizacaoService = localizacaoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Localizacao> getLocalizacaoById(@PathVariable Long id) throws Exception {
        Localizacao localizacao = localizacaoService.findById(id);
        if (localizacao == null) {
            throw new Exception("Localização não encontrada com ID: " + id);
        }
        return ResponseEntity.ok(localizacao);
    }

    @PostMapping
    public ResponseEntity<Localizacao> createLocalizacao(@Valid @RequestBody Localizacao localizacao) {
        Localizacao novaLocalizacao = localizacaoService.createLocalizacao(localizacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaLocalizacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Localizacao> updateLocalizacao(@PathVariable Long id, @Valid @RequestBody Localizacao localizacao) throws Exception {
        Localizacao localizacaoAtualizada = localizacaoService.updateLocalizacao(id, localizacao);
        if (localizacaoAtualizada == null) {
            throw new Exception("Localização não encontrada com ID: " + id);
        }
        return ResponseEntity.ok(localizacaoAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocalizacao(@PathVariable Long id) throws Exception {
        if (!localizacaoService.deleteLocalizacao(id)) {
            throw new Exception("Localização não encontrada com ID: " + id);
        }
        return ResponseEntity.noContent().build();
    }
}
