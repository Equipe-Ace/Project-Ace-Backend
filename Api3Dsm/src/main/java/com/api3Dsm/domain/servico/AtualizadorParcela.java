package com.api3Dsm.domain.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api3Dsm.domain.modelo.Parcela;
import com.api3Dsm.domain.repositorio.ParcelaRepositorio;

@Service
public class AtualizadorParcela {
	
	@Autowired
	private ParcelaRepositorio parcelaRepositorio;
	
	public Parcela atualiza(Parcela parcela){
		Parcela parcelaAtualizar  = parcelaRepositorio.getReferenceById(parcela.getId());
		parcelaAtualizar = parcela;
		parcelaRepositorio.saveAndFlush(parcelaAtualizar);
		return parcelaAtualizar;
	}
}
