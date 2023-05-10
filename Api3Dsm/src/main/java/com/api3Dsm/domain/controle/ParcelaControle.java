package com.api3Dsm.domain.controle;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
	// @PreAuthorize("hasAnyAuthority('ADMIN', 'FINANCEIRO')")
	@GetMapping("/buscarParcela/{id}")
	public Parcela buscarParcela(@PathVariable Long id){
		Cliente clienteSelecionado = clienteRepositorio.getReferenceById(id);
		List<Parcela> listaParcelas = clienteSelecionado.getServico().getParcelas();
		Parcela parcelaObtida = new Parcela();
		for(Parcela parcela: listaParcelas){
			if(parcela.getValorPago() < parcela.getValorParcela()){
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
		float valorExtraPago = parcela.getValorPago() - parcela.getValorParcela();
		if(valorExtraPago > 0) {
			parcela.setValorPago(parcela.getValorParcela());
		}
		parcelaAtualizar = parcela;
		parcelaRepositorio.saveAndFlush(parcelaAtualizar);
		if(valorExtraPago > 0 && parcela.getNumeroParcela() != 12){
			Parcela parcelaSeguinte = buscarParcela(parcela.getIdCliente());
			parcelaSeguinte.setValorPago(valorExtraPago);
			if(parcelaSeguinte.getValorPago() >= parcelaSeguinte.getValorParcela()) {
				parcelaSeguinte.setDataCredito(parcela.getDataCredito());
				parcelaSeguinte.setDataPagamento(parcela.getDataPagamento());
			}
			atualizaParcela(parcelaSeguinte);
		}
		return parcelaAtualizar;
	}
    
	
    @CrossOrigin
	// @PreAuthorize("hasAnyAuthority('ADMIN', 'FINANCEIRO')")
    @GetMapping("/buscarParcelas/vencimento/{dtInicio}/{dtFinal}")
    public List<Parcela> filtrarPorDataVencimento(@PathVariable String dtInicio,
    @PathVariable String dtFinal){

		LocalDate hoje = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataInicio = LocalDate.parse(dtInicio, formatter);
        LocalDate dataFinal = LocalDate.parse(dtFinal, formatter);
        List<Parcela> parcelasFiltradas = parcelaRepositorio.dataVencimentoEntre(dataInicio, dataFinal);

		for (Parcela parcela : parcelasFiltradas) {
			if(parcela.getDataVencimento().isAfter(hoje)){
				return null;
			}else{
				return parcelasFiltradas;
			}
		}
				return parcelasFiltradas;
    }

	@CrossOrigin
	// @PreAuthorize("hasAnyAuthority('ADMIN', 'FINANCEIRO')")
    @GetMapping("/buscarParcelas/pagamento/{dtInicio}/{dtFinal}")
    public List<Parcela> filtrarPorDataPagamento(@PathVariable String dtInicio,
    @PathVariable String dtFinal){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataInicio = LocalDate.parse(dtInicio, formatter);
        LocalDate dataFinal = LocalDate.parse(dtFinal, formatter);
        List<Parcela> parcelasFiltradas = parcelaRepositorio.dataPagamentoEntre(dataInicio, dataFinal);
        for (Parcela parcela : parcelasFiltradas) {
			if(parcela.getValorPago() == 0){
				return null;
			}else if(parcela.getValorPago() < parcela.getValorParcela()){
				return null;
			}else{
				return parcelasFiltradas;
			}
		}
		return parcelasFiltradas;		
    }

    @CrossOrigin
	// @PreAuthorize("hasAnyAuthority('ADMIN', 'FINANCEIRO')")
    @GetMapping("/buscarParcelas/credito/{dtInicio}/{dtFinal}")
    public List<Parcela> filtrarPorDataCredito(@PathVariable String dtInicio,
    @PathVariable String dtFinal){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataInicio = LocalDate.parse(dtInicio, formatter);
        LocalDate dataFinal = LocalDate.parse(dtFinal, formatter);
        List<Parcela> parcelasFiltradas = parcelaRepositorio.dataCreditoEntre(dataInicio, dataFinal);

		for (Parcela parcela : parcelasFiltradas) {
			if(parcela.getDataCredito().isAfter(parcela.getDataPagamento())){
				return parcelasFiltradas;
			}
			else if(parcela.getDataCredito() == (parcela.getDataVencimento())){
				return parcelasFiltradas;
    		}
			else{
				return null;
			}
		}
		return parcelasFiltradas;
	}
};
