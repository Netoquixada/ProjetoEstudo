package br.com.controlpro.bo;

import java.math.BigDecimal;
import java.util.List;

import br.com.controlpro.dao.VendasDao;
import br.com.controlpro.entity.consultas.Vendas;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class VendasBO {

	public static VendasBO getInstance() {
		return new VendasBO();
	}

	public Vendas find(Integer id)
			throws ObjetoNaoEncontradoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Vendas.class, id);
	}

	public LazyEntityDataModel<Vendas> vendasLazy(Vendas Vendas) {
		return VendasDao.getInstance().vendasLazy(Vendas);
	}

	public List<Vendas> vendasComplete(String VendasName) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return VendasDao.getInstance().vendas(VendasName);
	}

//	public Vendas vendaPorSequencia(Integer sequencia)
//			throws ObjetoNaoEncontradoHibernateException, SessaoNaoEncontradaParaEntidadeFornecidaException {
//		return VendasDao.getInstance().getVendaPorSequencia(sequencia);
//	}

	public List<Vendas> list(Vendas vendas) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return VendasDao.getInstance().vendas(vendas);
	}

	public List<Vendas> vendaPorSequencia(String sequencia) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return VendasDao.getInstance().vendasPorSequencia(sequencia);
	}
	
	public List<Vendas> consultaPorCodigoCliente(Integer id) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return VendasDao.getInstance().vendasPorCodigoCliente(id);
	}

	public BigDecimal totalVendasPorCodigoCliente(Integer codigo) {
		return VendasDao.getInstance().totalVendasPorCliente(codigo);
	}

	public StringBuilder dadosFiltro() {
		return VendasDao.getInstance().getDadosFiltro();
	}

	public void validation(Vendas vendas) {

	}

}