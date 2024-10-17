package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.GradeOrdem;
import br.com.controlpro.entity.OrdemCorte;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class GradeOrdemDao {

	private GradeOrdemDao() {
	}

	public static GradeOrdemDao getInstance() {
		return new GradeOrdemDao();
	}

	public List<GradeOrdem> gradeOrdemPorOrdemCorte(OrdemCorte ordemCorte)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (ordemCorte != null) {
			restrictions.add(Restrictions.eq("ordemCorte", ordemCorte));
		}

		return GenericDao.findAllByAttributesRestrictions(GradeOrdem.class,
				null, true, restrictions);
	}
	
	public List<GradeOrdem> grades() throws SessaoNaoEncontradaParaEntidadeFornecidaException{
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(GradeOrdem.class, null, true, restricions);
	}
}
