package com.projeto.gerenciadordetarefas.service;


import com.projeto.gerenciadordetarefas.domain.usuario.Usuario;
import com.projeto.gerenciadordetarefas.domain.usuario.UsuarioRequestDto;
import com.projeto.gerenciadordetarefas.domain.usuario.UsuarioResponseDto;
import com.projeto.gerenciadordetarefas.exception.EmailExistenteException;
import com.projeto.gerenciadordetarefas.infra.AuthenticationService;
import com.projeto.gerenciadordetarefas.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, AuthenticationService authenticationService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationService = authenticationService;
    }

    //CRIA USUARIO
    public UsuarioResponseDto cadastrar(UsuarioRequestDto dados){

        if(usuarioRepository.findByEmail(dados.email()).isPresent()){
            throw new EmailExistenteException("Não é possível cadastrar com um email existente");
        }

        Usuario usuario = new Usuario();

        usuario.setNome(dados.nome());
        usuario.setEmail(dados.email());
        usuario.setSenha(passwordEncoder.encode(dados.senha()));

        // salvar no banco
        usuarioRepository.save(usuario);

        return converterParaDto(usuario);
    }

    //BUSCA POR ID
    public UsuarioResponseDto buscarPorId(Long dadosId) {

        var buscarId = usuarioRepository.findById(dadosId);

        if (buscarId.isEmpty()) {
            throw new RuntimeException("Id de usuário inexistente");
        }

        Usuario usuario = buscarId.get();

        return converterParaDto(usuario);
    }

    //BUSCA TODOS
    public Page<UsuarioResponseDto> buscarTodos(Pageable pageable){
        return usuarioRepository.findAll(pageable)
                .map(this::converterParaDto);
    }

    //DELETE
    public void deletar(){

        Usuario usuario =
                authenticationService.obterUsuarioAutenticado();

        usuarioRepository.delete(usuario);
    }

    //ATUALIZA
    public UsuarioResponseDto editar(UsuarioRequestDto user){

        Usuario usuario =
                authenticationService.obterUsuarioAutenticado();

      usuario.setNome(user.nome());
      usuario.setEmail(user.email());

      usuarioRepository.save(usuario);

      return converterParaDto(usuario);

    }

    //CONVERSAO PARA DTO
    private UsuarioResponseDto converterParaDto(Usuario usuario){
        return new UsuarioResponseDto(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCriadoAt(),
                usuario.getAtualizadoAt()
        );
    }

}
