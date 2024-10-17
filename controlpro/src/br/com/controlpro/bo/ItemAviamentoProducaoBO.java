package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ItemAviamentoProducaoDao;
import br.com.controlpro.entity.ItemAviamentoProducao;
import br.com.controlpro.entity.OrdemProducao;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.IntegridadeObjetoHibernateException;
import br.hibernateutil.exception.ListaVaziaException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

public class ItemAviamentoProducaoBO {

	private ItemAviamentoProducaoBO() {
	}

	public static ItemAviamentoProducaoBO getInstance() {
		return new ItemAviamentoProducaoBO();
	}

	public void save(ItemAviamentoProducao itemAviamentoProducao)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.save(itemAviamentoProducao);
	}

	public void update(ItemAviamentoProducao itemAviamentoProducao)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.update(itemAviamentoProducao);
	}

	public void delete(ItemAviamentoProducao itemAviamentoProducao)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.delete(itemAviamentoProducao);
	}

	public List<ItemAviamentoProducao> itemAviamentoProducaoPorOrdemCorte(OrdemProducao ordemProducao)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ItemAviamentoProducaoDao.getInstance().itemAviamentoPorOrdemProducao(ordemProducao);
	}

	public void mergeAll(List<ItemAviamentoProducao> despesaSessenta)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.mergeAll(despesaSessenta);
	}

	public void updateAll(List<ItemAviamentoProducao> itemAviamentoProducao)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.updateAll(itemAviamentoProducao);
	}

	public void deleteAll(List<ItemAviamentoProducao> itemAviamentoProducaoAux)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		synchronized (ItemAviamentoProducao.class) {
			for (int i = 0; i < itemAviamentoProducaoAux.size(); i++) {
				GenericDao.delete(itemAviamentoProducaoAux.get(i));
			}
		}
	}

}
