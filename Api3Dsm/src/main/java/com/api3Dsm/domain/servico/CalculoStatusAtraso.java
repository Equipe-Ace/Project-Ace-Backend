package com.api3Dsm.domain.servico;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.api3Dsm.domain.modelo.Parcela;
import com.api3Dsm.domain.repositorio.ParcelaRepositorio;

@Configuration
@EnableScheduling
@Service
public class CalculoStatusAtraso {

    @Autowired
	private ParcelaRepositorio parcelaRepositorio;

    @Scheduled(fixedDelay = 60000)
    public List<Parcela> gerandoStatus() {
        LocalDate dtAtual = LocalDate.now();
        List<Parcela> parcelasFiltradas = parcelaRepositorio.listaParcelasAtrasadas(dtAtual);
        if(parcelasFiltradas != null) {
            for(Parcela parcela : parcelasFiltradas) {
                if(parcela.getStatusPago() == "À pagar") {
                    parcela.setStatusAtraso(true);
                    parcelaRepositorio.saveAndFlush(parcela);
                }
            }
        }
        return parcelasFiltradas;
    }
}
