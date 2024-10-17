package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ProdutosMovimentadosDAO;
import br.com.controlpro.entity.consultas.ProdutosMovimentados;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class ProdutosMovimentadosBO {

	private ProdutosMovimentadosBO() {
	}

	public static ProdutosMovimentadosBO getInstance() {
		return new ProdutosMovimentadosBO();
	}

	public List<ProdutosMovimentados> produtosMovimentados(ProdutosMovimentados filter)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ProdutosMovimentadosDAO.getInstance().produtosMovimentados(filter);
	}

}
