package com.api3Dsm.domain.controle;


import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.api3Dsm.domain.modelo.Cliente;
import com.api3Dsm.domain.repositorio.ClienteRepositorio;
import com.api3Dsm.domain.servico.GeradorParcela;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("Cliente")
public class ClienteControle {

	@Autowired
	private ClienteRepositorio clienteRepositorio;
	
	@Autowired
	private GeradorParcela gerador;
	@CrossOrigin
	@PostMapping("/inserir")
	@ResponseStatus(HttpStatus.CREATED)
	public void cadastrarCliente(@Valid @RequestBody Cliente cliente){
		gerador.gerarParcelas(cliente);
		clienteRepositorio.save(cliente);

	}

	@GetMapping
	public List<Cliente> Listar() {
		return clienteRepositorio.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long id) {
		return clienteRepositorio.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/atualizar/")
	public ResponseEntity<Cliente> Atualizar(@Valid @RequestBody Cliente cliente) {
		if (!clienteRepositorio.existsById(cliente.getId())) {
			return ResponseEntity.notFound().build();
		}else {
			clienteRepositorio.save(cliente);
			return ResponseEntity.ok(cliente);
		}
		
	}

	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<Void> Deletar(@PathVariable Long id) {
		if (!clienteRepositorio.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		clienteRepositorio.deleteById(id);
		return ResponseEntity.noContent().build();
	}


	@GetMapping("/pegarNome/{nome}")
	public Cliente pegarClienteNome(@PathVariable String nome){
		List<Cliente> listaClientes = clienteRepositorio.findAll();
		Cliente clienteBuscado = null;
		for(Cliente cliente: listaClientes){
			if(cliente.getNome().equals(nome)){
				clienteBuscado = cliente;
				break;
			}
		}
		return clienteBuscado;
	}
}
