package com.projeto.gerenciadordetarefas.controller;

import com.projeto.gerenciadordetarefas.domain.usuario.UsuarioRequestDto;
import com.projeto.gerenciadordetarefas.infra.JwtService;
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
    private final JwtService jwtService;

    public AutenticacaoController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody UsuarioRequestDto usuarioRequestDto){

        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(
                        usuarioRequestDto.email(),
                        usuarioRequestDto.senha()
                );

        Authentication authenticationResponse =
                this.authenticationManager.authenticate(authenticationRequest);

        String token = jwtService.gerarToken(authenticationResponse.getName());

        return ResponseEntity.ok(token);
    }


}
