package br.com.controlpro.bo;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import br.com.controlpro.constants.PerfilUser;
import br.com.controlpro.dao.UsuarioDao;
import br.com.controlpro.entity.Usuario;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.exception.SenhaAtualIncorretaException;
import br.com.controlpro.util.CriptografiaMD5;
import br.hibernateutil.core.GenericDao;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

public class UsuarioBO {

	public static UsuarioBO getInstance() {
			return new UsuarioBO();
	}

	public void save(Usuario Usuario) throws ViolacaoChaveHibernateException,
			ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		validation(Usuario);
		GenericDao.save(Usuario);
	}

	public void update(Usuario Usuario) throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException {
		GenericDao.update(Usuario);
	}

	public void updateSenha(Usuario usuario, String senhaAtual, String novaSenha)
			throws ViolacaoChaveHibernateException,
			ObjetoNaoEncontradoHibernateException, ValidacaoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException,
			ObjetoExistenteException, SenhaAtualIncorretaException,
			NoSuchAlgorithmException, UnsupportedEncodingException {
		Usuario u = GenericDao.findById(Usuario.class, usuario.getId());
		if (senhaAtual.equals(u.getSenha())) {
			usuario.setSenha(novaSenha);
			GenericDao.update(usuario);
		} else
			throw new SenhaAtualIncorretaException("Senha atual incorreta!");
	}

	public Usuario find(Integer id)
			throws ObjetoNaoEncontradoHibernateException,
			SessaoNaoEncontradaParaEntidadeFornecidaException {
		return GenericDao.findById(Usuario.class, id);
	}

	public LazyEntityDataModel<Usuario> usuariosLazy(Usuario Usuario) {
		return UsuarioDao.getInstance().usuariosLazy(Usuario);
	}

	public List<Usuario> usuariosComplete(String UsuarioName)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return UsuarioDao.getInstance().usuarios(UsuarioName);
	}

	public List<Usuario> list()
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return UsuarioDao.getInstance().usuarios();
	}

	public StringBuilder dadosFiltro() {
		return UsuarioDao.getInstance().getDadosFiltro();
	}

	public List<Usuario> usuarioListReport(Usuario usuario)
			throws SessaoNaoEncontradaParaEntidadeFornecidaException {
		return UsuarioDao.getInstance().usuarioListReport(usuario);
	}

	public void validation(Usuario usuario) {

	}

	public void criarPrimeiroUsuario() {
		try {

			Usuario user = new Usuario();

			user.setNome("Usuário administrador");
			user.setEmail("");
			user.setLogin("admin");
			user.setSenha(CriptografiaMD5.cifrar("admin"));
			user.setCpf("");
			user.setCelular("");
			user.setTelefone("");
			user.setPrimeiroAcesso(true);
			user.setPerfilUser(PerfilUser.ADMINISTRADOR);
			
			GenericDao.save(user);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ViolacaoChaveHibernateException e) {
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			e.printStackTrace();
		}
	}

}