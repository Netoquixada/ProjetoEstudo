package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.EntradaMateriaPrima;
import br.com.controlpro.entity.ItemEntradaMateriaPrima;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class ItemEntradaMateriaPrimaDao {

	private ItemEntradaMateriaPrimaDao() {
	}

	public static ItemEntradaMateriaPrimaDao getInstance() {
		return new ItemEntradaMateriaPrimaDao();
	}

	public List<ItemEntradaMateriaPrima> itemPorEntradaMateriaPrima(EntradaMateriaPrima entradaMateriaPrima)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (entradaMateriaPrima != null) {
			restrictions.add(Restrictions.eq("entradaMateriaPrima", entradaMateriaPrima));
		}

		return GenericDao.findAllByAttributesRestrictions(ItemEntradaMateriaPrima.class,
				null, true, restrictions);
	}
}
