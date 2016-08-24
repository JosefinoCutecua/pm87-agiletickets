package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco;

		TipoDeEspetaculo tipoDeEspectaculo = sessao.getEspetaculo().getTipo();

		double razaoIngressosRestantes = (sessao.getTotalIngressos() - sessao.getIngressosReservados())
				/ sessao.getTotalIngressos().doubleValue();

		switch (tipoDeEspectaculo) {
		case CINEMA:
		case SHOW:
			if (razaoIngressosRestantes <= 0.05) {
				preco = sessao.getPreco().add(precoPeloValorDaRazao(sessao, 0.10));
			} 
			else {
				preco = sessao.getPreco();
			}
			break;

		case BALLET:
		case ORQUESTRA:
			if (razaoIngressosRestantes <= 0.50) {
				preco = sessao.getPreco().add(precoPeloValorDaRazao(sessao, 0.20));
			} 
			else {
				preco = sessao.getPreco();
			}

			if (sessao.getDuracaoEmMinutos() > 60) {
				preco = preco.add(precoPeloValorDaRazao(sessao, 0.10));
			}

			break;

		default:
			preco = sessao.getPreco();
			break;
		}

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

	private static BigDecimal precoPeloValorDaRazao(Sessao sessao, double valorDaRazao) {
		return sessao.getPreco().multiply(BigDecimal.valueOf(valorDaRazao));
	}

}