package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.HistoricoCorteDao;
import br.com.controlpro.entity.HistoricoCorte;
import br.com.controlpro.entity.OrdemCorte;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class HistoricoCorteBO {

	public static HistoricoCorteBO getInstance() {
		return new HistoricoCorteBO();
	}

	public void save(HistoricoCorte historicoCorte) throws ViolacaoChaveHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException, ObjetoExistenteException {
		validation(historicoCorte);
		GenericDao.save(historicoCorte);
	}

	public void update(HistoricoCorte historicoCorte)
			throws ViolacaoChaveHibernateException, ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException, ObjetoExistenteException {
		GenericDao.update(historicoCorte);
	}

	public HistoricoCorte find(Integer id)
			throws ObjetoNaoEncontradoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(HistoricoCorte.class, id);
	}

	public LazyEntityDataModel<HistoricoCorte> historicoCorteLazy(HistoricoCorte historicoCorte) {
		return HistoricoCorteDao.getInstance().historicoCortesLazy(historicoCorte);
	}

	public List<HistoricoCorte> list() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return HistoricoCorteDao.getInstance().historicoCortes();
	}

	public List<HistoricoCorte> listFiltro(HistoricoCorte historico)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return HistoricoCorteDao.getInstance().historicoCortesFiltro(historico);
	}

	public List<HistoricoCorte> historicoCorte(OrdemCorte ordemCorte)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return HistoricoCorteDao.getInstance().historicoPorOrdemCorte(ordemCorte);
	}

	public void validation(HistoricoCorte ordemCorte) {

	}

}