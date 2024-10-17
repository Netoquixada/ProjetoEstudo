package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.FuncionarioControlPro;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class FuncionarioControlProDao {

	public static FuncionarioControlProDao getInstance() {
		return new FuncionarioControlProDao();
	}

	public LazyEntityDataModel<FuncionarioControlPro> funcionarioControlProsLazy(FuncionarioControlPro funcionarioControlPro) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (funcionarioControlPro.getNome() != null && !funcionarioControlPro.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", funcionarioControlPro.getNome(),
					MatchMode.ANYWHERE));
		}
		return new LazyEntityDataModel<FuncionarioControlPro>(FuncionarioControlPro.class, null, null,
				restrictions, null);
	}

	public List<FuncionarioControlPro> funcionarioControlPros(String funcionarioControlPro)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();
		restrictions.add(Restrictions.eq("status", true));
		if (funcionarioControlPro != null && !funcionarioControlPro.isEmpty()) {
			restrictions.add(Restrictions.like("nome", funcionarioControlPro, MatchMode.ANYWHERE));
		}
		return GenericDao.findAllByAttributesRestrictions(FuncionarioControlPro.class,
				"nome", true, restrictions);
	}

	public List<FuncionarioControlPro> funcionarioControlPros()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(FuncionarioControlPro.class,
				"nome", true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<FuncionarioControlPro> funcionarioControlProListReport(FuncionarioControlPro funcionarioControlPro)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (funcionarioControlPro.getNome() != null && !funcionarioControlPro.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", funcionarioControlPro.getNome(),
					MatchMode.ANYWHERE));

			dadosFiltro.append("NOME: " + funcionarioControlPro.getNome());
		}
		
		
		return GenericDao.findAllByAttributesRestrictions(FuncionarioControlPro.class,
				"nome", true, restrictions);
	}


}
