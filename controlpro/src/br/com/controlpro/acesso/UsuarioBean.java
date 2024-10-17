package br.com.controlpro.acesso;

import static br.com.controlpro.util.Mensagens.addErrorMessage;
import static br.com.controlpro.util.Mensagens.addInfoMessage;
import static br.com.controlpro.util.Mensagens.addWarnMessage;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.controlpro.bo.UsuarioBO;
import br.com.controlpro.constants.Loja;
import br.com.controlpro.constants.PerfilUser;
import br.com.controlpro.entity.Usuario;
import br.com.controlpro.exception.ObjetoExistenteException;
import br.com.controlpro.exception.SenhaAtualIncorretaException;
import br.com.controlpro.util.AfterRedirect;
import br.com.controlpro.util.CriptografiaMD5;
import br.com.controlpro.util.ManagedBeanHelper;
import br.hibernateutil.exception.ObjetoNaoEncontradoHibernateException;
import br.hibernateutil.exception.SessaoNaoEncontradaParaEntidadeFornecidaException;
import br.hibernateutil.exception.ValidacaoHibernateException;
import br.hibernateutil.exception.ViolacaoChaveHibernateException;
import br.hibernateutil.paginacao.LazyEntityDataModel;

@ManagedBean
@RequestScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 8197346996386546555L;
	private final String CAD_EDIT = "/private/cadastro/cadastrarUsuario.xhtml";
	private final String LIST = "/private/lista/listarUsuarios.xhtml?faces-redirect=true";
	private final String INDEX = "/private/index.xhtml?faces-redirect=true";
	private final String MUDAR_SENHA = "/private/mudarSenha.xhtml?faces-redirect=true";

	private Usuario usuario;
	private Usuario usuarioFilter;
	private Usuario usuarioLogado;
	private LazyEntityDataModel<Usuario> lazy;
	private List<Usuario> usuarios;
	private String senhaAtual;

	@PostConstruct
	public void init() {
		usuario = new Usuario();
		usuarioFilter = new Usuario();
		lazy = new LazyEntityDataModel<Usuario>(Usuario.class);
		usuarios = new ArrayList<Usuario>();
		usuarioLogado = ManagedBeanHelper.recuperaBean("escopoSessaoBean", EscopoSessaoBean.class).getUsuarioLogado();
	}

	public String save() {
		try {
			usuario.setSenha(CriptografiaMD5.cifrar(usuario.getSenha()));
			UsuarioBO.getInstance().save(usuario);
			addInfoMessage("Cadastrado com sucesso!", "Usuario " + usuario.getNome());
		} catch (ViolacaoChaveHibernateException e) {
			addErrorMessage("Formulario", "erro.salvar.entidade.campo.existente");
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		}
		usuario = new Usuario();
		AfterRedirect.manterMensagem();
		return CAD_EDIT;
	}

	public String update() {
		try {
			if (usuario.getPrimeiroAcesso() == true) {
				usuario.setPrimeiroAcesso(false);
			}
			if (usuario.getAtualizarSenha()) {
				usuario.setSenha(CriptografiaMD5.cifrar(usuario.getSenha()));
			}

			UsuarioBO.getInstance().update(usuario);
			usuarioLogado.setNome(usuario.getNome());
			addInfoMessage("Editado com sucesso!", "Usuario " + usuario.getNome());
		} catch (ViolacaoChaveHibernateException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		}
		usuario = new Usuario();
		AfterRedirect.manterMensagem();
		return LIST;
	}

	public String updateSenha() {
		try {
			usuario.setPrimeiroAcesso(false);
			String senha = CriptografiaMD5.cifrar(senhaAtual);
			String novaSenha = CriptografiaMD5.cifrar(usuario.getSenha());
			UsuarioBO.getInstance().updateSenha(usuario, senha, novaSenha);
			usuarioLogado = usuario;
			addInfoMessage("Senha Alterada com sucesso!", "Usuario " + usuario.getNome());
			return INDEX;
		} catch (ViolacaoChaveHibernateException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		} catch (SenhaAtualIncorretaException e) {
			addWarnMessage("Erro: " + e.getMessage(), "");
			e.printStackTrace();
		}
		usuario = new Usuario();
		return MUDAR_SENHA;
	}

	public PerfilUser[] getPerfis() {
		return PerfilUser.values();
	}

	public Loja[] getLojas() {
		return Loja.values();
	}

	public String list() {

		lazy = UsuarioBO.getInstance().usuariosLazy(usuarioFilter);

		return null;
	}

	public String goToList() {
		return LIST;
	}

	public String prepareUpdate() {
		return CAD_EDIT;
	}

	public String prepareSave() {
		usuario = new Usuario();
		return CAD_EDIT;
	}

	public String updateStatus() {
		try {

			if (usuario.getStatus()) {
				usuario.setStatus(false);
				UsuarioBO.getInstance().update(usuario);
			} else {
				usuario.setStatus(true);
				UsuarioBO.getInstance().update(usuario);
			}

		} catch (ViolacaoChaveHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ObjetoExistenteException e) {
			addWarnMessage("Erro!", "Erro: " + e.getMessage());
			e.printStackTrace();
		}

		String status = usuario.getStatus() ? "Ativo" : "Inativo";

		addInfoMessage("Status alterado com sucesso!", "O usuario est� " + status);

		return null;
	}

	public String updateUserLogado() {

		try {
			UsuarioBO.getInstance().update(usuario);

			addInfoMessage("Editado com sucesso!", "Usu�rio " + usuario.getNome());

			usuario = new Usuario();
		} catch (ViolacaoChaveHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ObjetoNaoEncontradoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (ValidacaoHibernateException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (SessaoNaoEncontradaParaEntidadeFornecidaException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		} catch (javax.validation.ConstraintViolationException e) {
			addErrorMessage("Erro!", e.getMessage());
		} catch (ObjetoExistenteException e) {
			addErrorMessage("Erro!", e.getMessage());
			e.printStackTrace();
		}

		return "/private/index.xhtml";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioFilter() {
		return usuarioFilter;
	}

	public void setUsuarioFilter(Usuario usuarioFilter) {
		this.usuarioFilter = usuarioFilter;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public LazyEntityDataModel<Usuario> getLazy() {
		return lazy;
	}

	public void setLazy(LazyEntityDataModel<Usuario> lazy) {
		this.lazy = lazy;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}
}
