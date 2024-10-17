package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.AquisicaoSaidaAcabamentoDao;
import br.com.controlpro.entity.Acabamento;
import br.com.controlpro.entity.AquisicaoSaida;
import br.com.controlpro.entity.AquisicaoSaidaAcabamento;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.IntegridadeObjetoHibernateException;
import br.hibernateutil.exception.ListaVaziaException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

public class AquisicaoSaidaAcabamentoBO {

	private AquisicaoSaidaAcabamentoBO() {
	}

	public static AquisicaoSaidaAcabamentoBO getInstance() {
		return new AquisicaoSaidaAcabamentoBO();
	}

	public void save(AquisicaoSaidaAcabamento aquisicaoSaidaAcabamento)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.save(aquisicaoSaidaAcabamento);
	}

	public void update(AquisicaoSaidaAcabamento aquisicaoSaida)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.update(aquisicaoSaida);
	}

	public void delete(AquisicaoSaidaAcabamento aquisicaoSaida)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.delete(aquisicaoSaida);
	}

	public List<AquisicaoSaidaAcabamento> aquisicaoPorAcabamento(Acabamento acabamento)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return AquisicaoSaidaAcabamentoDao.getInstance().itemAquisicaoPorAcabamento(acabamento);
	}

	public void mergeAll(List<AquisicaoSaidaAcabamento> despesaSessenta)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.mergeAll(despesaSessenta);
	}

	public void updateAll(List<AquisicaoSaidaAcabamento> aquisicaoSaida)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.updateAll(aquisicaoSaida);
	}

	public void deleteAll(List<AquisicaoSaidaAcabamento> aquisicaoSaidaAux)
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
