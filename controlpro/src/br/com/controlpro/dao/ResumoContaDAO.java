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

import br.com.controlpro.entity.consultas.ResumoContaTotal;
import br.com.controlpro.entity.consultas.Vendas;
import br.hibernateutil.core.SuperHibernate;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class ResumoContaDAO {

	private ResumoContaDAO() {
	}

	public static ResumoContaDAO getInstance() {
		return new ResumoContaDAO();
	}

	public List<ResumoContaTotal> resumoCartaoTotal(Vendas filter) {
		Session session = null;
		
		String sql = "select Contas_Bancarias.Nome as 'CONTA', sum(Recebe_Banco_Valor) as 'TOTAL' "
				+ "from Saidas inner join Contas_Bancarias	on Saidas.Recebe_Banco_Conta = "
				+ "Contas_Bancarias.Codigo where Data_Efetiva_Financ between ? and ? "
				+ "and Efetivada_Financ = 1	and Desfeita_Financ <> 1 group by Contas_Bancarias.Nome";
		
		List<ResumoContaTotal> lista = new ArrayList<>();
		
		try {
			session = SuperHibernate.getHibernateInstance().getSession(Session.class);
			SQLQuery query = session.createSQLQuery(sql);
			query.setParameter(0, filter.getDataVendaInicio());
			query.setParameter(1, filter.getDataVendaFim());
			
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			
			@SuppressWarnings("rawtypes")
			List data = query.list();
			for (Object o : data) {
				@SuppressWarnings("rawtypes")
				Map map = (Map) o;
				lista.add(new ResumoContaTotal(
						(String) map.get("CONTA"),
						new BigDecimal(map.get("TOTAL").toString())
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
