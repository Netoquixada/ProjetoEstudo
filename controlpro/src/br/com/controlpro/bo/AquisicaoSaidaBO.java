package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.AquisicaoSaidaDao;
import br.com.controlpro.entity.AquisicaoSaida;
import br.com.controlpro.entity.OrdemProducao;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.IntegridadeObjetoHibernateException;
import br.hibernateutil.exception.ListaVaziaException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

public class AquisicaoSaidaBO {

	private AquisicaoSaidaBO() {
	}

	public static AquisicaoSaidaBO getInstance() {
		return new AquisicaoSaidaBO();
	}

	public void save(AquisicaoSaida aquisicaoSaida)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.save(aquisicaoSaida);
	}

	public void update(AquisicaoSaida aquisicaoSaida)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.update(aquisicaoSaida);
	}

	public void delete(AquisicaoSaida aquisicaoSaida)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.delete(aquisicaoSaida);
	}

	public List<AquisicaoSaida> aquisicaoSaidaPorOrdemCorte(OrdemProducao ordemProducao)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return AquisicaoSaidaDao.getInstance().itemAviamentoPorOrdemProducao(ordemProducao);
	}

	public void mergeAll(List<AquisicaoSaida> despesaSessenta)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.mergeAll(despesaSessenta);
	}

	public void updateAll(List<AquisicaoSaida> aquisicaoSaida)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.updateAll(aquisicaoSaida);
	}

	public void deleteAll(List<AquisicaoSaida> aquisicaoSaidaAux)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		synchronized (AquisicaoSaida.class) {
			for (int i = 0; i < aquisicaoSaidaAux.size(); i++) {
				GenericDao.delete(aquisicaoSaidaAux.get(i));
			}
		}
	}

}
