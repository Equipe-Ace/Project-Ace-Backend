package com.api3Dsm.domain.controle;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api3Dsm.domain.modelo.AuthenticationRequest;
import com.api3Dsm.domain.modelo.AuthenticationResponse;
import com.api3Dsm.domain.modelo.RegisterRequest;
import com.api3Dsm.domain.servico.AutenticacaoServico;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("login")
@RequiredArgsConstructor
public class AutenticacaoControle {
    
    private final AutenticacaoServico autenticacaoServico;

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<AuthenticationResponse> cadastrar(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(autenticacaoServico.register(request));
    }

    @PostMapping("/autenticado")
    public ResponseEntity<AuthenticationResponse> autenticar(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(autenticacaoServico.authenticate(request));
    }
}
