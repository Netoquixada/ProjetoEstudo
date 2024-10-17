package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.DespesaFixa;
import br.com.controlpro.entity.ItemDespesaFixa;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class ItemDespesaFixaDao {

	private ItemDespesaFixaDao() {
	}

	public static ItemDespesaFixaDao getInstance() {
		return new ItemDespesaFixaDao();
	}

	public List<ItemDespesaFixa> itemDespesaFixaPorDespesaFixa(DespesaFixa despesaFixa)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (despesaFixa != null) {
			restrictions.add(Restrictions.eq("despesaFixa", despesaFixa));
		}

		return GenericDao.findAllByAttributesRestrictions(ItemDespesaFixa.class,
				null, true, restrictions);
	}
}
