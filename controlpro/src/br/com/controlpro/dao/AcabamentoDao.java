package br.com.controlpro.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;

import br.com.controlpro.entity.Acabamento;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class AcabamentoDao {

	public static AcabamentoDao getInstance() {
		return new AcabamentoDao();
	}

	public List<Acabamento> acabamentos(String acabamento) throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		return GenericDao.findAllByAttributesRestrictions(Acabamento.class, null, true, restrictions);
	}

	public List<Acabamento> acabamentos() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(Acabamento.class, "dataCadastro", false, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<Acabamento> acabamentoListReport(Acabamento acabamento)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (acabamento.getOrigem() != null) {
			restrictions.add(Restrictions.eq("origem", acabamento.getOrigem()));
		}
		if (acabamento.getSituacao() != null) {
			restrictions.add(Restrictions.eq("situacao", acabamento.getSituacao()));
		}
		if (acabamento.getProtocolo() != null) {
			restrictions.add(Restrictions.like("protocolo", acabamento.getProtocolo(), MatchMode.ANYWHERE));
			dadosFiltro.append("PROTOCOLO: " + acabamento.getProtocolo());
		}
		if (acabamento.getFaccao() != null) {
			restrictions.add(Restrictions.eq("faccao", acabamento.getFaccao()));
			dadosFiltro.append("FACÇÃO: " + acabamento.getFaccao());
		}
		if (acabamento.getDataCadastroInicio() != null && acabamento.getDataCadastroFim() != null) {
			restrictions.add(Restrictions.between("dataCadastro", acabamento.getDataCadastroInicio(),
					acabamento.getDataCadastroFim()));
			dadosFiltro.append("DATA INICIO: "
					+ new SimpleDateFormat("dd/MM/yyyy").format(acabamento.getDataCadastroInicio()) + " - DATA FINAL:"
					+ new SimpleDateFormat("dd/MM/yyyy").format(acabamento.getDataCadastroFim()) + "/ ");
		}

		if (acabamento.getDataCadastroInicio() != null && acabamento.getDataCadastroFim() == null) {
			acabamento.setDataCadastroFim(acabamento.getDataCadastroInicio());
			restrictions.add(Restrictions.between("dataCadastro", acabamento.getDataCadastroInicio(),
					acabamento.getDataCadastroFim()));
			dadosFiltro.append("DATA INICIO: "
					+ new SimpleDateFormat("dd/MM/yyyy").format(acabamento.getDataCadastroInicio()) + " - DATA FINAL:"
					+ new SimpleDateFormat("dd/MM/yyyy").format(acabamento.getDataCadastroFim()) + "/ ");
		}

		if (acabamento.getDataCadastroInicio() == null && acabamento.getDataCadastroFim() != null) {
			acabamento.setDataCadastroInicio(acabamento.getDataCadastroFim());
			restrictions.add(Restrictions.between("dataCadastro", acabamento.getDataCadastroInicio(),
					acabamento.getDataCadastroFim()));
			dadosFiltro.append("DATA INICIO: "
					+ new SimpleDateFormat("dd/MM/yyyy").format(acabamento.getDataCadastroInicio()) + " - DATA FINAL:"
					+ new SimpleDateFormat("dd/MM/yyyy").format(acabamento.getDataCadastroFim()) + "/ ");
		}

		return GenericDao.findAllByAttributesRestrictions(Acabamento.class, "dataCadastro", false, restrictions);
	}

	public String geradorDeProtocolo() throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Acabamento> list = GenericDao.getListaByDemanda(Acabamento.class, 0, 1, null, null, "id",
				SortOrder.DESCENDING, null, null);
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
