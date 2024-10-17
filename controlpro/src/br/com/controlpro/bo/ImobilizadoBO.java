package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ImobilizadoDao;
import br.com.controlpro.entity.Imobilizado;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.IntegridadeObjetoHibernateException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

public class ImobilizadoBO {

	public static ImobilizadoBO getInstance() {
			return new ImobilizadoBO();
	}

	public void save(Imobilizado Imobilizado) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(Imobilizado);
		GenericDao.save(Imobilizado);
	}
	
	public void delete(Imobilizado imobilizado) throws IntegridadeObjetoHibernateException, ObjetoNaoEncontradoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException{
			GenericDao.delete(imobilizado);
	}

	public void update(Imobilizado Imobilizado) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(Imobilizado);
	}

	public Imobilizado find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Imobilizado.class, id);
	}


	public List<Imobilizado> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ImobilizadoDao.getInstance().imobilizados();
	}

	public List<Imobilizado> imobilizadoListReport(Imobilizado imobilizado)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ImobilizadoDao.getInstance().imobilizadoListReport(imobilizado);
	}

	public void validation(Imobilizado imobilizado) {

	}

}