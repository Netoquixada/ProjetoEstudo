package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ItemDespesaFixaDao;
import br.com.controlpro.entity.ItemDespesaFixa;
import br.com.controlpro.entity.DespesaFixa;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.IntegridadeObjetoHibernateException;
import br.hibernateutil.exception.ListaVaziaException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

public class ItemDespesaFixaBO {

	private ItemDespesaFixaBO() {
	}

	public static ItemDespesaFixaBO getInstance() {
		return new ItemDespesaFixaBO();
	}

	public void save(ItemDespesaFixa itemDespesaFixa) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.save(itemDespesaFixa);
	}

	public void update(ItemDespesaFixa itemDespesaFixa)
			throws ViolacaoChaveHibernateException, ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.update(itemDespesaFixa);
	}

	public void delete(ItemDespesaFixa itemDespesaFixa) throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.delete(itemDespesaFixa);
	}

	public List<ItemDespesaFixa> itemDespesaFixaPorDespesaFixa(DespesaFixa despesaFixa)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ItemDespesaFixaDao.getInstance().itemDespesaFixaPorDespesaFixa(despesaFixa);
	}

	public void mergeAll(List<ItemDespesaFixa> despesaSessenta) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException, ListaVaziaException,
			ObjetoNaoEncontradoHibernateException {
		GenericDao.mergeAll(despesaSessenta);
	}

	public void updateAll(List<ItemDespesaFixa> itemDespesaFixa) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException, ListaVaziaException,
			ObjetoNaoEncontradoHibernateException {
		GenericDao.updateAll(itemDespesaFixa);
	}

	public void deleteAll(List<ItemDespesaFixa> itemDespesaFixaAux) throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		synchronized (ItemDespesaFixa.class) {
			for (int i = 0; i < itemDespesaFixaAux.size(); i++) {
				GenericDao.delete(itemDespesaFixaAux.get(i));
			}
		}
	}

}
