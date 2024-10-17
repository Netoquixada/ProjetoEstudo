package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.consultas.Funcionario;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class FuncionarioDao {

	public static FuncionarioDao getInstance() {
		return new FuncionarioDao();
	}

	public LazyEntityDataModel<Funcionario> funcionariosLazy(Funcionario funcionario) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (funcionario.getNome() != null && !funcionario.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", funcionario.getNome(),
					MatchMode.ANYWHERE));
		}
		return new LazyEntityDataModel<Funcionario>(Funcionario.class, null, null,
				restrictions, null);

	}

	public List<Funcionario> funcionarios(String funcionario)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (funcionario != null && !funcionario.isEmpty()) {
			restrictions.add(Restrictions.like("nome", funcionario,MatchMode.ANYWHERE));
		}
		return GenericDao.findAllByAttributesRestrictions(Funcionario.class,
				"nome", true, restrictions);
	}

	public List<Funcionario> funcionarios()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		return GenericDao.findAllByAttributesRestrictions(Funcionario.class,
				"nome", true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<Funcionario> funcionarioListReport(Funcionario funcionario)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (funcionario.getNome() != null && !funcionario.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", funcionario.getNome(),
					MatchMode.ANYWHERE));

			dadosFiltro.append("NOME: " + funcionario.getNome());
		}
		return GenericDao.findAllByAttributesRestrictions(Funcionario.class,
				"nome", true, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
