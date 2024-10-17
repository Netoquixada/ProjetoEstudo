package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.MovimentacaoBancariaDAO;
import br.com.controlpro.entity.consultas.MovimentacaoBancaria;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class MovimentacaoBancariaBO {

	private MovimentacaoBancariaBO() {
	}

	public static MovimentacaoBancariaBO getInstance() {
		return new MovimentacaoBancariaBO();
	}

	public List<MovimentacaoBancaria> movimentacaoBancaria(MovimentacaoBancaria filter)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return MovimentacaoBancariaDAO.getInstance().movimentacaoBancaria(filter);
	}

}
