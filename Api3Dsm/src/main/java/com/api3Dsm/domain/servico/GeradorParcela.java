package com.api3Dsm.domain.servico;


import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.api3Dsm.domain.modelo.Parcela;
import com.api3Dsm.domain.modelo.Servico;

@Service
public class GeradorParcela {
	public void gerarParcelas(Servico servico) {
		
		List<Parcela> parcelas = new ArrayList<>();
		parcelas.addAll(servico.getParcelas());
		Parcela primeira = parcelas.get(0);
		
        for (int i = 0; i < 11; i++) {
        	Parcela parcela = new Parcela();
        	parcela.setValorPago(primeira.getValorPago());
        	servico.getParcelas().add(parcela);
		}
    }
}