package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.RelacionamentoClienteDao;
import br.com.controlpro.entity.RelacionamentoCliente;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class RelacionamentoClienteBO {

	private RelacionamentoClienteBO() {
	}

	public static RelacionamentoClienteBO getInstance() {
		return new RelacionamentoClienteBO();
	}

	public List<RelacionamentoCliente> relatorioRelacionamentoCLiente()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return RelacionamentoClienteDao.getInstance().relacionamentoClienteLista();
	}
	
}
