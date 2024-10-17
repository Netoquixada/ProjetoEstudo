package br.com.controlpro.bo;

import java.math.BigDecimal;
import java.util.List;

import br.com.controlpro.dao.HistoricoContaAcabamentoDao;
import br.com.controlpro.entity.ContaPagarAcabamento;
import br.com.controlpro.entity.HistoricoContaAcabamento;
import br.com.controlpro.entity.HistoricoPagamentoResumo;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.exception.QuantidadeEnvioInvalidaException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class HistoricoContaAcabamentoBO {

	public static HistoricoContaAcabamentoBO getInstance() {
		return new HistoricoContaAcabamentoBO();
	}

	public void save(HistoricoContaAcabamento historicoConta)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(historicoConta);
		GenericDao.save(historicoConta);
	}

	public void update(HistoricoContaAcabamento historicoConta)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(historicoConta);
	}

	public HistoricoContaAcabamento find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(HistoricoContaAcabamento.class, id);
	}

	public LazyEntityDataModel<HistoricoContaAcabamento> historicoContaAcabamentoLazy(
			HistoricoContaAcabamento historicoConta) {
		return HistoricoContaAcabamentoDao.getInstance().historicoContasAcabamentoLazy(historicoConta);
	}

	public List<HistoricoContaAcabamento> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return HistoricoContaAcabamentoDao.getInstance().historicoContasAcabamento();
	}

	public List<HistoricoContaAcabamento> historicoContaAcabamento(ContaPagarAcabamento conta)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return HistoricoContaAcabamentoDao.getInstance().historicoAcabamentoPorConta(conta);
	}
	
	public List<HistoricoContaAcabamento> listFiltro(HistoricoContaAcabamento historico)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return HistoricoContaAcabamentoDao.getInstance().historicoContaAcabamentoFiltro(historico);
	}
	
	public List<HistoricoPagamentoResumo> pagamentosResumidos(HistoricoContaAcabamento filter){
		return HistoricoContaAcabamentoDao.getInstance().pagamentosResumidos(filter);
	}
	
	public void validation(HistoricoContaAcabamento ordemCorte) {

	}

	public void validarQuantidadeValorPago(BigDecimal valorDevedor, BigDecimal valorPago)
			throws QuantidadeEnvioInvalidaException {

		if (valorPago.doubleValue() > valorDevedor.doubleValue()) {
			throw new QuantidadeEnvioInvalidaException(
					"Valor pago é maior que o valor devedor!");
		}
	}
	
}