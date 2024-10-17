package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.ItemProdutoConsumo;
import br.com.controlpro.entity.ProdutoConsumo;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class ItemProdutoConsumoDao {

	private ItemProdutoConsumoDao() {
	}

	public static ItemProdutoConsumoDao getInstance() {
		return new ItemProdutoConsumoDao();
	}

	public List<ItemProdutoConsumo> itemPorProdutoConsumo(ProdutoConsumo produtoConsumo)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (produtoConsumo != null) {
			restrictions.add(Restrictions.eq("produtoConsumo", produtoConsumo));
		}

		return GenericDao.findAllByAttributesRestrictions(ItemProdutoConsumo.class,
				null, true, restrictions);
	}
}
