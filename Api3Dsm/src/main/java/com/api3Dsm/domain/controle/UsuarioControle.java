package com.api3Dsm.domain.controle;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.api3Dsm.domain.modelo.Usuario;
import com.api3Dsm.domain.repositorio.UsuarioRepositorio;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("Cliente")
public class UsuarioControle {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@PostMapping("inserir")
	@ResponseStatus(HttpStatus.CREATED)
	public void CadastrarUsuario(@Valid @RequestBody Usuario usuario) {
		usuarioRepositorio.save(usuario);
	}

	@GetMapping
	public List<Usuario> Listar() {
		return usuarioRepositorio.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> buscar(@PathVariable Long id) {
		return usuarioRepositorio.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/atualizar/")
	public ResponseEntity<Usuario> Atualizar(@Valid @RequestBody Usuario usuario) {
		if (!usuarioRepositorio.existsById(usuario.getId())) {
			return ResponseEntity.notFound().build();
		}else {
			usuarioRepositorio.save(usuario);
			return ResponseEntity.ok(usuario);
		}
		
	}

	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<Void> Deletar(@PathVariable Long id) {
		if (!usuarioRepositorio.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		usuarioRepositorio.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
