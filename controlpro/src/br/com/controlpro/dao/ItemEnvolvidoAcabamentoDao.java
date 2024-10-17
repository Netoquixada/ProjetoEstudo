package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.Acabamento;
import br.com.controlpro.entity.ItemEnvolvidoAcabamento;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class ItemEnvolvidoAcabamentoDao {

	private ItemEnvolvidoAcabamentoDao() {
	}

	public static ItemEnvolvidoAcabamentoDao getInstance() {
		return new ItemEnvolvidoAcabamentoDao();
	}

	public List<ItemEnvolvidoAcabamento> itemEnvolvidoAcabamentoPorAcabamento(Acabamento acabamento)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (acabamento != null) {
			restrictions.add(Restrictions.eq("acabamento", acabamento));
		}

		return GenericDao.findAllByAttributesRestrictions(ItemEnvolvidoAcabamento.class,
				null, true, restrictions);
	}
	
	public List<ItemEnvolvidoAcabamento> itens() throws SessaoNaoEncontradaParaEntidadeFornecidaException{
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(ItemEnvolvidoAcabamento.class, null, true, restricions);
	}
}
