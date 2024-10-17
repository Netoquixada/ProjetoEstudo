package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.Colecao;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ColecaoDao {

	public static ColecaoDao getInstance() {
		return new ColecaoDao();
	}

	public LazyEntityDataModel<Colecao> colecaosLazy(Colecao colecao) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (colecao.getNome() != null && !colecao.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", colecao.getNome(), MatchMode.ANYWHERE));
		}
		return new LazyEntityDataModel<Colecao>(Colecao.class, null, null, restrictions, null);
	}

	public List<Colecao> colecaos(String colecao) throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (colecao != null && !colecao.isEmpty()) {
			restrictions.add(Restrictions.like("nome", colecao));
		}
		return GenericDao.findAllByAttributesRestrictions(Colecao.class, "nome", true, restrictions);
	}

	public List<Colecao> colecaos() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(Colecao.class, "nome", true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<Colecao> colecaoListReport(Colecao colecao) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (colecao.getNome() != null && !colecao.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", colecao.getNome(), MatchMode.ANYWHERE));

			dadosFiltro.append("NOME: " + colecao.getNome());
		}
		return GenericDao.findAllByAttributesRestrictions(Colecao.class, "nome", true, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
