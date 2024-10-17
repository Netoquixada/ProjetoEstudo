package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.HistoricoControlePedidoDao;
import br.com.controlpro.entity.ControlePedido;
import br.com.controlpro.entity.AquisicaoSaida;
import br.com.controlpro.entity.HistoricoControlePedido;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.IntegridadeObjetoHibernateException;
import br.hibernateutil.exception.ListaVaziaException;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

public class HistoricoControlePedidoBO {

	private HistoricoControlePedidoBO() {
	}

	public static HistoricoControlePedidoBO getInstance() {
		return new HistoricoControlePedidoBO();
	}

	public void save(HistoricoControlePedido historicoControlePedido)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.save(historicoControlePedido);
	}

	public void update(HistoricoControlePedido aquisicaoSaida)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.update(aquisicaoSaida);
	}

	public void delete(HistoricoControlePedido aquisicaoSaida)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		GenericDao.delete(aquisicaoSaida);
	}

	public List<HistoricoControlePedido> historicoPorControlePedido(ControlePedido controlePedido)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return HistoricoControlePedidoDao.getInstance().historicoPorControlePedido(controlePedido);
	}

	public void mergeAll(List<HistoricoControlePedido> lista)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.mergeAll(lista);
	}

	public void updateAll(List<HistoricoControlePedido> historico)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ListaVaziaException, ObjetoNaoEncontradoHibernateException {
		GenericDao.updateAll(historico);
	}

	public void deleteAll(List<HistoricoControlePedido> historicoAux)
			throws IntegridadeObjetoHibernateException,
			ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		synchronized (AquisicaoSaida.class) {
			for (int i = 0; i < historicoAux.size(); i++) {
				GenericDao.delete(historicoAux.get(i));
			}
		}
	}

}
