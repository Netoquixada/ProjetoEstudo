package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.Logistica;
import br.com.controlpro.entity.LogisticaAud;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class LogisticaDao {

	public static LogisticaDao getInstance() {
		return new LogisticaDao();
	}

	public List<Logistica> logisticas(String logistica) throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		return GenericDao.findAllByAttributesRestrictions(Logistica.class, null, true, restrictions);
	}

	public List<Logistica> logisticas() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(Logistica.class, "dataCadastro", false, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<Logistica> logisticaListReport(Logistica logistica)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();
		if (logistica.getPedido() != null && !logistica.getPedido().isEmpty()) {
			restrictions.add(Restrictions.like("pedido", logistica.getPedido(), MatchMode.EXACT));
		}
		
		if (logistica.getStatusLogistica() != null) {
			restrictions.add(Restrictions.eq("statusLogistica", logistica.getStatusLogistica()));
		}
		
		if (logistica.getDataInicio() != null && logistica.getDataFim() != null) {
			restrictions.add(Restrictions.between("dataEnvio", logistica.getDataInicio(), logistica.getDataFim()));
		}

		if (logistica.getDataInicio() != null && logistica.getDataFim() == null) {
			logistica.setDataFim(logistica.getDataInicio());
			restrictions.add(Restrictions.between("dataEnvio", logistica.getDataInicio(), logistica.getDataFim()));
		}

		if (logistica.getDataInicio() == null && logistica.getDataFim() != null) {
			logistica.setDataInicio(logistica.getDataFim());
			restrictions.add(Restrictions.between("dataEnvio", logistica.getDataInicio(), logistica.getDataFim()));
		}
		
		return GenericDao.findAllByAttributesRestrictions(Logistica.class, "dataCadastro", false, restrictions);
	}

	public List<LogisticaAud> logisticaListAuditoria(LogisticaAud logistica)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();
		if (logistica.getPedido() != null && !logistica.getPedido().isEmpty()) {
			restrictions.add(Restrictions.like("pedido", logistica.getPedido(), MatchMode.EXACT));
		}
		return GenericDao.findAllByAttributesRestrictions(LogisticaAud.class, "dataAlteracao", false, restrictions);
	}

	public List<Logistica> logisticaValidacao(Logistica logistica)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		restrictions.add(Restrictions.like("pedido", logistica.getPedido(), MatchMode.EXACT));
		restrictions.add(Restrictions.eq("localLogistica", logistica.getLocalLogistica()));

		return GenericDao.findAllByAttributesRestrictions(Logistica.class, "dataCadastro", false, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
