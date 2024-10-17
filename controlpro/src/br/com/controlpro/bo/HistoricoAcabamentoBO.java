package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.HistoricoAcabamentoDao;
import br.com.controlpro.entity.Acabamento;
import br.com.controlpro.entity.HistoricoAcabamento;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class HistoricoAcabamentoBO {

	public static HistoricoAcabamentoBO getInstance() {
		return new HistoricoAcabamentoBO();
	}

	public void save(HistoricoAcabamento historicoAcabamento)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(historicoAcabamento);
		GenericDao.save(historicoAcabamento);
	}

	public void update(HistoricoAcabamento historicoAcabamento)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(historicoAcabamento);
	}

	public HistoricoAcabamento find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(HistoricoAcabamento.class, id);
	}

	public LazyEntityDataModel<HistoricoAcabamento> historicoAcabamentoLazy(
			HistoricoAcabamento historicoAcabamento) {
		return HistoricoAcabamentoDao.getInstance().historicoAcabamentosLazy(historicoAcabamento);
	}
	
	public List<HistoricoAcabamento> listFiltro(HistoricoAcabamento historico)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return HistoricoAcabamentoDao.getInstance().historicoAcabamentoFiltro(historico);
	}


	public List<HistoricoAcabamento> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return HistoricoAcabamentoDao.getInstance().historicoAcabamentos();
	}

	public List<HistoricoAcabamento> historicoAcabamento(Acabamento acabamento)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return HistoricoAcabamentoDao.getInstance().historicoPorAcabamento(acabamento);
	}

	public void validation(HistoricoAcabamento ordemCorte) {

	}

}