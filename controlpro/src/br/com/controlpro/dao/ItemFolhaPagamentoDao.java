package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.ItemFolhaPagamento;
import br.com.controlpro.entity.FolhaPagamento;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class ItemFolhaPagamentoDao {

	private ItemFolhaPagamentoDao() {
	}

	public static ItemFolhaPagamentoDao getInstance() {
		return new ItemFolhaPagamentoDao();
	}

	public List<ItemFolhaPagamento> itemFolhaPagamentoPorFolhaPagamento(FolhaPagamento folhaPagamento)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (folhaPagamento != null) {
			restrictions.add(Restrictions.eq("folhaPagamento", folhaPagamento));
		}

		return GenericDao.findAllByAttributesRestrictions(ItemFolhaPagamento.class,
				null, true, restrictions);
	}
	
	public List<ItemFolhaPagamento> grades() throws SessaoNaoEncontradaParaEntidadeFornecidaException{
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(ItemFolhaPagamento.class, null, true, restricions);
	}
}
