package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.CofreDao;
import br.com.controlpro.entity.Cofre;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class CofreBO {

	public static CofreBO getInstance() {
		return new CofreBO();
	}

	public void save(Cofre cofre)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(cofre);
		GenericDao.save(cofre);
	}

	public void update(Cofre cofre)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(cofre);
	}

	public Cofre find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Cofre.class, id);
	}

	public LazyEntityDataModel<Cofre> cofreLazy(
			Cofre cofre) {
		return CofreDao.getInstance().cofresLazy(cofre);
	}

	public List<Cofre> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return CofreDao.getInstance().cofres();
	}

	public StringBuilder dadosFiltro() {
		return CofreDao.getInstance().getDadosFiltro();
	}

	public List<Cofre> cofreListReport(Cofre cofre)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return CofreDao.getInstance().cofreListReport(
				cofre);
	}

	public void validation(Cofre ordemCorte) {

	}

}