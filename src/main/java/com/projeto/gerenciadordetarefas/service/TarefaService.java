package com.projeto.gerenciadordetarefas.service;

import com.projeto.gerenciadordetarefas.domain.Status;
import com.projeto.gerenciadordetarefas.domain.tarefa.Tarefa;
import com.projeto.gerenciadordetarefas.domain.tarefa.TarefaRequestDTO;
import com.projeto.gerenciadordetarefas.domain.tarefa.TarefaResponseDTO;
import com.projeto.gerenciadordetarefas.domain.usuario.Usuario;
import com.projeto.gerenciadordetarefas.exception.AcessoNegadoException;
import com.projeto.gerenciadordetarefas.infra.AuthenticationService;
import com.projeto.gerenciadordetarefas.repository.TarefaRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import org.springframework.stereotype.Service;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final AuthenticationService authenticationService;

    public TarefaService(TarefaRepository tarefaRepository, AuthenticationService authenticationService) {
        this.tarefaRepository = tarefaRepository;
        this.authenticationService = authenticationService;
    }

    // CRIAR TAREFA
    public TarefaResponseDTO cadastrar(TarefaRequestDTO dadosDTO) {

        Tarefa tarefa = new Tarefa();

        Usuario usuario =
                authenticationService.obterUsuarioAutenticado();



        tarefa.setTitulo(dadosDTO.tarefa());
        tarefa.setDescricao(dadosDTO.descricao());
        tarefa.setPrioridade(dadosDTO.prioridade());
        tarefa.setStatus(Status.PENDENTE);
        tarefa.setUsuario(usuario);
        tarefa.setDataVencimento(dadosDTO.dataVencimento());

        tarefaRepository.save(tarefa);

        return converterParaDTO(tarefa);
    }

    // BUSCAR TAREFA POR ID
    public TarefaResponseDTO buscarPorID(Long id) {

        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        Usuario usuario =
                authenticationService.obterUsuarioAutenticado();

        if(!tarefa.getUsuario().equals(usuario)){
            throw new AcessoNegadoException("Acesso negado");
        }
        return converterParaDTO(tarefa);
    }


    public Page<TarefaResponseDTO> buscarPorUsuario(Pageable pageable){

        Usuario usuario =
                authenticationService.obterUsuarioAutenticado();

        return tarefaRepository.findByUsuarioId(usuario.getId(),pageable)
            .map(this::converterParaDTO);
    }

    // ATUALIZAR TAREFA
    public TarefaResponseDTO atualizar(TarefaRequestDTO tarefa, Long id) {

        Tarefa novaTarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        Usuario usuario =
                authenticationService.obterUsuarioAutenticado();

        if(!novaTarefa.getUsuario().equals(usuario)){
            throw new AcessoNegadoException("Acesso negado");
        }

        novaTarefa.setTitulo(tarefa.tarefa());
        novaTarefa.setDescricao(tarefa.descricao());
        novaTarefa.setPrioridade(tarefa.prioridade());
        novaTarefa.setDataVencimento(tarefa.dataVencimento());

        tarefaRepository.save(novaTarefa);

        return converterParaDTO(novaTarefa);

    }

    @Transactional
    public void deletar(Long id){
           Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

            Usuario usuario =
                    authenticationService.obterUsuarioAutenticado();

            if(!tarefa.getUsuario().equals(usuario)){
                throw new AcessoNegadoException("Acesso negado");
            }

        tarefaRepository.deleteById(id);
    }

    //ATUALIZA APENAS UM STATUS DE UMA TAREFA
    public TarefaResponseDTO alterarStatus(Long id, Status status){
        var tarefa = tarefaRepository.findById(id).
            orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        Usuario usuario =
                authenticationService.obterUsuarioAutenticado();

        if(!tarefa.getUsuario().equals(usuario)){
            throw new AcessoNegadoException("Acesso negado");
        }

        tarefa.setStatus(status);

        tarefaRepository.save(tarefa);

        return converterParaDTO(tarefa);
    }

    //METODO PRIVADO DE CONVERSAO PARA DTO
    private TarefaResponseDTO converterParaDTO(Tarefa tarefa) {
        return new TarefaResponseDTO(
                tarefa.getId(),
                tarefa.getTitulo(),
                tarefa.getDescricao(),
                tarefa.getPrioridade(),
                tarefa.getDataVencimento(),
                tarefa.getStatus(),
                tarefa.getCriadoAt(),
                tarefa.getAtualizadoAt()
        );
    }

}