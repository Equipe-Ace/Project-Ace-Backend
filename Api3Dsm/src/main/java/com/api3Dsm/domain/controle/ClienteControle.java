package com.api3Dsm.domain.controle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.api3Dsm.domain.modelo.Parcela;
import com.api3Dsm.domain.modelo.Servico;
import com.api3Dsm.domain.repositorio.ClienteRepositorio;
import com.api3Dsm.domain.repositorio.ParcelaRepositorio;
import com.api3Dsm.domain.servico.GeradorParcela;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("Cliente")
public class ClienteControle {

	@Autowired
	private ClienteRepositorio clienteRepositorio;

	@Autowired
	private ParcelaRepositorio parcelaRepositorio;
	
	@Autowired
	private GeradorParcela gerador;

	/*@PostMapping("/inserir")
	@ResponseStatus(HttpStatus.CREATED)
	public void CadastrarUsuario(@Valid @RequestBody Cliente usuario) {
		List<Servico> servicos = new ArrayList<>();
		servicos.addAll(usuario.getServicos());
		Servico servico = servicos.get(0);
		gerador.gerarParcelas(servico);
		clienteRepositorio.save(usuario);
	}*/

	@CrossOrigin
	@PostMapping("/inserir")
	@ResponseStatus(HttpStatus.CREATED)
	public void cadastrarCliente(@Valid @RequestBody Cliente cliente){
		Double precoDoServico = cliente.getServico().getPreco();
        Double precoCadaParcela = precoDoServico / 12;
        List<Parcela> listaParcelas = new ArrayList<>();
		LocalDate dataVenci = LocalDate.now();
		

        for(int i = 0; i <12; i++){
            Parcela parcela = new Parcela();
		
            parcela.setValorParcela(precoCadaParcela);
            parcela.setNumeroParcela((i +1));
			//parcela.setDataVencimento("30/04/2023");
			if(i == 0){
				parcela.setDataVencimento(dataVenci.plusDays(30));
			}

            listaParcelas.add(parcela);
            cliente.getServico().setParcelas(listaParcelas);
        }
        clienteRepositorio.save(cliente);
		Long idCliente = cliente.getId();
		List<Parcela> parcelasDoCliente = cliente.getServico().getParcelas();
		int contador = 0;
		int multiplicadorData = 0;
		LocalDate alterarDataVen = LocalDate.now();
		for(Parcela parcela: parcelasDoCliente){
			
			if(contador == 0){
				alterarDataVen = parcela.getDataVencimento();
			}
			parcela.setIdCliente(idCliente);
			
			if(contador != 0){
				
				LocalDate dataVencimentoAlterada = alterarDataVen.plusDays(30 * multiplicadorData);
				parcela.setDataVencimento(dataVencimentoAlterada);
				multiplicadorData += 1;
			}else{
				contador += 1;
				multiplicadorData = 1;
			}
		}
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


	/*@PutMapping("/atualizarParcela")
	public Cliente atualizarParcelas(@RequestBody Cliente cliente){
		Cliente clienteAntigo = clienteRepositorio.getReferenceById(cliente.getId());
		Cliente clienteNovo = cliente;
        clienteAntigo = clienteNovo;
		clienteRepositorio.saveAndFlush(clienteAntigo);
		return clienteAntigo;
	}*/

	

	

}
