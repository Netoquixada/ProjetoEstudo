package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.AdmCartaoDao;
import br.com.controlpro.entity.consultas.AdmCartao;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class AdmCartaoBO {

	public static AdmCartaoBO getInstance() {
			return new AdmCartaoBO();
	}

	public void save(AdmCartao AdmCartao) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(AdmCartao);
		GenericDao.save(AdmCartao);
	}

	public void update(AdmCartao AdmCartao) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(AdmCartao);
	}

	public AdmCartao find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(AdmCartao.class, id);
	}

	public LazyEntityDataModel<AdmCartao> bancosLazy(AdmCartao AdmCartao) {
		return AdmCartaoDao.getInstance().admCartaosLazy(AdmCartao);
	}

	public List<AdmCartao> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return AdmCartaoDao.getInstance().admCartaos();
	}

	public StringBuilder dadosFiltro() {
		return AdmCartaoDao.getInstance().getDadosFiltro();
	}

	public void validation(AdmCartao funcionario) {

	}

}