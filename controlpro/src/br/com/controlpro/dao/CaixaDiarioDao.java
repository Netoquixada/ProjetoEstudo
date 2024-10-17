package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;

import br.com.controlpro.entity.CaixaDiario;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class CaixaDiarioDao {

	public static CaixaDiarioDao getInstance() {
		return new CaixaDiarioDao();
	}

	public List<CaixaDiario> caixaDiarios(String caixaDiario) throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (caixaDiario != null && !caixaDiario.isEmpty()) {
			restrictions.add(Restrictions.like("descricao", caixaDiario));
		}
		return GenericDao.findAllByAttributesRestrictions(CaixaDiario.class, "data", true, restrictions);
	}

	public List<CaixaDiario> caixaDiarios() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(CaixaDiario.class, "data", true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<CaixaDiario> caixaDiarioListReport(CaixaDiario caixaDiario) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (caixaDiario.getDescricao() != null && !caixaDiario.getDescricao().isEmpty()) {
			restrictions.add(Restrictions.like("descricao", caixaDiario.getDescricao(), MatchMode.ANYWHERE));
		}
		if (caixaDiario.getTipo() != null && !caixaDiario.getTipo().isEmpty()) {
			restrictions.add(Restrictions.like("tipo", caixaDiario.getTipo(), MatchMode.ANYWHERE));
		}
		
		if (caixaDiario.getDataPesquisaInicio() != null && caixaDiario.getDataPesquisaFim() != null) {
			restrictions.add(Restrictions.between("data", caixaDiario.getDataPesquisaInicio(), caixaDiario.getDataPesquisaFim()));
		}

		if (caixaDiario.getDataPesquisaInicio() != null && caixaDiario.getDataPesquisaFim() == null) {
			caixaDiario.setDataPesquisaFim(caixaDiario.getDataPesquisaInicio());
			restrictions.add(Restrictions.between("data", caixaDiario.getDataPesquisaInicio(), caixaDiario.getDataPesquisaFim()));
		}

		if (caixaDiario.getDataPesquisaInicio() == null && caixaDiario.getDataPesquisaFim() != null) {
			caixaDiario.setDataPesquisaInicio(caixaDiario.getDataPesquisaFim());
			restrictions.add(Restrictions.between("data", caixaDiario.getDataPesquisaInicio(), caixaDiario.getDataPesquisaFim()));
		}
		
		
		return GenericDao.findAllByAttributesRestrictions(CaixaDiario.class, "data", true, restrictions);
	}
	
	public String geradorDeProtocolo()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<CaixaDiario> list = GenericDao.getListaByDemanda(CaixaDiario.class, 0, 1,
				null, null, "id", SortOrder.DESCENDING, null, null);
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
