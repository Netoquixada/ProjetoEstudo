package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.MovimentacaoProdutoDao;
import br.com.controlpro.entity.consultas.MovimentacaoProduto;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class MovimentacaoProdutoBO {

	public static MovimentacaoProdutoBO getInstance() {
		return new MovimentacaoProdutoBO();
	}

	public MovimentacaoProduto find(Integer id)
			throws ObjetoNaoEncontradoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(MovimentacaoProduto.class, id);
	}

	public LazyEntityDataModel<MovimentacaoProduto> movimentacaoProdutoLazy(MovimentacaoProduto MovimentacaoProduto) {
		return MovimentacaoProdutoDao.getInstance().movimentacoesProdutoLazy(MovimentacaoProduto);
	}

	public List<MovimentacaoProduto> movimentacaoProdutoComplete(String MovimentacaoProdutoName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return MovimentacaoProdutoDao.getInstance().movimentacoesProduto(MovimentacaoProdutoName);
	}

	public List<MovimentacaoProduto> list() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return MovimentacaoProdutoDao.getInstance().movimentacoesProduto();
	}

	public StringBuilder dadosFiltro() {
		return MovimentacaoProdutoDao.getInstance().getDadosFiltro();
	}

	public List<MovimentacaoProduto> movimentacoesProdutoListReport(MovimentacaoProduto conferencia)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return MovimentacaoProdutoDao.getInstance().movimentacoesProdutoListReport(conferencia);
	}
	
	public List<MovimentacaoProduto> movimentacoesProdutoPorSequencia(String sequencia)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return MovimentacaoProdutoDao.getInstance().movimentacoesProdutoPorSequencia(sequencia);
	}

	public void validation(MovimentacaoProduto conferencia) {

	}

}