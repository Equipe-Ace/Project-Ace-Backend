package com.api3Dsm.domain.servico;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api3Dsm.domain.modelo.Cliente;
import com.api3Dsm.domain.modelo.Parcela;
import com.api3Dsm.domain.repositorio.ClienteRepositorio;

@Service
public class GeradorParcela {
	
	@Autowired
	private ClienteRepositorio clienteRepositorio;
	
	public void gerarParcelas(Cliente cliente) {
		float precoDoServico = cliente.getServico().getPreco();
        float precoCadaParcela = precoDoServico / 12;
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
    }
}