package br.com.controlpro.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;

import br.com.controlpro.constants.SituacaoOrdem;
import br.com.controlpro.entity.OrdemCorte;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class OrdemCorteDao {

	public static OrdemCorteDao getInstance() {
		return new OrdemCorteDao();
	}

	public LazyEntityDataModel<OrdemCorte> ordemCortesLazy(OrdemCorte ordemCorte) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (ordemCorte.getProtocolo() != null && !ordemCorte.getProtocolo().isEmpty()) {
			restrictions.add(Restrictions.like("protocolo",
					ordemCorte.getProtocolo(), MatchMode.ANYWHERE));
		}
		if (ordemCorte.getSituacao() != null) {
			restrictions.add(Restrictions.eq("situacao",
					ordemCorte.getSituacao()));
		}
		if (ordemCorte.getDataCadastroInicio() != null
				&& ordemCorte.getDataCadastroFim() != null) {
			restrictions.add(Restrictions.between("dataCadastro",
					ordemCorte.getDataCadastroInicio(),
					ordemCorte.getDataCadastroFim()));
		}

		if (ordemCorte.getDataCadastroInicio() != null
				&& ordemCorte.getDataCadastroFim() == null) {
			ordemCorte.setDataCadastroFim(ordemCorte.getDataCadastroInicio());
			restrictions.add(Restrictions.between("dataCadastro",
					ordemCorte.getDataCadastroInicio(),
					ordemCorte.getDataCadastroFim()));
		}

		if (ordemCorte.getDataCadastroInicio() == null
				&& ordemCorte.getDataCadastroFim() != null) {
			ordemCorte.setDataCadastroInicio(ordemCorte.getDataCadastroFim());
			restrictions.add(Restrictions.between("dataCadastro",
					ordemCorte.getDataCadastroInicio(),
					ordemCorte.getDataCadastroFim()));
		}

		return new LazyEntityDataModel<OrdemCorte>(OrdemCorte.class, null,
				null, restrictions, null);
	}

	public List<OrdemCorte> ordemCortes(String ordemCorte)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		return GenericDao.findAllByAttributesRestrictions(OrdemCorte.class,
				null, true, restrictions);
	}

	public List<OrdemCorte> ordemCortes()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		restricions.add(Restrictions.eq("situacao",SituacaoOrdem.ANDAMENTO));
		
		return GenericDao.findAllByAttributesRestrictions(OrdemCorte.class,
				"dataCadastro", false, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<OrdemCorte> ordemCorteListReport(OrdemCorte ordemCorte)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (ordemCorte.getProtocolo() != null) {
			restrictions.add(Restrictions.like("protocolo",
					ordemCorte.getProtocolo(), MatchMode.ANYWHERE));
			dadosFiltro.append("PROTOCOLO: " + ordemCorte.getProtocolo());
		}
		if (ordemCorte.getSituacao() != null) {
			restrictions.add(Restrictions.eq("situacao",
					ordemCorte.getSituacao()));
			dadosFiltro.append("SITUA��O: " + ordemCorte.getSituacao());
		}
		if (ordemCorte.getDataCadastroInicio() != null
				&& ordemCorte.getDataCadastroFim() != null) {
			restrictions.add(Restrictions.between("dataCadastro",
					ordemCorte.getDataCadastroInicio(),
					ordemCorte.getDataCadastroFim()));
			dadosFiltro.append("DATA INICIO: "
					+ new SimpleDateFormat("dd/MM/yyyy").format(ordemCorte
							.getDataCadastroInicio())
					+ " - DATA FINAL:"
					+ new SimpleDateFormat("dd/MM/yyyy").format(ordemCorte
							.getDataCadastroFim()) + "/ ");
		}

		if (ordemCorte.getDataCadastroInicio() != null
				&& ordemCorte.getDataCadastroFim() == null) {
			ordemCorte.setDataCadastroFim(ordemCorte.getDataCadastroInicio());
			restrictions.add(Restrictions.between("dataCadastro",
					ordemCorte.getDataCadastroInicio(),
					ordemCorte.getDataCadastroFim()));
			dadosFiltro.append("DATA INICIO: "
					+ new SimpleDateFormat("dd/MM/yyyy").format(ordemCorte
							.getDataCadastroInicio())
					+ " - DATA FINAL:"
					+ new SimpleDateFormat("dd/MM/yyyy").format(ordemCorte
							.getDataCadastroFim()) + "/ ");
		}

		if (ordemCorte.getDataCadastroInicio() == null
				&& ordemCorte.getDataCadastroFim() != null) {
			ordemCorte.setDataCadastroInicio(ordemCorte.getDataCadastroFim());
			restrictions.add(Restrictions.between("dataCadastro",
					ordemCorte.getDataCadastroInicio(),
					ordemCorte.getDataCadastroFim()));
			dadosFiltro.append("DATA INICIO: "
					+ new SimpleDateFormat("dd/MM/yyyy").format(ordemCorte
							.getDataCadastroInicio())
					+ " - DATA FINAL:"
					+ new SimpleDateFormat("dd/MM/yyyy").format(ordemCorte
							.getDataCadastroFim()) + "/ ");
		}

		return GenericDao.findAllByAttributesRestrictions(OrdemCorte.class,
				"dataCadastro", false, restrictions);
	}

	public String geradorDeProtocolo()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<OrdemCorte> list = GenericDao.getListaByDemanda(OrdemCorte.class,
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
