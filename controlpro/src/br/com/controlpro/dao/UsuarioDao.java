package br.com.controlpro.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.controlpro.entity.Usuario;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class UsuarioDao {

	public static UsuarioDao getInstance() {
		return new UsuarioDao();
	}

	public LazyEntityDataModel<Usuario> usuariosLazy(Usuario usuario) {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (usuario.getNome() != null && !usuario.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", usuario.getNome(),
					MatchMode.ANYWHERE));
		}
		if (usuario.getPerfilUser() != null) {
			restrictions.add(Restrictions.eq("perfilUser", usuario.getPerfilUser()));
		}
		return new LazyEntityDataModel<Usuario>(Usuario.class, null, null,
				restrictions, null);
	}

	public List<Usuario> usuarios(String usuario)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {

		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (usuario != null && !usuario.isEmpty()) {
			restrictions.add(Restrictions.like("nome", usuario));
		}
		return GenericDao.findAllByAttributesRestrictions(Usuario.class,
				"nome", true, restrictions);
	}

	public List<Usuario> usuarios()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		ArrayList<Criterion> restricions = new ArrayList<Criterion>();
		restricions.add(Restrictions.eq("status", true));
		return GenericDao.findAllByAttributesRestrictions(Usuario.class,
				"nome", true, restricions);
	}

	private StringBuilder dadosFiltro = new StringBuilder();

	public List<Usuario> usuarioListReport(Usuario usuario)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		List<Criterion> restrictions = new ArrayList<Criterion>();

		if (usuario.getNome() != null && !usuario.getNome().isEmpty()) {
			restrictions.add(Restrictions.like("nome", usuario.getNome(),
					MatchMode.ANYWHERE));
			dadosFiltro.append("NOME: " + usuario.getNome());
		}
		
		if (usuario.getPerfilUser() != null) {
			restrictions.add(Restrictions.eq("perfilUser", usuario.getPerfilUser()));
			dadosFiltro.append("PERFIL: " + usuario.getPerfilUser().getDescricao());
		}
		return GenericDao.findAllByAttributesRestrictions(Usuario.class,
				"nome", true, restrictions);
	}

	public StringBuilder getDadosFiltro() {
		return dadosFiltro;
	}

	public void setDadosFiltro(StringBuilder dadosFiltro) {
		this.dadosFiltro = dadosFiltro;
	}

}
