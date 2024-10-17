package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.HistoricoProducao;
import br.com.controlpro.entity.OrdemProducao;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class HistoricoProducaoDao {

	public static HistoricoProducaoDao getInstance() {
		return new HistoricoProducaoDao();
	}

	public LazyEntityDataModel<HistoricoProducao> historicoProducaosLazy(HistoricoProducao historicoProducao) {
		List<Criterion> restrictions = new ArrayList<Criterion>();
		restrictions.add(Restrictions.eq("itemReenviado", false));
		return new LazyEntityDataModel<HistoricoProducao>(HistoricoProducao.class, null,
				null, restrictions, null);
	}

	public List<HistoricoProducao> historicoProducaos()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		restricions.add(Restrictions.eq("itemReenviado", false));
		return GenericDao.findAllByAttributesRestrictions(HistoricoProducao.class,
				null, true, restricions);
	}
	
	public List<HistoricoProducao> historicoProducaoFiltro(HistoricoProducao historico)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		
		if(historico.getFaccao() != null){
			restricions.add(Restrictions.eq("faccao", historico.getFaccao()));
		}
		
		if(historico.getProduto() != null){
			restricions.add(Restrictions.eq("produto", historico.getProduto()));
		}
		
		if (historico.getDataPesquisaInicio() != null && historico.getDataPesquisaFim() != null) {
			restricions.add(Restrictions.between("dataRecebimento", historico.getDataPesquisaInicio(), historico.getDataPesquisaFim()));
		}

		if (historico.getDataPesquisaInicio() != null && historico.getDataPesquisaFim() == null) {
			historico.setDataPesquisaFim(historico.getDataPesquisaInicio());
			restricions.add(Restrictions.between("dataRecebimento", historico.getDataPesquisaInicio(), historico.getDataPesquisaFim()));
		}

		if (historico.getDataPesquisaInicio() == null && historico.getDataPesquisaFim() != null) {
			historico.setDataPesquisaInicio(historico.getDataPesquisaFim());
			restricions.add(Restrictions.between("dataRecebimento", historico.getDataPesquisaInicio(), historico.getDataPesquisaFim()));
		}
		restricions.add(Restrictions.eq("status", true));
		restricions.add(Restrictions.eq("itemReenviado", false));
		return GenericDao.findAllByAttributesRestrictions(HistoricoProducao.class,
				"dataRecebimento", false, restricions);
	}


	public List<HistoricoProducao> historicoPorOrdemProducao(OrdemProducao ordemProducao)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();
		if (ordemProducao != null) {
			restrictions.add(Restrictions.eq("ordemProducao", ordemProducao));
		}
		restrictions.add(Restrictions.eq("itemReenviado", false));
		return GenericDao.findAllByAttributesRestrictions(HistoricoProducao.class,
				null, true, restrictions);
	}
	
	public LazyEntityDataModel<HistoricoProducao> historicoReenviadoProducaosLazy(HistoricoProducao historicoProducao) {
		List<Criterion> restrictions = new ArrayList<Criterion>();
		restrictions.add(Restrictions.eq("itemReenviado", true));
		return new LazyEntityDataModel<HistoricoProducao>(HistoricoProducao.class, null,
				null, restrictions, null);
	}

	public List<HistoricoProducao> historicoReenviadoProducaos()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		restricions.add(Restrictions.eq("itemReenviado", true));
		return GenericDao.findAllByAttributesRestrictions(HistoricoProducao.class,
				null, true, restricions);
	}
	
	public List<HistoricoProducao> historicoReenviadoProducaoFiltro(HistoricoProducao historico)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		
		if(historico.getFaccao() != null){
			restricions.add(Restrictions.eq("faccao", historico.getFaccao()));
		}
		
		if(historico.getProduto() != null){
			restricions.add(Restrictions.eq("produto", historico.getProduto()));
		}
		
		if (historico.getDataPesquisaInicio() != null && historico.getDataPesquisaFim() != null) {
			restricions.add(Restrictions.between("dataRecebimento", historico.getDataPesquisaInicio(), historico.getDataPesquisaFim()));
		}

		if (historico.getDataPesquisaInicio() != null && historico.getDataPesquisaFim() == null) {
			historico.setDataPesquisaFim(historico.getDataPesquisaInicio());
			restricions.add(Restrictions.between("dataRecebimento", historico.getDataPesquisaInicio(), historico.getDataPesquisaFim()));
		}

		if (historico.getDataPesquisaInicio() == null && historico.getDataPesquisaFim() != null) {
			historico.setDataPesquisaInicio(historico.getDataPesquisaFim());
			restricions.add(Restrictions.between("dataRecebimento", historico.getDataPesquisaInicio(), historico.getDataPesquisaFim()));
		}
		restricions.add(Restrictions.eq("status", true));
		restricions.add(Restrictions.eq("itemReenviado", true));
		return GenericDao.findAllByAttributesRestrictions(HistoricoProducao.class,
				"dataRecebimento", false, restricions);
	}


	public List<HistoricoProducao> historicoReenviadoPorOrdemProducao(OrdemProducao ordemProducao)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();
		if (ordemProducao != null) {
			restrictions.add(Restrictions.eq("ordemProducao", ordemProducao));
		}
		restrictions.add(Restrictions.eq("itemReenviado", true));
		return GenericDao.findAllByAttributesRestrictions(HistoricoProducao.class,
				null, true, restrictions);
	}
}
