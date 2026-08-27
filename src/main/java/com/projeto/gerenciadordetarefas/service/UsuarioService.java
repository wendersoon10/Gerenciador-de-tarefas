package com.projeto.gerenciadordetarefas.service;


import com.projeto.gerenciadordetarefas.domain.usuario.Usuario;
import com.projeto.gerenciadordetarefas.domain.usuario.UsuarioRequestDto;
import com.projeto.gerenciadordetarefas.domain.usuario.UsuarioResponseDto;
import com.projeto.gerenciadordetarefas.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //CRIA USUARIO
    public UsuarioResponseDto cadastrar(UsuarioRequestDto dados){
        Usuario usuario = new Usuario();

        usuario.setNome(dados.nome());
        usuario.setEmail(dados.email());
        usuario.setSenha(dados.senha());

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
    public void deletar(Long dadosId){
        var deleteId = usuarioRepository.findById(dadosId);

        if(deleteId.isEmpty()){
            throw new RuntimeException("Id de usuário não encontrado");
        }

        usuarioRepository.delete(deleteId.get());
    }

    //ATUALIZA
    public UsuarioResponseDto editar(UsuarioRequestDto user, Long idUser){
        var buscarUsuario = usuarioRepository.findById(idUser);

        if (buscarUsuario.isEmpty()){
          throw new RuntimeException("Usuário não encontrado");
      }
      Usuario usuario = buscarUsuario.get();

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
