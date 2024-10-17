package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.Acabamento;
import br.com.controlpro.entity.HistoricoAcabamento;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class HistoricoAcabamentoDao {

	public static HistoricoAcabamentoDao getInstance() {
		return new HistoricoAcabamentoDao();
	}

	public LazyEntityDataModel<HistoricoAcabamento> historicoAcabamentosLazy(HistoricoAcabamento historicoAcabamento) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		return new LazyEntityDataModel<HistoricoAcabamento>(HistoricoAcabamento.class, null,
				null, restrictions, null);
	}

	public List<HistoricoAcabamento> historicoAcabamentos()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(HistoricoAcabamento.class,
				null, true, restricions);
	}
	
	public List<HistoricoAcabamento> historicoAcabamentoFiltro(HistoricoAcabamento historico)
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
		return GenericDao.findAllByAttributesRestrictions(HistoricoAcabamento.class,
				"dataRecebimento", false, restricions);
	}


	public List<HistoricoAcabamento> historicoPorAcabamento(Acabamento acabamento)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();
		if (acabamento != null) {
			restrictions.add(Restrictions.eq("acabamento", acabamento));
		}
		return GenericDao.findAllByAttributesRestrictions(HistoricoAcabamento.class,
				null, true, restrictions);
	}
}
