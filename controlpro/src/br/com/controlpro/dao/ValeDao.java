package br.com.controlpro.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.Vale;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ValeDao {

	public static ValeDao getInstance() {
		return new ValeDao();
	}

	public LazyEntityDataModel<Vale> valesLazy(Vale vale) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (vale.getStatusVale() != null) {
			restrictions.add(Restrictions.eq("statusVale", vale.getStatusVale()));
		}

		if (vale.getFuncionario() != null) {
			restrictions.add(Restrictions.eq("funcionario", vale.getFuncionario()));
		}

		if (vale.getDataPesquisaInicio() != null && vale.getDataPesquisaFim() != null) {
			restrictions.add(Restrictions.between("dataVale", vale.getDataPesquisaInicio(), vale.getDataPesquisaFim()));
		}

		if (vale.getDataPesquisaInicio() != null && vale.getDataPesquisaFim() == null) {
			vale.setDataPesquisaFim(vale.getDataPesquisaInicio());
			restrictions.add(Restrictions.between("dataVale", vale.getDataPesquisaInicio(), vale.getDataPesquisaFim()));
		}

		if (vale.getDataPesquisaInicio() == null && vale.getDataPesquisaFim() != null) {
			vale.setDataPesquisaInicio(vale.getDataPesquisaFim());
			restrictions.add(Restrictions.between("dataVale", vale.getDataPesquisaInicio(), vale.getDataPesquisaFim()));
		}

		return new LazyEntityDataModel<Vale>(Vale.class, null, null, restrictions, null);
	}

	public List<Vale> vales(String vale) throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();
		return GenericDao.findAllByAttributesRestrictions(Vale.class, null, true, restrictions);
	}

	public List<Vale> vales() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(Vale.class, "dataVale", false, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<Vale> valeListReport(Vale vale) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (vale.getStatusVale() != null) {
			restrictions.add(Restrictions.eq("statusVale", vale.getStatusVale()));
			dadosFiltro.append("STATUS: " + vale.getStatusVale());
		}
		
		if (vale.getLoja() != null) {
			restrictions.add(Restrictions.eq("loja", vale.getLoja()));
			dadosFiltro.append("LOJA: " + vale.getLoja());
		}

		if (vale.getFuncionario() != null) {
			restrictions.add(Restrictions.eq("funcionario", vale.getFuncionario()));
			dadosFiltro.append(" Funcionário: " + vale.getFuncionario().getNome());
		}
		
		if (vale.getDataPesquisaInicio() != null && vale.getDataPesquisaFim() != null) {
			restrictions.add(Restrictions.between("dataVale", vale.getDataPesquisaInicio(), vale.getDataPesquisaFim()));
			dadosFiltro.append("DATA INICIO: " + new SimpleDateFormat("dd/MM/yyyy").format(vale.getDataPesquisaInicio())
					+ " - DATA FINAL:" + new SimpleDateFormat("dd/MM/yyyy").format(vale.getDataPesquisaFim()) + "/ ");
		}

		if (vale.getDataPesquisaInicio() != null && vale.getDataPesquisaFim() == null) {
			vale.setDataPesquisaFim(vale.getDataPesquisaInicio());
			restrictions.add(Restrictions.between("dataVale", vale.getDataPesquisaInicio(), vale.getDataPesquisaFim()));
			dadosFiltro.append("DATA INICIO: " + new SimpleDateFormat("dd/MM/yyyy").format(vale.getDataPesquisaInicio())
					+ " - DATA FINAL:" + new SimpleDateFormat("dd/MM/yyyy").format(vale.getDataPesquisaFim()) + "/ ");
		}

		if (vale.getDataPesquisaInicio() == null && vale.getDataPesquisaFim() != null) {
			vale.setDataPesquisaInicio(vale.getDataPesquisaFim());
			restrictions.add(Restrictions.between("dataVale", vale.getDataPesquisaInicio(), vale.getDataPesquisaFim()));
			dadosFiltro.append("DATA INICIO: " + new SimpleDateFormat("dd/MM/yyyy").format(vale.getDataPesquisaInicio())
					+ " - DATA FINAL:" + new SimpleDateFormat("dd/MM/yyyy").format(vale.getDataPesquisaFim()) + "/ ");
		}

		return GenericDao.findAllByAttributesRestrictions(Vale.class, "dataVale", false, restrictions);
	}
	public List<Vale> valeListReportPagamentos(Vale vale) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();
		
		if (vale.getFuncionario() != null) {
			restrictions.add(Restrictions.eq("funcionario", vale.getFuncionario()));
		}
		
		if (vale.getLoja() != null) {
			restrictions.add(Restrictions.eq("loja", vale.getLoja()));
		}
		
		if (vale.getDataPesquisaInicio() != null && vale.getDataPesquisaFim() != null) {
			restrictions.add(Restrictions.between("dataPagamento", vale.getDataPesquisaInicio(), vale.getDataPesquisaFim()));
		}
		
		if (vale.getDataPesquisaInicio() != null && vale.getDataPesquisaFim() == null) {
			vale.setDataPesquisaFim(vale.getDataPesquisaInicio());
			restrictions.add(Restrictions.between("dataPagamento", vale.getDataPesquisaInicio(), vale.getDataPesquisaFim()));
		}
		
		if (vale.getDataPesquisaInicio() == null && vale.getDataPesquisaFim() != null) {
			vale.setDataPesquisaInicio(vale.getDataPesquisaFim());
			restrictions.add(Restrictions.between("dataPagamento", vale.getDataPesquisaInicio(), vale.getDataPesquisaFim()));
		}
		
		restrictions.add(Restrictions.eq("pago", true));
		
		return GenericDao.findAllByAttributesRestrictions(Vale.class, "dataPagamento", false, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
