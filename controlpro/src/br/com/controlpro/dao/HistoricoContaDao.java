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

import br.com.controlpro.constants.Loja;
import br.com.controlpro.entity.ContaPagar;
import br.com.controlpro.entity.HistoricoConta;
import br.com.controlpro.entity.HistoricoPagamentoResumo;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.core.SuperHibernate;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;
import br.hibernateutil.util.AliasUece;

public class HistoricoContaDao {

	@PersistenceContext
	private EntityManager manager;

	public static HistoricoContaDao getInstance() {
		return new HistoricoContaDao();
	}

	public LazyEntityDataModel<HistoricoConta> historicoContasLazy(HistoricoConta historicoConta) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		return new LazyEntityDataModel<HistoricoConta>(HistoricoConta.class, null, null, restrictions, null);
	}

	public List<HistoricoConta> historicoContas() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(HistoricoConta.class, null, true, restricions);
	}

	public List<HistoricoConta> historicoPorConta(ContaPagar conta)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();
		if (conta != null) {
			restrictions.add(Restrictions.eq("contaPagar", conta));
		}
		return GenericDao.findAllByAttributesRestrictions(HistoricoConta.class, null, true, restrictions);
	}

	public List<HistoricoConta> historicoContaFiltro(HistoricoConta historico, Loja loja)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();

		List<AliasUece> aliases = new ArrayList<AliasUece>();

		aliases.add(new AliasUece("contaPagar", "c"));
		aliases.add(new AliasUece("c.itemProducao", "i"));
		aliases.add(new AliasUece("i.ordemProducao", "p"));
		
		if (loja != null) {
			restricions.add(Restrictions.eq("p.loja", loja));
		}

		if (historico.getFaccao() != null) {
			restricions.add(Restrictions.eq("faccao", historico.getFaccao()));
		}

		if (historico.getContaPagar() != null) {
			restricions.add(Restrictions.eq("contaPagar", historico.getContaPagar()));
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
		return GenericDao.findAllByAttributesRestrictions(HistoricoConta.class, null, true, restricions,aliases);
	}

	public List<HistoricoPagamentoResumo> pagamentosResumidos(HistoricoConta filter) {
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
					"	CONTROL_HISTORICO_CONTA AS C\r\n" + 
					"		INNER JOIN CONTROL_FACCAO AS FACCAO\r\n" + 
					"			ON FACCAO.id = c.faccao_id\r\n" + 
					"		INNER JOIN CONTROL_CONTA_PAGAR AS CP\r\n" + 
					"			ON C.id_conta_pagar = CP.id\r\n" + 
					" WHERE \r\n" + 
					"	c.data_pagamento between ? and ? and FACCAO.id = ? \r\n" + 
					" GROUP BY FACCAO.nome";
		} else {
			sql = "SELECT \r\n" + 
					"	faccao.nome as FACCAO,\r\n" + 
					"	SUM(C.valor_desconto) AS BONUS,\r\n" + 
					"	SUM(C.valor_juros) AS ACRESCIMO,\r\n" + 
					"	SUM(C.valor_pago) AS PAGO,\r\n" + 
					"	SUM(CP.valor_pagar) AS TOTAL_CONTA \r\n" + 
					" FROM\r\n" + 
					"	CONTROL_HISTORICO_CONTA AS C\r\n" + 
					"		INNER JOIN CONTROL_FACCAO AS FACCAO\r\n" + 
					"			ON FACCAO.id = c.faccao_id\r\n" + 
					"		INNER JOIN CONTROL_CONTA_PAGAR AS CP\r\n" + 
					"			ON C.id_conta_pagar = CP.id\r\n" + 
					" WHERE \r\n" + 
					"	c.data_pagamento between ? and ?\r\n" + 
					" GROUP BY FACCAO.nome";
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
