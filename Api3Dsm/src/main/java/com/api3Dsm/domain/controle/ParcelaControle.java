package com.api3Dsm.domain.controle;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api3Dsm.domain.modelo.Cliente;
import com.api3Dsm.domain.modelo.Parcela;
import com.api3Dsm.domain.repositorio.ClienteRepositorio;
import com.api3Dsm.domain.repositorio.ParcelaRepositorio;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("Parcela")
public class ParcelaControle {
    
    @Autowired
	private ClienteRepositorio clienteRepositorio;

	@Autowired
	private ParcelaRepositorio parcelaRepositorio;

    @CrossOrigin
	@GetMapping("/buscarParcela/{id}")
	public Parcela buscarParcela(@PathVariable Long id){
		Cliente clienteSelecionado = clienteRepositorio.getReferenceById(id);
		List<Parcela> listaParcelas = clienteSelecionado.getServico().getParcelas();
		Parcela parcelaObtida = new Parcela();
		for(Parcela parcela: listaParcelas){
			if(parcela.getValorPago() == 0){
				parcelaObtida = parcela;
				break;
			}
		}
		return parcelaObtida;
	}


    @CrossOrigin
	@PutMapping("/atualizarParcela")
	public Parcela atualizaParcela(@RequestBody Parcela parcela){
		Parcela parcelaAtualizar  = parcelaRepositorio.getReferenceById(parcela.getId());
		parcelaAtualizar = parcela;
		parcelaRepositorio.saveAndFlush(parcelaAtualizar);
		return parcelaAtualizar;
	}

	@CrossOrigin
	@GetMapping("/buscarParcelas/pagamento/{dtInicio}/{dtFinal}")
	public List<Parcela> filtrarPorDataPagamento(@PathVariable LocalDate dtInicio,
	@PathVariable LocalDate dtFinal){
		List<Parcela> parcelasFiltradas = parcelaRepositorio.dataPagamentoEntre(dtInicio, dtFinal);
		return parcelasFiltradas;
	}

	@CrossOrigin
	@GetMapping("/buscarParcelas/vencimento/{dtInicio}/{dtFinal}")
	public List<Parcela> filtrarPorDataVencimento(@PathVariable LocalDate dtInicio,
	@PathVariable LocalDate dtFinal){
		List<Parcela> parcelasFiltradas = parcelaRepositorio.dataVencimentoEntre(dtInicio, dtFinal);
		return parcelasFiltradas;
	}

	@CrossOrigin
	@GetMapping("/buscarParcelas/credito/{dtInicio}/{dtFinal}")
	public List<Parcela> filtrarPorDataCredito(@PathVariable LocalDate dtInicio,
	@PathVariable LocalDate dtFinal){
		List<Parcela> parcelasFiltradas = parcelaRepositorio.dataCreditoEntre(dtInicio, dtFinal);
		return parcelasFiltradas;
	}
}
