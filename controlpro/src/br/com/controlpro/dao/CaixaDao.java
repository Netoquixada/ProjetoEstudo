package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.consultas.Caixa;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class CaixaDao {

	public static CaixaDao getInstance() {
		return new CaixaDao();
	}

	public LazyEntityDataModel<Caixa> caixasLazy(Caixa caixas) {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (caixas.getDataVendaInicio() != null && caixas.getDataVendaFim() != null) {
			restrictions.add(Restrictions.between("data", caixas.getDataVendaInicio(), caixas.getDataVendaFim()));
		}

		if (caixas.getDataVendaInicio() != null && caixas.getDataVendaFim() == null) {
			caixas.setDataVendaFim(caixas.getDataVendaInicio());
			restrictions.add(Restrictions.between("data", caixas.getDataVendaInicio(), caixas.getDataVendaFim()));
		}

		if (caixas.getTipoMov() != null && !caixas.getTipoMov().isEmpty()) {
			restrictions.add(Restrictions.like("tipoMov", caixas.getTipoMov(), MatchMode.ANYWHERE));
		}

		if (caixas.getDataVendaInicio() == null && caixas.getDataVendaFim() != null) {
			caixas.setDataVendaInicio(caixas.getDataVendaFim());
			restrictions.add(Restrictions.between("data", caixas.getDataVendaInicio(), caixas.getDataVendaFim()));
		}

		// Deu erro aqui
		return new LazyEntityDataModel<Caixa>(Caixa.class, null, null, restrictions, null);
	}

	public List<Caixa> caixas(String caixas) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();
		return GenericDao.findAllByAttributesRestrictions(Caixa.class, null, true, restrictions);
	}

	public List<Caixa> caixas(Caixa caixas) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (caixas.getTipoMov() != null && !caixas.getTipoMov().isEmpty()) {
			restrictions.add(Restrictions.like("tipoMov", caixas.getTipoMov(), MatchMode.ANYWHERE));
			dadosFiltro.append("TIPOMOV: " + caixas.getTipoMov());
		}

		if (caixas.getDataVendaInicio() != null && caixas.getDataVendaFim() != null) {
			restrictions.add(Restrictions.between("data", caixas.getDataVendaInicio(), caixas.getDataVendaFim()));
		}

		if (caixas.getDataVendaInicio() != null && caixas.getDataVendaFim() == null) {
			caixas.setDataVendaFim(caixas.getDataVendaInicio());
			restrictions.add(Restrictions.between("data", caixas.getDataVendaInicio(), caixas.getDataVendaFim()));
		}

		if (caixas.getDataVendaInicio() == null && caixas.getDataVendaFim() != null) {
			caixas.setDataVendaInicio(caixas.getDataVendaFim());
			restrictions.add(Restrictions.between("data", caixas.getDataVendaInicio(), caixas.getDataVendaFim()));
		}
		return GenericDao.findAllByAttributesRestrictions(Caixa.class, "data", true, restrictions);
	}

	public List<Caixa> caixasDinheiro(Caixa caixas) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (caixas.getTipoMov() != null && !caixas.getTipoMov().isEmpty()) {
			restrictions.add(Restrictions.like("tipoMov", caixas.getTipoMov(), MatchMode.ANYWHERE));
			dadosFiltro.append("TIPOMOV: " + caixas.getTipoMov());
		}

		restrictions.add(Restrictions.ne("dinheiro", 0.0f));

		if (caixas.getDataVendaInicio() != null && caixas.getDataVendaFim() != null) {
			restrictions.add(Restrictions.between("data", caixas.getDataVendaInicio(), caixas.getDataVendaFim()));
		}

		if (caixas.getDataVendaInicio() != null && caixas.getDataVendaFim() == null) {
			caixas.setDataVendaFim(caixas.getDataVendaInicio());
			restrictions.add(Restrictions.between("data", caixas.getDataVendaInicio(), caixas.getDataVendaFim()));
		}

		if (caixas.getDataVendaInicio() == null && caixas.getDataVendaFim() != null) {
			caixas.setDataVendaInicio(caixas.getDataVendaFim());
			restrictions.add(Restrictions.between("data", caixas.getDataVendaInicio(), caixas.getDataVendaFim()));
		}
		return GenericDao.findAllByAttributesRestrictions(Caixa.class, "data", true, restrictions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
