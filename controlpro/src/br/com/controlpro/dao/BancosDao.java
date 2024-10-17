package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.consultas.Banco;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class BancosDao {

	public static BancosDao getInstance() {
		return new BancosDao();
	}

	public LazyEntityDataModel<Banco> bancosLazy(Banco banco) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (banco.getNome() != null && !banco.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", banco.getNome(), MatchMode.ANYWHERE));
		}
		return new LazyEntityDataModel<Banco>(Banco.class, null, null, restrictions, null);

	}

	public List<Banco> bancos(String banco) throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (banco != null && !banco.isEmpty()) {
			restrictions.add(Restrictions.like("nome", banco, MatchMode.ANYWHERE));
		}
		return GenericDao.findAllByAttributesRestrictions(Banco.class, "nome", true, restrictions);
	}

	public List<Banco> bancos() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		return GenericDao.findAllByAttributesRestrictions(Banco.class, "nome", true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<Banco> bancoListReport(Banco banco) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (banco.getNome() != null && !banco.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", banco.getNome(), MatchMode.ANYWHERE));

			dadosFiltro.append("NOME: " + banco.getNome());
		}
		return GenericDao.findAllByAttributesRestrictions(Banco.class, "nome", true, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
