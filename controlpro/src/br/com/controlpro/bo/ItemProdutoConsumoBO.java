package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ItemProdutoConsumoDao;
import br.com.controlpro.entity.ItemProdutoConsumo;
import br.com.controlpro.entity.ProdutoConsumo;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.IntegridadeObjetoHibernateException;
import br.hibernateutil.exception.ListaVaziaException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

public class ItemProdutoConsumoBO {

	private ItemProdutoConsumoBO() {
	}

	public static ItemProdutoConsumoBO getInstance() {
		return new ItemProdutoConsumoBO();
	}

	public void save(ItemProdutoConsumo itemProdutoConsumo)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.save(itemProdutoConsumo);
	}

	public void update(ItemProdutoConsumo itemProdutoConsumo)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.update(itemProdutoConsumo);
	}

	public void delete(ItemProdutoConsumo itemProdutoConsumo)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.delete(itemProdutoConsumo);
	}

	public List<ItemProdutoConsumo> itemPorProdutoConsumo(ProdutoConsumo produtoConsumo)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ItemProdutoConsumoDao.getInstance().itemPorProdutoConsumo(produtoConsumo);
	}

	public void mergeAll(List<ItemProdutoConsumo> despesaSessenta)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.mergeAll(despesaSessenta);
	}

	public void updateAll(List<ItemProdutoConsumo> itemProdutoConsumo)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.updateAll(itemProdutoConsumo);
	}

	public void deleteAll(List<ItemProdutoConsumo> itemProdutoConsumoAux)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		synchronized (ItemProdutoConsumo.class) {
			for (int i = 0; i < itemProdutoConsumoAux.size(); i++) {
				GenericDao.delete(itemProdutoConsumoAux.get(i));
			}
		}
	}

}
