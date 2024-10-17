package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ItemAcabamentoDao;
import br.com.controlpro.entity.Acabamento;
import br.com.controlpro.entity.ItemAcabamento;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.IntegridadeObjetoHibernateException;
import br.hibernateutil.exception.ListaVaziaException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

public class ItemAcabamentoBO {

	private ItemAcabamentoBO() {
	}

	public static ItemAcabamentoBO getInstance() {
		return new ItemAcabamentoBO();
	}

	public void save(ItemAcabamento itemAcabamento)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.save(itemAcabamento);
	}

	public void update(ItemAcabamento itemAcabamento)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.update(itemAcabamento);
	}

	public void delete(ItemAcabamento itemAcabamento)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.delete(itemAcabamento);
	}

	public List<ItemAcabamento> itemAcabamentoPorAcabamento(Acabamento acabamento)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ItemAcabamentoDao.getInstance().itemAcabamentoPorAcabamento(acabamento);
	}

	public List<ItemAcabamento> itens()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ItemAcabamentoDao.getInstance().itens();
	}

	public void mergeAll(List<ItemAcabamento> itemAcabamento)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.mergeAll(itemAcabamento);
	}

	public void updateAll(List<ItemAcabamento> itemAcabamento)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.updateAll(itemAcabamento);
	}

	public void deleteAll(List<ItemAcabamento> itemAcabamentoAux)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		synchronized (ItemAcabamento.class) {
			for (int i = 0; i < itemAcabamentoAux.size(); i++) {
				GenericDao.delete(itemAcabamentoAux.get(i));
			}
		}
	}

}
