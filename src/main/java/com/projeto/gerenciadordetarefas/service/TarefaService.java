package com.projeto.gerenciadordetarefas.service;

import com.projeto.gerenciadordetarefas.domain.Status;
import com.projeto.gerenciadordetarefas.domain.tarefa.Tarefa;
import com.projeto.gerenciadordetarefas.domain.tarefa.TarefaRequestDTO;
import com.projeto.gerenciadordetarefas.domain.tarefa.TarefaResponseDTO;
import com.projeto.gerenciadordetarefas.repository.TarefaRepository;
import com.projeto.gerenciadordetarefas.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final UsuarioRepository usuarioRepository;

    public TarefaService(
            TarefaRepository tarefaRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.tarefaRepository = tarefaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // CRIAR TAREFA
    public TarefaResponseDTO cadastrar(TarefaRequestDTO dadosDTO) {

        var usuario = usuarioRepository.findById(dadosDTO.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Tarefa tarefa = new Tarefa();

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

        return converterParaDTO(tarefa);
    }

    public Page<TarefaResponseDTO> buscarTodos(Pageable pageable){
        return tarefaRepository.findAll(pageable)
                .map(this::converterParaDTO);

    }

    // BUSCA TODAS AS TAREFAS DE UM USUÁRIO COM PAGINAÇÃO
    public Page<TarefaResponseDTO> buscarPorUsuario(Long id, Pageable pageable){
        return tarefaRepository.findByUsuarioId(id,pageable)
            .map(this::converterParaDTO);
    }

    // ATUALIZAR TAREFA
    public TarefaResponseDTO atualizar(TarefaRequestDTO tarefa, Long id) {

        Tarefa novaTarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        novaTarefa.setTitulo(tarefa.tarefa());
        novaTarefa.setDescricao(tarefa.descricao());
        novaTarefa.setPrioridade(tarefa.prioridade());
        novaTarefa.setDataVencimento(tarefa.dataVencimento());

        tarefaRepository.save(novaTarefa);

        return converterParaDTO(novaTarefa);

    }

    @Transactional
    public void deletar(Long id){
            tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        tarefaRepository.deleteById(id);
    }

    //ATUALIZA APENAS UM STATUS DE UMA TAREFA
    public TarefaResponseDTO alterarStatus(Long id, Status status){
        var tarefa = tarefaRepository.findById(id).
            orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        tarefa.setStatus(status);

        tarefaRepository.save(tarefa);

        return converterParaDTO(tarefa);
    }

    //METODO PRIVADO DECONVERSAO PARA DTO
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