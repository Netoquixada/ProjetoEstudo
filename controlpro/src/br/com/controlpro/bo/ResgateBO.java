package br.com.controlpro.bo;

import java.math.BigDecimal;
import java.util.List;

import br.com.controlpro.dao.ResgateDao;
import br.com.controlpro.entity.Resgate;
import br.com.controlpro.entity.consultas.Cliente;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.exception.ValorResgateExcedidoException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ResgateBO {

	public static ResgateBO getInstance() {
			return new ResgateBO();
	}

	public void save(Resgate Resgate) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException, ValorResgateExcedidoException {
		validation(Resgate);
		GenericDao.save(Resgate);
	}

	public void update(Resgate Resgate) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(Resgate);
	}

	public Resgate find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Resgate.class, id);
	}

	public LazyEntityDataModel<Resgate> produtosLazy(Resgate resgate) {
		return ResgateDao.getInstance().resgateLazy(resgate);
	}
	
	public BigDecimal totalResgatePorCodigoCliente(Integer codigo) {
		return ResgateDao.getInstance().totalResgatePorCliente(codigo);
	}
	
	public List<Resgate> resgatePorCliente(Cliente cliente) throws SessaoNaoEncontradaParaEntidadeFornecidaException{
		return ResgateDao.getInstance().resgatePorCliente(cliente);
	}

	public void validation(Resgate resgate) throws ValorResgateExcedidoException {
		if (resgate.getValorResgate().doubleValue() > resgate.getSaldo().doubleValue()	) {
			throw new ValorResgateExcedidoException("Valor do resgate é maior ou igual ao saldo disposnível!");
		}else if(resgate.getSaldo().doubleValue() == 0.0) {
			throw new ValorResgateExcedidoException("Seu saldo � R$0,00!");
		}
	}

}