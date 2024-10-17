package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;

import br.com.controlpro.entity.ControleAjuste;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ControleAjusteDao {

	public static ControleAjusteDao getInstance() {
		return new ControleAjusteDao();
	}

	public ControleAjuste findById(Integer id)
			throws ObjetoNaoEncontradoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(ControleAjuste.class, id);
	}

	public LazyEntityDataModel<ControleAjuste> ControleAjusteLazy(ControleAjuste conferencia) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (conferencia.getProtocoloControle() != null && !conferencia.getProtocoloControle().isEmpty()) {
			restrictions.add(Restrictions.like("protocoloControle", conferencia.getProtocoloControle(),MatchMode.ANYWHERE));
		}

		if (conferencia.getDataInicio() != null && conferencia.getDataFim() != null) {
			restrictions
					.add(Restrictions.between("dataCadastro", conferencia.getDataInicio(), conferencia.getDataFim()));
		}

		if (conferencia.getDataInicio() != null && conferencia.getDataFim() == null) {
			conferencia.setDataFim(conferencia.getDataInicio());
			restrictions
					.add(Restrictions.between("dataCadastro", conferencia.getDataInicio(), conferencia.getDataFim()));
		}

		if (conferencia.getDataInicio() == null && conferencia.getDataFim() != null) {
			conferencia.setDataInicio(conferencia.getDataFim());
			restrictions
					.add(Restrictions.between("dataCadastro", conferencia.getDataInicio(), conferencia.getDataFim()));
		}
		return new LazyEntityDataModel<ControleAjuste>(ControleAjuste.class, null, null, restrictions, null);
	}

	public List<ControleAjuste> controlesPedidos(String conferencia)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		return GenericDao.findAllByAttributesRestrictions(ControleAjuste.class, "dataCadastro", true, restrictions);
	}

	public List<ControleAjuste> controlesPedidos() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		return GenericDao.findAllByAttributesRestrictions(ControleAjuste.class, "dataCadastro", true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<ControleAjuste> ControleAjusteListReport(ControleAjuste conferencia)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();
		if (conferencia.getProtocoloControle() != null && !conferencia.getProtocoloControle().isEmpty()) {
			restrictions.add(Restrictions.like("protocoloControle", conferencia.getProtocoloControle(),MatchMode.ANYWHERE));
		}

		if (conferencia.getDataInicio() != null && conferencia.getDataFim() != null) {
			restrictions
					.add(Restrictions.between("dataCadastro", conferencia.getDataInicio(), conferencia.getDataFim()));
		}

		if (conferencia.getDataInicio() != null && conferencia.getDataFim() == null) {
			conferencia.setDataFim(conferencia.getDataInicio());
			restrictions
					.add(Restrictions.between("dataCadastro", conferencia.getDataInicio(), conferencia.getDataFim()));
		}

		if (conferencia.getDataInicio() == null && conferencia.getDataFim() != null) {
			conferencia.setDataInicio(conferencia.getDataFim());
			restrictions
					.add(Restrictions.between("dataCadastro", conferencia.getDataInicio(), conferencia.getDataFim()));
		}
		return GenericDao.findAllByAttributesRestrictions(ControleAjuste.class, "dataCadastro", false, restrictions);
	}
	
	public String geradorDeProtocolo()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<ControleAjuste> list = GenericDao.getListaByDemanda(ControleAjuste.class, 0, 1,
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
