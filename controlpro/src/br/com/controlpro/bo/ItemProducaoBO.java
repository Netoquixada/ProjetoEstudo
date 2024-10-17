package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ItemProducaoDao;
import br.com.controlpro.entity.ItemProducao;
import br.com.controlpro.entity.ItemProducaoAud;
import br.com.controlpro.entity.OrdemProducao;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.IntegridadeObjetoHibernateException;
import br.hibernateutil.exception.ListaVaziaException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

public class ItemProducaoBO {

	private ItemProducaoBO() {
	}

	public static ItemProducaoBO getInstance() {
		return new ItemProducaoBO();
	}

	public void save(ItemProducao itemProducao)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.save(itemProducao);
	}

	public void update(ItemProducao itemProducao)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.update(itemProducao);
	}

	public void delete(ItemProducao itemProducao)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.delete(itemProducao);
	}

	public List<ItemProducao> itemProducaoPorOrdemProducao(OrdemProducao ordemProducao)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ItemProducaoDao.getInstance().itemProducaoPorOrdemProducao(ordemProducao);
	}

	public List<ItemProducao> itens()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ItemProducaoDao.getInstance().itens();
	}

	public List<ItemProducao> itensFilter(ItemProducao item)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ItemProducaoDao.getInstance().itensFilter(item);
	}
	
	public List<ItemProducao> itemReenviarProducaoPorOrdemProducao(OrdemProducao ordemProducao)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ItemProducaoDao.getInstance().itemReenviadoProducaoPorOrdemProducao(ordemProducao);
	}

	public List<ItemProducao> itensReenviado()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ItemProducaoDao.getInstance().itensReenviado();
	}

	public List<ItemProducao> itensReenviadoFilter(ItemProducao item)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ItemProducaoDao.getInstance().itensReenviadoFilter(item);
	}
	
	public List<ItemProducaoAud> itensAudFilter(ItemProducaoAud item)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ItemProducaoDao.getInstance().itensAudFilter(item);
	}
	
	public void mergeAll(List<ItemProducao> despesaSessenta)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.mergeAll(despesaSessenta);
	}

	public void updateAll(List<ItemProducao> itemProducao)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.updateAll(itemProducao);
	}

	public void deleteAll(List<ItemProducao> itemProducaoAux)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		synchronized (ItemProducao.class) {
			for (int i = 0; i < itemProducaoAux.size(); i++) {
				GenericDao.delete(itemProducaoAux.get(i));
			}
		}
	}

}
