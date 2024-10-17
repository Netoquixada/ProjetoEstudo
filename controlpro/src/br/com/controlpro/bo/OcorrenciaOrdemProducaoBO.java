package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.OcorrenciaOrdemProducaoDao;
import br.com.controlpro.entity.AquisicaoSaida;
import br.com.controlpro.entity.OcorrenciaOrdemProducao;
import br.com.controlpro.entity.OrdemProducao;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.IntegridadeObjetoHibernateException;
import br.hibernateutil.exception.ListaVaziaException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

public class OcorrenciaOrdemProducaoBO {

	private OcorrenciaOrdemProducaoBO() {
	}

	public static OcorrenciaOrdemProducaoBO getInstance() {
		return new OcorrenciaOrdemProducaoBO();
	}

	public void save(OcorrenciaOrdemProducao OcorrenciaOrdemProducao)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.save(OcorrenciaOrdemProducao);
	}

	public void update(OcorrenciaOrdemProducao aquisicaoSaida)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.update(aquisicaoSaida);
	}

	public void delete(OcorrenciaOrdemProducao aquisicaoSaida)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.delete(aquisicaoSaida);
	}

	public List<OcorrenciaOrdemProducao> historicoPorControlePedido(OrdemProducao ordem)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return OcorrenciaOrdemProducaoDao.getInstance().ocorrenciaPorOrdemProducao(ordem);
	}

	public List<OcorrenciaOrdemProducao> ocorrenciasFilter(OcorrenciaOrdemProducao ocorrencia)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return OcorrenciaOrdemProducaoDao.getInstance().ocorrenciasFilter(ocorrencia);
	}

	public void mergeAll(List<OcorrenciaOrdemProducao> lista)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.mergeAll(lista);
	}

	public void updateAll(List<OcorrenciaOrdemProducao> historico)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.updateAll(historico);
	}

	public void deleteAll(List<OcorrenciaOrdemProducao> historicoAux)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		synchronized (AquisicaoSaida.class) {
			for (int i = 0; i < historicoAux.size(); i++) {
				GenericDao.delete(historicoAux.get(i));
			}
		}
	}

}
