package com.gianmarques001.biblioteca_api.livro.controller;

import com.gianmarques001.biblioteca_api.common.model.MensagemDeErro;
import com.gianmarques001.biblioteca_api.livro.dto.LivroDetailsResponseDTO;
import com.gianmarques001.biblioteca_api.livro.dto.LivroRequestDTO;
import com.gianmarques001.biblioteca_api.livro.dto.LivroResponseDTO;
import com.gianmarques001.biblioteca_api.livro.entity.Livro;
import com.gianmarques001.biblioteca_api.livro.entity.enums.GeneroLivro;
import com.gianmarques001.biblioteca_api.livro.mapper.LivroMapper;
import com.gianmarques001.biblioteca_api.livro.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/livros")
@Tag(name = "Livros", description = "Controller para gerenciar livros.")
public class LivroController {

    private final LivroService livroService;
    private final LivroMapper livroMapper;

    public LivroController(LivroService livroService, LivroMapper livroMapper) {
        this.livroService = livroService;
        this.livroMapper = livroMapper;
    }

    @Operation(summary = "Salvar livro", description = "Endpoint para adicionar um livro. (Apenas para gerentes)",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Livro salvo.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = LivroDetailsResponseDTO.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso proibido.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemDeErro.class))),
                    @ApiResponse(responseCode = "409", description = "Conflito de dados.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemDeErro.class))),
                    @ApiResponse(responseCode = "409", description = "Campos inválidos.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemDeErro.class))),
            })
    @PreAuthorize("hasRole('GERENTE')")
    @PostMapping
    public ResponseEntity<LivroDetailsResponseDTO> salvar(@Valid @RequestBody LivroRequestDTO livroRequestDto) {
        Livro livro = livroService.salvarLivro(livroMapper.toEntity(livroRequestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(livroMapper.toDTO(livro));
    }


    @Operation(summary = "Listar livros", description = "Endpoint para listar livros do sistema.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de livros de forma paginada.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = LivroResponseDTO.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso Proibido.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemDeErro.class))),

            })
    @GetMapping
    public ResponseEntity<PagedModel<LivroResponseDTO>> listar(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) GeneroLivro genero,
            @RequestParam(defaultValue = "0") Integer pagina,
            @RequestParam(defaultValue = "10") Integer tam_pagina) {

        Page<Livro> livros = livroService.listarLivros(titulo, autor, genero, pagina, tam_pagina);

        PagedModel<LivroResponseDTO> resultado = new PagedModel<>(livros.map(livroMapper::toPesquisaDTO));
        return ResponseEntity.ok(resultado);


    }
}
