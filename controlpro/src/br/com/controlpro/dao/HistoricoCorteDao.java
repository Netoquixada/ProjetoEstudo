package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.HistoricoCorte;
import br.com.controlpro.entity.OrdemCorte;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class HistoricoCorteDao {

	public static HistoricoCorteDao getInstance() {
		return new HistoricoCorteDao();
	}

	public LazyEntityDataModel<HistoricoCorte> historicoCortesLazy(HistoricoCorte historicoCorte) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		return new LazyEntityDataModel<HistoricoCorte>(HistoricoCorte.class, null,
				null, restrictions, null);
	}

	public List<HistoricoCorte> historicoCortes()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(HistoricoCorte.class,
				null, true, restricions);
	}
	
	public List<HistoricoCorte> historicoCortesFiltro(HistoricoCorte historico)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		
		if(historico.getCortador() != null){
			restricions.add(Restrictions.eq("cortador", historico.getCortador()));
		}
		
		if(historico.getProduto() != null){
			restricions.add(Restrictions.eq("produto", historico.getProduto()));
		}
		
		if (historico.getDataPesquisaInicio() != null && historico.getDataPesquisaFim() != null) {
			restricions.add(Restrictions.between("dataCorte", historico.getDataPesquisaInicio(), historico.getDataPesquisaFim()));
		}

		if (historico.getDataPesquisaInicio() != null && historico.getDataPesquisaFim() == null) {
			historico.setDataPesquisaFim(historico.getDataPesquisaInicio());
			restricions.add(Restrictions.between("dataCorte", historico.getDataPesquisaInicio(), historico.getDataPesquisaFim()));
		}

		if (historico.getDataPesquisaInicio() == null && historico.getDataPesquisaFim() != null) {
			historico.setDataPesquisaInicio(historico.getDataPesquisaFim());
			restricions.add(Restrictions.between("dataCorte", historico.getDataPesquisaInicio(), historico.getDataPesquisaFim()));
		}
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(HistoricoCorte.class,
				null, true, restricions);
	}



	public List<HistoricoCorte> historicoPorOrdemCorte(OrdemCorte ordemCorte)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();
		if (ordemCorte != null) {
			restrictions.add(Restrictions.eq("ordemCorte", ordemCorte));
		}
		return GenericDao.findAllByAttributesRestrictions(HistoricoCorte.class,
				null, true, restrictions);
	}
}
