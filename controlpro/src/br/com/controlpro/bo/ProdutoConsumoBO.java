package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.ProdutoConsumoDao;
import br.com.controlpro.entity.ProdutoConsumo;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ProdutoConsumoBO {

	public static ProdutoConsumoBO getInstance() {
		return new ProdutoConsumoBO();
	}

	public void save(ProdutoConsumo ProdutoConsumo) throws ViolacaoChaveHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException, ObjetoExistenteException {
		validation(ProdutoConsumo);
		GenericDao.save(ProdutoConsumo);
	}

	public void update(ProdutoConsumo ProdutoConsumo)
			throws ViolacaoChaveHibernateException, ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException, ObjetoExistenteException {
		GenericDao.update(ProdutoConsumo);
	}

	public ProdutoConsumo find(Integer id)
			throws ObjetoNaoEncontradoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(ProdutoConsumo.class, id);
	}

	public LazyEntityDataModel<ProdutoConsumo> produtoConsumoLazy(ProdutoConsumo ProdutoConsumo) {
		return ProdutoConsumoDao.getInstance().produtoConsumosLazy(ProdutoConsumo);
	}

	public List<ProdutoConsumo> produtosConsumoComplete(String ProdutoConsumoName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ProdutoConsumoDao.getInstance().produtoConsumos(ProdutoConsumoName);
	}

	public List<ProdutoConsumo> list() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ProdutoConsumoDao.getInstance().produtoConsumos();
	}

	public StringBuilder dadosFiltro() {
		return ProdutoConsumoDao.getInstance().getDadosFiltro();
	}

	public List<ProdutoConsumo> produtosConsumoListReport(ProdutoConsumo produtoConsumo)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return ProdutoConsumoDao.getInstance().produtoConsumoListReport(produtoConsumo);
	}

	public void validation(ProdutoConsumo produtoConsumo)
			throws ObjetoExistenteException, SessaoNaoEncontradaParaEntidadeFornecidaException {

		ProdutoConsumo produto = GenericDao.findByAttribute(ProdutoConsumo.class, "produto", produtoConsumo.getProduto());

		if (produto != null) {
			if (!produto.equals(produtoConsumo)) {
				throw new ObjetoExistenteException("Já uma precificação para o produto: " +produto.getProduto().getId() + " - "+  produto.getProduto().getNome());
			}
		}
	}

}