package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.Cargo;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class CargoDao {

	public static CargoDao getInstance() {
		return new CargoDao();
	}

	public LazyEntityDataModel<Cargo> cargosLazy(Cargo cargo) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (cargo.getNome() != null && !cargo.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", cargo.getNome(),
					MatchMode.ANYWHERE));
		}
		return new LazyEntityDataModel<Cargo>(Cargo.class, null, null,
				restrictions, null);
	}

	public List<Cargo> cargos(String cargo)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (cargo != null && !cargo.isEmpty()) {
			restrictions.add(Restrictions.like("nome", cargo));
		}
		return GenericDao.findAllByAttributesRestrictions(Cargo.class,
				"nome", true, restrictions);
	}

	public List<Cargo> cargos()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(Cargo.class,
				"nome", true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<Cargo> cargoListReport(Cargo cargo)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (cargo.getNome() != null && !cargo.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", cargo.getNome(),
					MatchMode.ANYWHERE));

			dadosFiltro.append("NOME: " + cargo.getNome());
		}
		return GenericDao.findAllByAttributesRestrictions(Cargo.class,
				"nome", true, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
