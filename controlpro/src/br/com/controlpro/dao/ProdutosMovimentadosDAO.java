package br.com.controlpro.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import br.com.controlpro.entity.consultas.ProdutosMovimentados;
import br.hibernateutil.core.SuperHibernate;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class ProdutosMovimentadosDAO {

	private ProdutosMovimentadosDAO() {
	}

	public static ProdutosMovimentadosDAO getInstance() {
		return new ProdutosMovimentadosDAO();
	}

	public List<ProdutosMovimentados> produtosMovimentados(ProdutosMovimentados filter) {
		Session session = null;

		String sql = "SELECT Produto, Produtos.nome as 'Nome', "
				+ " sum(Saida) as 'Vendido', SUM(Valor_Saida) as 'Valor'"
				+ " FROM mov_produtos inner join Produtos on mov_produtos.Produto = Produtos.Codigo"
				+ " WHERE data between ? and ? GROUP BY Produto,"
				+ "	Produtos.nome ORDER BY 'Vendido' desc, 'Nome'";
		
		List<ProdutosMovimentados> lista = new ArrayList<>();

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
				lista.add(new ProdutosMovimentados(
						(String) map.get("Produto"),
						(String) map.get("Nome"),
						new BigDecimal(map.get("Vendido").toString()),
						new BigDecimal(map.get("Valor").toString())
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
