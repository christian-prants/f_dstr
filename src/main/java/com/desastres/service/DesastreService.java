package com.desastres.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.desastres.model.Desastre;
import com.desastres.repository.DesastreRepository;
import java.util.Optional;

@Service
public class DesastreService {

    @Autowired
    private DesastreRepository desastreRepository;

    public Desastre findById(Long id) {
        Optional<Desastre> desastre = desastreRepository.findById(id);
        return desastre.orElse(null);
    }

    public Desastre createDesastre(Desastre desastre) {
        Desastre novoDesastre = new Desastre();
        novoDesastre.setTipo(desastre.getTipo());
        novoDesastre.setData(desastre.getData());
        novoDesastre.setIntensidade(desastre.getIntensidade());
        novoDesastre.setDuracao(desastre.getDuracao());
        novoDesastre.setLocalizacaoId(desastre.getLocalizacaoId());
        novoDesastre.setUsuarioId(desastre.getUsuarioId());
        return desastreRepository.save(novoDesastre);
    }

    public Desastre updateDesastre(Long id, Desastre desastre) {
        Desastre desastreExistente = findById(id);
        if (desastreExistente != null) {
        	desastreExistente.setTipo(desastre.getTipo());
        	desastreExistente.setData(desastre.getData());
        	desastreExistente.setIntensidade(desastre.getIntensidade());
        	desastreExistente.setDuracao(desastre.getDuracao());
        	desastreExistente.setLocalizacaoId(desastre.getLocalizacaoId());
        	desastreExistente.setUsuarioId(desastre.getUsuarioId());
            return desastreRepository.save(desastreExistente);
        }
        return null;
    }

    public boolean deleteDesastre(Long id) {
        if (desastreRepository.existsById(id)) {
            desastreRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
