package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.GradeOrdemDao;
import br.com.controlpro.entity.GradeOrdem;
import br.com.controlpro.entity.OrdemCorte;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.IntegridadeObjetoHibernateException;
import br.hibernateutil.exception.ListaVaziaException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

public class GradeOrdemBO {

	private GradeOrdemBO() {
	}

	public static GradeOrdemBO getInstance() {
		return new GradeOrdemBO();
	}

	public void save(GradeOrdem gradeOrdem)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.save(gradeOrdem);
	}

	public void update(GradeOrdem gradeOrdem)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.update(gradeOrdem);
	}

	public void delete(GradeOrdem gradeOrdem)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.delete(gradeOrdem);
	}

	public List<GradeOrdem> gradeOrdemPorOrdemCorte(OrdemCorte ordemCorte)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GradeOrdemDao.getInstance().gradeOrdemPorOrdemCorte(ordemCorte);
	}

	public List<GradeOrdem> grades()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GradeOrdemDao.getInstance().grades();
	}
	
	

	public void mergeAll(List<GradeOrdem> despesaSessenta)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.mergeAll(despesaSessenta);
	}

	public void updateAll(List<GradeOrdem> gradeOrdem)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.updateAll(gradeOrdem);
	}
	
	public Double totalEnviado() {
		Double result = 0.0;
		try {
			result = GenericDao.findEntityNotMappedBySQLQuery(Double.class, GradeOrdem.class,
					"SELECT SUM(QUANTIDADE) FROM CONTROL_GRADE_ORDEM");
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		if (result == null)
			return 0.0;
		return result;
	}
	public Double totalCortado() {
		Double result = 0.0;
		try {
			result = GenericDao.findEntityNotMappedBySQLQuery(Double.class, GradeOrdem.class,
					"SELECT SUM(PRONTAS) FROM CONTROL_GRADE_ORDEM");
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		if (result == null)
			return 0.0;
		return result;
	}

	public void deleteAll(List<GradeOrdem> gradeOrdemAux)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		synchronized (GradeOrdem.class) {
			for (int i = 0; i < gradeOrdemAux.size(); i++) {
				GenericDao.delete(gradeOrdemAux.get(i));
			}
		}
	}

}
