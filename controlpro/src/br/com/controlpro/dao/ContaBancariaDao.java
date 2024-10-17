package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.consultas.ContaBancaria;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ContaBancariaDao {

	public static ContaBancariaDao getInstance() {
		return new ContaBancariaDao();
	}

	public LazyEntityDataModel<ContaBancaria> bontaBancariasLazy(ContaBancaria bontaBancaria) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (bontaBancaria.getNome() != null && !bontaBancaria.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", bontaBancaria.getNome(), MatchMode.ANYWHERE));
		}
		return new LazyEntityDataModel<ContaBancaria>(ContaBancaria.class, null, null, restrictions, null);

	}

	public List<ContaBancaria> bontaBancarias(String bontaBancaria) throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (bontaBancaria != null && !bontaBancaria.isEmpty()) {
			restrictions.add(Restrictions.like("nome", bontaBancaria, MatchMode.ANYWHERE));
		}
		return GenericDao.findAllByAttributesRestrictions(ContaBancaria.class, "nome", true, restrictions);
	}

	public List<ContaBancaria> bontaBancarias() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		return GenericDao.findAllByAttributesRestrictions(ContaBancaria.class, "nome", true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<ContaBancaria> bontaBancariaListReport(ContaBancaria bontaBancaria) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (bontaBancaria.getNome() != null && !bontaBancaria.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", bontaBancaria.getNome(), MatchMode.ANYWHERE));

			dadosFiltro.append("NOME: " + bontaBancaria.getNome());
		}
		return GenericDao.findAllByAttributesRestrictions(ContaBancaria.class, "nome", true, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
