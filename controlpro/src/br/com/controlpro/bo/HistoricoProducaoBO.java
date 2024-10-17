package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.HistoricoProducaoDao;
import br.com.controlpro.entity.HistoricoProducao;
import br.com.controlpro.entity.OrdemProducao;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class HistoricoProducaoBO {

	public static HistoricoProducaoBO getInstance() {
		return new HistoricoProducaoBO();
	}

	public void save(HistoricoProducao historicoProducao)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(historicoProducao);
		GenericDao.save(historicoProducao);
	}

	public void update(HistoricoProducao historicoProducao)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(historicoProducao);
	}

	public HistoricoProducao find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(HistoricoProducao.class, id);
	}

	public LazyEntityDataModel<HistoricoProducao> historicoProducaoLazy(
			HistoricoProducao historicoProducao) {
		return HistoricoProducaoDao.getInstance().historicoProducaosLazy(historicoProducao);
	}
	
	public List<HistoricoProducao> listFiltro(HistoricoProducao historico)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return HistoricoProducaoDao.getInstance().historicoProducaoFiltro(historico);
	}


	public List<HistoricoProducao> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return HistoricoProducaoDao.getInstance().historicoProducaos();
	}

	public List<HistoricoProducao> historicoProducao(OrdemProducao ordemProducao)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return HistoricoProducaoDao.getInstance().historicoPorOrdemProducao(ordemProducao);
	}
	
	
	public LazyEntityDataModel<HistoricoProducao> historicoReenviadoProducaoLazy(
			HistoricoProducao historicoProducao) {
		return HistoricoProducaoDao.getInstance().historicoReenviadoProducaosLazy(historicoProducao);
	}
	
	public List<HistoricoProducao> listReenviadoFiltro(HistoricoProducao historico)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return HistoricoProducaoDao.getInstance().historicoReenviadoProducaoFiltro(historico);
	}


	public List<HistoricoProducao> listReenviado()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return HistoricoProducaoDao.getInstance().historicoReenviadoProducaos();
	}

	public List<HistoricoProducao> historicoReenviadoProducao(OrdemProducao ordemProducao)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return HistoricoProducaoDao.getInstance().historicoReenviadoPorOrdemProducao(ordemProducao);
	}

	public void validation(HistoricoProducao ordemCorte) {

	}

}