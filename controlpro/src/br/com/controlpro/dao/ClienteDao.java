package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.consultas.Cliente;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ClienteDao {

	public static ClienteDao getInstance() {
		return new ClienteDao();
	}

	public LazyEntityDataModel<Cliente> clientesLazy(Cliente cliente) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (cliente.getNome() != null && !cliente.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", cliente.getNome(), MatchMode.ANYWHERE));
		}
		if (cliente.getCidade() != null && !cliente.getCidade().isEmpty()) {
			restrictions.add(Restrictions.like("cidade", cliente.getCidade(), MatchMode.ANYWHERE));
		}
		return new LazyEntityDataModel<Cliente>(Cliente.class, null, null, restrictions, null);
	}

	public List<Cliente> clientes(String cliente) throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (cliente != null && !cliente.isEmpty()) {
			restrictions.add(Restrictions.like("nome", cliente, MatchMode.ANYWHERE));
		}
		return GenericDao.findAllByAttributesRestrictions(Cliente.class, "nome", true, restrictions);
	}
	
	public List<Cliente> fornecedores(String cliente) throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (cliente != null && !cliente.isEmpty()) {
			restrictions.add(Restrictions.like("nome", cliente));
		}
		restrictions.add(Restrictions.like("tipo", "F"));
		
		return GenericDao.findAllByAttributesRestrictions(Cliente.class, "nome", true, restrictions);
	}

	public List<Cliente> clientes() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		return GenericDao.findAllByAttributesRestrictions(Cliente.class, "nome", true, restricions);
	}
	
	public List<Cliente> fornecedores() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.like("tipo", "F"));
		return GenericDao.findAllByAttributesRestrictions(Cliente.class, "nome", true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<Cliente> clienteListReport(Cliente cliente) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (cliente.getNome() != null && !cliente.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", cliente.getNome(), MatchMode.ANYWHERE));

			dadosFiltro.append("NOME: " + cliente.getNome());
		}
		return GenericDao.findAllByAttributesRestrictions(Cliente.class, "nome", true, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
