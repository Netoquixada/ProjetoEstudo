package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ItemControleAjusteDao;
import br.com.controlpro.entity.ControleAjuste;
import br.com.controlpro.entity.ItemControleAjuste;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.IntegridadeObjetoHibernateException;
import br.hibernateutil.exception.ListaVaziaException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

public class ItemControleAjusteBO {

	private ItemControleAjusteBO() {
	}

	public static ItemControleAjusteBO getInstance() {
		return new ItemControleAjusteBO();
	}

	public void save(ItemControleAjuste itemControleAjuste)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.save(itemControleAjuste);
	}

	public void update(ItemControleAjuste itemControleAjuste)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.update(itemControleAjuste);
	}

	public void delete(ItemControleAjuste itemControleAjuste)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.delete(itemControleAjuste);
	}

	public List<ItemControleAjuste> itemPorControleAjuste(ControleAjuste controleAjuste)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ItemControleAjusteDao.getInstance().itemPorControleAjuste(controleAjuste);
	}

	public void mergeAll(List<ItemControleAjuste> despesaSessenta)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.mergeAll(despesaSessenta);
	}

	public void updateAll(List<ItemControleAjuste> itemControleAjuste)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.updateAll(itemControleAjuste);
	}

	public void deleteAll(List<ItemControleAjuste> itemControleAjusteAux)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		synchronized (ItemControleAjuste.class) {
			for (int i = 0; i < itemControleAjusteAux.size(); i++) {
				GenericDao.delete(itemControleAjusteAux.get(i));
			}
		}
	}

}
