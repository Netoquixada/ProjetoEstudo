package br.com.controlpro.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.Resgate;
import br.com.controlpro.entity.consultas.Cliente;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class ResgateDao {

	public static ResgateDao getInstance() {
		return new ResgateDao();
	}

	public LazyEntityDataModel<Resgate> resgateLazy(Resgate resgate) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		return new LazyEntityDataModel<Resgate>(Resgate.class, null, null,
				restrictions, null);
	}
	
	public List<Resgate> resgatePorCliente(Cliente cliente) throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (cliente != null) {
			restrictions.add(Restrictions.eq("cliente", cliente));
		}
		return GenericDao.findAllByAttributesRestrictions(Resgate.class, null, true, restrictions);
	}
	
	
	public BigDecimal totalResgatePorCliente(Integer codigo) {
		BigDecimal result = new BigDecimal(0.0);
		try {
			System.out.println("select sum(valor_resgate) from CONTROL_RESGATE where cliente_Codigo = " + codigo);
			result = GenericDao
					.findEntityNotMappedBySQLQuery(BigDecimal.class,
							Resgate.class,
							"select sum(valor_resgate) from CONTROL_RESGATE where cliente_Codigo = " + codigo);
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
		if (result == null)
			return new BigDecimal(0.0);
		return result;
	}



}
