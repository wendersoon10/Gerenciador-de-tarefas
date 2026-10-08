package com.projeto.gerenciadordetarefas.service;

import com.projeto.gerenciadordetarefas.domain.usuario.UsuarioRequestDto;
import com.projeto.gerenciadordetarefas.domain.usuario.UsuarioResponseDto;
import com.projeto.gerenciadordetarefas.infra.AuthenticationService;
import com.projeto.gerenciadordetarefas.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationService authenticationService;

    @Test
    void cenario01 (){
        String nome = "nome";
        String emailNovo = "novo_usuario@gmail.com";
        String senha = "123456";

        UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto(nome, emailNovo, senha);

        Mockito.when(usuarioRepository.findByEmail(emailNovo))
                .thenReturn(Optional.empty());

        Mockito.when(passwordEncoder.encode(senha))
                .thenReturn("senha_criptografada");

       UsuarioResponseDto resultado = usuarioService.cadastrar(usuarioRequestDto);

        assertEquals(nome, resultado.nome());
        assertEquals(emailNovo, resultado.email());
    }

}