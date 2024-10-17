package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.Faccao;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class FaccaoDao {

	public static FaccaoDao getInstance() {
		return new FaccaoDao();
	}

	public LazyEntityDataModel<Faccao> faccaosLazy(Faccao faccao) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (faccao.getNome() != null && !faccao.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", faccao.getNome(),
					MatchMode.ANYWHERE));
		}
		return new LazyEntityDataModel<Faccao>(Faccao.class, null, null,
				restrictions, null);
	}

	public List<Faccao> faccaos(String faccao)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();
		restrictions.add(Restrictions.eq("status", true));
		if (faccao != null && !faccao.isEmpty()) {
			restrictions.add(Restrictions.like("nome", faccao, MatchMode.ANYWHERE));
		}
		return GenericDao.findAllByAttributesRestrictions(Faccao.class,
				"nome", true, restrictions);
	}

	public List<Faccao> faccaos()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(Faccao.class,
				"nome", true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<Faccao> faccaoListReport(Faccao faccao)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (faccao.getNome() != null && !faccao.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", faccao.getNome(),
					MatchMode.ANYWHERE));

			dadosFiltro.append("NOME: " + faccao.getNome());
		}
		
		if(faccao.getStatusFilter() != null) {
			restrictions.add(Restrictions.eq("status", faccao.getStatusFilter()));
		}
		
		
		return GenericDao.findAllByAttributesRestrictions(Faccao.class,
				"nome", true, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
