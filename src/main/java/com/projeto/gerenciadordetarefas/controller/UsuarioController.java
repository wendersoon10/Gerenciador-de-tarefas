package com.projeto.gerenciadordetarefas.controller;

import com.projeto.gerenciadordetarefas.domain.usuario.UsuarioRequestDto;
import com.projeto.gerenciadordetarefas.domain.usuario.UsuarioResponseDto;
import com.projeto.gerenciadordetarefas.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> cadastrar(@Valid @RequestBody UsuarioRequestDto dadosUser){
        var usuario = usuarioService.cadastrar(dadosUser);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> buscarPorId(@PathVariable Long id){
        var buscarUsuario = usuarioService.buscarPorId(id);

        return ResponseEntity.ok(buscarUsuario);
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioResponseDto>> listarTodos(Pageable pageable){
        var listarTodos = usuarioService.buscarTodos(pageable);

        return ResponseEntity.ok(listarTodos);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletar(){
            usuarioService.deletar();

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<UsuarioResponseDto> editarUser(@RequestBody UsuarioRequestDto user){
            var buscarUser = usuarioService.editar(user);

        return ResponseEntity.ok(buscarUser);
    }


}
