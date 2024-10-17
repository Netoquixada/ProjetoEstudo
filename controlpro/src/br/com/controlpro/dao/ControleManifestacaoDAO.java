package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;

import br.com.controlpro.entity.ControleManifestacao;
import br.com.controlpro.entity.OcorrenciaControleManifestacao;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ControleManifestacaoDAO {

	public static ControleManifestacaoDAO getInstance() {
		return new ControleManifestacaoDAO();
	}

	public LazyEntityDataModel<ControleManifestacao> controleManifestacaosLazy(ControleManifestacao controleManifestacao) {
		List<Criterion> restrictions = new ArrayList<Criterion>();
		
		if (controleManifestacao.getProtocolo() != null && controleManifestacao.getProtocolo().isEmpty()) {
			restrictions.add(Restrictions.like("protocolo",
					controleManifestacao.getProtocolo(), MatchMode.ANYWHERE));
		}
		if (controleManifestacao.getCodigoRastreio() != null && controleManifestacao.getCodigoRastreio().isEmpty()) {
			restrictions.add(Restrictions.like("codigoRastreio",
					controleManifestacao.getCodigoRastreio(), MatchMode.ANYWHERE));
		}

		return new LazyEntityDataModel<ControleManifestacao>(ControleManifestacao.class, null,
				null, restrictions, null);
	}

	public List<ControleManifestacao> controleManifestacaos(String controleManifestacao)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		return GenericDao.findAllByAttributesRestrictions(ControleManifestacao.class,
				null, true, restrictions);
	}

	public List<ControleManifestacao> controleManifestacaos()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(ControleManifestacao.class,
				"dataCadastro", false, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<ControleManifestacao> controleManifestacaoListReport(ControleManifestacao controleManifestacao)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();
		
		if (controleManifestacao.getProtocolo() != null && !controleManifestacao.getProtocolo().isEmpty()) {
			restrictions.add(Restrictions.like("protocolo",
					controleManifestacao.getProtocolo(), MatchMode.ANYWHERE));
		}
		if (controleManifestacao.getCodigoRastreio() != null && !controleManifestacao.getCodigoRastreio().isEmpty()) {
			restrictions.add(Restrictions.like("codigoRastreio",
					controleManifestacao.getCodigoRastreio(), MatchMode.ANYWHERE));
		}
		
		if(controleManifestacao.getStatusOcorrencia() != 0) {
			restrictions.add(Restrictions.eq("statusOcorrencia", controleManifestacao.getStatusOcorrencia()));
		}

		return GenericDao.findAllByAttributesRestrictions(ControleManifestacao.class,
				"dataCadastro", false, restrictions);
	}
	
	public String geradorDeProtocolo()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<ControleManifestacao> list = GenericDao.getListaByDemanda(ControleManifestacao.class, 0, 1,
				null, null, "id", SortOrder.DESCENDING, null, null);
		Integer num = null;
		if (list != null && !list.isEmpty()) {
			num = Integer.valueOf(list.get(0).getId() + 1);
			return num.toString();
		}
		return "1";
	}
	
	public String geradorDeProtocoloOcorrencia()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<OcorrenciaControleManifestacao> list = GenericDao.getListaByDemanda(OcorrenciaControleManifestacao.class,
				0, 1, null, null, "id", SortOrder.DESCENDING, null, null);
		Integer num = null;
		if (list != null && !list.isEmpty()) {
			num = Integer.valueOf(list.get(0).getId() + 1);
			return num.toString();
		}
		return "1";
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
