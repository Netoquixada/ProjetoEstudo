package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.Grade;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class GradeDao {

	public static GradeDao getInstance() {
		return new GradeDao();
	}

	public LazyEntityDataModel<Grade> gradesLazy(Grade grade) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (grade.getNome() != null && !grade.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", grade.getNome(),
					MatchMode.ANYWHERE));
		}
		return new LazyEntityDataModel<Grade>(Grade.class, null, null,
				restrictions, null);
	}

	public List<Grade> grades(String grade)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (grade != null && !grade.isEmpty()) {
			restrictions.add(Restrictions.like("nome", grade));
		}
		return GenericDao.findAllByAttributesRestrictions(Grade.class,
				"nome", true, restrictions);
	}

	public List<Grade> grades()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(Grade.class,
				"nome", true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<Grade> gradeListReport(Grade grade)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (grade.getNome() != null && !grade.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", grade.getNome(),
					MatchMode.ANYWHERE));

			dadosFiltro.append("NOME: " + grade.getNome());
		}
		return GenericDao.findAllByAttributesRestrictions(Grade.class,
				"nome", true, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
