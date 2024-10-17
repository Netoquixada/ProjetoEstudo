package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;

import br.com.controlpro.entity.Sangria;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class SangriaDao {

	public static SangriaDao getInstance() {
		return new SangriaDao();
	}

	public List<Sangria> sangrias(String sangria) throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (sangria != null && !sangria.isEmpty()) {
			restrictions.add(Restrictions.like("descricao", sangria));
		}
		return GenericDao.findAllByAttributesRestrictions(Sangria.class, "dataSangria", true, restrictions);
	}

	public List<Sangria> sangrias() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(Sangria.class, "dataSangria", true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<Sangria> sangriaListReport(Sangria sangria) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (sangria.getDescricao() != null && !sangria.getDescricao().isEmpty()) {
			restrictions.add(Restrictions.like("descricao", sangria.getDescricao(), MatchMode.ANYWHERE));

			dadosFiltro.append("DESCRIÇÃO: " + sangria.getDescricao());
		}
		
		if (sangria.getFavorecido() != null) {
			restrictions.add(Restrictions.eq("favorecido", sangria.getFavorecido()));
		}

		if (sangria.getDataPesquisaInicio() != null && sangria.getDataPesquisaFim() != null) {
			restrictions.add(Restrictions.between("dataSangria", sangria.getDataPesquisaInicio(), sangria.getDataPesquisaFim()));
		}

		if (sangria.getDataPesquisaInicio() != null && sangria.getDataPesquisaFim() == null) {
			sangria.setDataPesquisaFim(sangria.getDataPesquisaInicio());
			restrictions.add(Restrictions.between("dataSangria", sangria.getDataPesquisaInicio(), sangria.getDataPesquisaFim()));
		}

		if (sangria.getDataPesquisaInicio() == null && sangria.getDataPesquisaFim() != null) {
			sangria.setDataPesquisaInicio(sangria.getDataPesquisaFim());
			restrictions.add(Restrictions.between("dataSangria", sangria.getDataPesquisaInicio(), sangria.getDataPesquisaFim()));
		}
		
		
		return GenericDao.findAllByAttributesRestrictions(Sangria.class, "dataSangria", true, restrictions);
	}
	
	public String geradorDeProtocolo()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Sangria> list = GenericDao.getListaByDemanda(Sangria.class, 0, 1,
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
