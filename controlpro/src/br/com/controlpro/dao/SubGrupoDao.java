package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.SubGrupo;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class SubGrupoDao {

	public static SubGrupoDao getInstance() {
		return new SubGrupoDao();
	}

	public LazyEntityDataModel<SubGrupo> subGruposLazy(SubGrupo subGrupo) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (subGrupo.getNome() != null && !subGrupo.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", subGrupo.getNome(),
					MatchMode.ANYWHERE));
		}
		return new LazyEntityDataModel<SubGrupo>(SubGrupo.class, null, null,
				restrictions, null);
	}

	public List<SubGrupo> subGrupos(String subGrupo)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (subGrupo != null && !subGrupo.isEmpty()) {
			restrictions.add(Restrictions.like("nome", subGrupo));
		}
		return GenericDao.findAllByAttributesRestrictions(SubGrupo.class,
				"nome", true, restrictions);
	}

	public List<SubGrupo> subGrupos()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(SubGrupo.class,
				"nome", true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<SubGrupo> subGrupoListReport(SubGrupo subGrupo)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (subGrupo.getNome() != null && !subGrupo.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", subGrupo.getNome(),
					MatchMode.ANYWHERE));

			dadosFiltro.append("NOME: " + subGrupo.getNome());
		}
		return GenericDao.findAllByAttributesRestrictions(SubGrupo.class,
				"nome", true, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
