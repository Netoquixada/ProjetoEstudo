package br.com.controlpro.bo;

import java.util.List;

import br.com.controlpro.dao.LogisticaDao;
import br.com.controlpro.entity.Logistica;
import br.com.controlpro.entity.LogisticaAud;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;

public class LogisticaBO {

	public static LogisticaBO getInstance() {
		return new LogisticaBO();
	}

	public void saveAud(LogisticaAud logisticaAud)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.save(logisticaAud);
	}
	public void save(Logistica logistica)
			throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(logistica);
		GenericDao.save(logistica);
	}

	public void update(Logistica logistica)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(logistica);
	}

	public Logistica find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Logistica.class, id);
	}

	public List<Logistica> logisticaComplete(String logisticaName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return LogisticaDao.getInstance().logisticas(logisticaName);
	}

	public List<Logistica> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return LogisticaDao.getInstance().logisticas();
	}

	public StringBuilder dadosFiltro() {
		return LogisticaDao.getInstance().getDadosFiltro();
	}

	public List<Logistica> logisticaListReport(Logistica logistica)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return LogisticaDao.getInstance().logisticaListReport(
				logistica);
	}

	public List<LogisticaAud> logisticaListAuditoria(LogisticaAud logistica)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return LogisticaDao.getInstance().logisticaListAuditoria(
				logistica);
	}

	public void validation(Logistica logistica) throws SessaoNaoEncontradaParaEntidadeFornecidaException, ObjetoExistenteException {

		List<Logistica> lista = LogisticaBO.getInstance().list();

		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getPedido().equals(logistica.getPedido())
					&& lista.get(i).getLocalLogistica()
							.equals(logistica.getLocalLogistica())) {
				throw new ObjetoExistenteException("Foi encontrado um registro com esse pedido e esse local!");
			}
		}
		
	}
	

}