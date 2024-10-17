package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.Cofre;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class CofreDao {

	public static CofreDao getInstance() {
		return new CofreDao();
	}

	public LazyEntityDataModel<Cofre> cofresLazy(Cofre cofre) {
		List<Criterion> restrictions = new ArrayList<Criterion>();
		
		if (cofre.getTipoMovimentacao() != null) {
			restrictions.add(Restrictions.eq("tipoMovimentacao", cofre.getTipoMovimentacao()));
		}

		if (cofre.getLoja() != null) {
			restrictions.add(Restrictions.eq("loja", cofre.getLoja()));
		}
		
		if (cofre.getDataInicio() != null && cofre.getDataFim() != null) {
			restrictions.add(Restrictions.between("data", cofre.getDataInicio(), cofre.getDataFim()));
		}

		if (cofre.getDataInicio() != null && cofre.getDataFim() == null) {
			cofre.setDataFim(cofre.getDataInicio());
			restrictions.add(Restrictions.between("data", cofre.getDataInicio(), cofre.getDataFim()));
		}

		if (cofre.getDataInicio() == null && cofre.getDataFim() != null) {
			cofre.setDataInicio(cofre.getDataFim());
			restrictions.add(Restrictions.between("data", cofre.getDataInicio(), cofre.getDataFim()));
		}

		return new LazyEntityDataModel<Cofre>(Cofre.class, null,
				null, restrictions, null);
	}

	public List<Cofre> cofres()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(Cofre.class,
				null, true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<Cofre> cofreListReport(Cofre cofre)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();
		if (cofre.getTipoMovimentacao() != null) {
			restrictions.add(Restrictions.eq("tipoMovimentacao", cofre.getTipoMovimentacao()));
		}

		if (cofre.getLoja() != null) {
			restrictions.add(Restrictions.eq("loja", cofre.getLoja()));
		}
		
		if (cofre.getDataInicio() != null && cofre.getDataFim() != null) {
			restrictions.add(Restrictions.between("data", cofre.getDataInicio(), cofre.getDataFim()));
		}

		if (cofre.getDataInicio() != null && cofre.getDataFim() == null) {
			cofre.setDataFim(cofre.getDataInicio());
			restrictions.add(Restrictions.between("data", cofre.getDataInicio(), cofre.getDataFim()));
		}

		if (cofre.getDataInicio() == null && cofre.getDataFim() != null) {
			cofre.setDataInicio(cofre.getDataFim());
			restrictions.add(Restrictions.between("data", cofre.getDataInicio(), cofre.getDataFim()));
		}
		return GenericDao.findAllByAttributesRestrictions(Cofre.class,
				"data", false, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
