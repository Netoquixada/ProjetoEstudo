package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.constants.GrupoItem;
import br.com.controlpro.entity.EntradaMateriaPrima;
import br.com.controlpro.entity.ItemEntradaMateriaPrima;
import br.com.controlpro.entity.MateriaPrima;
import br.com.controlpro.entity.SubGrupo;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class MateriaPrimaDao {

	public static MateriaPrimaDao getInstance() {
		return new MateriaPrimaDao();
	}

	public LazyEntityDataModel<MateriaPrima> materiaPrimasLazy(MateriaPrima materiaPrima) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (materiaPrima.getNome() != null && !materiaPrima.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", materiaPrima.getNome(), MatchMode.ANYWHERE));
		}

		return new LazyEntityDataModel<MateriaPrima>(MateriaPrima.class, null, null, restrictions, null);
	}

	public List<MateriaPrima> materiaPrimas(String materiaPrima)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (materiaPrima != null && !materiaPrima.isEmpty()) {
			restrictions.add(Restrictions.like("nome", materiaPrima, MatchMode.ANYWHERE));
		}

		return GenericDao.findAllByAttributesRestrictions(MateriaPrima.class, null, true, restrictions);
	}

	public List<MateriaPrima> materiaPrimas() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(MateriaPrima.class, null, true, restricions);
	}

	public List<MateriaPrima> materiaPrimasAviamento() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		restricions.add(Restrictions.eq("grupoItem", GrupoItem.AVIAMENTO));
		return GenericDao.findAllByAttributesRestrictions(MateriaPrima.class, null, true, restricions);
	}

	public List<MateriaPrima> materiaPrimasMalha() throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		restricions.add(Restrictions.eq("grupoItem", GrupoItem.TECIDO));
		return GenericDao.findAllByAttributesRestrictions(MateriaPrima.class, null, true, restricions);
	}

	public List<MateriaPrima> materiaPrimasPorGrupo(GrupoItem grupo, SubGrupo subGrupo)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		if (grupo != null) {
			restricions.add(Restrictions.eq("grupoItem", grupo));
		}
		if (subGrupo != null) {
			restricions.add(Restrictions.eq("subGrupo", subGrupo));
		}
		return GenericDao.findAllByAttributesRestrictions(MateriaPrima.class, null, true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<MateriaPrima> materiaPrimaListReport(MateriaPrima materiaPrima)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (materiaPrima.getStatus() != null) {
			restrictions.add(Restrictions.eq("status", materiaPrima.getStatus()));
		}

		if (materiaPrima.getGrupoItem() != null) {
			restrictions.add(Restrictions.eq("grupoItem", materiaPrima.getGrupoItem()));
		}

		if (materiaPrima.getNome() != null) {
			restrictions.add(Restrictions.like("nome", materiaPrima.getNome(), MatchMode.ANYWHERE));
			dadosFiltro.append("NOME: " + materiaPrima.getNome());
		}

		return GenericDao.findAllByAttributesRestrictions(MateriaPrima.class, null, true, restrictions);
	}

	// public List<ItemEntradaMateriaPrima>
	// itemPorEntradaMateriaPrima(EntradaMateriaPrima entradaMateriaPrima)
	// throws SessaoNaoEncontradaParaEntidadeFornecidaException {
	//
	// List<Criterion> restrictions = new ArrayList<Criterion>();
	//
	// if (entradaMateriaPrima != null) {
	// restrictions.add(Restrictions.eq("entradaMateriaPrima",
	// entradaMateriaPrima));
	// }
	//
	// return
	// GenericDao.findAllByAttributesRestrictions(ItemEntradaMateriaPrima.class,
	// null, true, restrictions);
	// }

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
