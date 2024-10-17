package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.Reserva;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ReservaDao {

	public static ReservaDao getInstance() {
		return new ReservaDao();
	}

	public LazyEntityDataModel<Reserva> reservasLazy(Reserva reserva) {
		List<Criterion> restrictions = new ArrayList<Criterion>();
		
		if (reserva.getOs() != null && !reserva.getOs().isEmpty()) {
			restrictions.add(Restrictions.like("os", reserva.getOs(), MatchMode.ANYWHERE));
		}
		if (reserva.getTipo() != null && !reserva.getOs().isEmpty()) {
			restrictions.add(Restrictions.like("tipo", reserva.getTipo(), MatchMode.ANYWHERE));
		}
		if (reserva.getLoja() != null) {
			restrictions.add(Restrictions.eq("loja", reserva.getLoja()));
		}
		
		if (reserva.getDataInicio() != null && reserva.getDataFim() != null) {
			restrictions.add(Restrictions.between("data", reserva.getDataInicio(), reserva.getDataFim()));
		}

		if (reserva.getDataInicio() != null && reserva.getDataFim() == null) {
			reserva.setDataFim(reserva.getDataInicio());
			restrictions.add(Restrictions.between("data", reserva.getDataInicio(), reserva.getDataFim()));
		}

		if (reserva.getDataInicio() == null && reserva.getDataFim() != null) {
			reserva.setDataInicio(reserva.getDataFim());
			restrictions.add(Restrictions.between("data", reserva.getDataInicio(), reserva.getDataFim()));
		}

		return new LazyEntityDataModel<Reserva>(Reserva.class, null,
				null, restrictions, null);
	}

	public List<Reserva> reservas()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(Reserva.class,
				null, true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<Reserva> reservaListReport(Reserva reserva)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();
		if (reserva.getOs() != null && !reserva.getOs().isEmpty()) {
			restrictions.add(Restrictions.like("os", reserva.getOs(), MatchMode.ANYWHERE));
		}
		if (reserva.getTipo() != null && !reserva.getTipo().isEmpty()) {
			restrictions.add(Restrictions.like("tipo", reserva.getTipo(), MatchMode.ANYWHERE));
		}
		if (reserva.getLoja() != null) {
			restrictions.add(Restrictions.eq("loja", reserva.getLoja()));
		}
		if (reserva.getDataInicio() != null && reserva.getDataFim() != null) {
			restrictions.add(Restrictions.between("data", reserva.getDataInicio(), reserva.getDataFim()));
		}
		if (reserva.getDataInicio() != null && reserva.getDataFim() == null) {
			reserva.setDataFim(reserva.getDataInicio());
			restrictions.add(Restrictions.between("data", reserva.getDataInicio(), reserva.getDataFim()));
		}
		if (reserva.getDataInicio() == null && reserva.getDataFim() != null) {
			reserva.setDataInicio(reserva.getDataFim());
			restrictions.add(Restrictions.between("data", reserva.getDataInicio(), reserva.getDataFim()));
		}
		return GenericDao.findAllByAttributesRestrictions(Reserva.class,
				null, true, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
