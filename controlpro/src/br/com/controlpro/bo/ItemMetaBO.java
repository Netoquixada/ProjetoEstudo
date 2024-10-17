package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ItemMetaDao;
import br.com.controlpro.entity.Meta;
import br.com.controlpro.entity.ItemMeta;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.IntegridadeObjetoHibernateException;
import br.hibernateutil.exception.ListaVaziaException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

public class ItemMetaBO {

	private ItemMetaBO() {
	}

	public static ItemMetaBO getInstance() {
		return new ItemMetaBO();
	}

	public void save(ItemMeta itemMeta)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.save(itemMeta);
	}

	public void update(ItemMeta itemMeta)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.update(itemMeta);
	}

	public void delete(ItemMeta itemMeta)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.delete(itemMeta);
	}

	public List<ItemMeta> itemMetaPorMeta(Meta meta)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ItemMetaDao.getInstance().itemMetaPorMeta(meta);
	}

	public List<ItemMeta> itens()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ItemMetaDao.getInstance().itens();
	}

	public void mergeAll(List<ItemMeta> itemMeta)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.mergeAll(itemMeta);
	}

	public void updateAll(List<ItemMeta> itemMeta)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.updateAll(itemMeta);
	}

	public void deleteAll(List<ItemMeta> itemMetaAux)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		synchronized (ItemMeta.class) {
			for (int i = 0; i < itemMetaAux.size(); i++) {
				GenericDao.delete(itemMetaAux.get(i));
			}
		}
	}

}
