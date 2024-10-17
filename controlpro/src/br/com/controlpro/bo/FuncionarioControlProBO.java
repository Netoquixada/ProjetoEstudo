package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.FuncionarioControlProDao;
import br.com.controlpro.entity.FuncionarioControlPro;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class FuncionarioControlProBO {

	public static FuncionarioControlProBO getInstance() {
			return new FuncionarioControlProBO();
	}

	public void save(FuncionarioControlPro FuncionarioControlPro) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(FuncionarioControlPro);
		GenericDao.save(FuncionarioControlPro);
	}

	public void update(FuncionarioControlPro FuncionarioControlPro) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(FuncionarioControlPro);
	}

	public FuncionarioControlPro find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(FuncionarioControlPro.class, id);
	}

	public LazyEntityDataModel<FuncionarioControlPro> funcionarioControlProsLazy(FuncionarioControlPro FuncionarioControlPro) {
		return FuncionarioControlProDao.getInstance().funcionarioControlProsLazy(FuncionarioControlPro);
	}

	public List<FuncionarioControlPro> funcionarioControlProsComplete(String FuncionarioControlProName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return FuncionarioControlProDao.getInstance().funcionarioControlPros(FuncionarioControlProName);
	}

	public List<FuncionarioControlPro> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return FuncionarioControlProDao.getInstance().funcionarioControlPros();
	}


	public List<FuncionarioControlPro> funcionarioControlProListReport(FuncionarioControlPro funcionarioControlPro)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return FuncionarioControlProDao.getInstance().funcionarioControlProListReport(funcionarioControlPro);
	}

	public void validation(FuncionarioControlPro funcionarioControlPro) {

	}

}