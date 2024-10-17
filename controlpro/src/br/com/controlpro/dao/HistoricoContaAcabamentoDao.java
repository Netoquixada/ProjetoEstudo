package br.com.controlpro.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.ContaPagarAcabamento;
import br.com.controlpro.entity.HistoricoContaAcabamento;
import br.com.controlpro.entity.HistoricoPagamentoResumo;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.core.SuperHibernate;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class HistoricoContaAcabamentoDao {

	@PersistenceContext
	private EntityManager manager;

	public static HistoricoContaAcabamentoDao getInstance() {
		return new HistoricoContaAcabamentoDao();
	}

	public LazyEntityDataModel<HistoricoContaAcabamento> historicoContasAcabamentoLazy(HistoricoContaAcabamento historicoConta) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		return new LazyEntityDataModel<HistoricoContaAcabamento>(HistoricoContaAcabamento.class, null, null, restrictions, null);
	}

	public List<HistoricoContaAcabamento> historicoContasAcabamento() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(HistoricoContaAcabamento.class, null, true, restricions);
	}

	public List<HistoricoContaAcabamento> historicoAcabamentoPorConta(ContaPagarAcabamento conta)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();
		if (conta != null) {
			restrictions.add(Restrictions.eq("contaPagarAcabamento", conta));
		}
		return GenericDao.findAllByAttributesRestrictions(HistoricoContaAcabamento.class, null, true, restrictions);
	}

	public List<HistoricoContaAcabamento> historicoContaAcabamentoFiltro(HistoricoContaAcabamento historico)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();

		if (historico.getFaccao() != null) {
			restricions.add(Restrictions.eq("faccao", historico.getFaccao()));
		}

		if (historico.getContaPagarAcabamento() != null) {
			restricions.add(Restrictions.eq("contaPagarAcabamento", historico.getContaPagarAcabamento()));
		}

		if (historico.getDataPesquisaInicio() != null && historico.getDataPesquisaFim() != null) {
			restricions.add(Restrictions.between("dataPagamento", historico.getDataPesquisaInicio(),
					historico.getDataPesquisaFim()));
		}

		if (historico.getDataPesquisaInicio() != null && historico.getDataPesquisaFim() == null) {
			historico.setDataPesquisaFim(historico.getDataPesquisaInicio());
			restricions.add(Restrictions.between("dataPagamento", historico.getDataPesquisaInicio(),
					historico.getDataPesquisaFim()));
		}

		if (historico.getDataPesquisaInicio() == null && historico.getDataPesquisaFim() != null) {
			historico.setDataPesquisaInicio(historico.getDataPesquisaFim());
			restricions.add(Restrictions.between("dataPagamento", historico.getDataPesquisaInicio(),
					historico.getDataPesquisaFim()));
		}
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(HistoricoContaAcabamento.class, null, true, restricions);
	}

	public List<HistoricoPagamentoResumo> pagamentosResumidos(HistoricoContaAcabamento filter) {
		Session session = null;

		String sql = "";
		if (filter.getFaccao() != null) {
			sql = "SELECT \r\n" + 
					"	faccao.nome as FACCAO,\r\n" + 
					"	SUM(C.valor_desconto) AS BONUS,\r\n" + 
					"	SUM(C.valor_juros) AS ACRESCIMO,\r\n" + 
					"	SUM(C.valor_pago) AS PAGO,\r\n" + 
					"	SUM(CP.valor_pagar) AS TOTAL_CONTA\r\n" + 
					" FROM\r\n" + 
					"	CONTROL_HISTORICO_CONTA_ACABAMENTO AS C\r\n" + 
					"		INNER JOIN CONTROL_FACCAO AS FACCAO\r\n" + 
					"			ON FACCAO.id = c.faccao_id\r\n" + 
					"		INNER JOIN CONTROL_CONTA_PAGAR_ACABAMENTO AS CP\r\n" + 
					"			ON C.id_conta_pagar_acabamento = CP.id\r\n" + 
					" WHERE \r\n" + 
					"	c.data_pagamento between ? and ? and FACCAO.id = ?\r\n" + 
					" GROUP BY FACCAO.nome";
		} else {
			sql = "SELECT\r\n" + 
					"	faccao.nome as FACCAO,\r\n" + 
					"	SUM(C.valor_desconto) AS BONUS,\r\n" + 
					"	SUM(C.valor_juros) AS ACRESCIMO,\r\n" + 
					"	SUM(C.valor_pago) AS PAGO,\r\n" + 
					"	SUM(CP.valor_pagar) AS TOTAL_CONTA\r\n" + 
					" FROM\r\n" + 
					"	CONTROL_HISTORICO_CONTA_ACABAMENTO AS C\r\n" + 
					"		INNER JOIN CONTROL_FACCAO AS FACCAO\r\n" + 
					"			ON FACCAO.id = c.faccao_id\r\n" + 
					"		INNER JOIN CONTROL_CONTA_PAGAR_ACABAMENTO AS CP\r\n" + 
					"			ON C.id_conta_pagar_acabamento = CP.id\r\n" + 
					" WHERE \r\n" + 
					"	c.data_pagamento between ? and ?\r\n" + 
					" GROUP BY FACCAO.nome ";
		}
		List<HistoricoPagamentoResumo> lista = new ArrayList<>();

		try {
			session = SuperHibernate.getHibernateInstance().getSession(Session.class);
			SQLQuery query = session.createSQLQuery(sql);
			query.setParameter(0, filter.getDataPesquisaInicio());
			query.setParameter(1, filter.getDataPesquisaFim());
			if (filter.getFaccao() != null) {
				query.setParameter(2, filter.getFaccao().getId());
			}
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

			@SuppressWarnings("rawtypes")
			List data = query.list();
			for (Object o : data) {
				@SuppressWarnings("rawtypes")
				Map map = (Map) o;
				lista.add(new HistoricoPagamentoResumo((String) map.get("FACCAO"),
						new BigDecimal(map.get("BONUS").toString()), 
						new BigDecimal(map.get("ACRESCIMO").toString()),
						new BigDecimal(map.get("PAGO").toString()),
						new BigDecimal(map.get("TOTAL_CONTA").toString())
						));
			}

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return lista;
	}

}
