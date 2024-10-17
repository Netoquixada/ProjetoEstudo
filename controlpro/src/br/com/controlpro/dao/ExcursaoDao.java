package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.Excursao;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ExcursaoDao {

	public static ExcursaoDao getInstance() {
		return new ExcursaoDao();
	}

	public LazyEntityDataModel<Excursao> excursaosLazy(Excursao excursao) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (excursao.getNome() != null && !excursao.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", excursao.getNome(), MatchMode.ANYWHERE));
		}
		return new LazyEntityDataModel<Excursao>(Excursao.class, null, null, restrictions, null);
	}

	public List<Excursao> excursaos(String excursao) throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (excursao != null && !excursao.isEmpty()) {
			restrictions.add(Restrictions.like("nome", excursao,MatchMode.ANYWHERE));
		}
		return GenericDao.findAllByAttributesRestrictions(Excursao.class, "nome", true, restrictions);
	}

	public List<Excursao> excursaos() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(Excursao.class, "nome", true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<Excursao> excursaoListReport(Excursao excursao) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (excursao.getNome() != null && !excursao.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", excursao.getNome(), MatchMode.ANYWHERE));

			dadosFiltro.append("NOME: " + excursao.getNome());
		}
		return GenericDao.findAllByAttributesRestrictions(Excursao.class, "nome", true, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
