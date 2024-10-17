package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ControleInternoDao;
import br.com.controlpro.entity.ControleInterno;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.IntegridadeObjetoHibernateException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ControleInternoBO {

	public static ControleInternoBO getInstance() {
			return new ControleInternoBO();
	}

	public void save(ControleInterno ControleInterno) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(ControleInterno);
		GenericDao.save(ControleInterno);
	}
	
	public void delete(ControleInterno controleInterno) throws IntegridadeObjetoHibernateException, ObjetoNaoEncontradoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException{
			GenericDao.delete(controleInterno);
	}

	public void update(ControleInterno ControleInterno) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(ControleInterno);
	}

	public ControleInterno find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(ControleInterno.class, id);
	}

	public LazyEntityDataModel<ControleInterno> controleInternosLazy(ControleInterno ControleInterno) {
		return ControleInternoDao.getInstance().controleInternosLazy(ControleInterno);
	}

	public List<ControleInterno> controleInternosComplete(String ControleInternoName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ControleInternoDao.getInstance().controleInternos(ControleInternoName);
	}

	public List<ControleInterno> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ControleInternoDao.getInstance().controleInternos();
	}

	public StringBuilder dadosFiltro() {
		return ControleInternoDao.getInstance().getDadosFiltro();
	}

	public List<ControleInterno> controleInternoListReport(ControleInterno controleInterno)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ControleInternoDao.getInstance().controleInternoListReport(controleInterno);
	}

	public void validation(ControleInterno controleInterno) {

	}

}