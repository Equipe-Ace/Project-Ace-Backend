package com.api3Dsm.domain.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.api3Dsm.domain.modelo.Parcela;
import com.api3Dsm.domain.servico.AtualizadorParcela;
import com.api3Dsm.domain.servico.BuscadorParcela;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("Parcela")
public class ParcelaControle {
	
	@Autowired
	private BuscadorParcela buscador;
	
	@Autowired
	private AtualizadorParcela atualizador;

    @CrossOrigin
	@GetMapping("/buscarParcela/{id}")
    public Parcela buscarParcela(@PathVariable Long id) {
    	 Parcela parcelaBuscada = buscador.buscar(id);
    	 return parcelaBuscada;
    }
    
    @CrossOrigin
	@PutMapping("/atualizarParcela")
    public Parcela atualizarParcela(@RequestBody Parcela parcela) {
    	Parcela novaParcela = atualizador.atualiza(parcela);
    	return novaParcela;
    }
	
}
