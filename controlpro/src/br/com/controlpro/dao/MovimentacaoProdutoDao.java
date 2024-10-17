package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.consultas.MovimentacaoProduto;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class MovimentacaoProdutoDao {

	public static MovimentacaoProdutoDao getInstance() {
		return new MovimentacaoProdutoDao();
	}

	public MovimentacaoProduto findById(Integer id)
			throws ObjetoNaoEncontradoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(MovimentacaoProduto.class, id);
	}

	public LazyEntityDataModel<MovimentacaoProduto> movimentacoesProdutoLazy(MovimentacaoProduto movimentacao) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (movimentacao.getSequencia() != null) {
			restrictions.add(Restrictions.like("sequencia", movimentacao.getSequencia(), MatchMode.EXACT));
		}
		return new LazyEntityDataModel<MovimentacaoProduto>(MovimentacaoProduto.class, null, null, restrictions, null);
	}

	public List<MovimentacaoProduto> movimentacoesProduto(String movimentacoes)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		return GenericDao.findAllByAttributesRestrictions(MovimentacaoProduto.class, null, true, restrictions);
	}

	public List<MovimentacaoProduto> movimentacoesProduto() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		return GenericDao.findAllByAttributesRestrictions(MovimentacaoProduto.class, null, true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<MovimentacaoProduto> movimentacoesProdutoListReport(MovimentacaoProduto movimentacaoProduto)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (movimentacaoProduto.getSequencia() != null) {
			restrictions.add(Restrictions.like("sequencia", movimentacaoProduto.getSequencia(), MatchMode.EXACT));
		}
		return GenericDao.findAllByAttributesRestrictions(MovimentacaoProduto.class, null, true, restrictions);
	}
	
	public List<MovimentacaoProduto> movimentacoesProdutoPorSequencia(String sequencia)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (sequencia != null) {
			restrictions.add(Restrictions.like("sequencia", sequencia, MatchMode.EXACT));
		}
		return GenericDao.findAllByAttributesRestrictions(MovimentacaoProduto.class, null, true, restrictions);
	}
	
	

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
