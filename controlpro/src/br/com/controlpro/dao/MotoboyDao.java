package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.Motoboy;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class MotoboyDao {

	public static MotoboyDao getInstance() {
		return new MotoboyDao();
	}

	public List<Motoboy> motoboys(String motoboy)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (motoboy != null && !motoboy.isEmpty()) {
			restrictions.add(Restrictions.like("nome", motoboy, MatchMode.ANYWHERE));
		}
		return GenericDao.findAllByAttributesRestrictions(Motoboy.class,
				"nome", true, restrictions);
	}

	public List<Motoboy> motoboys()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(Motoboy.class,
				"nome", true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<Motoboy> motoboyListReport(Motoboy motoboy)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (motoboy.getNome() != null && !motoboy.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", motoboy.getNome(),
					MatchMode.ANYWHERE));

			dadosFiltro.append("NOME: " + motoboy.getNome());
		}
		return GenericDao.findAllByAttributesRestrictions(Motoboy.class,
				"nome", true, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
