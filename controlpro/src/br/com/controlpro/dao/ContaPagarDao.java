package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.ContaPagar;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;
import br.hibernateutil.util.AliasUece;

public class ContaPagarDao {

	public static ContaPagarDao getInstance() {
		return new ContaPagarDao();
	}

	public LazyEntityDataModel<ContaPagar> contaPagarsLazy(ContaPagar contaPagar) {
		List<Criterion> restrictions = new ArrayList<Criterion>();
		
//		List<AliasUece> alias = new ArrayList<AliasUece>();
//
//		alias.add(new AliasUece("itemProducao", "itemProducao", JoinType.INNER_JOIN));
//
//		if (contaPagar.getItemProducao().getProduto() != null) {
//			restrictions.add(Restrictions.eq("itemProducao.produto", contaPagar.getItemProducao().getProduto()));
//		}
		
		List<AliasUece> aliases = new ArrayList<AliasUece>();

		aliases.add(new AliasUece("itemProducao", "itemProducao"));
		
		if (contaPagar.getSituacao() != null) {
			restrictions.add(Restrictions.eq("situacao", contaPagar.getSituacao()));
		}
		if (contaPagar.getItemProducao().getFaccao() != null) {
			restrictions.add(Restrictions.eq("itemProducao.faccao", contaPagar.getItemProducao().getFaccao()));
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

		return new LazyEntityDataModel<ContaPagar>(ContaPagar.class, aliases,
				null, restrictions, null);
	}

	public List<ContaPagar> contaPagars()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(ContaPagar.class,
				null, true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<ContaPagar> contaPagarListReport(ContaPagar contaPagar)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();
		

		List<AliasUece> aliases = new ArrayList<AliasUece>();
		
		aliases.add(new AliasUece("itemProducao.ordemProducao", "ordemProducao"));
		
//		aliases.add(new AliasUece("itemProducao", "itemProducao"));
		
		if (contaPagar.getSituacao() != null) {
			restrictions.add(Restrictions.eq("situacao", contaPagar.getSituacao()));
		}
		if (contaPagar.getItemProducao().getOrdemProducao().getFaccao() != null) {
			restrictions.add(Restrictions.eq("ordemProducao.faccao", contaPagar.getItemProducao().getOrdemProducao().getFaccao()));
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
		
		return GenericDao.findAllByAttributesRestrictions(ContaPagar.class,
				null, true, restrictions,aliases);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
