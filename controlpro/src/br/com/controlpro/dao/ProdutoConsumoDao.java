package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.ProdutoConsumo;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ProdutoConsumoDao {

	public static ProdutoConsumoDao getInstance() {
		return new ProdutoConsumoDao();
	}
	
	public ProdutoConsumo getById(Integer id) {
		try {
			return GenericDao.findById(ProdutoConsumo.class, id);
		} catch (ObjetoNaoEncontradoHibernateException e) {
			e.printStackTrace();
			return null;
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
			return null;
		}
	}

	public LazyEntityDataModel<ProdutoConsumo> produtoConsumosLazy(ProdutoConsumo produtoConsumo) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (produtoConsumo.getProduto() != null) {
			restrictions.add(Restrictions.eq("produto", produtoConsumo.getProduto()));
		}

		return new LazyEntityDataModel<ProdutoConsumo>(ProdutoConsumo.class, null, null, restrictions, null);
	}

	public List<ProdutoConsumo> produtoConsumos(String produtoConsumo)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();
		return GenericDao.findAllByAttributesRestrictions(ProdutoConsumo.class, null, true, restrictions);
	}

	public List<ProdutoConsumo> produtoConsumos() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(ProdutoConsumo.class, "id", false, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<ProdutoConsumo> produtoConsumoListReport(ProdutoConsumo produtoConsumo)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (produtoConsumo.getProduto() != null) {
			restrictions.add(Restrictions.eq("produto", produtoConsumo.getProduto()));
			dadosFiltro.append("PRODUTO: " + produtoConsumo.getProduto());
		}
		return GenericDao.findAllByAttributesRestrictions(ProdutoConsumo.class, "id", false, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
