package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.ControlePedido;
import br.com.controlpro.entity.HistoricoControlePedido;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class HistoricoControlePedidoDao {

	private HistoricoControlePedidoDao() {
	}

	public static HistoricoControlePedidoDao getInstance() {
		return new HistoricoControlePedidoDao();
	}

	public List<HistoricoControlePedido> historicoPorControlePedido(ControlePedido controlePedido)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (controlePedido != null) {
			restrictions.add(Restrictions.eq("controlePedido", controlePedido));
		}

		return GenericDao.findAllByAttributesRestrictions(HistoricoControlePedido.class,
				null, true, restrictions);
	}
}
