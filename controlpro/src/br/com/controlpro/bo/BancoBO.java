package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.BancosDao;
import br.com.controlpro.entity.consultas.Banco;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class BancoBO {

	public static BancoBO getInstance() {
			return new BancoBO();
	}

	public void save(Banco Banco) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(Banco);
		GenericDao.save(Banco);
	}

	public void update(Banco Banco) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(Banco);
	}

	public Banco find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Banco.class, id);
	}

	public LazyEntityDataModel<Banco> bancosLazy(Banco Banco) {
		return BancosDao.getInstance().bancosLazy(Banco);
	}

	public List<Banco> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return BancosDao.getInstance().bancos();
	}

	public StringBuilder dadosFiltro() {
		return BancosDao.getInstance().getDadosFiltro();
	}

	public void validation(Banco funcionario) {

	}

}