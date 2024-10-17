package br.com.controlpro.bo;

import java.math.BigDecimal;
import java.util.List;

import br.com.controlpro.constants.Loja;
import br.com.controlpro.dao.HistoricoContaDao;
import br.com.controlpro.entity.ContaPagar;
import br.com.controlpro.entity.HistoricoConta;
import br.com.controlpro.entity.HistoricoPagamentoResumo;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.exception.QuantidadeEnvioInvalidaException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class HistoricoContaBO {

	public static HistoricoContaBO getInstance() {
		return new HistoricoContaBO();
	}

	public void save(HistoricoConta historicoConta)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(historicoConta);
		GenericDao.save(historicoConta);
	}

	public void update(HistoricoConta historicoConta)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(historicoConta);
	}

	public HistoricoConta find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(HistoricoConta.class, id);
	}

	public LazyEntityDataModel<HistoricoConta> historicoContaLazy(
			HistoricoConta historicoConta) {
		return HistoricoContaDao.getInstance().historicoContasLazy(historicoConta);
	}

	public List<HistoricoConta> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return HistoricoContaDao.getInstance().historicoContas();
	}

	public List<HistoricoConta> historicoConta(ContaPagar conta)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return HistoricoContaDao.getInstance().historicoPorConta(conta);
	}
	
	public List<HistoricoConta> listFiltro(HistoricoConta historico, Loja loja)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return HistoricoContaDao.getInstance().historicoContaFiltro(historico, loja);
	}
	
	public List<HistoricoPagamentoResumo> pagamentosResumidos(HistoricoConta filter){
		return HistoricoContaDao.getInstance().pagamentosResumidos(filter);
	}

	public void validation(HistoricoConta ordemCorte) {

	}

	public void validarQuantidadeValorPago(BigDecimal valorDevedor, BigDecimal valorPago)
			throws QuantidadeEnvioInvalidaException {

		if (valorPago.doubleValue() > valorDevedor.doubleValue()) {
			throw new QuantidadeEnvioInvalidaException(
					"Valor pago é maior que o valor devedor!");
		}
	}
	
}