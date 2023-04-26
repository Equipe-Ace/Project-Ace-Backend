package com.api3Dsm.domain.repositorio;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.api3Dsm.domain.modelo.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>{

    // @Query("SELECT usuario FROM Usuario WHERE usuario.email = username")
    // Usuario pegarUsuarioPeloNome(String username);

    Optional<Usuario> findByEmail(String email);
}

