package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ProdutoDao;
import br.com.controlpro.entity.consultas.Produto;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ProdutoBO {

	public static ProdutoBO getInstance() {
			return new ProdutoBO();
	}

	public void save(Produto Produto) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(Produto);
		GenericDao.save(Produto);
	}

	public void update(Produto Produto) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(Produto);
	}

	public Produto find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Produto.class, id);
	}
	
	public Produto findById(String id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Produto.class, id);
	}

	public LazyEntityDataModel<Produto> produtosLazy(Produto Produto) {
		return ProdutoDao.getInstance().produtosLazy(Produto);
	}

	public List<Produto> produtosComplete(String ProdutoName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ProdutoDao.getInstance().produtos(ProdutoName);
	}

	public List<Produto> produtosCompleteCodFornecedor(String codFornecedor)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ProdutoDao.getInstance().produtosCompleteCodFornecedor(codFornecedor);
	}
	public List<Produto> produtosCompleteCodProduto(String codProduto)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ProdutoDao.getInstance().produtosCompleteCodProdutoi(codProduto);
	}
	
	

	public List<Produto> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ProdutoDao.getInstance().produtos();
	}

	public StringBuilder dadosFiltro() {
		return ProdutoDao.getInstance().getDadosFiltro();
	}

	public List<Produto> produtoListReport(Produto produto)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ProdutoDao.getInstance().produtoListReport(produto);
	}

	public void validation(Produto produto) {

	}

}