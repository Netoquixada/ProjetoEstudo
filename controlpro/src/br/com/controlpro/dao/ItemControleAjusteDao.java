package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.ControleAjuste;
import br.com.controlpro.entity.ItemControleAjuste;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class ItemControleAjusteDao {

	private ItemControleAjusteDao() {
	}

	public static ItemControleAjusteDao getInstance() {
		return new ItemControleAjusteDao();
	}

	public List<ItemControleAjuste> itemPorControleAjuste(ControleAjuste controleAjuste)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (controleAjuste != null) {
			restrictions.add(Restrictions.eq("controleAjuste", controleAjuste));
		}

		return GenericDao.findAllByAttributesRestrictions(ItemControleAjuste.class,
				null, true, restrictions);
	}
}
