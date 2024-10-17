package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.FuncionarioDao;
import br.com.controlpro.entity.consultas.Funcionario;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class FuncionarioBO {

	public static FuncionarioBO getInstance() {
			return new FuncionarioBO();
	}

	public void save(Funcionario Funcionario) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(Funcionario);
		GenericDao.save(Funcionario);
	}

	public void update(Funcionario Funcionario) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(Funcionario);
	}

	public Funcionario find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Funcionario.class, id);
	}

	public LazyEntityDataModel<Funcionario> funcionariosLazy(Funcionario Funcionario) {
		return FuncionarioDao.getInstance().funcionariosLazy(Funcionario);
	}

	public List<Funcionario> funcionariosComplete(String FuncionarioName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return FuncionarioDao.getInstance().funcionarios(FuncionarioName);
	}

	public List<Funcionario> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return FuncionarioDao.getInstance().funcionarios();
	}

	public StringBuilder dadosFiltro() {
		return FuncionarioDao.getInstance().getDadosFiltro();
	}

	public List<Funcionario> funcionarioListReport(Funcionario funcionario)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return FuncionarioDao.getInstance().funcionarioListReport(funcionario);
	}

	public void validation(Funcionario funcionario) {

	}

}