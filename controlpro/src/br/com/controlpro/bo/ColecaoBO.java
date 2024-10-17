package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ColecaoDao;
import br.com.controlpro.entity.Colecao;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ColecaoBO {

	public static ColecaoBO getInstance() {
		return new ColecaoBO();
	}

	public void save(Colecao Colecao) throws ViolacaoChaveHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException, ObjetoExistenteException {
		validation(Colecao);
		GenericDao.save(Colecao);
	}

	public void update(Colecao Colecao) throws ViolacaoChaveHibernateException, ObjetoNaoEncontradoHibernateException,
			ValidacaoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException, ObjetoExistenteException {
		GenericDao.update(Colecao);
	}

	public Colecao find(Integer id)
			throws ObjetoNaoEncontradoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Colecao.class, id);
	}

	public LazyEntityDataModel<Colecao> colecaosLazy(Colecao Colecao) {
		return ColecaoDao.getInstance().colecaosLazy(Colecao);
	}

	public List<Colecao> colecaosComplete(String ColecaoName) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ColecaoDao.getInstance().colecaos(ColecaoName);
	}

	public List<Colecao> list() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ColecaoDao.getInstance().colecaos();
	}

	public StringBuilder dadosFiltro() {
		return ColecaoDao.getInstance().getDadosFiltro();
	}

	public List<Colecao> colecaoListReport(Colecao colecao) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ColecaoDao.getInstance().colecaoListReport(colecao);
	}

	public void validation(Colecao colecao) {

	}

}