package com.api3Dsm.domain.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.api3Dsm.domain.modelo.Cliente;
import com.api3Dsm.domain.modelo.Parcela;
import com.api3Dsm.domain.repositorio.ClienteRepositorio;

@Service
public class BuscadorParcela {
	
	@Autowired
	private ClienteRepositorio clienteRepositorio;

	public Parcela buscar(Long id){
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
}
