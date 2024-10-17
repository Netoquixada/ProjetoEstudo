package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.Grupo;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class GrupoDao {

	public static GrupoDao getInstance() {
		return new GrupoDao();
	}

	public LazyEntityDataModel<Grupo> gruposLazy(Grupo grupo) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (grupo.getNome() != null && !grupo.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", grupo.getNome(),
					MatchMode.ANYWHERE));
		}
		return new LazyEntityDataModel<Grupo>(Grupo.class, null, null,
				restrictions, null);
	}

	public List<Grupo> grupos(String grupo)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (grupo != null && !grupo.isEmpty()) {
			restrictions.add(Restrictions.like("nome", grupo));
		}
		return GenericDao.findAllByAttributesRestrictions(Grupo.class,
				"nome", true, restrictions);
	}

	public List<Grupo> grupos()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(Grupo.class,
				"nome", true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<Grupo> grupoListReport(Grupo grupo)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (grupo.getNome() != null && !grupo.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", grupo.getNome(),
					MatchMode.ANYWHERE));

			dadosFiltro.append("NOME: " + grupo.getNome());
		}
		return GenericDao.findAllByAttributesRestrictions(Grupo.class,
				"nome", true, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
