package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.DespesaFixa;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class DespesaFixaDao {

	public static DespesaFixaDao getInstance() {
		return new DespesaFixaDao();
	}

	public LazyEntityDataModel<DespesaFixa> despesaFixasLazy(DespesaFixa despesaFixa) {
		List<Criterion> restrictions = new ArrayList<Criterion>();
		

		return new LazyEntityDataModel<DespesaFixa>(DespesaFixa.class, null,
				null, restrictions, null);
	}

	public List<DespesaFixa> despesaFixas(String despesaFixa)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		return GenericDao.findAllByAttributesRestrictions(DespesaFixa.class,
				null, true, restrictions);
	}

	public List<DespesaFixa> despesaFixas()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(DespesaFixa.class,
				null, true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<DespesaFixa> despesaFixaListReport(DespesaFixa despesaFixa)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		return GenericDao.findAllByAttributesRestrictions(DespesaFixa.class,
				null, true, restrictions);
	}
	
	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
