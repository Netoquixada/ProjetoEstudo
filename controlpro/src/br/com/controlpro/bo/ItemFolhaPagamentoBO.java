package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ItemFolhaPagamentoDao;
import br.com.controlpro.entity.ItemFolhaPagamento;
import br.com.controlpro.entity.FolhaPagamento;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.IntegridadeObjetoHibernateException;
import br.hibernateutil.exception.ListaVaziaException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

public class ItemFolhaPagamentoBO {

	private ItemFolhaPagamentoBO() {
	}

	public static ItemFolhaPagamentoBO getInstance() {
		return new ItemFolhaPagamentoBO();
	}

	public void save(ItemFolhaPagamento itemFolhaPagamento)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.save(itemFolhaPagamento);
	}

	public void update(ItemFolhaPagamento itemFolhaPagamento)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.update(itemFolhaPagamento);
	}

	public void delete(ItemFolhaPagamento itemFolhaPagamento)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.delete(itemFolhaPagamento);
	}

	public List<ItemFolhaPagamento> itemFolhaPagamentoPorFolhaPagamento(FolhaPagamento ordemCorte)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ItemFolhaPagamentoDao.getInstance().itemFolhaPagamentoPorFolhaPagamento(ordemCorte);
	}

	public List<ItemFolhaPagamento> grades()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ItemFolhaPagamentoDao.getInstance().grades();
	}
	
	public void mergeAll(List<ItemFolhaPagamento> despesaSessenta)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.mergeAll(despesaSessenta);
	}

	public void updateAll(List<ItemFolhaPagamento> itemFolhaPagamento)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.updateAll(itemFolhaPagamento);
	}
	
	public void deleteAll(List<ItemFolhaPagamento> itemFolhaPagamentoAux)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		synchronized (ItemFolhaPagamento.class) {
			for (int i = 0; i < itemFolhaPagamentoAux.size(); i++) {
				GenericDao.delete(itemFolhaPagamentoAux.get(i));
			}
		}
	}

}
