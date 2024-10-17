package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.FaccaoDao;
import br.com.controlpro.entity.Faccao;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class FaccaoBO {

	public static FaccaoBO getInstance() {
			return new FaccaoBO();
	}

	public void save(Faccao Faccao) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(Faccao);
		GenericDao.save(Faccao);
	}

	public void update(Faccao Faccao) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(Faccao);
	}

	public Faccao find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Faccao.class, id);
	}

	public LazyEntityDataModel<Faccao> faccaosLazy(Faccao Faccao) {
		return FaccaoDao.getInstance().faccaosLazy(Faccao);
	}

	public List<Faccao> faccaosComplete(String FaccaoName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return FaccaoDao.getInstance().faccaos(FaccaoName);
	}

	public List<Faccao> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return FaccaoDao.getInstance().faccaos();
	}

	public StringBuilder dadosFiltro() {
		return FaccaoDao.getInstance().getDadosFiltro();
	}

	public List<Faccao> faccaoListReport(Faccao faccao)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return FaccaoDao.getInstance().faccaoListReport(faccao);
	}

	public void validation(Faccao faccao) {

	}

}