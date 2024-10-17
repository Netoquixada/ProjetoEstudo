package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.consultas.AdmCartao;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class AdmCartaoDao {

	public static AdmCartaoDao getInstance() {
		return new AdmCartaoDao();
	}

	public LazyEntityDataModel<AdmCartao> admCartaosLazy(AdmCartao admCartao) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (admCartao.getNome() != null && !admCartao.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", admCartao.getNome(), MatchMode.ANYWHERE));
		}
		return new LazyEntityDataModel<AdmCartao>(AdmCartao.class, null, null, restrictions, null);

	}

	public List<AdmCartao> admCartaos(String admCartao) throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (admCartao != null && !admCartao.isEmpty()) {
			restrictions.add(Restrictions.like("nome", admCartao, MatchMode.ANYWHERE));
		}
		return GenericDao.findAllByAttributesRestrictions(AdmCartao.class, "nome", true, restrictions);
	}

	public List<AdmCartao> admCartaos() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		return GenericDao.findAllByAttributesRestrictions(AdmCartao.class, "nome", true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<AdmCartao> admCartaoListReport(AdmCartao admCartao) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (admCartao.getNome() != null && !admCartao.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", admCartao.getNome(), MatchMode.ANYWHERE));

			dadosFiltro.append("NOME: " + admCartao.getNome());
		}
		return GenericDao.findAllByAttributesRestrictions(AdmCartao.class, "nome", true, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
