package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ItemEntradaMateriaPrimaDao;
import br.com.controlpro.entity.EntradaMateriaPrima;
import br.com.controlpro.entity.ItemEntradaMateriaPrima;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.IntegridadeObjetoHibernateException;
import br.hibernateutil.exception.ListaVaziaException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

public class ItemEntradaMateriaPrimaBO {

	private ItemEntradaMateriaPrimaBO() {
	}

	public static ItemEntradaMateriaPrimaBO getInstance() {
		return new ItemEntradaMateriaPrimaBO();
	}

	public void save(ItemEntradaMateriaPrima itemEntradaMateriaPrima)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.save(itemEntradaMateriaPrima);
	}

	public void update(ItemEntradaMateriaPrima itemEntradaMateriaPrima)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.update(itemEntradaMateriaPrima);
	}

	public void delete(ItemEntradaMateriaPrima itemEntradaMateriaPrima)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.delete(itemEntradaMateriaPrima);
	}

	public List<ItemEntradaMateriaPrima> itemPorEntradaMateriaPrima(EntradaMateriaPrima entradaMateriaPrima)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ItemEntradaMateriaPrimaDao.getInstance().itemPorEntradaMateriaPrima(entradaMateriaPrima);
	}

	public void mergeAll(List<ItemEntradaMateriaPrima> despesaSessenta)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.mergeAll(despesaSessenta);
	}

	public void updateAll(List<ItemEntradaMateriaPrima> itemEntradaMateriaPrima)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.updateAll(itemEntradaMateriaPrima);
	}

	public void deleteAll(List<ItemEntradaMateriaPrima> itemEntradaMateriaPrimaAux)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		synchronized (ItemEntradaMateriaPrima.class) {
			for (int i = 0; i < itemEntradaMateriaPrimaAux.size(); i++) {
				GenericDao.delete(itemEntradaMateriaPrimaAux.get(i));
			}
		}
	}

}
