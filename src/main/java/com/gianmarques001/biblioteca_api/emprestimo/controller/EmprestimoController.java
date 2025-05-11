package com.gianmarques001.biblioteca_api.emprestimo.controller;

import com.gianmarques001.biblioteca_api.auth.entity.AuthDetails;
import com.gianmarques001.biblioteca_api.common.model.MensagemDeErro;
import com.gianmarques001.biblioteca_api.emprestimo.dto.*;
import com.gianmarques001.biblioteca_api.emprestimo.entity.Emprestimo;
import com.gianmarques001.biblioteca_api.emprestimo.mapper.EmprestimoMapper;
import com.gianmarques001.biblioteca_api.emprestimo.service.EmprestimoService;
import com.gianmarques001.biblioteca_api.livro.entity.Livro;
import com.gianmarques001.biblioteca_api.livro.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emprestimos")
@Tag(name = "Emprestimo", description = "Controller para realizar empréstimos.")
public class EmprestimoController {


    private final EmprestimoService emprestimoService;
    private final LivroService livroService;
    private final EmprestimoMapper emprestimoMapper;

    public EmprestimoController(EmprestimoService emprestimoService, LivroService livroService, EmprestimoMapper emprestimoMapper) {
        this.emprestimoService = emprestimoService;
        this.livroService = livroService;
        this.emprestimoMapper = emprestimoMapper;
    }


    @PreAuthorize("hasAnyRole('ALUNO', 'PROFESSOR')")
    @PostMapping
    public ResponseEntity<EmprestimoResponseDTO> salvar(@AuthenticationPrincipal AuthDetails authDetails, @RequestBody EmprestimoRequestDTO emprestimoRequestDTO) {
        Livro livro = livroService.buscarLivroPorId(emprestimoRequestDTO.livro());
        Emprestimo emprestimo = emprestimoService.realizarEmprestimo(livro, authDetails.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(emprestimoMapper.toResponseDTO(emprestimo));
    }


    @Operation(summary = "Encerrar empréstimo", description = "Endpoint para encerrar o empréstimo.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Empréstimo  de empréstimos.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EmprestimoListResponseDTO.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso proibido.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemDeErro.class))),
                    @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemDeErro.class))),
            })
    @PatchMapping("/devolver/{id}")
    public ResponseEntity<EmprestimoEncerradoResponseDTO> atualizar(@PathVariable Long id) {
        Emprestimo emprestimo = emprestimoService.buscarEmprestimoPorId(id);
        Double taxa = emprestimoService.encerrarEmprestimo(emprestimo);
        return ResponseEntity.ok(new EmprestimoEncerradoResponseDTO("Emprestimo Encerrado", taxa));
    }

    @Operation(summary = "Listar empréstimos", description = "Endpoint para listar os empréstimos do sistema. (Apenas para gerentes)",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de empréstimos.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EmprestimoListResponseDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Acesso não autorizado.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemDeErro.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso proibido.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemDeErro.class))),
            })
    @PreAuthorize("hasRole('GERENTE')")
    @GetMapping
    public ResponseEntity<List<EmprestimoListResponseDTO>> listarEmprestimos() {
        List<Emprestimo> emprestimos = emprestimoService.listarEmprestimos();
        return ResponseEntity.ok(emprestimos.stream().map(emprestimoMapper::toEmprestimoListResponseDTO).toList());
    }

    @Operation(summary = "Listar empréstimos do usuário", description = "Endpoint para listar os empréstimos do usuário",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista os empréstimos de forma paginada do usuário.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EmprestimoListResponseDTO.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso proibido.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemDeErro.class))),
            })
    @GetMapping("/meus")
    public ResponseEntity<PagedModel<EmprestimoDetailsResponseDTO>> listarEmprestimosPorUsuario(@AuthenticationPrincipal AuthDetails authDetails,
                                                                                                @RequestParam(defaultValue = "0") Integer pagina,
                                                                                                @RequestParam(defaultValue = "5") Integer tam_pagina) {
        Page<Emprestimo> emprestimos = emprestimoService.listarEmprestimosPorUsuario(authDetails.getId(), pagina, tam_pagina);
        PagedModel<EmprestimoDetailsResponseDTO> resultado = new PagedModel<>(emprestimos.map(emprestimoMapper::toEmprestimoDetailsResponseDTO));
        return ResponseEntity.ok(resultado);

    }

}
