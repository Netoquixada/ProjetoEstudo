package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.EntradaMateriaPrima;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class EntradaMateriaPrimaDao {

	public static EntradaMateriaPrimaDao getInstance() {
		return new EntradaMateriaPrimaDao();
	}

	public LazyEntityDataModel<EntradaMateriaPrima> entradaMateriaPrimasLazy(EntradaMateriaPrima entradaMateriaPrima) {
		List<Criterion> restrictions = new ArrayList<Criterion>();
		
		if (entradaMateriaPrima.getFornecedor() != null) {
			restrictions.add(Restrictions.eq("fornecedor", entradaMateriaPrima.getFornecedor()));
		}
		
		return new LazyEntityDataModel<EntradaMateriaPrima>(EntradaMateriaPrima.class, null, null, restrictions, null);
	}

	public List<EntradaMateriaPrima> entradaMateriaPrimas(String entradaMateriaPrima)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();
		return GenericDao.findAllByAttributesRestrictions(EntradaMateriaPrima.class, null, true, restrictions);
	}

	public List<EntradaMateriaPrima> entradaMateriaPrimas() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(EntradaMateriaPrima.class, null, true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<EntradaMateriaPrima> entradaMateriaPrimasListReport(EntradaMateriaPrima entradaMateriaPrima)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (entradaMateriaPrima.getFornecedor() != null) {
			restrictions.add(Restrictions.eq("fornecedor", entradaMateriaPrima.getFornecedor()));
			dadosFiltro.append("FORNECEDOR: " + entradaMateriaPrima.getFornecedor());
		}
		
		if (entradaMateriaPrima.getDataInicio() != null && entradaMateriaPrima.getDataFim() != null) {
			restrictions.add(Restrictions.between("dataCadastro", entradaMateriaPrima.getDataInicio(), entradaMateriaPrima.getDataFim()));
		}

		if (entradaMateriaPrima.getDataInicio() != null && entradaMateriaPrima.getDataFim() == null) {
			entradaMateriaPrima.setDataFim(entradaMateriaPrima.getDataInicio());
			restrictions.add(Restrictions.between("dataCadastro", entradaMateriaPrima.getDataInicio(), entradaMateriaPrima.getDataFim()));
		}

		if (entradaMateriaPrima.getDataInicio() == null && entradaMateriaPrima.getDataFim() != null) {
			entradaMateriaPrima.setDataInicio(entradaMateriaPrima.getDataFim());
			restrictions.add(Restrictions.between("dataCadastro", entradaMateriaPrima.getDataInicio(), entradaMateriaPrima.getDataFim()));
		}
		
		if(entradaMateriaPrima.getLoja() != null){
			restrictions.add(Restrictions.eq("loja", entradaMateriaPrima.getLoja()));
		}
		return GenericDao.findAllByAttributesRestrictions(EntradaMateriaPrima.class, null, true, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
