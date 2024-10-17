package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.Imobilizado;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;

public class ImobilizadoDao {

	public static ImobilizadoDao getInstance() {
		return new ImobilizadoDao();
	}

	public List<Imobilizado> imobilizados() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(Imobilizado.class, "dataCadastro", false, restricions);
	}


	public List<Imobilizado> imobilizadoListReport(Imobilizado imobilizado) throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (imobilizado.getTipo() != null) {
			restrictions.add(Restrictions.eq("tipo", imobilizado.getTipo()));
		}
		
		if (imobilizado.getNome() != null && !imobilizado.getNome().isEmpty()) {
			restrictions.add(Restrictions.eq("nome", imobilizado.getNome()));
		}
		
		if (imobilizado.getTombamento() != null && !imobilizado.getTombamento().isEmpty()) {
			restrictions.add(Restrictions.eq("tombamento", imobilizado.getTombamento()));
		}
		
		if (imobilizado.getDataPesquisaInicio() != null && imobilizado.getDataPesquisaFim() != null) {
			restrictions.add(Restrictions.between("dataCadastro", imobilizado.getDataPesquisaInicio(), imobilizado.getDataPesquisaFim()));
		}

		if (imobilizado.getDataPesquisaInicio() != null && imobilizado.getDataPesquisaFim() == null) {
			imobilizado.setDataPesquisaFim(imobilizado.getDataPesquisaInicio());
			restrictions.add(Restrictions.between("dataCadastro", imobilizado.getDataPesquisaInicio(), imobilizado.getDataPesquisaFim()));
		}

		if (imobilizado.getDataPesquisaInicio() == null && imobilizado.getDataPesquisaFim() != null) {
			imobilizado.setDataPesquisaInicio(imobilizado.getDataPesquisaFim());
			restrictions.add(Restrictions.between("dataCadastro", imobilizado.getDataPesquisaInicio(), imobilizado.getDataPesquisaFim()));
		}

		return GenericDao.findAllByAttributesRestrictions(Imobilizado.class, "dataCadastro", false, restrictions);
	}

}
