package com.desastres.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.desastres.model.Localizacao;
import com.desastres.repository.LocalizacaoRepository;
import java.util.Optional;

@Service
public class LocalizacaoService {

    @Autowired
    private LocalizacaoRepository localizacaoRepository;
    
    public Localizacao findById(Long id) {
        Optional<Localizacao> localizacao = localizacaoRepository.findById(id);
        return localizacao.orElse(null);
    }
    
    public Localizacao createLocalizacao(Localizacao localizacao) {
    	Localizacao novaLocalizacao = new Localizacao(null, null, null, null);
    	novaLocalizacao.setTipo(localizacao.getTipo());
    	novaLocalizacao.setNome(localizacao.getNome());
    	novaLocalizacao.setLatitude(localizacao.getLatitude());
    	novaLocalizacao.setLongitude(localizacao.getLongitude());
        return localizacaoRepository.save(novaLocalizacao);
    }

    public Localizacao updateLocalizacao(Long id, Localizacao localizacao) {
    	Localizacao localizacaoExistente = findById(id);
        if (localizacaoExistente != null) {
        	localizacaoExistente.setTipo(localizacao.getTipo());
        	localizacaoExistente.setNome(localizacao.getNome());
        	localizacaoExistente.setLatitude(localizacao.getLatitude());
        	localizacaoExistente.setLongitude(localizacao.getLongitude());
            return localizacaoRepository.save(localizacaoExistente);
        }
        return null;
    }

	public boolean deleteLocalizacao(Long id) {
        if (localizacaoRepository.existsById(id)) {
        	localizacaoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
