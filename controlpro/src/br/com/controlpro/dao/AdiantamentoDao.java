package br.com.controlpro.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.Adiantamento;
import br.com.controlpro.entity.Faccao;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class AdiantamentoDao {

	public static AdiantamentoDao getInstance() {
		return new AdiantamentoDao();
	}


	public List<Adiantamento> adiantamentos(String adiantamento) throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();
		return GenericDao.findAllByAttributesRestrictions(Adiantamento.class, null, true, restrictions);
	}

	public List<Adiantamento> adiantamentos() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(Adiantamento.class, "data", false, restricions);
	}
	
	public List<Adiantamento> adiantamentosPendentesPorFaccao(Faccao faccao) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("pago", false));
		if (faccao != null) {
			restricions.add(Restrictions.eq("faccao", faccao));
		}
		return GenericDao.findAllByAttributesRestrictions(Adiantamento.class, "data", false, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<Adiantamento> adiantamentoListReport(Adiantamento adiantamento) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (adiantamento.getStatusAdiantamento() != null) {
			restrictions.add(Restrictions.eq("statusAdiantamento", adiantamento.getStatusAdiantamento()));
			dadosFiltro.append("STATUS: " + adiantamento.getStatusAdiantamento());
		}
		
		if (adiantamento.getTipoAdiantamento() != null) {
			restrictions.add(Restrictions.eq("tipoAdiantamento", adiantamento.getTipoAdiantamento()));
			dadosFiltro.append("LOJA: " + adiantamento.getTipoAdiantamento());
		}

		if (adiantamento.getFaccao() != null) {
			restrictions.add(Restrictions.eq("faccao", adiantamento.getFaccao()));
			dadosFiltro.append(" Funcionário: " + adiantamento.getFaccao().getNome());
		}
		
		if (adiantamento.getDataPesquisaInicio() != null && adiantamento.getDataPesquisaFim() != null) {
			restrictions.add(Restrictions.between("data", adiantamento.getDataPesquisaInicio(), adiantamento.getDataPesquisaFim()));
			dadosFiltro.append("DATA INICIO: " + new SimpleDateFormat("dd/MM/yyyy").format(adiantamento.getDataPesquisaInicio())
					+ " - DATA FINAL:" + new SimpleDateFormat("dd/MM/yyyy").format(adiantamento.getDataPesquisaFim()) + "/ ");
		}

		if (adiantamento.getDataPesquisaInicio() != null && adiantamento.getDataPesquisaFim() == null) {
			adiantamento.setDataPesquisaFim(adiantamento.getDataPesquisaInicio());
			restrictions.add(Restrictions.between("data", adiantamento.getDataPesquisaInicio(), adiantamento.getDataPesquisaFim()));
			dadosFiltro.append("DATA INICIO: " + new SimpleDateFormat("dd/MM/yyyy").format(adiantamento.getDataPesquisaInicio())
					+ " - DATA FINAL:" + new SimpleDateFormat("dd/MM/yyyy").format(adiantamento.getDataPesquisaFim()) + "/ ");
		}

		if (adiantamento.getDataPesquisaInicio() == null && adiantamento.getDataPesquisaFim() != null) {
			adiantamento.setDataPesquisaInicio(adiantamento.getDataPesquisaFim());
			restrictions.add(Restrictions.between("data", adiantamento.getDataPesquisaInicio(), adiantamento.getDataPesquisaFim()));
			dadosFiltro.append("DATA INICIO: " + new SimpleDateFormat("dd/MM/yyyy").format(adiantamento.getDataPesquisaInicio())
					+ " - DATA FINAL:" + new SimpleDateFormat("dd/MM/yyyy").format(adiantamento.getDataPesquisaFim()) + "/ ");
		}

		return GenericDao.findAllByAttributesRestrictions(Adiantamento.class, "data", false, restrictions);
	}
	public List<Adiantamento> adiantamentoListReportPagamentos(Adiantamento adiantamento) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();
		
		if (adiantamento.getFaccao() != null) {
			restrictions.add(Restrictions.eq("faccao", adiantamento.getFaccao()));
		}
		
		if (adiantamento.getTipoAdiantamento() != null) {
			restrictions.add(Restrictions.eq("tipoAdiantamento", adiantamento.getTipoAdiantamento()));
		}
		
		if (adiantamento.getDataPesquisaInicio() != null && adiantamento.getDataPesquisaFim() != null) {
			restrictions.add(Restrictions.between("data", adiantamento.getDataPesquisaInicio(), adiantamento.getDataPesquisaFim()));
		}
		
		if (adiantamento.getDataPesquisaInicio() != null && adiantamento.getDataPesquisaFim() == null) {
			adiantamento.setDataPesquisaFim(adiantamento.getDataPesquisaInicio());
			restrictions.add(Restrictions.between("data", adiantamento.getDataPesquisaInicio(), adiantamento.getDataPesquisaFim()));
		}
		
		if (adiantamento.getDataPesquisaInicio() == null && adiantamento.getDataPesquisaFim() != null) {
			adiantamento.setDataPesquisaInicio(adiantamento.getDataPesquisaFim());
			restrictions.add(Restrictions.between("data", adiantamento.getDataPesquisaInicio(), adiantamento.getDataPesquisaFim()));
		}
		
		restrictions.add(Restrictions.eq("pago", true));
		
		return GenericDao.findAllByAttributesRestrictions(Adiantamento.class, "data", false, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
