package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.consultas.Estoque;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class EstoqueDao {

	public static EstoqueDao getInstance() {
		return new EstoqueDao();
	}

	
	public List<Estoque> estoques()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		return GenericDao.findAllByAttributesRestrictions(Estoque.class,
				"data", true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<Estoque> estoqueListReport(Estoque estoque)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (estoque.getProduto() != null && !estoque.getProduto().isEmpty()) {
			System.out.println(estoque.getProduto());
			restrictions.add(Restrictions.like("produto", estoque.getProduto(),MatchMode.EXACT));
		}
		
		if (estoque.getDataPesquisaInicio() != null && estoque.getDataPesquisaFim() != null) {
			restrictions.add(Restrictions.between("data", estoque.getDataPesquisaInicio(), estoque.getDataPesquisaFim()));
		}

		if (estoque.getDataPesquisaInicio() != null && estoque.getDataPesquisaFim() == null) {
			estoque.setDataPesquisaFim(estoque.getDataPesquisaInicio());
			restrictions.add(Restrictions.between("data", estoque.getDataPesquisaInicio(), estoque.getDataPesquisaFim()));
		}

		if (estoque.getDataPesquisaInicio() == null && estoque.getDataPesquisaFim() != null) {
			estoque.setDataPesquisaInicio(estoque.getDataPesquisaFim());
			restrictions.add(Restrictions.between("data", estoque.getDataPesquisaInicio(), estoque.getDataPesquisaFim()));
		}
		return GenericDao.findAllByAttributesRestrictions(Estoque.class,
				"data", false, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
