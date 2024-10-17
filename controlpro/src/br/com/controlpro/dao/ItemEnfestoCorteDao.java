package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.ItemEnfestoCorte;
import br.com.controlpro.entity.OrdemCorte;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class ItemEnfestoCorteDao {

	private ItemEnfestoCorteDao() {
	}

	public static ItemEnfestoCorteDao getInstance() {
		return new ItemEnfestoCorteDao();
	}

	public List<ItemEnfestoCorte> itemEnfestoPorOrdemCorte(OrdemCorte ordemCorte)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (ordemCorte != null) {
			restrictions.add(Restrictions.eq("ordemCorte", ordemCorte));
		}

		return GenericDao.findAllByAttributesRestrictions(ItemEnfestoCorte.class,
				null, true, restrictions);
	}
}
