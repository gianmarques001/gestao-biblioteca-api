package com.gianmarques001.biblioteca_api.auth.controller;


import com.gianmarques001.biblioteca_api.auth.dto.AuthDTO;
import com.gianmarques001.biblioteca_api.auth.model.UserAuthDetails;
import com.gianmarques001.biblioteca_api.common.model.MensagemDeErro;
import com.gianmarques001.biblioteca_api.common.security.JwtToken;
import com.gianmarques001.biblioteca_api.common.security.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Controller para se autenticar.")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Operation(summary = "Autenticar um usuário", description = "Endpoint para autenticar um usuário no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sucesso na autenticação",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JwtToken.class))),
                    @ApiResponse(responseCode = "400", description = "Credenciais inválidas.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemDeErro.class))),
                    @ApiResponse(responseCode = "422", description = "Campos inválidos",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemDeErro.class))),
            })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthDTO authDTO, HttpServletRequest request) {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authDTO.email(), authDTO.senha());
            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            String token = JwtUtils.generateToken((UserAuthDetails) authenticate.getPrincipal());
            return ResponseEntity.ok(new JwtToken(token));
        } catch (AuthenticationException e) {
            log.warn("Error no ", e.getMessage());
        }
        return ResponseEntity.badRequest().body(new MensagemDeErro(request, HttpStatus.BAD_REQUEST, "Credenciais inválidas."));

    }

}
