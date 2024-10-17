package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ClienteDao;
import br.com.controlpro.entity.consultas.Cliente;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ClienteBO {

	public static ClienteBO getInstance() {
			return new ClienteBO();
	}

	public void save(Cliente Cliente) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(Cliente);
		GenericDao.save(Cliente);
	}

	public void update(Cliente Cliente) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(Cliente);
	}

	public Cliente find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Cliente.class, id);
	}

	public LazyEntityDataModel<Cliente> clientesLazy(Cliente Cliente) {
		return ClienteDao.getInstance().clientesLazy(Cliente);
	}

	public List<Cliente> clientesComplete(String ClienteName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ClienteDao.getInstance().clientes(ClienteName);
	}
	
	public List<Cliente> fornecedoresComplete(String ClienteName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ClienteDao.getInstance().fornecedores(ClienteName);
	}

	public List<Cliente> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ClienteDao.getInstance().clientes();
	}
	
	public List<Cliente> listFornecedores()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ClienteDao.getInstance().fornecedores();
	}

	public StringBuilder dadosFiltro() {
		return ClienteDao.getInstance().getDadosFiltro();
	}

	public List<Cliente> clienteListReport(Cliente cliente)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ClienteDao.getInstance().clienteListReport(cliente);
	}

	public void validation(Cliente cliente) {

	}

}