package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.OcorrenciaOrdemProducao;
import br.com.controlpro.entity.OrdemProducao;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.util.AliasUece;

public class OcorrenciaOrdemProducaoDao {

	private OcorrenciaOrdemProducaoDao() {
	}

	public static OcorrenciaOrdemProducaoDao getInstance() {
		return new OcorrenciaOrdemProducaoDao();
	}

	public List<OcorrenciaOrdemProducao> ocorrenciaPorOrdemProducao(OrdemProducao ordem)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (ordem != null) {
			restrictions.add(Restrictions.eq("ordemProducao", ordem));
		}

		return GenericDao.findAllByAttributesRestrictions(OcorrenciaOrdemProducao.class,
				null, true, restrictions);
	}

	public List<OcorrenciaOrdemProducao> ocorrenciasFilter(OcorrenciaOrdemProducao ocorrencia)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		
		List<Criterion> restrictions = new ArrayList<Criterion>();
		List<AliasUece> aliases = new ArrayList<AliasUece>();
		
		aliases.add(new AliasUece("ordemProducao", "ordemProducao"));
		
		if(ocorrencia.getOrdemProducao().getProtocolo() != null && !ocorrencia.getOrdemProducao().getProtocolo().isEmpty()){
			restrictions.add(Restrictions.eq("ordemProducao.protocolo", ocorrencia.getOrdemProducao().getProtocolo()));
		}
		
		if(ocorrencia.getStatusOcorrenciaFilter() != 0) {
			restrictions.add(Restrictions.eq("ordemProducao.statusOcorrencia", ocorrencia.getStatusOcorrenciaFilter()));
		}
		
		return GenericDao.findAllByAttributesRestrictions(OcorrenciaOrdemProducao.class,
				null, true, restrictions,aliases);
		
	}
	
}
