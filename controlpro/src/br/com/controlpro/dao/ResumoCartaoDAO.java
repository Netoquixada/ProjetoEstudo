package br.com.controlpro.dao;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import br.com.controlpro.entity.consultas.ResumoCartao;
import br.com.controlpro.entity.consultas.ResumoCartaoTotal;
import br.hibernateutil.core.SuperHibernate;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class ResumoCartaoDAO {

	private ResumoCartaoDAO() {
	}

	public static ResumoCartaoDAO getInstance() {
		return new ResumoCartaoDAO();
	}

	public List<ResumoCartao> resumoCartao(ResumoCartao filter) {
		Session session = null;

		String sql = "SELECT \r\n" + 
				"	ac.Nome AS ADMINISTRADORA, \r\n" + 
				"	sr.Bom_Para AS DATA, \r\n" + 
				"	sr.Valor as VALOR, \r\n" + 
				"	sr.Adm_Cartao as ADM \r\n" + 
				" FROM saidas_recebimento AS SR\r\n" + 
				"		JOIN Adm_Cartao AS AC\r\n" + 
				"		ON sr.Adm_Cartao = ac.Codigo\r\n" + 
				" WHERE sr.Bom_Para between ? and ?";
		
		List<ResumoCartao> lista = new ArrayList<>();

		try {
			session = SuperHibernate.getHibernateInstance().getSession(Session.class);
			SQLQuery query = session.createSQLQuery(sql);
			query.setParameter(0, filter.getDataInicioFilter());
			query.setParameter(1, filter.getDataFimFilter());

			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

			@SuppressWarnings("rawtypes")
			List data = query.list();
			for (Object o : data) {
				@SuppressWarnings("rawtypes")
				Map map = (Map) o;
				lista.add(new ResumoCartao(
						(String) map.get("ADMINISTRADORA"),
						new BigDecimal(map.get("VALOR").toString()),
						convertendoBigDecimalParaData(map.get("DATA").toString()),
						new BigDecimal(map.get("ADM").toString()).intValue()
						));
			}

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return lista;
	}
	public List<ResumoCartaoTotal> resumoCartaoTotal(ResumoCartao filter) {
		Session session = null;
		
		String sql = "SELECT \r\n" + 
				"	ac.Nome AS ADMINISTRADORA, \r\n" + 
				"	SUM(sr.Valor) as VALOR\r\n" + 
				" FROM saidas_recebimento AS SR\r\n" + 
				"		JOIN Adm_Cartao AS AC\r\n" + 
				"		ON sr.Adm_Cartao = ac.Codigo\r\n" + 
				" WHERE sr.Bom_Para between ? and ?\r\n" + 
				" GROUP BY ac.Nome ";
		
		List<ResumoCartaoTotal> lista = new ArrayList<>();
		
		try {
			session = SuperHibernate.getHibernateInstance().getSession(Session.class);
			SQLQuery query = session.createSQLQuery(sql);
			query.setParameter(0, filter.getDataInicioFilter());
			query.setParameter(1, filter.getDataFimFilter());
			
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			
			@SuppressWarnings("rawtypes")
			List data = query.list();
			for (Object o : data) {
				@SuppressWarnings("rawtypes")
				Map map = (Map) o;
				lista.add(new ResumoCartaoTotal(
						(String) map.get("ADMINISTRADORA"),
						new BigDecimal(map.get("VALOR").toString())
						));
			}
			
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return lista;
	}
	
	public Date convertendoBigDecimalParaData(String valor) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setLenient(false); // to prevent things like February 30th
		Date date = format.parse(valor);
		return date;
	}
}
