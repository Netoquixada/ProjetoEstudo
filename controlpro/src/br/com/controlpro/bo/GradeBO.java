package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.GradeDao;
import br.com.controlpro.entity.Grade;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class GradeBO {

	public static GradeBO getInstance() {
			return new GradeBO();
	}

	public void save(Grade Grade) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(Grade);
		GenericDao.save(Grade);
	}

	public void update(Grade Grade) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(Grade);
	}

	public Grade find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Grade.class, id);
	}

	public LazyEntityDataModel<Grade> gradesLazy(Grade Grade) {
		return GradeDao.getInstance().gradesLazy(Grade);
	}

	public List<Grade> gradesComplete(String GradeName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GradeDao.getInstance().grades(GradeName);
	}

	public List<Grade> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GradeDao.getInstance().grades();
	}

	public StringBuilder dadosFiltro() {
		return GradeDao.getInstance().getDadosFiltro();
	}

	public List<Grade> gradeListReport(Grade grade)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GradeDao.getInstance().gradeListReport(grade);
	}

	public void validation(Grade grade) {

	}

}