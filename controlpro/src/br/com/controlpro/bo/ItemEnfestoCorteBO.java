package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ItemEnfestoCorteDao;
import br.com.controlpro.entity.ItemEnfestoCorte;
import br.com.controlpro.entity.OrdemCorte;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.IntegridadeObjetoHibernateException;
import br.hibernateutil.exception.ListaVaziaException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

public class ItemEnfestoCorteBO {

	private ItemEnfestoCorteBO() {
	}

	public static ItemEnfestoCorteBO getInstance() {
		return new ItemEnfestoCorteBO();
	}

	public void save(ItemEnfestoCorte itemEnfestoCorte)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.save(itemEnfestoCorte);
	}

	public void update(ItemEnfestoCorte itemEnfestoCorte)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.update(itemEnfestoCorte);
	}

	public void delete(ItemEnfestoCorte itemEnfestoCorte)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.delete(itemEnfestoCorte);
	}

	public List<ItemEnfestoCorte> itemEnfestoCortePorOrdemCorte(OrdemCorte ordemCorte)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ItemEnfestoCorteDao.getInstance().itemEnfestoPorOrdemCorte(ordemCorte);
	}

	public void mergeAll(List<ItemEnfestoCorte> despesaSessenta)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.mergeAll(despesaSessenta);
	}

	public void updateAll(List<ItemEnfestoCorte> itemEnfestoCorte)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.updateAll(itemEnfestoCorte);
	}

	public void deleteAll(List<ItemEnfestoCorte> itemEnfestoCorteAux)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		synchronized (ItemEnfestoCorte.class) {
			for (int i = 0; i < itemEnfestoCorteAux.size(); i++) {
				GenericDao.delete(itemEnfestoCorteAux.get(i));
			}
		}
	}

}
