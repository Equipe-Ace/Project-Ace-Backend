package com.api3Dsm.domain.controle;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
	@PreAuthorize("hasAnyAuthority('ADMIN', 'FINANCEIRO')")
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
	@PreAuthorize("hasAnyAuthority('ADMIN','FINANCEIRO')")
	public Parcela atualizaParcela(@RequestBody Parcela parcela){
		Parcela parcelaAtualizar  = parcelaRepositorio.getReferenceById(parcela.getId());
		float valorExtraPago = parcela.getValorPago() - parcela.getValorParcela();
		if(valorExtraPago > 0) {
			parcela.setValorPago(parcela.getValorParcela());
		}
		parcelaAtualizar = parcela;
		parcelaRepositorio.saveAndFlush(parcelaAtualizar);
		if(valorExtraPago > 0){
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
	@PreAuthorize("hasAnyAuthority('ADMIN', 'FINANCEIRO')")
    @GetMapping("/buscarParcelas/vencimento/{dtInicio}/{dtFinal}")
    public List<Parcela> filtrarPorDataVencimento(@PathVariable String dtInicio,
    @PathVariable String dtFinal){

		LocalDate hoje = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataInicio = LocalDate.parse(dtInicio, formatter);
        LocalDate dataFinal = LocalDate.parse(dtFinal, formatter);
        List<Parcela> parcelasFiltradas = parcelaRepositorio.dataVencimentoEntre(dataInicio, dataFinal);
		
		// for (Parcela parcela : parcelasFiltradas) {
		// 	if(parcela.getDataVencimento().isAfter(hoje)){
		// 		return null;
		// 	}else{
		// 		return parcelasFiltradas;
		// 	}
		// }
		// 		return parcelasFiltradas;
		
		//************************
		
		List<Parcela> parcelasVencidas = new ArrayList<>();
		for(Parcela parcela : parcelasFiltradas){
			if(parcela.getDataVencimento().isBefore(hoje) || parcela.getDataVencimento().isEqual(hoje)){
				parcela.setStatusVencida("Vencida");
				parcelasVencidas.add(parcela);
			}else{
				parcela.setStatusVencida("A vencer");
				parcelasVencidas.add(parcela);
			}
		}
		return parcelasVencidas;
		//return parcelasFiltradas;
    }

	@CrossOrigin
	@PreAuthorize("hasAnyAuthority('ADMIN', 'FINANCEIRO')")
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
	@PreAuthorize("hasAnyAuthority('ADMIN', 'FINANCEIRO')")
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

	@CrossOrigin
	@PreAuthorize("hasAnyAuthority('ADMIN', 'FINANCEIRO')")
    @GetMapping("/buscarParcelas/{tipoRelatorio}/{dtInicio}/{dtFinal}")
    public List<Parcela> filtrarPorDataVencimento(@PathVariable String dtInicio,
    @PathVariable String dtFinal, @PathVariable String tipoRelatorio){

		LocalDate hoje = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataInicio = LocalDate.parse(dtInicio, formatter);
        LocalDate dataFinal = LocalDate.parse(dtFinal, formatter);
        List<Parcela> parcelasFiltradas = parcelaRepositorio.dataVencimentoEntre(dataInicio, dataFinal);
		List<Parcela> parcelasFinais = new ArrayList<>(); 

		if(tipoRelatorio.equals("vencer")){
			System.out.println("oi1");
			for(Parcela parcela : parcelasFiltradas){
				System.out.println("oi2");
				if(parcela.getDataPagamento()== null &&  parcela.getDataVencimento().isBefore(hoje)){
					parcela.setStatusVencida("vencida");
					parcelasFinais.add(parcela);
					System.out.println("oi3");
				}else
				if(parcela.getDataPagamento() != null && parcela.getDataPagamento().isAfter(parcela.getDataVencimento()) && parcela.getDataPagamento().isBefore(hoje) ||parcela.getDataPagamento() != null && parcela.getDataPagamento().isAfter(parcela.getDataVencimento()) && parcela.getDataPagamento().isEqual(hoje)){
					parcela.setStatusVencida("paga");
					parcelasFinais.add(parcela);
					System.out.println("oi4");
				}else
				if(parcela.getDataPagamento() == null && parcela.getDataVencimento().isAfter(hoje)){
					parcela.setStatusVencida("A vencer");
					parcelasFinais.add(parcela);
					System.out.println("oi5");
				}else
				if(parcela.getDataPagamento() != null &&  parcela.getDataPagamento().isBefore(parcela.getDataVencimento()) && parcela.getDataPagamento().isBefore(hoje) || parcela.getDataPagamento() != null &&  parcela.getDataPagamento().isBefore(parcela.getDataVencimento()) && parcela.getDataPagamento().isEqual(hoje)){
					parcela.setStatusVencida("paga");
					parcelasFinais.add(parcela);
					System.out.println("oi6");
				}else{
					parcela.setStatusVencida("A vencer");
					parcelasFinais.add(parcela);
				}
			}
		}
		if(tipoRelatorio.equals("atraso")){
			System.out.println("oi");
			for(Parcela parcela : parcelasFiltradas){
				if(parcela.getDataPagamento() == null && parcela.getDataVencimento().isBefore(hoje)){
					parcela.setStatusVencida("Em atraso");
					parcelasFinais.add(parcela);
				}else
				if(parcela.getDataPagamento() != null && parcela.getDataPagamento().isBefore(parcela.getDataVencimento()) && parcela.getDataPagamento().isBefore(hoje)|| parcela.getDataPagamento() != null && parcela.getDataPagamento().isEqual(parcela.getDataVencimento()) && parcela.getDataPagamento().isEqual(hoje)){
					parcela.setStatusVencida("paga");
					parcelasFinais.add(parcela);
				}else
				if(parcela.getDataPagamento() != null && parcela.getDataPagamento().isAfter(parcela.getDataVencimento()) && parcela.getDataPagamento().isBefore(hoje) || parcela.getDataPagamento() != null && parcela.getDataPagamento().isAfter(parcela.getDataVencimento()) && parcela.getDataPagamento().isEqual(hoje)){
					parcela.setStatusVencida("Paga em atraso");
					parcelasFinais.add(parcela);
				}else{
					parcela.setStatusVencida("A pagar");
					parcelasFinais.add(parcela);
				}
			}
		}
		if(tipoRelatorio.equals("paga")){
			System.out.println("oi");
			for(Parcela parcela : parcelasFiltradas){
				if(parcela.getDataPagamento() != null && parcela.getDataPagamento().isBefore(hoje) || parcela.getDataPagamento() != null && parcela.getDataPagamento().isEqual(hoje) ){
					parcela.setStatusVencida("paga");
					parcelasFinais.add(parcela);
				}else{
					parcela.setStatusVencida("Não paga");
					parcelasFinais.add(parcela);
				}
			}
		}
		if(tipoRelatorio.equals("creditada")){
			System.out.println("oi");
			for(Parcela parcela : parcelasFiltradas){
				if(parcela.getDataPagamento() == null){
					parcela.setStatusVencida("Em aberto");
					parcelasFinais.add(parcela);
				}else
				if(parcela.getDataCredito() != null && parcela.getDataCredito().isBefore(hoje) || parcela.getDataCredito() != null &&  parcela.getDataCredito().equals(hoje)){
					parcela.setStatusVencida("creditada");
					parcelasFinais.add(parcela);
				}else
				if(parcela.getDataCredito() != null && parcela.getDataCredito().isAfter(hoje)){
					parcela.setStatusVencida("A creditar");
					parcelasFinais.add(parcela);
				}
			}
		}
		return parcelasFinais;
	}

	// @CrossOrigin
	// @PreAuthorize("hasAnyAuthority('ADMIN', 'FINANCEIRO')")
    // @GetMapping("/buscarParcelas/{tipoRelatorio}/{dtInicio}/{dtFinal}")
    // public List<Parcela> filtrarPorDataVencimento(@PathVariable String dtInicio,
    // @PathVariable String dtFinal, @PathVariable String tipoRelatorio){

	// 	LocalDate hoje = LocalDate.now();

    //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    //     LocalDate dataInicio = LocalDate.parse(dtInicio, formatter);
    //     LocalDate dataFinal = LocalDate.parse(dtFinal, formatter);
    //     List<Parcela> parcelasFiltradas = parcelaRepositorio.dataVencimentoEntre(dataInicio, dataFinal);
	// 	List<Parcela> parcelasFinais = new ArrayList<>(); 

	// 	if(tipoRelatorio.equals("vencer")){
	// 		System.out.println("oi1");
	// 		for(Parcela parcela : parcelasFiltradas){
	// 			System.out.println("oi2");
	// 			if(parcela.getDataPagamento()== null &&  parcela.getDataVencimento().isBefore(hoje)){
	// 				parcela.setStatusVencida("vencida");
	// 				parcelasFinais.add(parcela);
	// 				System.out.println("oi3");
	// 			}else
	// 			if(parcela.getDataPagamento() != null && parcela.getDataPagamento().isAfter(parcela.getDataVencimento())){
	// 				parcela.setStatusVencida("paga");
	// 				parcelasFinais.add(parcela);
	// 				System.out.println("oi4");
	// 			}else
	// 			if(parcela.getDataPagamento() == null && parcela.getDataVencimento().isAfter(hoje)){
	// 				parcela.setStatusVencida("A vencer");
	// 				parcelasFinais.add(parcela);
	// 				System.out.println("oi5");
	// 			}else
	// 			if(parcela.getDataPagamento() != null &&  parcela.getDataPagamento().isBefore(parcela.getDataVencimento())){
	// 				parcela.setStatusVencida("paga");
	// 				parcelasFinais.add(parcela);
	// 				System.out.println("oi6");
	// 			}else{
	// 				parcela.setStatusVencida("A vencer");
	// 				parcelasFinais.add(parcela);
	// 			}
	// 		}
	// 	}
	// 	if(tipoRelatorio.equals("atraso")){
	// 		System.out.println("oi");
	// 		for(Parcela parcela : parcelasFiltradas){
	// 			if(parcela.getDataPagamento() == null && parcela.getDataVencimento().isBefore(hoje)){
	// 				parcela.setStatusVencida("Em atraso");
	// 				parcelasFinais.add(parcela);
	// 			}else
	// 			if(parcela.getDataPagamento() != null && parcela.getDataPagamento().isBefore(parcela.getDataVencimento()) || parcela.getDataPagamento() != null && parcela.getDataPagamento().isEqual(parcela.getDataVencimento())){
	// 				parcela.setStatusVencida("paga");
	// 				parcelasFinais.add(parcela);
	// 			}else
	// 			if(parcela.getDataPagamento() != null && parcela.getDataPagamento().isAfter(parcela.getDataVencimento())){
	// 				parcela.setStatusVencida("Paga em atraso");
	// 				parcelasFinais.add(parcela);
	// 			}else{
	// 				parcela.setStatusVencida("A pagar");
	// 				parcelasFinais.add(parcela);
	// 			}
	// 		}
	// 	}
	// 	if(tipoRelatorio.equals("paga")){
	// 		System.out.println("oi");
	// 		for(Parcela parcela : parcelasFiltradas){
	// 			if(parcela.getDataPagamento() == null){
	// 				parcela.setStatusVencida("Não paga");
	// 				parcelasFinais.add(parcela);
	// 			}else{
	// 				parcela.setStatusVencida("paga");
	// 				parcelasFinais.add(parcela);
	// 			}
	// 		}
	// 	}
	// 	if(tipoRelatorio.equals("creditada")){
	// 		System.out.println("oi");
	// 		for(Parcela parcela : parcelasFiltradas){
	// 			if(parcela.getDataPagamento() == null){
	// 				parcela.setStatusVencida("Em aberto");
	// 				parcelasFinais.add(parcela);
	// 			}else
	// 			if(parcela.getDataCredito() != null && parcela.getDataCredito().isBefore(hoje) || parcela.getDataCredito() != null &&  parcela.getDataCredito().equals(hoje)){
	// 				parcela.setStatusVencida("creditada");
	// 				parcelasFinais.add(parcela);
	// 			}else
	// 			if(parcela.getDataCredito() != null && parcela.getDataCredito().isAfter(hoje)){
	// 				parcela.setStatusVencida("A creditar");
	// 				parcelasFinais.add(parcela);
	// 			}
	// 		}
	// 	}
	// 	return parcelasFinais;
	// }
};
