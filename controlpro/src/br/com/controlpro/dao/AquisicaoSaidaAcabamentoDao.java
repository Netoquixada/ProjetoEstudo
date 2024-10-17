package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.Acabamento;
import br.com.controlpro.entity.AquisicaoSaidaAcabamento;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class AquisicaoSaidaAcabamentoDao {

	private AquisicaoSaidaAcabamentoDao() {
	}

	public static AquisicaoSaidaAcabamentoDao getInstance() {
		return new AquisicaoSaidaAcabamentoDao();
	}

	public List<AquisicaoSaidaAcabamento> itemAquisicaoPorAcabamento(Acabamento acabamento)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (acabamento != null) {
			restrictions.add(Restrictions.eq("acabamento", acabamento));
		}

		return GenericDao.findAllByAttributesRestrictions(AquisicaoSaidaAcabamento.class,
				null, true, restrictions);
	}
}
