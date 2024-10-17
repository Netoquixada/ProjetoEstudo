package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ExcursaoDao;
import br.com.controlpro.entity.Excursao;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ExcursaoBO {

	public static ExcursaoBO getInstance() {
		return new ExcursaoBO();
	}

	public void save(Excursao Excursao) throws ViolacaoChaveHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException, ObjetoExistenteException {
		validation(Excursao);
		GenericDao.save(Excursao);
	}

	public void update(Excursao Excursao) throws ViolacaoChaveHibernateException, ObjetoNaoEncontradoHibernateException,
			ValidacaoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException, ObjetoExistenteException {
		GenericDao.update(Excursao);
	}

	public Excursao find(Integer id)
			throws ObjetoNaoEncontradoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Excursao.class, id);
	}

	public LazyEntityDataModel<Excursao> excursaosLazy(Excursao Excursao) {
		return ExcursaoDao.getInstance().excursaosLazy(Excursao);
	}

	public List<Excursao> excursaosComplete(String ExcursaoName) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ExcursaoDao.getInstance().excursaos(ExcursaoName);
	}

	public List<Excursao> list() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ExcursaoDao.getInstance().excursaos();
	}

	public StringBuilder dadosFiltro() {
		return ExcursaoDao.getInstance().getDadosFiltro();
	}

	public List<Excursao> excursaoListReport(Excursao excursao) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ExcursaoDao.getInstance().excursaoListReport(excursao);
	}

	public void validation(Excursao excursao) {

	}

}