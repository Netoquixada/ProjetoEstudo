package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ClienteGlamixDao;
import br.com.controlpro.entity.ClienteGlamix;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ClienteGlamixBO {

	public static ClienteGlamixBO getInstance() {
			return new ClienteGlamixBO();
	}

	public void save(ClienteGlamix ClienteGlamix) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(ClienteGlamix);
		GenericDao.save(ClienteGlamix);
	}

	public void update(ClienteGlamix ClienteGlamix) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(ClienteGlamix);
	}

	public ClienteGlamix find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(ClienteGlamix.class, id);
	}

	public LazyEntityDataModel<ClienteGlamix> clienteGlamixsLazy(ClienteGlamix ClienteGlamix) {
		return ClienteGlamixDao.getInstance().clienteGlamixsLazy(ClienteGlamix);
	}

	public List<ClienteGlamix> clienteGlamixsComplete(String ClienteGlamixName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ClienteGlamixDao.getInstance().clienteGlamixs(ClienteGlamixName);
	}

	public List<ClienteGlamix> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ClienteGlamixDao.getInstance().clienteGlamixs();
	}

	public StringBuilder dadosFiltro() {
		return ClienteGlamixDao.getInstance().getDadosFiltro();
	}

	public List<ClienteGlamix> clienteGlamixListReport(ClienteGlamix clienteGlamix)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ClienteGlamixDao.getInstance().clienteGlamixListReport(clienteGlamix);
	}

	public void validation(ClienteGlamix clienteGlamix) {

	}

}