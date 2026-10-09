
package com.projeto.gerenciadordetarefas.service;

import com.projeto.gerenciadordetarefas.domain.usuario.Usuario;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    // Cria uma instância real do UsuarioService e injeta os mocks necessários.
    @InjectMocks
    private UsuarioService usuarioService;

    // Simula o repositório, evitando acesso ao banco de dados.
    @Mock
    private UsuarioRepository usuarioRepository;

    // Simula o componente responsável por criptografar senhas.
    @Mock
    private PasswordEncoder passwordEncoder;

    // Simula o serviço responsável por recuperar o usuário autenticado.
    @Mock
    private AuthenticationService authenticationService;

    // CENÁRIO 01: cadastrar um usuário com e-mail disponível.
    @Test
    void cenario01() {
        // Prepara os dados que serão utilizados no cadastro.
        String nome = "nome";
        String emailNovo = "novo_usuario@gmail.com";
        String senha = "123456";

        // Cria o DTO que representa os dados recebidos para o cadastro.
        UsuarioRequestDto usuarioRequestDto =
                new UsuarioRequestDto(nome, emailNovo, senha);

        // Simula que não existe usuário cadastrado com esse e-mail.
        Mockito.when(usuarioRepository.findByEmail(emailNovo))
                .thenReturn(Optional.empty());

        // Simula o resultado da criptografia da senha.
        Mockito.when(passwordEncoder.encode(senha))
                .thenReturn("senha_criptografada");

        // Executa o método real de cadastro do serviço.
        UsuarioResponseDto resultado =
                usuarioService.cadastrar(usuarioRequestDto);

        // Verifica se o nome e o e-mail retornados são os esperados.
        assertEquals(nome, resultado.nome());
        assertEquals(emailNovo, resultado.email());
    }

    // CENÁRIO 02: buscar um usuário existente pelo ID.
    @Test
    void cenario02() {

        Long idUsuario = 7L;
        String nome = "usuario";
        String email = "usuario@testegmail.com";
        String senha = "senha_criptografada";

        // Cria uma entidade Usuario para simular um registro existente.
        Usuario usuario = new Usuario();

        // Preenche a entidade com os dados necessários para o teste.
        usuario.setId(idUsuario);
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);


        Mockito.when(usuarioRepository.findById(idUsuario))
                .thenReturn(Optional.of(usuario));


        UsuarioResponseDto resultado =
                usuarioService.buscarPorId(idUsuario);


        assertEquals(nome, resultado.nome());
        assertEquals(email, resultado.email());
    }

    // CENÁRIO 03: tentar buscar um usuário que não existe.
    @Test
    void cenario03() {

        Long idUsuario = 7L;

        // Simula que o repositório não encontrou nenhum usuário.
        Mockito.when(usuarioRepository.findById(idUsuario))
                .thenReturn(Optional.empty());

        // Verifica se o serviço lança a exceção esperada ao realizar a busca.
        RuntimeException excecao = assertThrows(
                RuntimeException.class,
                () -> usuarioService.buscarPorId(idUsuario)
        );

        // Verifica se a mensagem da exceção é a esperada.
        assertEquals("Id de usuário inexistente", excecao.getMessage());
    }
}