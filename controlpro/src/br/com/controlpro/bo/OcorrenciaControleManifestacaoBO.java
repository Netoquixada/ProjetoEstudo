package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.OcorrenciaControleManifestacaoDAO;
import br.com.controlpro.entity.AquisicaoSaida;
import br.com.controlpro.entity.ControleManifestacao;
import br.com.controlpro.entity.OcorrenciaControleManifestacao;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.IntegridadeObjetoHibernateException;
import br.hibernateutil.exception.ListaVaziaException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

public class OcorrenciaControleManifestacaoBO {

	private OcorrenciaControleManifestacaoBO() {
	}

	public static OcorrenciaControleManifestacaoBO getInstance() {
		return new OcorrenciaControleManifestacaoBO();
	}

	public void save(OcorrenciaControleManifestacao OcorrenciaControleManifestacao)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.save(OcorrenciaControleManifestacao);
	}

	public void update(OcorrenciaControleManifestacao aquisicaoSaida)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.update(aquisicaoSaida);
	}

	public void delete(OcorrenciaControleManifestacao aquisicaoSaida)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.delete(aquisicaoSaida);
	}

	public List<OcorrenciaControleManifestacao> historicoPorControlePedido(ControleManifestacao ordem)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return OcorrenciaControleManifestacaoDAO.getInstance().ocorrenciaPorControleManifestacao(ordem);
	}

	public void mergeAll(List<OcorrenciaControleManifestacao> lista)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.mergeAll(lista);
	}

	public void updateAll(List<OcorrenciaControleManifestacao> historico)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.updateAll(historico);
	}

	public void deleteAll(List<OcorrenciaControleManifestacao> historicoAux)
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
