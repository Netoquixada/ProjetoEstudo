package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.Acabamento;
import br.com.controlpro.entity.ItemAcabamento;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class ItemAcabamentoDao {

	private ItemAcabamentoDao() {
	}

	public static ItemAcabamentoDao getInstance() {
		return new ItemAcabamentoDao();
	}

	public List<ItemAcabamento> itemAcabamentoPorAcabamento(Acabamento acabamento)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (acabamento != null) {
			restrictions.add(Restrictions.eq("acabamento", acabamento));
		}

		return GenericDao.findAllByAttributesRestrictions(ItemAcabamento.class,
				null, true, restrictions);
	}
	
	public List<ItemAcabamento> itens() throws SessaoNaoEncontradaParaEntidadeFornecidaException{
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(ItemAcabamento.class, null, true, restricions);
	}
}
