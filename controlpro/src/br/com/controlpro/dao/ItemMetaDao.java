package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.Meta;
import br.com.controlpro.entity.ItemMeta;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class ItemMetaDao {

	private ItemMetaDao() {
	}

	public static ItemMetaDao getInstance() {
		return new ItemMetaDao();
	}

	public List<ItemMeta> itemMetaPorMeta(Meta meta)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (meta != null) {
			restrictions.add(Restrictions.eq("meta", meta));
		}

		return GenericDao.findAllByAttributesRestrictions(ItemMeta.class,
				null, true, restrictions);
	}
	
	public List<ItemMeta> itens() throws SessaoNaoEncontradaParaEntidadeFornecidaException{
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(ItemMeta.class, null, true, restricions);
	}
}
