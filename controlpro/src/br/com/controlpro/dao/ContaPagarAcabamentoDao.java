package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.ContaPagarAcabamento;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.util.AliasUece;

public class ContaPagarAcabamentoDao {

	public static ContaPagarAcabamentoDao getInstance() {
		return new ContaPagarAcabamentoDao();
	}

	public List<ContaPagarAcabamento> contaPagarAcabamentoList()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(ContaPagarAcabamento.class,
				null, true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<ContaPagarAcabamento> contaPagarAcabamentoListReport(ContaPagarAcabamento contaPagar)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();
		
		List<AliasUece> aliases = new ArrayList<AliasUece>();
		
		aliases.add(new AliasUece("itemAcabamento.acabamento", "acabamento"));
		
		if (contaPagar.getSituacao() != null) {
			restrictions.add(Restrictions.eq("situacao", contaPagar.getSituacao()));
		}
		if (contaPagar.getItemAcabamento().getAcabamento().getFaccao() != null) {
			restrictions.add(Restrictions.eq("acabamento.faccao", contaPagar.getItemAcabamento().getAcabamento().getFaccao()));
		}
		
		if (contaPagar.getDataInicio() != null && contaPagar.getDataFim() != null) {
			restrictions.add(Restrictions.between("dataRecebimento", contaPagar.getDataInicio(), contaPagar.getDataFim()));
		}

		if (contaPagar.getDataInicio() != null && contaPagar.getDataFim() == null) {
			contaPagar.setDataFim(contaPagar.getDataInicio());
			restrictions.add(Restrictions.between("dataRecebimento", contaPagar.getDataInicio(), contaPagar.getDataFim()));
		}

		if (contaPagar.getDataInicio() == null && contaPagar.getDataFim() != null) {
			contaPagar.setDataInicio(contaPagar.getDataFim());
			restrictions.add(Restrictions.between("dataRecebimento", contaPagar.getDataInicio(), contaPagar.getDataFim()));
		}
		
		return GenericDao.findAllByAttributesRestrictions(ContaPagarAcabamento.class,
				null, true, restrictions,null);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
