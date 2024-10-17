package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ReservaDao;
import br.com.controlpro.entity.Reserva;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ReservaBO {

	public static ReservaBO getInstance() {
		return new ReservaBO();
	}

	public void save(Reserva reserva)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(reserva);
		GenericDao.save(reserva);
	}

	public void update(Reserva reserva)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(reserva);
	}

	public Reserva find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Reserva.class, id);
	}

	public LazyEntityDataModel<Reserva> reservaLazy(
			Reserva reserva) {
		return ReservaDao.getInstance().reservasLazy(reserva);
	}

	public List<Reserva> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ReservaDao.getInstance().reservas();
	}

	public StringBuilder dadosFiltro() {
		return ReservaDao.getInstance().getDadosFiltro();
	}

	public List<Reserva> reservaListReport(Reserva reserva)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ReservaDao.getInstance().reservaListReport(
				reserva);
	}

	public void validation(Reserva ordemCorte) {

	}

}