package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ProducaoAnalisticaDao;
import br.com.controlpro.entity.Faccao;
import br.com.controlpro.entity.HistoricoConta;
import br.com.controlpro.entity.HistoricoProducao;
import br.com.controlpro.entity.ItemProducao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class ProducaoAnalisticaBO {

	public static ProducaoAnalisticaBO getInstance() {
		return new ProducaoAnalisticaBO();
	}

	public List<ItemProducao> itens(String protocolo, Faccao faccao)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ProducaoAnalisticaDao.getInstance().itens(protocolo, faccao);
	}
	public List<HistoricoProducao> historico(String protocolo, Faccao faccao)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ProducaoAnalisticaDao.getInstance().historico(protocolo, faccao);
	}
	public List<HistoricoConta> pagamentos(String protocolo, Faccao faccao)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ProducaoAnalisticaDao.getInstance().pagamentos(protocolo, faccao);
	}


}