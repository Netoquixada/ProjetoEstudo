package br.com.controlpro.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.Meta;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class MetaDao {

	public static MetaDao getInstance() {
		return new MetaDao();
	}

	public List<Meta> metas(String meta) throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		return GenericDao.findAllByAttributesRestrictions(Meta.class, null, true, restrictions);
	}

	public List<Meta> metas() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(Meta.class, "dataCadastro", false, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<Meta> metaListReport(Meta meta)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if(meta.getLoja() != null) {
			restrictions.add(Restrictions.eq("loja", meta.getLoja()));
		}
		
		if(meta.getCompetencia() != null && !meta.getCompetencia().isEmpty()) {
			restrictions.add(Restrictions.like("competencia", meta.getCompetencia(), MatchMode.ANYWHERE));
		}
		
		
		if (meta.getDataCadastroInicio() != null && meta.getDataCadastroFim() != null) {
			restrictions.add(Restrictions.between("dataCadastro", meta.getDataCadastroInicio(),
					meta.getDataCadastroFim()));
			dadosFiltro.append("DATA INICIO: "
					+ new SimpleDateFormat("dd/MM/yyyy").format(meta.getDataCadastroInicio()) + " - DATA FINAL:"
					+ new SimpleDateFormat("dd/MM/yyyy").format(meta.getDataCadastroFim()) + "/ ");
		}

		if (meta.getDataCadastroInicio() != null && meta.getDataCadastroFim() == null) {
			meta.setDataCadastroFim(meta.getDataCadastroInicio());
			restrictions.add(Restrictions.between("dataCadastro", meta.getDataCadastroInicio(),
					meta.getDataCadastroFim()));
			dadosFiltro.append("DATA INICIO: "
					+ new SimpleDateFormat("dd/MM/yyyy").format(meta.getDataCadastroInicio()) + " - DATA FINAL:"
					+ new SimpleDateFormat("dd/MM/yyyy").format(meta.getDataCadastroFim()) + "/ ");
		}

		if (meta.getDataCadastroInicio() == null && meta.getDataCadastroFim() != null) {
			meta.setDataCadastroInicio(meta.getDataCadastroFim());
			restrictions.add(Restrictions.between("dataCadastro", meta.getDataCadastroInicio(),
					meta.getDataCadastroFim()));
			dadosFiltro.append("DATA INICIO: "
					+ new SimpleDateFormat("dd/MM/yyyy").format(meta.getDataCadastroInicio()) + " - DATA FINAL:"
					+ new SimpleDateFormat("dd/MM/yyyy").format(meta.getDataCadastroFim()) + "/ ");
		}

		return GenericDao.findAllByAttributesRestrictions(Meta.class, "dataCadastro", false, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
