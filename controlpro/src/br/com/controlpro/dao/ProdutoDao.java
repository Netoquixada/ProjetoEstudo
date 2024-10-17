package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.consultas.Produto;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ProdutoDao {

	public static ProdutoDao getInstance() {
		return new ProdutoDao();
	}
	
	public Produto getById(String id) throws ObjetoNaoEncontradoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException{
		return GenericDao.findById(Produto.class, id);
	}

	public LazyEntityDataModel<Produto> produtosLazy(Produto produto) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (produto.getId() != null && !produto.getId().isEmpty()) {
			restrictions.add(Restrictions.like("id", produto.getId()));
		}
		
		if (produto.getNome() != null && !produto.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", produto.getNome(),
					MatchMode.ANYWHERE));
		}
		return new LazyEntityDataModel<Produto>(Produto.class, null, null,
				restrictions, null);

	}

	public List<Produto> produtos(String produto)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (produto != null && !produto.isEmpty()) {
			restrictions.add(Restrictions.like("nome", produto));
		}
		return GenericDao.findAllByAttributesRestrictions(Produto.class,
				"nome", true, restrictions);
	}

	public List<Produto> produtosCompleteCodFornecedor(String codFornecedor)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (codFornecedor != null && !codFornecedor.isEmpty()) {
			restrictions.add(Restrictions.like("codFornecedor", codFornecedor,
					MatchMode.START));
		}
		return GenericDao.findAllByAttributesRestrictions(Produto.class,
				"nome", true, restrictions);
	}
	
	public List<Produto> produtosCompleteCodProdutoi(String codProduto)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (codProduto != null) {
			restrictions.add(Restrictions.like("id", codProduto));
		}
		return GenericDao.findAllByAttributesRestrictions(Produto.class,
				"nome", true, restrictions);
	}

	public List<Produto> produtos()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		return GenericDao.findAllByAttributesRestrictions(Produto.class,
				"nome", true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<Produto> produtoListReport(Produto produto)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (produto.getNome() != null && !produto.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", produto.getNome(),
					MatchMode.ANYWHERE));

			dadosFiltro.append("NOME: " + produto.getNome());
		}
		return GenericDao.findAllByAttributesRestrictions(Produto.class,
				"nome", true, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
