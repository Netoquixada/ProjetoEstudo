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

import br.com.controlpro.entity.consultas.MovimentacaoBancaria;
import br.hibernateutil.core.SuperHibernate;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class MovimentacaoBancariaDAO {

	private MovimentacaoBancariaDAO() {
	}

	public static MovimentacaoBancariaDAO getInstance() {
		return new MovimentacaoBancariaDAO();
	}

	public List<MovimentacaoBancaria> movimentacaoBancaria(MovimentacaoBancaria filter) {
		Session session = null;

		String sql = "select Data, Descricao, Doc_Tipo,"
				+ " Debito, Credito, Origem_Tipo, Consolidado, Gerou_Conta from"
				+ " Lanc_Banc where data between ? and ? and Doc_Tipo like '%"+filter.getDocTipo()+"%'"
						+ " and Origem_Tipo like '%"+filter.getOrigem()+"%' "
								+ " and Gerou_Conta like '%"+filter.getGerouConta()+"%'";
		
		List<MovimentacaoBancaria> lista = new ArrayList<>();

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
				lista.add(new MovimentacaoBancaria(
						convertendoBigDecimalParaData(map.get("Data").toString()),
						(String) map.get("Descricao"),
						(String) map.get("Doc_Tipo"),
						(String) map.get("Origem_Tipo"),
						new BigDecimal(map.get("Debito").toString()),
						new BigDecimal(map.get("Credito").toString()),
						(Boolean) map.get("Consolidado"),
						(String) map.get("Gerou_Conta").toString()
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
	
	public Date convertendoBigDecimalParaData(String valor) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setLenient(false); // to prevent things like February 30th
		Date date = format.parse(valor);
		return date;
	}
}
