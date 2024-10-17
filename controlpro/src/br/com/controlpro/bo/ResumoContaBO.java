package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ResumoContaDAO;
import br.com.controlpro.entity.consultas.ResumoContaTotal;
import br.com.controlpro.entity.consultas.Vendas;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class ResumoContaBO {

	private ResumoContaBO() {
	}

	public static ResumoContaBO getInstance() {
		return new ResumoContaBO();
	}

	public List<ResumoContaTotal> resumoCartaoTotal(Vendas filter)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ResumoContaDAO.getInstance().resumoCartaoTotal(filter);
	}

}
