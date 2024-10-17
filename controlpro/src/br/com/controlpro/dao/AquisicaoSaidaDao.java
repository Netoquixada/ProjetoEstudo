package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.AquisicaoSaida;
import br.com.controlpro.entity.OrdemProducao;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class AquisicaoSaidaDao {

	private AquisicaoSaidaDao() {
	}

	public static AquisicaoSaidaDao getInstance() {
		return new AquisicaoSaidaDao();
	}

	public List<AquisicaoSaida> itemAviamentoPorOrdemProducao(OrdemProducao ordemProducao)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (ordemProducao != null) {
			restrictions.add(Restrictions.eq("ordemProducao", ordemProducao));
		}

		return GenericDao.findAllByAttributesRestrictions(AquisicaoSaida.class,
				null, true, restrictions);
	}
}
