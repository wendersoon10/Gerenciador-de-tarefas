package com.projeto.gerenciadordetarefas.controller;

import com.projeto.gerenciadordetarefas.domain.Status;
import com.projeto.gerenciadordetarefas.domain.tarefa.TarefaRequestDTO;
import com.projeto.gerenciadordetarefas.domain.tarefa.TarefaResponseDTO;
import com.projeto.gerenciadordetarefas.service.TarefaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @PostMapping
    public ResponseEntity<TarefaResponseDTO> criar(@RequestBody TarefaRequestDTO dadosTarefa){
        var tarefa = tarefaService.cadastrar(dadosTarefa);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tarefa);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaResponseDTO> buscarPorID(@PathVariable Long id){
        var buscarId = tarefaService.buscarPorID(id);

        return ResponseEntity.ok(buscarId);
    }

    @GetMapping
    public ResponseEntity<Page<TarefaResponseDTO>> buscarPorUsuario(
            Pageable pageable
    ) {
        var tarefas = tarefaService.buscarPorUsuario(pageable);

        return ResponseEntity.ok(tarefas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaResponseDTO> atualizarTarefa(@RequestBody TarefaRequestDTO tarefa, @PathVariable Long id){
        var atualizarTarefa = tarefaService.atualizar(tarefa, id);

        return ResponseEntity.ok(atualizarTarefa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id){
        tarefaService.deletar(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TarefaResponseDTO> alterarStatus(@PathVariable Long id, @RequestBody Status status){
        var tarefa = tarefaService.alterarStatus(id, status);

        return ResponseEntity.ok(tarefa);
    }
}
