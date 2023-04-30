package com.api3Dsm.domain.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.api3Dsm.domain.modelo.AuthenticationRequest;
import com.api3Dsm.domain.modelo.AuthenticationResponse;
import com.api3Dsm.domain.modelo.RegisterRequest;
import com.api3Dsm.domain.modelo.Usuario;
import com.api3Dsm.domain.repositorio.UsuarioRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutenticacaoServico{

    private final UsuarioRepositorio usuarioRepositorio;

    private final PasswordEncoder passwordEncoder;

    private final JwtServico jwtServico;

    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(RegisterRequest request){
        var usuario = Usuario.builder()
            .email(request.getEmail())
            .senha(passwordEncoder.encode(request.getSenha()))
            .cargo(request.getCargo())
            .build();
            usuarioRepositorio.save(usuario);

        var jwtToken = jwtServico.generateToken(usuario);
        return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()) 
        );

        var usuario = usuarioRepositorio.findByEmail(request.getEmail()).orElseThrow();
        String userRole = usuario.getCargo();
        var jwtToken = jwtServico.generateToken(usuario);
        return AuthenticationResponse.builder()
        .token(jwtToken)
        .role(userRole)
        .build();
    }
    
}
