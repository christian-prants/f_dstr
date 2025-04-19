package com.desastres.service;

import com.desastres.model.Usuario;
import com.desastres.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UsuarioRepository UsuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario authenticate(String email, String senha) {
    	Usuario usuario = UsuarioRepository.findByEmail(email);
        if (usuario != null && passwordEncoder.matches(senha, usuario.getSenha())) {
            return usuario;
        }
        return null;
    }
    
    public Usuario createUser(String nome, String email, String senha, String telefone) {
        if (UsuarioRepository.findByEmail(email) != null) {
            throw new RuntimeException("Email já existe");
        }

        Usuario novoUsuario = new Usuario(nome, email, senha, telefone);
        novoUsuario.setNome(nome);
        novoUsuario.setEmail(email);
        novoUsuario.setTelefone(telefone);
        novoUsuario.setSenha(passwordEncoder.encode(senha));

        return UsuarioRepository.save(novoUsuario);
    }
}
