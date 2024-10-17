package br.com.controlpro.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;

import br.com.controlpro.constants.SituacaoOrdem;
import br.com.controlpro.entity.FolhaPagamento;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class FolhaPagamentoDao {

	public static FolhaPagamentoDao getInstance() {
		return new FolhaPagamentoDao();
	}

	public LazyEntityDataModel<FolhaPagamento> folhaPagamentosLazy(FolhaPagamento folhaPagamento) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (folhaPagamento.getProtocolo() != null && !folhaPagamento.getProtocolo().isEmpty()) {
			restrictions.add(Restrictions.like("protocolo",
					folhaPagamento.getProtocolo(), MatchMode.ANYWHERE));
		}
		
		if (folhaPagamento.getReferencia() != null && !folhaPagamento.getReferencia().isEmpty()) {
			restrictions.add(Restrictions.like("referencia",
					folhaPagamento.getReferencia(), MatchMode.ANYWHERE));
		}

		if (folhaPagamento.getDataCadastroInicio() != null
				&& folhaPagamento.getDataCadastroFim() != null) {
			restrictions.add(Restrictions.between("dataCadastro",
					folhaPagamento.getDataCadastroInicio(),
					folhaPagamento.getDataCadastroFim()));
		}

		if (folhaPagamento.getDataCadastroInicio() != null
				&& folhaPagamento.getDataCadastroFim() == null) {
			folhaPagamento.setDataCadastroFim(folhaPagamento.getDataCadastroInicio());
			restrictions.add(Restrictions.between("dataCadastro",
					folhaPagamento.getDataCadastroInicio(),
					folhaPagamento.getDataCadastroFim()));
		}

		if (folhaPagamento.getDataCadastroInicio() == null
				&& folhaPagamento.getDataCadastroFim() != null) {
			folhaPagamento.setDataCadastroInicio(folhaPagamento.getDataCadastroFim());
			restrictions.add(Restrictions.between("dataCadastro",
					folhaPagamento.getDataCadastroInicio(),
					folhaPagamento.getDataCadastroFim()));
		}

		return new LazyEntityDataModel<FolhaPagamento>(FolhaPagamento.class, null,
				null, restrictions, null);
	}

	public List<FolhaPagamento> folhaPagamentos(String folhaPagamento)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		return GenericDao.findAllByAttributesRestrictions(FolhaPagamento.class,
				null, true, restrictions);
	}

	public List<FolhaPagamento> folhaPagamentos()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		
		return GenericDao.findAllByAttributesRestrictions(FolhaPagamento.class,
				"dataCadastro", false, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<FolhaPagamento> folhaPagamentoListReport(FolhaPagamento folhaPagamento)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (folhaPagamento.getProtocolo() != null) {
			restrictions.add(Restrictions.like("protocolo",
					folhaPagamento.getProtocolo(), MatchMode.ANYWHERE));
			dadosFiltro.append("PROTOCOLO: " + folhaPagamento.getProtocolo());
		}
		
		if (folhaPagamento.getReferencia() != null && !folhaPagamento.getReferencia().isEmpty()) {
			restrictions.add(Restrictions.like("referencia",
					folhaPagamento.getReferencia(), MatchMode.ANYWHERE));
		}

		if (folhaPagamento.getDataCadastroInicio() != null
				&& folhaPagamento.getDataCadastroFim() != null) {
			restrictions.add(Restrictions.between("dataCadastro",
					folhaPagamento.getDataCadastroInicio(),
					folhaPagamento.getDataCadastroFim()));
			dadosFiltro.append("DATA INICIO: "
					+ new SimpleDateFormat("dd/MM/yyyy").format(folhaPagamento
							.getDataCadastroInicio())
					+ " - DATA FINAL:"
					+ new SimpleDateFormat("dd/MM/yyyy").format(folhaPagamento
							.getDataCadastroFim()) + "/ ");
		}

		if (folhaPagamento.getDataCadastroInicio() != null
				&& folhaPagamento.getDataCadastroFim() == null) {
			folhaPagamento.setDataCadastroFim(folhaPagamento.getDataCadastroInicio());
			restrictions.add(Restrictions.between("dataCadastro",
					folhaPagamento.getDataCadastroInicio(),
					folhaPagamento.getDataCadastroFim()));
			dadosFiltro.append("DATA INICIO: "
					+ new SimpleDateFormat("dd/MM/yyyy").format(folhaPagamento
							.getDataCadastroInicio())
					+ " - DATA FINAL:"
					+ new SimpleDateFormat("dd/MM/yyyy").format(folhaPagamento
							.getDataCadastroFim()) + "/ ");
		}

		if (folhaPagamento.getDataCadastroInicio() == null
				&& folhaPagamento.getDataCadastroFim() != null) {
			folhaPagamento.setDataCadastroInicio(folhaPagamento.getDataCadastroFim());
			restrictions.add(Restrictions.between("dataCadastro",
					folhaPagamento.getDataCadastroInicio(),
					folhaPagamento.getDataCadastroFim()));
			dadosFiltro.append("DATA INICIO: "
					+ new SimpleDateFormat("dd/MM/yyyy").format(folhaPagamento
							.getDataCadastroInicio())
					+ " - DATA FINAL:"
					+ new SimpleDateFormat("dd/MM/yyyy").format(folhaPagamento
							.getDataCadastroFim()) + "/ ");
		}

		return GenericDao.findAllByAttributesRestrictions(FolhaPagamento.class,
				"dataCadastro", false, restrictions);
	}

	public String geradorDeProtocolo()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<FolhaPagamento> list = GenericDao.getListaByDemanda(FolhaPagamento.class,
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
