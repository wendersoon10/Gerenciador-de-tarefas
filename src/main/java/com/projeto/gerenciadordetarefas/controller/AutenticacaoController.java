package com.projeto.gerenciadordetarefas.controller;

import com.projeto.gerenciadordetarefas.domain.usuario.UsuarioRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;

    public AutenticacaoController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login (@RequestBody UsuarioRequestDto usuarioRequestDto){
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(usuarioRequestDto.email(), usuarioRequestDto.senha());
        Authentication authenticationResponse =
                this.authenticationManager.authenticate(authenticationRequest);

        return ResponseEntity.ok().build();
    }


}
