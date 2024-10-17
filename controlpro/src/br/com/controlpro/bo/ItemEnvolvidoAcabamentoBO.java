package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ItemEnvolvidoAcabamentoDao;
import br.com.controlpro.entity.Acabamento;
import br.com.controlpro.entity.ItemEnvolvidoAcabamento;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.IntegridadeObjetoHibernateException;
import br.hibernateutil.exception.ListaVaziaException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

public class ItemEnvolvidoAcabamentoBO {

	private ItemEnvolvidoAcabamentoBO() {
	}

	public static ItemEnvolvidoAcabamentoBO getInstance() {
		return new ItemEnvolvidoAcabamentoBO();
	}

	public void save(ItemEnvolvidoAcabamento itemEnvolvidoAcabamento)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.save(itemEnvolvidoAcabamento);
	}

	public void update(ItemEnvolvidoAcabamento itemEnvolvidoAcabamento)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.update(itemEnvolvidoAcabamento);
	}

	public void delete(ItemEnvolvidoAcabamento itemEnvolvidoAcabamento)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.delete(itemEnvolvidoAcabamento);
	}

	public List<ItemEnvolvidoAcabamento> itemEnvolvidoAcabamentoPorAcabamento(Acabamento acabamento)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ItemEnvolvidoAcabamentoDao.getInstance().itemEnvolvidoAcabamentoPorAcabamento(acabamento);
	}

	public List<ItemEnvolvidoAcabamento> itens()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ItemEnvolvidoAcabamentoDao.getInstance().itens();
	}

	public void mergeAll(List<ItemEnvolvidoAcabamento> itemEnvolvidoAcabamento)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.mergeAll(itemEnvolvidoAcabamento);
	}

	public void updateAll(List<ItemEnvolvidoAcabamento> itemEnvolvidoAcabamento)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.updateAll(itemEnvolvidoAcabamento);
	}

	public void deleteAll(List<ItemEnvolvidoAcabamento> itemEnvolvidoAcabamentoAux)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		synchronized (ItemEnvolvidoAcabamento.class) {
			for (int i = 0; i < itemEnvolvidoAcabamentoAux.size(); i++) {
				GenericDao.delete(itemEnvolvidoAcabamentoAux.get(i));
			}
		}
	}

}
