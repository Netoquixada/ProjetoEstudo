package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ComprovanteFreteDao;
import br.com.controlpro.entity.ComprovanteFrete;
import br.com.controlpro.entity.ControlePedido;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.IntegridadeObjetoHibernateException;
import br.hibernateutil.exception.ListaVaziaException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

public class ComprovanteFreteBO {

	private ComprovanteFreteBO() {
	}

	public static ComprovanteFreteBO getInstance() {
		return new ComprovanteFreteBO();
	}

	public void save(ComprovanteFrete comprovanteFrete)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.save(comprovanteFrete);
	}

	public void update(ComprovanteFrete comprovanteFrete)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.update(comprovanteFrete);
	}

	public void delete(ComprovanteFrete comprovanteFrete)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.delete(comprovanteFrete);
	}

	public List<ComprovanteFrete> comprovanteFretePorControlePedido(ControlePedido controle)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ComprovanteFreteDao.getInstance().comprovanteFretePorControlePedido(controle);
	}

	public List<ComprovanteFrete> comprovantes()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ComprovanteFreteDao.getInstance().comprovantes();
	}
	
	public byte[] buscarArquivo(Integer id) throws ObjetoNaoEncontradoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException{
		return ComprovanteFreteDao.getInstance().buscarArquivo(id);
	}

	public void mergeAll(List<ComprovanteFrete> despesaSessenta)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.mergeAll(despesaSessenta);
	}

	public void updateAll(List<ComprovanteFrete> comprovanteFrete)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.updateAll(comprovanteFrete);
	}

	public void deleteAll(List<ComprovanteFrete> comprovanteFreteAux)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		synchronized (ComprovanteFrete.class) {
			for (int i = 0; i < comprovanteFreteAux.size(); i++) {
				GenericDao.delete(comprovanteFreteAux.get(i));
			}
		}
	}

}
