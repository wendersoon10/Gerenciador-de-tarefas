
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

        // Prepara os dados utilizados no cadastro.
        String nome = "nome";
        String emailNovo = "novo_usuario@gmail.com";
        String senha = "123456";

        // Cria o DTO com os dados recebidos para o cadastro.
        UsuarioRequestDto usuarioRequestDto =
                new UsuarioRequestDto(nome, emailNovo, senha);

        // Simula que não existe usuário cadastrado com esse e-mail.
        Mockito.when(usuarioRepository.findByEmail(emailNovo))
                .thenReturn(Optional.empty());

        // Simula a criptografia da senha.
        Mockito.when(passwordEncoder.encode(senha))
                .thenReturn("senha_criptografada");

        // Executa o método de cadastro do serviço.
        UsuarioResponseDto resultado =
                usuarioService.cadastrar(usuarioRequestDto);

        // Verifica os dados retornados pelo cadastro.
        assertEquals(nome, resultado.nome());
        assertEquals(emailNovo, resultado.email());
    }


    // CENÁRIO 02: tentar cadastrar um usuário com e-mail já existente.
    @Test
    void cenario02() {

        // Prepara os dados utilizados no cadastro.
        String nome = "nome teste";
        String email = "nometeste@gmail.com";
        String senha = "senha-teste";

        // Cria o DTO com os dados recebidos para o cadastro.
        UsuarioRequestDto usuarioRequestDto =
                new UsuarioRequestDto(nome, email, senha);

        // Cria uma entidade para representar o usuário já cadastrado.
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setNome(nome);
        usuarioExistente.setEmail(email);
        usuarioExistente.setSenha("senha-criptografada");

        // Simula que o repositório encontrou o e-mail cadastrado.
        Mockito.when(usuarioRepository.findByEmail(email))
                .thenReturn(Optional.of(usuarioExistente));

        // Verifica se o cadastro lança uma exceção.
        RuntimeException excecao = assertThrows(
                RuntimeException.class,
                () -> usuarioService.cadastrar(usuarioRequestDto)
        );

        // Verifica a mensagem da exceção.
        assertEquals(
                "Não é possível cadastrar com um email existente",
                excecao.getMessage()
        );
    }


    // CENÁRIO 03: buscar um usuário existente pelo ID.
    @Test
    void cenario03() {

        // Define os dados do usuário que será encontrado.
        Long idUsuario = 7L;
        String nome = "usuario";
        String email = "usuario@testegmail.com";
        String senha = "senha_criptografada";

        // Cria uma entidade para representar o usuário existente.
        Usuario usuario = new Usuario();

        // Preenche a entidade com os dados necessários.
        usuario.setId(idUsuario);
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        // Simula que o repositório encontrou o usuário pelo ID.
        Mockito.when(usuarioRepository.findById(idUsuario))
                .thenReturn(Optional.of(usuario));

        // Executa a busca do usuário pelo ID.
        UsuarioResponseDto resultado =
                usuarioService.buscarPorId(idUsuario);

        // Verifica os dados retornados pela busca.
        assertEquals(nome, resultado.nome());
        assertEquals(email, resultado.email());
    }


    // CENÁRIO 04: tentar buscar um usuário inexistente pelo ID.
    @Test
    void cenario04() {

        // Define o ID do usuário que não será encontrado.
        Long idUsuario = 7L;

        // Simula que o repositório não encontrou o usuário.
        Mockito.when(usuarioRepository.findById(idUsuario))
                .thenReturn(Optional.empty());

        // Verifica se a busca lança uma exceção.
        RuntimeException excecao = assertThrows(
                RuntimeException.class,
                () -> usuarioService.buscarPorId(idUsuario)
        );

        // Verifica a mensagem da exceção.
        assertEquals(
                "Id de usuário inexistente",
                excecao.getMessage()
        );
    }
}
