package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.consultas.Cidade;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.core.SuperHibernate;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class CidadeDao {

	public static CidadeDao getInstance() {
		return new CidadeDao();
	}

	public List<Cidade> cidades(String cidade)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();
		if (cidade != null && !cidade.isEmpty()) {
			restrictions.add(Restrictions.like("cidadeNome", cidade));
		}
		return GenericDao.findAllByAttributesRestrictions(Cidade.class,
				"cidadeNome", true, restrictions);
	}

	public List<Cidade> cidadesPorEstado(String uf)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();
		if (uf != null) {
			restrictions.add(Restrictions.like("estado", uf));
		}
		return GenericDao.findAllByAttributesRestrictions(Cidade.class,
				"cidadeNome", true, restrictions);
	}
	public List<Cidade> cidadesDoCeara()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();
			restrictions.add(Restrictions.like("estado", "CE"));
		return GenericDao.findAllByAttributesRestrictions(Cidade.class,
				"cidadeNome", true, restrictions);
	}

	public List<Cidade> cidades()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		return GenericDao.findAllByAttributesRestrictions(Cidade.class,
				"cidadeNome", true, restricions);
	}
	
	public List<String> cidadesVenda() {
		Session session = null;

		String sql = "select distinct Cidade from CLi_For where len(Cidade) > 0";
		
		List<String> lista = new ArrayList<>();

		try {
			session = SuperHibernate.getHibernateInstance().getSession(Session.class);
			SQLQuery query = session.createSQLQuery(sql);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

			@SuppressWarnings("rawtypes")
			List data = query.list();
			for (Object o : data) {
				@SuppressWarnings("rawtypes")
				Map map = (Map) o;
				lista.add((String) map.get("Cidade"));
			}

		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return lista;
	}

}
