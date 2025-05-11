package com.gianmarques001.biblioteca_api.usuario.controller;


import com.gianmarques001.biblioteca_api.auth.entity.AuthDetails;
import com.gianmarques001.biblioteca_api.common.model.MensagemDeErro;
import com.gianmarques001.biblioteca_api.usuario.dto.UsuarioDetailsResponseDTO;
import com.gianmarques001.biblioteca_api.usuario.dto.UsuarioRequestDTO;
import com.gianmarques001.biblioteca_api.usuario.dto.UsuarioResponseDTO;
import com.gianmarques001.biblioteca_api.usuario.dto.UsuarioUpdateRequestDTO;
import com.gianmarques001.biblioteca_api.usuario.entity.Usuario;
import com.gianmarques001.biblioteca_api.usuario.mapper.UsuarioMapper;
import com.gianmarques001.biblioteca_api.usuario.repository.projection.UsuarioDetailsProjection;
import com.gianmarques001.biblioteca_api.usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuários", description = "Controller para gerenciar usuários. (Apenas para gerentes)")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    public UsuarioController(UsuarioService usuarioService, UsuarioMapper usuarioMapper) {
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
    }

    @Operation(summary = "Salvar usuário", description = "Endpoint para salvar um usuário.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Salva um usuário.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso proibido.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemDeErro.class))),
                    @ApiResponse(responseCode = "409", description = "Conflito de dados.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemDeErro.class))),
                    @ApiResponse(responseCode = "422", description = "Campos inválidos.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemDeErro.class))),
            })
    @PreAuthorize("hasRole('GERENTE')")
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> salvar(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        Usuario usuario = usuarioService.salvarUsuario(usuarioMapper.toEntiy(usuarioRequestDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.toDto(usuario));
    }


    @Operation(summary = "Listar usuários", description = "Endpoint para listar os usuários.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de usuários.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso proibido.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemDeErro.class))),
            })
    @PreAuthorize("hasRole('GERENTE')")
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios.stream().map(usuarioMapper::toDto).toList());
    }

    @Operation(summary = "Buscar usuário", description = "Endpoint para buscar um usuário pelo ID.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Retorna o usuário.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso proibido.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemDeErro.class))),
                    @ApiResponse(responseCode = "404", description = "Usuárion não encontrado.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemDeErro.class))),
            })
    @PreAuthorize("hasRole('GERENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDetailsResponseDTO> buscar(@PathVariable Long id) {
        UsuarioDetailsProjection usuario = usuarioService.buscarUsuario(id);
        return ResponseEntity.ok(usuarioMapper.toDetailsDTO(usuario));
    }


    @Operation(summary = "Atualizar usuário", description = "Endpoint para atualizar o usuário autenticado do sistema.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Alteração realizada."),
                    @ApiResponse(responseCode = "422", description = "Campos inválidos.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemDeErro.class))),
            })
    @PatchMapping
    public ResponseEntity<Void> atualizar(@AuthenticationPrincipal AuthDetails authDetails, @RequestBody UsuarioUpdateRequestDTO usuarioUpdateRequestDTO) {
        usuarioService.atualizarUsuario(authDetails.getId(), usuarioUpdateRequestDTO.senha(), usuarioUpdateRequestDTO.novaSenha());
        return ResponseEntity.noContent().build();
    }
}
