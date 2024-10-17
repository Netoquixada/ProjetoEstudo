package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.MotoboyDao;
import br.com.controlpro.entity.Motoboy;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

public class MotoboyBO {

	public static MotoboyBO getInstance() {
			return new MotoboyBO();
	}

	public void save(Motoboy Motoboy) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(Motoboy);
		GenericDao.save(Motoboy);
	}

	public void update(Motoboy Motoboy) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(Motoboy);
	}

	public Motoboy find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Motoboy.class, id);
	}

	public List<Motoboy> motoboysComplete(String MotoboyName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return MotoboyDao.getInstance().motoboys(MotoboyName);
	}

	public List<Motoboy> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return MotoboyDao.getInstance().motoboys();
	}

	public StringBuilder dadosFiltro() {
		return MotoboyDao.getInstance().getDadosFiltro();
	}

	public List<Motoboy> motoboyListReport(Motoboy motoboy)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return MotoboyDao.getInstance().motoboyListReport(motoboy);
	}

	public void validation(Motoboy motoboy) {

	}

}