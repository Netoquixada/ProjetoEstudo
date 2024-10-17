package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ContaBancariaDao;
import br.com.controlpro.entity.consultas.ContaBancaria;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ContaBancariaBO {

	public static ContaBancariaBO getInstance() {
			return new ContaBancariaBO();
	}

	public void save(ContaBancaria ContaBancaria) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(ContaBancaria);
		GenericDao.save(ContaBancaria);
	}

	public void update(ContaBancaria ContaBancaria) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(ContaBancaria);
	}

	public ContaBancaria find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(ContaBancaria.class, id);
	}

	public LazyEntityDataModel<ContaBancaria> bancosLazy(ContaBancaria ContaBancaria) {
		return ContaBancariaDao.getInstance().bontaBancariasLazy(ContaBancaria);
	}

	public List<ContaBancaria> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ContaBancariaDao.getInstance().bontaBancarias();
	}

	public StringBuilder dadosFiltro() {
		return ContaBancariaDao.getInstance().getDadosFiltro();
	}

	public void validation(ContaBancaria funcionario) {

	}

}