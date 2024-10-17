package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.ControleInterno;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ControleInternoDao {

	public static ControleInternoDao getInstance() {
		return new ControleInternoDao();
	}

	public LazyEntityDataModel<ControleInterno> controleInternosLazy(ControleInterno controleInterno) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (controleInterno.getRelatoControleInterno() != null) {
			restrictions.add(Restrictions.eq("relatoControleInterno", controleInterno.getRelatoControleInterno()));
		}

		if (controleInterno.getDataPesquisaInicio() != null && controleInterno.getDataPesquisaFim() != null) {
			restrictions.add(Restrictions.between("dataCadastro", controleInterno.getDataPesquisaInicio(), controleInterno.getDataPesquisaFim()));
		}

		if (controleInterno.getDataPesquisaInicio() != null && controleInterno.getDataPesquisaFim() == null) {
			controleInterno.setDataPesquisaFim(controleInterno.getDataPesquisaInicio());
			restrictions.add(Restrictions.between("dataCadastro", controleInterno.getDataPesquisaInicio(), controleInterno.getDataPesquisaFim()));
		}

		if (controleInterno.getDataPesquisaInicio() == null && controleInterno.getDataPesquisaFim() != null) {
			controleInterno.setDataPesquisaInicio(controleInterno.getDataPesquisaFim());
			restrictions.add(Restrictions.between("dataCadastro", controleInterno.getDataPesquisaInicio(), controleInterno.getDataPesquisaFim()));
		}

		return new LazyEntityDataModel<ControleInterno>(ControleInterno.class, null, null, restrictions, null);
	}

	public List<ControleInterno> controleInternos(String controleInterno) throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();
		return GenericDao.findAllByAttributesRestrictions(ControleInterno.class, null, true, restrictions);
	}

	public List<ControleInterno> controleInternos() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(ControleInterno.class, "dataCadastro", false, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<ControleInterno> controleInternoListReport(ControleInterno controleInterno) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (controleInterno.getRelatoControleInterno() != null) {
			restrictions.add(Restrictions.eq("relatoControleInterno", controleInterno.getRelatoControleInterno()));
		}

		if (controleInterno.getDataPesquisaInicio() != null && controleInterno.getDataPesquisaFim() != null) {
			restrictions.add(Restrictions.between("dataCadastro", controleInterno.getDataPesquisaInicio(), controleInterno.getDataPesquisaFim()));
		}

		if (controleInterno.getDataPesquisaInicio() != null && controleInterno.getDataPesquisaFim() == null) {
			controleInterno.setDataPesquisaFim(controleInterno.getDataPesquisaInicio());
			restrictions.add(Restrictions.between("dataCadastro", controleInterno.getDataPesquisaInicio(), controleInterno.getDataPesquisaFim()));
		}

		if (controleInterno.getDataPesquisaInicio() == null && controleInterno.getDataPesquisaFim() != null) {
			controleInterno.setDataPesquisaInicio(controleInterno.getDataPesquisaFim());
			restrictions.add(Restrictions.between("dataCadastro", controleInterno.getDataPesquisaInicio(), controleInterno.getDataPesquisaFim()));
		}

		return GenericDao.findAllByAttributesRestrictions(ControleInterno.class, "dataCadastro", false, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
