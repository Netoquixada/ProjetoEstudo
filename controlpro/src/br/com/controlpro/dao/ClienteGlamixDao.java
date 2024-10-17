package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.ClienteGlamix;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ClienteGlamixDao {

	public static ClienteGlamixDao getInstance() {
		return new ClienteGlamixDao();
	}

	public LazyEntityDataModel<ClienteGlamix> clienteGlamixsLazy(ClienteGlamix clienteGlamix) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (clienteGlamix.getNome() != null && !clienteGlamix.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", clienteGlamix.getNome(),
					MatchMode.ANYWHERE));
		}
		return new LazyEntityDataModel<ClienteGlamix>(ClienteGlamix.class, null, null,
				restrictions, null);
	}

	public List<ClienteGlamix> clienteGlamixs(String clienteGlamix)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (clienteGlamix != null && !clienteGlamix.isEmpty()) {
			restrictions.add(Restrictions.like("nome", clienteGlamix, MatchMode.ANYWHERE));
		}
		return GenericDao.findAllByAttributesRestrictions(ClienteGlamix.class,
				"nome", true, restrictions);
	}

	public List<ClienteGlamix> clienteGlamixs()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(ClienteGlamix.class,
				"nome", true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<ClienteGlamix> clienteGlamixListReport(ClienteGlamix clienteGlamix)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (clienteGlamix.getNome() != null && !clienteGlamix.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", clienteGlamix.getNome(),
					MatchMode.ANYWHERE));

			dadosFiltro.append("NOME: " + clienteGlamix.getNome());
		}
		return GenericDao.findAllByAttributesRestrictions(ClienteGlamix.class,
				"nome", true, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
