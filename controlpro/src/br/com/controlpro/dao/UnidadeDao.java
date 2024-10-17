package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.Unidade;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class UnidadeDao {

	public static UnidadeDao getInstance() {
		return new UnidadeDao();
	}

	public LazyEntityDataModel<Unidade> unidadesLazy(Unidade unidade) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (unidade.getNome() != null && !unidade.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", unidade.getNome(),
					MatchMode.ANYWHERE));
		}
		return new LazyEntityDataModel<Unidade>(Unidade.class, null, null,
				restrictions, null);
	}

	public List<Unidade> unidades(String unidade)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (unidade != null && !unidade.isEmpty()) {
			restrictions.add(Restrictions.like("nome", unidade));
		}
		return GenericDao.findAllByAttributesRestrictions(Unidade.class,
				"nome", true, restrictions);
	}

	public List<Unidade> unidades()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(Unidade.class,
				"nome", true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<Unidade> unidadeListReport(Unidade unidade)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (unidade.getNome() != null && !unidade.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", unidade.getNome(),
					MatchMode.ANYWHERE));

			dadosFiltro.append("NOME: " + unidade.getNome());
		}
		return GenericDao.findAllByAttributesRestrictions(Unidade.class,
				"nome", true, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
