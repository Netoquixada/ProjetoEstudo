package br.com.controlpro.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;

import br.com.controlpro.entity.OcorrenciaOrdemProducao;
import br.com.controlpro.entity.OrdemProducao;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class OrdemProducaoDao {

	public static OrdemProducaoDao getInstance() {
		return new OrdemProducaoDao();
	}

	public LazyEntityDataModel<OrdemProducao> ordemProducaosLazy(OrdemProducao ordemProducao) {
		List<Criterion> restrictions = new ArrayList<Criterion>();
		
		if (ordemProducao.getProtocolo() != null && ordemProducao.getProtocolo().isEmpty()) {
			restrictions.add(Restrictions.like("protocolo",
					ordemProducao.getProtocolo(), MatchMode.ANYWHERE));
		}
		if (ordemProducao.getSituacao() != null) {
			restrictions.add(Restrictions.eq("situacao",
					ordemProducao.getSituacao()));
		}
		if (ordemProducao.getDataCadastroInicio() != null
				&& ordemProducao.getDataCadastroFim() != null) {
			restrictions.add(Restrictions.between("dataCadastro",
					ordemProducao.getDataCadastroInicio(),
					ordemProducao.getDataCadastroFim()));
		}

		if (ordemProducao.getDataCadastroInicio() != null
				&& ordemProducao.getDataCadastroFim() == null) {
			ordemProducao.setDataCadastroFim(ordemProducao.getDataCadastroInicio());
			restrictions.add(Restrictions.between("dataCadastro",
					ordemProducao.getDataCadastroInicio(),
					ordemProducao.getDataCadastroFim()));
		}

		if (ordemProducao.getDataCadastroInicio() == null
				&& ordemProducao.getDataCadastroFim() != null) {
			ordemProducao.setDataCadastroInicio(ordemProducao.getDataCadastroFim());
			restrictions.add(Restrictions.between("dataCadastro",
					ordemProducao.getDataCadastroInicio(),
					ordemProducao.getDataCadastroFim()));
		}


		return new LazyEntityDataModel<OrdemProducao>(OrdemProducao.class, null,
				null, restrictions, null);
	}

	public List<OrdemProducao> ordemProducaos(String ordemProducao)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		return GenericDao.findAllByAttributesRestrictions(OrdemProducao.class,
				null, true, restrictions);
	}

	public List<OrdemProducao> ordemProducaos()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(OrdemProducao.class,
				"dataCadastro", false, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<OrdemProducao> ordemProducaoListReport(OrdemProducao ordemProducao)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();
		if (ordemProducao.getProtocolo() != null) {
			restrictions.add(Restrictions.like("protocolo",
					ordemProducao.getProtocolo(), MatchMode.ANYWHERE));
			dadosFiltro.append("PROTOCOLO: " + ordemProducao.getProtocolo());
		}
		if (ordemProducao.getTipoCostura() != null) {
			restrictions.add(Restrictions.eq("tipoCostura",
					ordemProducao.getSituacao()));
			dadosFiltro.append("TIPO COSTURA: " + ordemProducao.getTipoCostura());
		}
		if (ordemProducao.getFaccao() != null) {
			restrictions.add(Restrictions.eq("faccao",
					ordemProducao.getFaccao()));
			dadosFiltro.append("FACÇÃO: " + ordemProducao.getFaccao());
		}
		if (ordemProducao.getColecao() != null) {
			restrictions.add(Restrictions.eq("colecao",
					ordemProducao.getColecao()));
			dadosFiltro.append("COLEÇÃO: " + ordemProducao.getColecao());
		}
		if (ordemProducao.getSituacao() != null) {
			restrictions.add(Restrictions.eq("situacao",
					ordemProducao.getSituacao()));
			dadosFiltro.append("SITUA��O: " + ordemProducao.getSituacao());
		}
		if (ordemProducao.getDataCadastroInicio() != null
				&& ordemProducao.getDataCadastroFim() != null) {
			restrictions.add(Restrictions.between("dataCadastro",
					ordemProducao.getDataCadastroInicio(),
					ordemProducao.getDataCadastroFim()));
			dadosFiltro.append("DATA INICIO: "
					+ new SimpleDateFormat("dd/MM/yyyy").format(ordemProducao
							.getDataCadastroInicio())
					+ " - DATA FINAL:"
					+ new SimpleDateFormat("dd/MM/yyyy").format(ordemProducao
							.getDataCadastroFim()) + "/ ");
		}

		if (ordemProducao.getDataCadastroInicio() != null
				&& ordemProducao.getDataCadastroFim() == null) {
			ordemProducao.setDataCadastroFim(ordemProducao.getDataCadastroInicio());
			restrictions.add(Restrictions.between("dataCadastro",
					ordemProducao.getDataCadastroInicio(),
					ordemProducao.getDataCadastroFim()));
			dadosFiltro.append("DATA INICIO: "
					+ new SimpleDateFormat("dd/MM/yyyy").format(ordemProducao
							.getDataCadastroInicio())
					+ " - DATA FINAL:"
					+ new SimpleDateFormat("dd/MM/yyyy").format(ordemProducao
							.getDataCadastroFim()) + "/ ");
		}

		if (ordemProducao.getDataCadastroInicio() == null
				&& ordemProducao.getDataCadastroFim() != null) {
			ordemProducao.setDataCadastroInicio(ordemProducao.getDataCadastroFim());
			restrictions.add(Restrictions.between("dataCadastro",
					ordemProducao.getDataCadastroInicio(),
					ordemProducao.getDataCadastroFim()));
			dadosFiltro.append("DATA INICIO: "
					+ new SimpleDateFormat("dd/MM/yyyy").format(ordemProducao
							.getDataCadastroInicio())
					+ " - DATA FINAL:"
					+ new SimpleDateFormat("dd/MM/yyyy").format(ordemProducao
							.getDataCadastroFim()) + "/ ");
		}
		
		if(ordemProducao.getStatusOcorrencia() != 0) {
			restrictions.add(Restrictions.eq("statusOcorrencia", ordemProducao.getStatusOcorrencia()));
		}

		return GenericDao.findAllByAttributesRestrictions(OrdemProducao.class,
				"dataCadastro", false, restrictions);
	}
	
	public String geradorDeProtocolo()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<OrdemProducao> list = GenericDao.getListaByDemanda(OrdemProducao.class, 0, 1,
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

		List<OcorrenciaOrdemProducao> list = GenericDao.getListaByDemanda(OcorrenciaOrdemProducao.class,
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
